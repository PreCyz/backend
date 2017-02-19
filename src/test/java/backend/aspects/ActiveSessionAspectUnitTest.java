package backend.aspects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import backend.AbstractUnitTest;
import backend.annotation.SecurityGuard;
import backend.aspect.ActiveSessionAspect;
import backend.exception.DAOException;

public class ActiveSessionAspectUnitTest extends AbstractUnitTest {
	
	@Autowired
	private ActiveSessionAspect aspect;
	
	private SecurityGuard activeSessionRequired;
	private SecurityGuard activeSessionNotRequired;

	@Before
	public void setUp() throws Exception {
		activeSessionRequired = new SecurityGuard() {
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
				return false;
			}
			
		};
		activeSessionNotRequired = new SecurityGuard() {
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
		activeSessionRequired = null;
		activeSessionNotRequired = null;
		aspect = null;
	}

	@Test
	public void givenActiveSessionWithRequiredAndNoUserInSessionWhenCheckActiveSessionThenThrowDAOException() {
		sessionService.removeFromSession(sessionService.getSessionId());
		try {
			aspect.checkActiveSession(activeSessionRequired);
			fail("Should be DAOException");
		} catch (DAOException ex) {
			assertThat(ex, is( notNullValue() ));
			assertThat(ex.getMessage(), is( equalTo("User does not have actual session.") ));
			assertThat(ex.getMessageKey(), is( equalTo("backend.session.service.invalid.user") ));
		}
	}
	
	@Test
	public void givenActiveSessionWithRequiredAndNullUserInSessionWhenCheckActiveSessionThenThrowDAOException() {
		sessionService.addToSession(sessionService.getSessionId(), null);
		try {
			aspect.checkActiveSession(activeSessionRequired);
			fail("Should be DAOException");
		} catch (DAOException ex) {
			assertThat(ex, is( notNullValue() ));
			assertThat(ex.getMessage(), is( equalTo("User does not have actual session.") ));
			assertThat(ex.getMessageKey(), is( equalTo("backend.session.service.invalid.user") ));
		}
	}
	
	@Test
	public void givenNotRequiredActiveSessionWhenCheckActiveSessionThenDoNothing() {
		aspect.checkActiveSession(activeSessionNotRequired);
		assertTrue(true);
	}
	
	@Test(expected=NullPointerException.class)
	public void givenNullActiveSessionWhenCheckActiveSessionThenDoNothing() {
		aspect.checkActiveSession(null);
		fail("should be NullPointerException");
	}
}
