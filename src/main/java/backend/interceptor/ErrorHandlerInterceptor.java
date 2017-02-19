package backend.interceptor;

import javax.ws.rs.core.Response;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class ErrorHandlerInterceptor extends AbstractPhaseInterceptor<Message> {

	public ErrorHandlerInterceptor() {
		super(Phase.POST_LOGICAL);
	}

	public ErrorHandlerInterceptor(String phase) {
		super(phase);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		/*
		Exception ex = message.getContent(Exception.class);
		
		ErrorDetails details = new ErrorDetails(Response.Status.BAD_REQUEST.getStatusCode(), ex.getMessage());

		Response response = Response.status(Response.Status.BAD_REQUEST).entity(details).build();
		
		message.getExchange().put(Response.class, response);*/
	}

}
