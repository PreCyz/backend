package backend.util.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import backend.AbstractUnitTest;
import backend.util.helper.StringHelper;

public class StringHelperUnitTest extends AbstractUnitTest {

	@Test
	public void givenStringValueWhenSafeUpperCaseThenReturnUpperCaseValue() {
		String lowerCase = "aAaA";
		assertEquals("AAAA", StringHelper.safeUpperCase(lowerCase));
		assertEquals(null, StringHelper.safeUpperCase(null));
	}

	@Test
	public void givenLengthWhenCutStringToLengthThenReturnCutValue() {
		int stringLength = 3;
		assertEquals("hel", StringHelper.cutStringToLength("hello", stringLength));
		assertEquals("hel", StringHelper.cutStringToLength("hel", stringLength));
		assertEquals("he", StringHelper.cutStringToLength("he", stringLength));
	}

	@Test
	public void givenBoundryConditionwhenCutStringToLengthThenReturnValue() {
		assertEquals("", StringHelper.cutStringToLength("", 3));
		assertEquals(null, StringHelper.cutStringToLength(null, 3));
		assertEquals("minus", StringHelper.cutStringToLength("minus", -1));
		assertEquals("", StringHelper.cutStringToLength("", -1));
		assertEquals(null, StringHelper.cutStringToLength(null, -1));
		assertEquals(null, StringHelper.cutStringToLength(null, 0));
		assertEquals("", StringHelper.cutStringToLength("", 0));
		assertEquals("", StringHelper.cutStringToLength("", 1));
	}

	@Test
	public void givenEmptyStringWhenEmptyThenTrue() {
		assertTrue(StringHelper.empty(""));
		assertTrue(StringHelper.empty(null));
	}
	
	@Test
	public void givenNotEmptyStringWhenEmptyThenFalse() {
		assertTrue(!StringHelper.empty("hello"));
	}

	@Test
	public void givenNotEmptyStringWhenNotEmptyThenTrue() {
		assertTrue(StringHelper.notEmpty("hello"));
	}
	
	@Test
	public void givenEmptyStringWhenNotEmptyThenFalse() {
		assertTrue(!StringHelper.notEmpty(""));
		assertTrue(!StringHelper.notEmpty(null));
	}

	@Test
	public void givenNullOrEmptyWhenNullifyThenNull() {
		assertEquals(null, StringHelper.nullify(null));
		assertEquals(null, StringHelper.nullify(""));
	}
	
	@Test
	public void givenValueWhenNullifyThenValue() {
		assertEquals("hello", StringHelper.nullify("hello"));
	}


	@Test
	public void givenNullWhenNullToEmptyThenReturnEmptyString() {
		assertEquals("", StringHelper.nullToEmpty(null));
	}
	
	@Test
	public void givenValueWhenNullToEmptyThenReturnValue() {
		assertEquals("hello", StringHelper.nullToEmpty("hello"));
	}

	@Test
	public void givenNullWhenNullToZeroThenZero() {
		assertEquals(0, StringHelper.nullToZero(null), 0.0);
	}
	
	@Test
	public void givenValueWhenNullToZeroThenValue() {
		assertEquals(10, StringHelper.nullToZero(10d), 0.0);
	}

	@Test
	public void givenValuesWhenEqualsWithNullsThenTrue() {
		assertTrue(StringHelper.equalsWithNulls("hello", "hello"));
		assertTrue(StringHelper.equalsWithNulls(null, null));
	}
	
	@Test
	public void givenNullsWhenEqualsWithNullsThenTrue() {
		assertTrue(StringHelper.equalsWithNulls(null, null));
	}
	
	@Test
	public void givenNullAndValueWhenEqualsWithNullsThenFalse() {
		assertTrue(!StringHelper.equalsWithNulls(null, "hello"));
	}
	
	@Test
	public void givenValueAndNullWhenEqualsWithNullsThenFalse() {
		assertTrue(!StringHelper.equalsWithNulls("hello", null));
	}

