package backend.web;

import java.io.Serializable;
import java.util.Properties;

import backend.dao.RestRequester;

public class RestConfigBean implements Serializable{
	private static final long serialVersionUID = -7730956263651546347L;
	
	private String serverUrl;
	private String restTemplateUri;
	private RestRequester.Method restMethod = RestRequester.DEFAULT_METHOD;
	private String requestJson;
	private Properties requestProperties;
	
	public String getRestMethod() {
		return restMethod.name();
	}
	public void setRestMethod(RestRequester.Method restMethod) {
		this.restMethod = restMethod;
	}
	public String getRequestJson() {
		return requestJson;
	}
	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}
	public Properties getRequestProperties() {
		return requestProperties;
	}
	public void setRequestProperties(Properties requestProperties) {
		this.requestProperties = requestProperties;
	}
	
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public void setRestTemplateUri(String restTemplateUri) {
		this.restTemplateUri = restTemplateUri;
	}
	public String getRestUrl() {
		return serverUrl + restTemplateUri;
	}
}
