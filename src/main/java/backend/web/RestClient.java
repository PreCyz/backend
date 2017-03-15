package backend.web;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * @author pgawedzki
 * 
 * Klasa umozliwia requesty restowe pod jakis URL. Wywoluje sie ja tak jak wzorzec Builder.
 * Aby wyslac pliki nalezy uzyc metody attach(...) a nastepnie ktorejs z metod response*(). 
 * Aby przeslac dane json'em nalezy uzyc metody post() lub invoke(...).
 * Wyslanie requesta realizuje metoda private invoke. Odpowiedz serwera zwracana jest jako String lub byte[].
 * Aby dobrac sie do odpowiedzi mozna wywolac jedna z metod: get(), post(...), invoke(...), responseAsString(), 
 * responseAsByteArray
 * Przykladowe wywolania: 1- wyslanie danych json'em, 2- przeslanie plikow 
 * 1) WebClient client = new WebClient.Builder(URL).create(); 
 *    String responseJson = client.post(requestJson);
 * 2) WebClient client = new WebClient.Builder(URL)
		    .encoding("UTF-8")
		    .cookie("TestCookie", "MyCookieTest")
		    .attach(json, MediaType.APPLICATION_JSON, "root")
		    .attach(pdf, MediaType.APPLICATION_OCTET_STREAM, "pdf.pdf")
		    .attach(doc, MediaType.APPLICATION_OCTET_STREAM, "doc.docx")
		    .attach(byteArray, MediaType.APPLICATION_OCTET_STREAM, "xml.xml")
		    .create();
	    byte[] is = client.responseAsByteArray());
 */
public class RestClient {
	
	private static final String UTF8 = "UTF-8";
	private static final String COOKIE_KEY = "Set-Cookie";

	private final HttpURLConnection httpConn;
	private String cookies;
	private String responseString;
	private int responseCode;
	private byte[] responseByteArray;

	public String getCookies() {
		return cookies;
	}

	public int getResponseCode() {
		return responseCode;
	}
	
	private void invoke() throws IOException, UnsupportedEncodingException {
		// checks server's status code first
		responseCode = httpConn.getResponseCode();
		if (isValidResponseCode()) {
			
			responseByteArray = inputStreamToByteArray(httpConn.getInputStream());
			
			cookies = extractCookiesFromHeader(); 
			
			responseString = new String(responseByteArray, UTF8);
			
			httpConn.disconnect();
		} else {
			throw new IOException("Server returned non-OK status: " + responseCode);
		}
	}
	
	private boolean isValidResponseCode() {
		return responseCode == HttpURLConnection.HTTP_OK || 
				responseCode == HttpURLConnection.HTTP_CREATED || 
				responseCode == HttpURLConnection.HTTP_NO_CONTENT || 
				responseCode == HttpURLConnection.HTTP_ACCEPTED;
	}
	
