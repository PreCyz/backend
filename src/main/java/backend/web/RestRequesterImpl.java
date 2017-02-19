package backend.web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import backend.dao.RestRequester;

public class RestRequesterImpl implements RestRequester{
	
	private final int RESPONSE_FATAL_ERROR_CODE = -1;
	
	private HttpURLConnection connection;
	private final RestConfigBean restConfigBean;
	private String responseJson;
	
	public RestRequesterImpl(RestConfigBean restConfigBean) {
		this.restConfigBean = restConfigBean;
	}

	public String getResponseJson() {
		return responseJson;
	}

	public int getResponseCode() throws IOException {
		if(connection != null){
			return connection.getResponseCode();
		}
		return RESPONSE_FATAL_ERROR_CODE;
	}
	
	public void executeRequest() throws IOException {
		openDefaultConnection();
		
		changeRequestMethod();
		changeRequestProperties();
		
		processRequestJson();
		
		responseJson = getServerResponse();
	}
	
	private void openDefaultConnection() throws IOException, MalformedURLException {
		connection = (HttpURLConnection) new URL(restConfigBean.getRestUrl()).openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod(DEFAULT_METHOD.name());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Accept", "application/json");
	}
	
	private void changeRequestMethod() throws ProtocolException {
		if(restConfigBean.getRestMethod() != null){
			connection.setRequestMethod(restConfigBean.getRestMethod());
		}
	}
	
	private void changeRequestProperties() {
		Properties properties = restConfigBean.getRequestProperties();
		if(properties != null){
			connection.getRequestProperties().clear();
			for(String propertyName : properties.stringPropertyNames()){
				connection.setRequestProperty(propertyName, properties.getProperty(propertyName));
			}
		}
	}
	
	private void processRequestJson() throws IOException{
		if(restConfigBean.getRequestJson() != null){
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(restConfigBean.getRequestJson());
			wr.flush();
			wr.close();
		}
	}

	private String getServerResponse() throws IOException {
		responseJson = IOUtils.toString(connection.getInputStream(), "UTF-8");
		return responseJson;
	}
}
