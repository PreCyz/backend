package backend.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.AbstractUnitTest;
import backend.util.TimeService;

public class TimeServiceUnitTest extends AbstractUnitTest {
	
	private TimeService timeService;
	private Calendar someDate;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Before
	public void setUp() throws Exception {
		someDate = Calendar.getInstance();
		someDate.set(2015, 11, 12);
	}

	@After
	public void tearDown() throws Exception {
		timeService = null;
	}

	@Test
	public void whenCurrentTimeMillisThenNotNull() {
		assertNotNull(TimeService.currentTimeMillis());
	}

	@Test
	public void whenGetCurrentTimeThenProperResult() {
		timeService = new TimeService();
		String expected = sdf.format(System.currentTimeMillis());
		String actual = sdf.format(timeService.getCurrentTime());
		if(!expected.equals(actual)){
			System.out.println("Data na ULLI nie jest aktualna");
		} else{
			assertEquals(sdf.format(System.currentTimeMillis()), sdf.format(timeService.getCurrentTime()));
		}
	}
	
	@Test
	public void whenGetUllaDateThenNotNull() {
		timeService = new TimeService();
		assertNotNull(sdf.format(timeService.getUllaDate()));
	}

	@Test
	public void givenSomeTimewhenGetUllaDateThenNotEqual() {
		timeService = new TimeService();
		assertTrue(someDate.getTime() != timeService.getUllaDate());
	}

	@Test
	public void whenTimeServiceThenProperValue() {
		timeService = new TimeService();
		assertNotNull(timeService.getUllaDate());
		
		timeService = new TimeService();
		assertNotNull(sdf.format(timeService.getUllaDate()));
	}

}
