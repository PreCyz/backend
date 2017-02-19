package backend.helper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import backend.exception.DAOException;
import backend.helper.ExceptionHelper;

public class ExceptionHelperUnitTest {

	@Test
	public void givenNullExceptionWhenCheckIfNotLogReturnFalse() {
		boolean result = ExceptionHelper.checkIfNotLog(null);
		assertTrue(!result);
	}
	
	@Test
	public void givenExceptionWhenCheckIfNotLogReturnFalse() {
		Exception exception = new Exception();
		
		boolean result = ExceptionHelper.checkIfNotLog(exception);
		
		assertTrue(!result);
	}
	
	@Test
	public void givenNestedExceptionsWhenCheckIfNotLogReturnFalse() {
		Exception exception = new Exception(new IllegalArgumentException());
		
		boolean result = ExceptionHelper.checkIfNotLog(exception);
		
		assertTrue(!result);
	}
	
	@Test
	public void givenNestedExceptionsWithDAOExceptionWhenCheckIfNotLogReturnFalse() {
		Exception exception = new Exception(new DAOException("", new IllegalArgumentException()));
		
		boolean result = ExceptionHelper.checkIfNotLog(exception);
		
		assertTrue(!result);
	}
	
	@Test
	public void givenNestedExceptionsWithDAOExceptionWhenCheckIfNotLogReturnTrue() {
		Exception exception = new Exception(new DAOException("", new IllegalArgumentException()).setNotLog());
		boolean result = ExceptionHelper.checkIfNotLog(exception);
		assertTrue(result);
		
		exception = new Exception(new IllegalArgumentException(new DAOException("").setNotLog()));
		result = ExceptionHelper.checkIfNotLog(exception);
		assertTrue(result);
	}
}
