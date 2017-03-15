package backend.web;

import java.io.IOException;

public interface RestRequester {
	
	void executeRequest() throws IOException;
	int getResponseCode() throws IOException ;
	String getResponseJson();
	String getResponseSessionId();
	
}
