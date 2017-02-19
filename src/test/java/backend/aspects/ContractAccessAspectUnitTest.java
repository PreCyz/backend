package backend.aspects;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.annotation.Annotation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import backend.AbstractUnitTest;
import backend.annotation.SecurityGuard;
import backend.aspect.ContractAccessAspect;
import backend.exception.DAOException;

public class ContractAccessAspectUnitTest extends AbstractUnitTest {

	@Autowired
	private ContractAccessAspect aspect;
	
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
		try {
			aspect.checkContractAccess(accessToContractRequired, contractNumber);
			fail("Should be DAOException");
		} catch (DAOException ex) {
			assertThat(ex, is( notNullValue() ));
			assertThat(ex.getMessage(), is( equalTo("User [clienttest] does not have access to contract [wrongContractNumber].") ));
			assertThat(ex.getMessageKey(), is( equalTo("backend.aspect.noAccessToContract") ));
		}
	}

}
