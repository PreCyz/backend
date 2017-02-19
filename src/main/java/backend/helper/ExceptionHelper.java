package backend.helper;

import backend.exception.DAOException;

public class ExceptionHelper {

	public static boolean checkIfNotLog(Exception exception) {
		if (exception == null) {
			return false;
		}
		while (exception != null) {
			if (exception instanceof DAOException) {
				DAOException daoException = (DAOException) exception;
				if (daoException.isNotLog()) {
					return daoException.isNotLog();
				}
			}
			if (exception.getCause() instanceof Exception) {
				exception = (Exception) exception.getCause();
			} else {
				exception = null;
			}
		}

		return false;
	}

}
