package backend.web;

import java.io.IOException;

public interface RestRequester {
	enum Method {GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE}
	Method DEFAULT_METHOD = Method.GET;
	
	void executeRequest() throws IOException;
	int getResponseCode() throws IOException ;
	String getResponseJson();
	String getResponseSessionId();
	
}
