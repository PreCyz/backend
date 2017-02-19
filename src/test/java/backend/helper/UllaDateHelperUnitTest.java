package backend.helper;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.helper.UllaDateHelper;

public class UllaDateHelperUnitTest {
	private Calendar calendar;
	
	@Before
	public void setUp(){
		calendar = Calendar.getInstance();
	}
	
	@After
	public void tearDown(){
		calendar = null;
	}

	@Test
	public void givenNullDateWhenDateToUllaTimeThenReturn0() {
		assertEquals(0, UllaDateHelper.dateToUllaTime(null));
	}

	@Test
	public void givenDateWhenDateToUllaTimeThenReturnProperValue() {
		calendar.set(Calendar.HOUR_OF_DAY, 16);
		calendar.set(Calendar.MINUTE, 26);
		calendar.set(Calendar.SECOND, 12);
		assertEquals(162612, UllaDateHelper.dateToUllaTime(calendar.getTime()));
		
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 26);
		calendar.set(Calendar.SECOND, 12);
		assertEquals(102612, UllaDateHelper.dateToUllaTime(calendar.getTime()));
	}
	
	@Test
	public void givenNullDateWhenDateToUllaThenReturn0() {
		assertEquals(0, UllaDateHelper.dateToUlla(null));
	}
	
	@Test
	public void givenDateWhenDateToUllaThenInt() {
		calendar.set(Calendar.YEAR, 2016);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 2);
		assertEquals(20160202, UllaDateHelper.dateToUlla(calendar.getTime()));
	}
}
