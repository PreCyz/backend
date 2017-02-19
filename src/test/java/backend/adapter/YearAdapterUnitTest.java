package backend.adapter;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.adapter.YearAdapter;

public class YearAdapterUnitTest {
	private DateFormat df;
	private YearAdapter adapter;
	private Calendar calendar; 

	@Before
	public void setUp() throws Exception {
		adapter = new YearAdapter();
		df = new SimpleDateFormat("yyyy");
		calendar = Calendar.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		df = null;
		adapter = null;
		calendar = null;
	}
	
	@Test
	public void givenDateFormatYYYYWhenCompareWithYYYYThenEquals() throws Exception {
		assertEquals(df, adapter.df);
	}
	
	@Test
	public void givenDateFormatYYYYWhenCompareWithDateFormatMMThenNotEquals() throws Exception {
		df = new SimpleDateFormat("mm");
		assertNotEquals(df, adapter.df);
	}
	
	@Test
	public void givenProperStringYYYYWhenUnmarshalThenReturnDate() throws Exception {
		String year = "1111";
		Date dateYear = adapter.unmarshal(year);
		calendar.setTime(dateYear);
		assertEquals(Integer.parseInt(year), calendar.get(Calendar.YEAR));
		
		year = "0123";
		dateYear = adapter.unmarshal(year);
		calendar.setTime(dateYear);
		assertEquals(Integer.parseInt(year), calendar.get(Calendar.YEAR));
	}
	
	@Test
	public void givenStringNotYYYYFormatWhenUnmarshalThenReturnNotProperYear() throws Exception {
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
	public void givenProperDateWhenMarshalThenReturnYear() throws Exception {
		long dateLong = 1453974685244L;
		calendar.setTimeInMillis(dateLong);
		int expectedYear = 2016;
		assertEquals(expectedYear, calendar.get(Calendar.YEAR));
	}
	
	@Test
	public void givenNullDateWhenMarshalThenReturnNull() throws Exception {
		assertNull(adapter.marshal(null));
	}
}
