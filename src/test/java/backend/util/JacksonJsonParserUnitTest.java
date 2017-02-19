package backend.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonParseException;

import backend.util.JacksonUtilJson;

public class JacksonJsonParserUnitTest {
	private Bean bean;

	@Before
	public void setUp() throws Exception {
		bean = new Bean("SomeKey", false, 0, new Date());
	}

	@After
	public void tearDown() throws Exception {
		bean = null;
	}
	
	@Test
	public void givenBeanWhenMarshalToStringThenReturnProperString() throws JsonParseException, IOException {
		String json = JacksonUtilJson.marshalToString(bean);
		assertNotNull(json);
		assertTrue(json.contains("key"));
		assertTrue(json.contains("SomeKey"));
		assertTrue(json.contains("redirect"));
		assertTrue(json.contains("false"));
	}
	
	@Test
	public void givenBeanWhenMarshalThenSetOutputStream() {
		OutputStream out = new ByteArrayOutputStream();
		JacksonUtilJson.marshal(bean, out);
		assertNotNull(out);
	}
	
	@Test
	public void givenInputStreamWhenUnmarshalThenReturnProperBean() throws JsonParseException, IOException {
		String json = "{\"key\" : \"SomeKey\",\"redirect\" : false}";
		InputStream is = IOUtils.toInputStream(json);
		Bean resultBean = JacksonUtilJson.unmarshal(is, Bean.class, JacksonUtilJson.DeserializationFeatures.NO_UNWRAP_ROOT);
		assertEquals(bean.getKey(), resultBean.getKey());
		assertEquals(bean.isRedirect(), resultBean.isRedirect());
	}
	
	@Test
	public void givenBeanWhenUnmarshalFromStringThenReturnProperBean() {
		String json = "{\"key\" : \"SomeKey\",\"redirect\" : false}";
		Bean resultBean = (Bean) JacksonUtilJson.unmarshalFromString(json, Bean.class, JacksonUtilJson.DeserializationFeatures.NO_UNWRAP_ROOT);
		assertEquals(bean.getKey(), resultBean.getKey());
		assertEquals(bean.isRedirect(), resultBean.isRedirect());
	}
	
	@Test(expected = Exception.class)
	public void givenNullTypeWhenUnmarshalToStringThenException(){
		JacksonUtilJson.unmarshalFromString("", null);
	}
	
	@Test(expected = Exception.class)
	public void givenNullTypeAndNullJsonWhenUnmarshalToStringThenException(){
		JacksonUtilJson.unmarshalFromString(null, null);
	}
	
	@Test(expected = Exception.class)
	public void givenNullTypeWhenUnmarshalThenException() throws Exception{
		JacksonUtilJson.unmarshal(new ByteArrayInputStream("".getBytes("utf-8")), null);
	}
	
	@Test(expected = Exception.class)
	public void givenNullTypeAndInputStreamWhenUnmarshalThenException(){
		JacksonUtilJson.unmarshalFromString(null, null);
	}
	
	@Test
	public void givenNullObjectWhenMarshalThenException(){
		OutputStream os = new ByteArrayOutputStream(); 
		JacksonUtilJson.marshal(null, os);
		assertTrue(os != null);
		assertEquals("null", os.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void givenNullOutputStreamWhenMarshalThenException(){
		JacksonUtilJson.marshal(new Bean(null, true, 0, null), null);
	}
	
	@Test
	public void givenNullObjectWhenMarshalToStringThenException(){
		assertEquals("null", JacksonUtilJson.marshalToString(null));
	}
	
	@Test
	public void givenBeanWhenMarshalToStringThenCheckJason(){
		Bean actualBean = new Bean(" ", true, 1, new Date());
		String json = JacksonUtilJson.marshalToString(actualBean);
		assertEquals("MyBean", true, json.contains("MyBean"));
		assertEquals("key", true, json.contains("key"));
		assertEquals("redirect", true, json.contains("redirect"));
		assertEquals("empty", true, json.contains("empty"));
		assertEquals("now", true, json.contains("now"));
		
		actualBean = new Bean(null, true, 0, null);
		json = JacksonUtilJson.marshalToString(actualBean, JacksonUtilJson.SerializationFeatures.NO_WRAP_ROOT);
		assertEquals("MyBean", false, json.contains("MyBean"));
		assertEquals("key", false, json.contains("key"));
		assertEquals("redirect", true, json.contains("redirect"));
		assertEquals("empty", true, json.contains("empty"));
		assertEquals("now", false, json.contains("now"));
		
		actualBean = new Bean();
		json = JacksonUtilJson.marshalToString(actualBean);
		assertEquals("MyBean", true, json.contains("MyBean"));
		assertEquals("key", false, json.contains("key"));
		assertEquals("redirect", true, json.contains("redirect"));
		assertEquals("empty", true, json.contains("empty"));
		assertEquals("now", false, json.contains("now"));
	}
	
	@JsonRootName(value = "MyBean")
	@JsonInclude(Include.NON_EMPTY)
	@SuppressWarnings("unused")
	private static class Bean {
		@JsonProperty("key")
		String key;
		@JsonProperty
		boolean redirect;
		@JsonProperty
		int empty;
		@JsonProperty
		@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss", timezone="CET")
		Date now;
		
		public Bean(String key, boolean redirect, int empty, Date now) {
			this.key = key;
			this.redirect = redirect;
			this.empty = empty;
			this.now = now;
		}

		public Bean() {}

		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public boolean isRedirect() {
			return redirect;
		}
		public void setRedirect(boolean redirect) {
			this.redirect = redirect;
		}
		public int getEmpty() {
			return empty;
		}
		public void setEmpty(int empty) {
			this.empty = empty;
		}
		public Date getNow() {
			return now;
		}
		public void setNow(Date now) {
			this.now = now;
		}
	}

}
