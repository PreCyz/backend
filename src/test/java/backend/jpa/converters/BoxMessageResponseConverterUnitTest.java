package backend.jpa.converters;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoxMessageResponseConverterUnitTest {
	
	private BoxMessageResponseConverter converter;
	private final String SOME_MESSAGE = "SomeMessage"; 

	@Before
	public void setUp() throws Exception {
		converter = new BoxMessageResponseConverter();
	}

	@After
	public void tearDown() throws Exception {
		converter = null;
	}

	@Test
	public void givenStringWhenConvertToDatabaseColumnThenReturnByteArray() throws UnsupportedEncodingException {
		byte[] result = converter.convertToDatabaseColumn(SOME_MESSAGE);
		assertNotNull(result);
		assertArrayEquals(SOME_MESSAGE.getBytes("UTF-8"), result);
	}
	
	@Test
	public void givenNullStringWhenConvertToDatabaseColumnThenReturnNull() {
		assertNull(converter.convertToDatabaseColumn(null));
	}

	@Test
	public void givenByteArrayWhenConvertToEntityAttributeThenReturnString() throws UnsupportedEncodingException {
		String result = converter.convertToEntityAttribute(SOME_MESSAGE.getBytes("UTF-8"));
		assertEquals(SOME_MESSAGE, result);
	}
	
	@Test
	public void givenNullArrayWhenConvertToEntityAttributeThenReturnNull() {
		assertNull(converter.convertToEntityAttribute(null));
	}

}