	private byte[] inputStreamToByteArray(InputStream is) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();
		while(reads != -1){ 
			baos.write(reads); 
			reads = is.read(); 
		} 
		return baos.toByteArray();
	}
	
	private String extractCookiesFromHeader() {
		StringBuilder builder = new StringBuilder(COOKIE_KEY);
		builder.append("=[");
		for (int i = 0;; i++) {
			String headerName = httpConn.getHeaderFieldKey(i);
			String headerValue = httpConn.getHeaderField(i);
			if (headerName == null && headerValue == null) {
				break;
			}
			if (COOKIE_KEY.equals(headerName)) {
				builder.append(headerValue).append(", ");
			}
		}
		if(builder.lastIndexOf(",") > 0){
			builder.replace(builder.lastIndexOf(","), builder.length(), "]");
		} else {
			builder.append("]");
		}
		return builder.toString();
	}
	
	public String get() throws ProtocolException, IOException {
		this.httpConn.setRequestMethod(RestMethod.GET);
		invoke();
		return responseString;
	}
	
	public String post(String json) throws ProtocolException, IOException {
		return invoke(RestMethod.POST, json);
	}

	public String invoke(String requestMethod, String json) throws ProtocolException, IOException {
		this.httpConn.setRequestMethod(requestMethod);
		this.httpConn.setRequestProperty("Content-Type", "application/json");
		this.httpConn.setRequestProperty("charset", UTF8);
		this.httpConn.setRequestProperty("Accept", "application/json");
		if(json != null && json.length() > 0){
			processRequestJson(json);
		}
		invoke();
		return responseString;
	}
	
	public String responseAsString() throws IOException {
		if (responseString != null) {
			return responseString;
		}
		invoke();
		return responseString;
	}

	public byte[] responseAsByteArray() throws IOException {
		if(responseByteArray == null) {
			invoke();
		}
		return responseByteArray;
	}

	private RestClient(Builder builder) throws MalformedURLException, IOException {
		this.httpConn = builder.httpConn;
	}

	private void processRequestJson(String json) throws IOException {
		if (json != null && json.length() > 0) {
			DataOutputStream wr = new DataOutputStream(httpConn.getOutputStream());
			wr.writeBytes(json);
			wr.flush();
			wr.close();
		}
	}

	public static class Builder {
		
		private static final String LINE_FEED = String.format("\r%n");
		private static final String DEFAULT_METHOD = RestMethod.POST;

		private HttpURLConnection httpConn;
		private final URL requestUrl;
		private String boundary;
		private String charset;
		private OutputStream outputStream;
		private PrintWriter writer;

		public Builder(String requestUrl) throws MalformedURLException, IOException {
			this.requestUrl = new URL(requestUrl);
			httpConn = (HttpURLConnection) this.requestUrl.openConnection();
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setUseCaches(false);
			httpConn.setInstanceFollowRedirects(false);
			httpConn.setRequestMethod(DEFAULT_METHOD);
		}

		public Builder cookie(String name, String value) {
			String cookie = String.format("%s=%s", name, value);
			httpConn.addRequestProperty(COOKIE_KEY, cookie);
			return this;
		}

		public Builder method(String requestMethod) throws ProtocolException {
			httpConn.setRequestMethod(requestMethod);
			return this;
		}

		public Builder encoding(String charset) {
			this.charset = charset;
			httpConn.setRequestProperty("charset", charset);
			return this;
		}

		public Builder contentType(String contentType) {
			httpConn.setRequestProperty("Content-Type", contentType);
			return this;
		}

		public Builder accept(String acceptValue) {
			httpConn.setRequestProperty("Accept", acceptValue);
			return this;
		}

		public Builder attach(InputStream attachment, String contentType, String contentId) throws IOException {
			prepareAttachmentContent(contentType, contentId);
			addAttachmentToOutputStream(attachment);
			return this;
		}

		private void prepareAttachmentContent(String contentType, String contentId) throws IOException {
			boundary(contentType);
			createOutputStreamAndWriter();

			writer.append("--" + boundary).append(LINE_FEED);

			String content = String.format("Content-Type: %s", contentType);
			//jak będzie potrzebne kodowanie każdego z załączników to można odkompenotwać
			/*if (charset != null) {
				content = String.format("%s; charset=%s", content, charset);
			}*/
			writer.append(content).append(LINE_FEED);
			writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
			content = String.format("Content-ID: <%s>", contentId);
			writer.append(content).append(LINE_FEED);
			content = String.format("Content-Disposition: form-data; filename=\"%s\"", contentId);
			writer.append(content).append(LINE_FEED);

			writer.append(LINE_FEED);
			writer.flush();
		}

		private void boundary(String contentType) {
			if (boundary == null) {
				boundary = String.format("*****%d*****", System.currentTimeMillis());
				String content = String.format("multipart/related; type=\"%s\"; boundary=\"%s\"", contentType, 
						boundary);
				httpConn.setRequestProperty("Content-Type", content);
			}
		}
		
		private void createOutputStreamAndWriter() throws IOException {
			if (outputStream == null) {
				outputStream = httpConn.getOutputStream();
				if (charset == null) {
					writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
				} else {
					writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
				}
			}
		}
		
		private void addAttachmentToOutputStream(InputStream attachment) throws IOException {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = attachment.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			attachment.close();
			writer.append(LINE_FEED).append(LINE_FEED);
			writer.flush();
		}
		
		public Builder attach(byte[] attachment, String contentType, String contentId) throws IOException {
			prepareAttachmentContent(contentType, contentId);
			addAttachmentToOutputStream(attachment);
			return this;
		}

		private void addAttachmentToOutputStream(byte[] attachment) throws IOException {
			outputStream.write(attachment);
			outputStream.flush();
			writer.append(LINE_FEED).append(LINE_FEED);
			writer.flush();
		}

		protected Builder addFormField(String name, String value, String contentType) throws IOException {
			boundary(contentType);
			createOutputStreamAndWriter();
			writer.append("--" + boundary).append(LINE_FEED);
			String content = String.format("Content-Disposition: form-data; fileType=\"%s\"", name);
			writer.append(content).append(LINE_FEED);
			content = String.format("Content-Type: %s; charset=%s", contentType, charset);
			writer.append(content).append(LINE_FEED);
			writer.append(LINE_FEED);
			writer.append(value).append(LINE_FEED);
			writer.flush();
			return this;
		}

		public Builder addHeaderField(String name, String value) throws IOException {
			createOutputStreamAndWriter();
			String content = String.format("%s: %s", name, value);
			writer.append(content).append(LINE_FEED);
			writer.flush();
			return this;
		}
		
		public Builder addProperty(String key, String value){
		    httpConn.addRequestProperty(key, value);
		    return this;
		}

		public RestClient create() throws IOException {
			if (writer != null) {
				writer.append(LINE_FEED).flush();
				String content = String.format("--%s--", boundary);
				writer.append(content).append(LINE_FEED);
				writer.close();
			}
			return new RestClient(this);
		}
	}
}
