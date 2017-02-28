package backend.util.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import backend.util.helper.MessageHelper;

public class MessageHelperUnitTest {
	
	private String[] messageParams;
	private final String LOG_PATH = MessageHelper.LOG_BUNDLE_NAME;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		messageParams = new String[]{"param","param_","param#"};
	}

	@Test
	public void givenProperKeyAndParamsWhenGetMessageThenReturnMessage() {
		String key = "dao.task.start";
		String expected = String.format("start task: \"%s\" login: \"%s\" taskId: %s", messageParams[0], messageParams[1], messageParams[2]);
		String actual = MessageHelper.getMessage(key, messageParams);
		assertNotNull(actual);
		assertEquals(expected, actual);
		
		key = "eapplication.registerDirect";
		expected = "Command REGISTER Direct is executed";
		actual = MessageHelper.getMessage(key, null);
		assertNotNull(actual);
		assertEquals(expected, actual);
		
		messageParams = new String[]{"1"};
		key = "setClientPersonalData.1";
		expected=String.format("[setClientPersonalData][client]=%s", messageParams[0]);
		actual = MessageHelper.getMessage(key, messageParams);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void givenErrorKeyWhenGetMessageThenReturnMessageNoKey() {
		String errorKey = "some.error.key.not.in.properties.file";
		String expected = String.format("?????%s?????", errorKey);
		String actual = MessageHelper.getMessage(errorKey, null);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test(expected = NullPointerException.class)
	public void givenNullKeyWhenGetMessageThenThrowNullPointerException() {
		assertNull(MessageHelper.getMessage(null, null));
	}

	@Test
	public void givenProperKeyAndParamsWhenGetMessageOrNullThenReturnMessage() {
		String key = "dao.task.start";
		String expected = String.format("start task: \"%s\" login: \"%s\" taskId: %s", messageParams[0], messageParams[1], messageParams[2]);
		String actual = MessageHelper.getMessage(key, messageParams);
		assertNotNull(actual);
		assertEquals(expected, actual);
		
		key = "eapplication.registerDirect";
		expected = "Command REGISTER Direct is executed";
		actual = MessageHelper.getMessage(key, null);
		assertNotNull(actual);
		assertEquals(expected, actual);
		
		messageParams = new String[]{"1"};
		key = "setClientPersonalData.1";
		expected=String.format("[setClientPersonalData][client]=%s", messageParams[0]);
		actual = MessageHelper.getMessage(key, messageParams);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void givenErrorKeyWhenGetMessageOrNullThenReturnMessageNoKey() {
		String errorKey = "some.error.key.not.in.properties.file";
		String expected = String.format("?????%s?????", errorKey);
		String actual = MessageHelper.getMessageOrNull(errorKey, null);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void givenNullKeyWhenGetMessageOrNullThenReturnNull() {
		assertNull(MessageHelper.getMessageOrNull(null, null));
	}
	
	@Test
	public void givenProperResourceNameWhenGetResourceBundleThenReturnEnglishBoundle() {
		String rsrcPath = String.format(LOG_PATH);
		ResourceBundle expected = ResourceBundle.getBundle(rsrcPath, new Locale("en"));
		ResourceBundle actual = MessageHelper.getResourceBundle();
		assertNotNull(actual);
		assertTrue(!actual.keySet().isEmpty());
		assertEquals(expected.keySet().size(), actual.keySet().size());
		assertEquals(expected.getBaseBundleName(), actual.getBaseBundleName());
	}
	
	@Test
	public void givenStringWhenPutNumbersToBracketsReturnProperValue(){
		int initialNumber = 0;
		String value = "start task: \"{}\" login: \"{}\" taskId: {}";
		String actual = MessageHelper.putNumbersToBrackets(value, initialNumber);
		String expected = "start task: \"{0}\" login: \"{1}\" taskId: {2}";
		assertEquals(expected, actual);
		
		value = "start task: ";
		actual = MessageHelper.putNumbersToBrackets(value, initialNumber);
		assertEquals(value, actual);
		
		value = "";
		actual = MessageHelper.putNumbersToBrackets(value, initialNumber);
		assertEquals(value, actual);
		
		try {
			value = null;
			MessageHelper.putNumbersToBrackets(value, initialNumber);
		} catch (NullPointerException ex) {
			assertNotNull(ex);
		}
		
		initialNumber = -5;
		value = "start task: \"{}\" login: \"{}\" taskId: {}";
		actual = MessageHelper.putNumbersToBrackets(value, initialNumber);
		expected = "start task: \"{-5}\" login: \"{-4}\" taskId: {-3}";
		assertEquals(expected, actual);
	}
}
