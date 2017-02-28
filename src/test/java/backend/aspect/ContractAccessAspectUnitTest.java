package backend.aspect;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doThrow;

import java.lang.annotation.Annotation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import backend.AbstractUnitTest;
import backend.annotation.SecurityGuard;
import backend.aspect.ContractAccessAspect;
import backend.exception.ApplicationUncheckedException;

public class ContractAccessAspectUnitTest extends AbstractUnitTest {

	@Autowired
	private ContractAccessAspect aspect;
	
	@Mock
	private ContractAccessAspect mockAspect;
	
	private SecurityGuard accessToContractRequired;
	private SecurityGuard accessToContractNotRequired;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		accessToContractRequired = new SecurityGuard() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return SecurityGuard.class;
			}
			@Override
			public boolean activeSessionRequired() {
				return true;
			}
			@Override
			public boolean accessToContractRequired() {
				return true;
			}
			
		};
		accessToContractNotRequired = new SecurityGuard() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return SecurityGuard.class;
			}
			@Override
			public boolean activeSessionRequired() {
				return false;
			}
			@Override
			public boolean accessToContractRequired() {
				return false;
			}
		};
		
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		accessToContractRequired = null;
		accessToContractNotRequired = null;
		aspect = null;
	}
	
	@Test
	public void givenNoContractAndAccessToContractNotRequiredWhencheckContractAccessThenDoNothing() {
		aspect.checkContractAccess(accessToContractNotRequired, null);
		aspect.checkContractAccess(accessToContractNotRequired, "");
	}
	
	@Test
	public void givenNoContractAndAccessToContractRequiredWhencheckContractAccessThenDoNothing() {
		aspect.checkContractAccess(accessToContractRequired, null);
		aspect.checkContractAccess(accessToContractRequired, "");
	}
	
	@Test
	public void givenContractNumberAndAccessToContractNotRequiredWhencheckContractAccessThenDoNothing() {
		aspect.checkContractAccess(accessToContractNotRequired, CONTRACT_NUMBER);
	}
	
	@Test
	public void givenProperContractAndAccessToContractRequiredWhencheckContractAccessThenDoNothing() {
		aspect.checkContractAccess(accessToContractRequired, CONTRACT_NUMBER);
	}
	
	@Test
	public void givenWrongContractAndAccessToContractRequiredWhencheckContractAccessThenThrowDAOException() {
		String contractNumber = "wrongContractNumber";
		String errorMsg = "User [clienttest] does not have access to contract [wrongContractNumber].";
		String bundleKey = "backend.aspect.noAccessToContract";
		doThrow(new ApplicationUncheckedException(errorMsg, bundleKey))
			.when(mockAspect)
			.checkContractAccess(accessToContractRequired, contractNumber);
		try {
			mockAspect.checkContractAccess(accessToContractRequired, contractNumber);
			fail("Should be DAOException");
		} catch (ApplicationUncheckedException ex) {
			assertThat(ex, is( notNullValue() ));
			assertThat(ex.getMessage(), is( equalTo(errorMsg) ));
			assertThat(ex.getMessageKey(), is( equalTo(bundleKey) ));
		}
	}

}
