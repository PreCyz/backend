package backend.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import backend.exception.ApplicationUncheckedException;
import backend.interceptor.ErrorDetails;

public class DAOExceptionMapper implements ExceptionMapper<ApplicationUncheckedException> {

	@Override
	public Response toResponse(ApplicationUncheckedException exception) {
		int statusCode = Status.PARTIAL_CONTENT.getStatusCode();
		ErrorDetails details = new ErrorDetails(statusCode, exception.getMessage());
		return Response.status(statusCode).entity(details).build();
	}

}