	@Test
	public void givenValuesWhenIsStringChangedThenTrue() {
		assertTrue(StringHelper.isStringChanged("", "hello"));
		assertTrue(StringHelper.isStringChanged("1asd", "hello"));
	}
	
	@Test
	public void givenValuesWhenIsStringChangedThenFalse() {
		assertTrue(!StringHelper.isStringChanged("", ""));
		assertTrue(!StringHelper.isStringChanged("hello", "hello"));
	}
	
	@Test
	public void givenVariouseValuesWhenIsStringChangedThenResult() {
		assertTrue(StringHelper.isStringChanged(null, "hello"));
		assertTrue(!StringHelper.isStringChanged("", null));
		assertTrue(StringHelper.isStringChanged("hello", null));
		assertTrue(!StringHelper.isStringChanged(null, ""));
	}

	@Test
	public void whenEncodeURLParamThenEncode() {
		assertEquals(null, StringHelper.encodeURLParam(null));
		assertEquals("", StringHelper.encodeURLParam(""));
		String url = "<![CDATA[ <IMG SRC=\"  javascript:document.vulnerable=true;\"> ]]>";
		String url2 = "String with spaces";
		assertEquals("%3C%21%5BCDATA%5B+%3CIMG+SRC%3D%22+%0E+javascript%3Adocument.vulnerable%3Dtrue%3B%22%3E+%5D%5D%3E", StringHelper.encodeURLParam(url));
		assertEquals("String+with+spaces", StringHelper.encodeURLParam(url2));
	}
	
	@Test
	public void givenStringValueWhenEncodeThenReturnProperStringValue() throws Exception {
		String properValue = "properValue";
		String symbol = "proper";
		String replacement = "replaced";
		String expectedValue = "replacedValue";
		assertEquals(expectedValue, StringHelper.encode(properValue, symbol, replacement));
		
		symbol = "e";
		expectedValue = "propreplacedrValureplaced";
		assertEquals(expectedValue, StringHelper.encode(properValue, symbol, replacement));

		symbol = "";
		expectedValue = "replacedpreplacedrreplacedoreplacedpreplacedereplacedrreplacedVreplacedareplacedlreplacedureplacedereplaced";
		assertEquals(expectedValue, StringHelper.encode(properValue, symbol, replacement));
		
		symbol = "symbol";
		assertEquals(properValue, StringHelper.encode(properValue, symbol, null));
	}
	
	@Test
	public void givenNullOrEmptyStringValueWhenEncodeThenReturnThatValue() throws Exception {
		assertNull(StringHelper.encode(null, "someSymbol", "someReplacement"));
		assertEquals("", StringHelper.encode("", "someSymbol", "someReplacement"));
	}
	
	@Test(expected = NullPointerException.class)
	public void givenNullReplacementAndProperSymbolAndValueWhenEncodeThenThrowNullPointerException() throws Exception{
		String properValue = "properValue";
		String symbol = "proper";
		StringHelper.encode(properValue, symbol, null);
	}
	
	@Test
	public void givenValueWhenExtractBooleanThenReturnTrue() {
		String properValue = "true";
		boolean actual = StringHelper.extractBoolean(properValue);
		assertTrue(actual);
	}
	
	@Test
	public void givenValueWhenExtractBooleanThenReturnFalse() {
		String properValue = "";
		boolean actual = StringHelper.extractBoolean(properValue);
		assertFalse(actual);
		
		properValue = null;
		actual = StringHelper.extractBoolean(properValue);
		assertFalse(actual);
		
		properValue = "1";
		actual = StringHelper.extractBoolean(properValue);
		assertFalse(actual);
		
		properValue = "abra";
		actual = StringHelper.extractBoolean(properValue);
		assertFalse(actual);
		
		properValue = "false";
		actual = StringHelper.extractBoolean(properValue);
		assertFalse(actual);
	}
}
