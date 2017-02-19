package backend.adapter;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.adapter.DateTimeAdapter;

public class DateTimeAdapterUnitTest {
	private DateFormat df;
	private DateTimeAdapter adapter;
	private Calendar calendar; 

	@Before
	public void setUp() throws Exception {
		adapter = new DateTimeAdapter();
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		calendar = Calendar.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		df = null;
		adapter = null;
		calendar = null;
	}

	@Test
	public void givenProperDateFormatWhenCompareWith_yyyy_MM_dd_HH_mm_ss_ThenEquals() throws Exception {
		assertEquals(df, adapter.df);
	}
	
	@Test
	public void givenProperDateFormatWhenCompareWithDateFormatMMThenNotEquals() throws Exception {
		df = new SimpleDateFormat("MM");
		assertNotEquals(df, adapter.df);
	}
	
	@Test
	public void givenProperDateTimeStringWhenUnmarshalThenReturnDateTime() throws Exception {
		int year = 1111;
		int month = 12;
		int day = 1;
		int hour = 23;
		int minutes = 54;
		int seconds = 22;
		
		String dateTimeString = String.format("%d-%d-%d %d:%d:%d", year, month, day, hour, minutes, seconds);
		Date dateTime = adapter.unmarshal(dateTimeString);
		calendar.setTime(dateTime);
		assertEquals(year, calendar.get(Calendar.YEAR));
		assertEquals(month, calendar.get(Calendar.MONTH) + 1);
		assertEquals(day, calendar.get(Calendar.DAY_OF_MONTH));
		assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(minutes, calendar.get(Calendar.MINUTE));
		assertEquals(seconds, calendar.get(Calendar.SECOND));
	}
	
	@Test(expected = ParseException.class)
	public void givenStringNotProperFormatedWhenUnmarshalThenThrowException() throws Exception {
		String year = "11-11-1111";
		Date dateYear = adapter.unmarshal(year);
		calendar.setTime(dateYear);
		assertNotEquals("1111", calendar.get(Calendar.YEAR));
	}
	
	@Test(expected = ParseException.class)
	public void givenSomeStringWhenUnmarshalThenThrowException() throws Exception {
		adapter.unmarshal("abc");
	}
	
	@Test(expected = ParseException.class)
	public void givenEmptyStringWhenUnmarshalThenThrowException() throws Exception {
		adapter.unmarshal("");
	}
	
	@Test(expected = NullPointerException.class)
	public void givenNullStringWhenUnmarshalThenThrowException() throws Exception {
		adapter.unmarshal(null);
	}
	
	@Test
	public void givenProperDateWhenMarshalThenReturnFormatedString() throws Exception {
		String expectedDate = "2016-01-28 10:51:25";
		long dateLong = 1453974685244L;
		calendar.setTimeInMillis(dateLong);
		String actualDate = adapter.marshal(calendar.getTime());
		assertEquals(expectedDate, actualDate);
	}
	
	@Test(expected = NullPointerException.class)
	public void givenNullDateWhenMarshalThenThrowException() throws Exception {
		assertNull(adapter.marshal(null));
	}

}
