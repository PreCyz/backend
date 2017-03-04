package backend.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.dto.LoggedUser;
import backend.util.JaxbJsonUtil;

public class JAXBUtilJSONUnitTest {

	private SimpleObject so;
	private String expected;
	LoggedUser user = new LoggedUser();
	String pushToken = "dhjkeSjie235";
	String operatingSystem = "Android";
	String operatingSystemVersion = "5.0.1";
	String deviceProducer = "OnePlus";
	String deviceModel = "A0001";
	String deviceId = "dev1c3";
	String customUserId = "dev1c3";

	@Before
	public void setUp() throws Exception {
		so = new SimpleObject();
		so.setSomeBoolean(true);
		so.setSomeString("testString");
		so.setSomeInt(15);
		expected = "{\n   \"simpleObject\" : {\n      \"someBoolean\" : true,\n      \"someInt\" : 15,\n      \"someString\" : \"testString\"\n   }\n}";
	}

	@After
	public void tearDown() throws Exception {
		so = null;
		expected = null;
	}

	@Test
	public void testUnmarshal() throws Exception {
		InputStream is = new ByteArrayInputStream(expected.getBytes(BackendConstants.UTF8_CODING));
		SimpleObject unSo = JaxbJsonUtil.unmarshal(is, SimpleObject.class);
		assertNotNull(unSo);
		assertEquals(15, unSo.getSomeInt());
		assertEquals("testString", unSo.getSomeString());
		assertTrue(unSo.isSomeBoolean());
	}

	@Test
	public void testUnmarshalFromString() throws Exception {
		SimpleObject unSo = JaxbJsonUtil.unmarshalFromString(expected, SimpleObject.class);
		assertNotNull(unSo);
		assertEquals(15, unSo.getSomeInt());
		assertEquals("testString", unSo.getSomeString());
		assertTrue(unSo.isSomeBoolean());
	}

	@Test
	public void testMarshalToStringObject_SimpleCall() {
		assertNotNull(JaxbJsonUtil.marshalToString(so));
	}

	@Test
	public void testMarshalToString_extendedCall() {
		String result = JaxbJsonUtil.marshalToString(so, SimpleObject.class);
		assertTrue(result.contains("\"simpleObject\" : {"));
		assertTrue(result.contains("\"someBoolean\" : true,"));
		assertTrue(result.contains("\"someInt\" : 15,"));
		assertTrue(result.contains("\"someString\" : \"testString\""));
	}

	@Test
	public void testMarshalOutputStream_simpleCall() {
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		JaxbJsonUtil.marshal(so, bais);
		assertNotNull(bais);
		JaxbJsonUtil.marshal(so, System.out);
	}

	@Test
	public void testMarshalObjectOutputStreamClassOfQextendsObject() {
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		JaxbJsonUtil.marshal(so, bais, SimpleObject.class);
		assertNotNull(bais);
		
		JaxbJsonUtil.marshal(so, System.out, SimpleObject.class);
	}
	
	@Test
	public void whenContextParamsThenMarshalWithContextParams() throws Exception {
		JaxbJsonUtil.OperationProperties[] params = new JaxbJsonUtil.OperationProperties[]{
				JaxbJsonUtil.OperationProperties.NO_ROOT, 
				JaxbJsonUtil.OperationProperties.NO_FORMATTED_OUTPUT
		};
		
		String expected = JaxbJsonUtil.marshalToString(so, SimpleObject.class, params);
		assertTrue(!expected.contains("simpleObject"));
		
		expected = JaxbJsonUtil.marshalToString(so);
		assertTrue(expected.contains("simpleObject"));
		
		assertEquals(UnmarshallerProperties.JSON_INCLUDE_ROOT, MarshallerProperties.JSON_INCLUDE_ROOT);
		
	}
	
	@Test(expected = Exception.class)
	public void givenNullTypeWhenUnmarshalToStringThenException(){
		JaxbJsonUtil.unmarshalFromString("", null);
	}
	
	@Test(expected = Exception.class)
	public void givenNullTypeAndNullJsonWhenUnmarshalToStringThenException() {
		JaxbJsonUtil.unmarshalFromString(null, null);
	}
	
	@Test(expected = Exception.class)
	public void givenNullTypeWhenUnmarshalThenException() throws Exception {
		JaxbJsonUtil.unmarshal(new ByteArrayInputStream("".getBytes("utf-8")), null);
	}
	
	@Test(expected = Exception.class)
	public void givenNullTypeAndInputStreamWhenUnmarshalThenException() {
		JaxbJsonUtil.unmarshalFromString(null, null);
	}
	
	@Test(expected = Exception.class)
	public void givenNullObjectWhenMarshalThenException() {
		JaxbJsonUtil.marshal(null, new ByteArrayOutputStream());
	}
	
	@Test(expected = Exception.class)
	public void givenNullOutputStreamWhenMarshalThenException() {
		JaxbJsonUtil.marshal(new SimpleObject(), null);
	}
	
	@Test(expected = Exception.class)
	public void givenNullObjectWhenMarshalToStringThenException() {
		JaxbJsonUtil.marshalToString(null);
	}
	
	@XmlRootElement
	public static class SimpleObject {
		
		private String someString;
		private int someInt;
		private boolean someBoolean;
		
		public String getSomeString() {
			return someString;
		}
		public void setSomeString(String someString) {
			this.someString = someString;
		}
		public int getSomeInt() {
			return someInt;
		}
		public void setSomeInt(int someInt) {
			this.someInt = someInt;
		}
		public boolean isSomeBoolean() {
			return someBoolean;
		}
		public void setSomeBoolean(boolean someBoolean) {
			this.someBoolean = someBoolean;
		}
	}
	
}
