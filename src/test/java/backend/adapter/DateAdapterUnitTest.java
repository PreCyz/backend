package backend.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.adapter.DateAdapter;

public class DateAdapterUnitTest {
	private DateAdapter adapter;
	
	private int year, month, day_of_math;
	private String dateInString;

	@Before
	public void setUp() throws Exception {
		year = 2016;
		month = 1;
		day_of_math = 20;
		dateInString = String.format("%d-0%s-%d", year, month, day_of_math);
		adapter = new DateAdapter();
	}

	@After
	public void tearDown() throws Exception {
		adapter = null;
		dateInString = null;
		year = month = day_of_math = -1;
	}

	@Test
	public void givenAdapterWhenMarshalThenReturnProperFormat() throws Exception {
		int hour_of_day = 10;
		int minute = 44;
		int second = 59;
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, Calendar.JANUARY);
		date.set(Calendar.DAY_OF_MONTH, day_of_math);
		date.set(Calendar.HOUR_OF_DAY, hour_of_day);
		date.set(Calendar.MINUTE, minute);
		date.set(Calendar.SECOND, second);
		
		assertEquals(dateInString, adapter.marshal(date.getTime()));
	}
	
	@Test(expected = NullPointerException.class)
	public void givenAdapterWhenMarshalThenThrowException() throws Exception {
		adapter.marshal(null);
	}
	
	@Test
	public void givenAdapterWhenUnmarshalThenReturnDate() throws Exception {
		Calendar expected = Calendar.getInstance();
		expected.set(Calendar.YEAR, year);
		expected.set(Calendar.MONTH, Calendar.JANUARY);
		expected.set(Calendar.DAY_OF_MONTH, day_of_math);
		expected.set(Calendar.HOUR_OF_DAY, 0);
		expected.set(Calendar.MINUTE, 0);
		expected.set(Calendar.SECOND, 0);
		expected.set(Calendar.MILLISECOND, 0);
		
		assertEquals(expected.getTime(), adapter.unmarshal(dateInString));
		
		dateInString = "1111-11-14";
		assertNotSame(expected.getTime(), adapter.unmarshal(dateInString));
	}
	
	@Test
	public void givenAdapterWhenUnmarshalThenThrowException() throws Exception {
		try{
			adapter.unmarshal(null);
			fail("Should be NullPointerException.");
		} catch (NullPointerException ex){
			assertNotNull(ex);
		}
		
		String value = "";
		try{
			adapter.unmarshal(value);
			fail("Should be ParseException.");
		} catch (ParseException ex){
			assertNotNull(ex);
			String expected = String.format("Unparseable date: \"%s\"", value);
			assertEquals(expected, ex.getMessage());
		}
		
		value = "aa";
		try{
			adapter.unmarshal(value);
			fail("Should be ParseException.");
		} catch (ParseException ex){
			assertNotNull(ex);
			String expected = String.format("Unparseable date: \"%s\"", value);
			assertEquals(expected, ex.getMessage());
		}
		
		value = "aaaa-11-31";
		try{
			adapter.unmarshal(value);
			fail("Should be ParseException.");
		} catch (ParseException ex){
			assertNotNull(ex);
			String expected = String.format("Unparseable date: \"%s\"", value);
			assertEquals(expected, ex.getMessage());
		}
	}

}
