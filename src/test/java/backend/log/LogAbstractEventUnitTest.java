package backend.log;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.jpa.Entry;
import backend.jpa.entities.log.LogAbstract;

public class LogAbstractEventUnitTest {
	private class LogAbstractEventTest extends LogAbstract{
		private static final long serialVersionUID = -7601633105695080999L;

		public LogAbstractEventTest(String key, String[] params) {
			super(key, params);
		}
		
		public LogAbstractEventTest(String key) {
			super(key);
		}

		public LogAbstractEventTest() {
			super();
		}

		@Override
		public Long getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setId(Long id) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update(Entry entry) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private LogAbstract abstractEvent;
	private String[] messageParams;

	@Before
	public void setUp() throws Exception {
		String key = "dao.task.start";
		messageParams = new String[]{"1","a","2c"};
		abstractEvent = new LogAbstractEventTest(key, messageParams);
	}

	@After
	public void tearDown() throws Exception {
		abstractEvent = null;
	}

	@Test
	public void givenExtraParamWhenAddExtraParameterReturnProperObject() {
		String extraParam = "extraParam";
		
		assertNotNull(abstractEvent.getMessageParams());
		
		abstractEvent = abstractEvent.addExtraParameter(extraParam);
		
		assertNotNull(abstractEvent);
		assertNotNull(abstractEvent.getMessageParams());
		assertTrue(abstractEvent.getMessageParams().length == 4);
		assertEquals(extraParam, abstractEvent.getMessageParams()[abstractEvent.getMessageParams().length - 1]);
		
		abstractEvent = new LogAbstractEventTest();
		assertNull(abstractEvent.getMessageParams());
		
		abstractEvent = abstractEvent.addExtraParameter(extraParam);
		assertNotNull(abstractEvent);
		assertNotNull(abstractEvent.getMessageParams());
		assertTrue(abstractEvent.getMessageParams().length == 1);
		assertEquals(extraParam, abstractEvent.getMessageParams()[abstractEvent.getMessageParams().length - 1]);
		
		
		abstractEvent = new LogAbstractEventTest();
		
		abstractEvent = abstractEvent.addExtraParameter(null);
		assertNotNull(abstractEvent);
		assertNotNull(abstractEvent.getMessageParams());
		assertTrue(abstractEvent.getMessageParams().length == 1);
		assertEquals(null, abstractEvent.getMessageParams()[abstractEvent.getMessageParams().length - 1]);
	}

	@Test
	public void givenVeriouseMessageParamsWhenGetConcatenatedMessageParamsThenReturnProperString() {
		String expected = "1~a~2c~";
		assertEquals(expected, abstractEvent.getConcatenatedMessageParams());
		
		abstractEvent = new LogAbstractEventTest();
		assertNull(abstractEvent.getConcatenatedMessageParams());
		
		abstractEvent = new LogAbstractEventTest("dao.task.start", new String[]{});
		assertNull(abstractEvent.getConcatenatedMessageParams());
	}

	@Test
	public void givenParamsWhenSetConcatenatedMessageParamsThenSetProperValue() {
		String params = "1~a~2c~";
		abstractEvent = new LogAbstractEventTest("dao.task.start");
		assertNull(abstractEvent.getMessageParams());
		
		abstractEvent.setConcatenatedMessageParams(params);
		assertArrayEquals(messageParams, abstractEvent.getMessageParams());
		
		abstractEvent.setConcatenatedMessageParams(null);
		assertNull(abstractEvent.getMessageParams());
		
		abstractEvent.setConcatenatedMessageParams("");
		assertNull(abstractEvent.getMessageParams());
	}

	@Test
	public void givenParamsWhenConcatenateThenReturnValue() {
		String expected = "1~a~2c~";
		assertEquals(expected, LogAbstract.concatenate(messageParams));
		assertNull(LogAbstract.concatenate(null));
		assertNull(LogAbstract.concatenate(new String[]{}));
	}

	@Test
	public void givenObjectWithoutDateWhenAddDateThenReturnObjectWithDate() {
		Timestamp now = new Timestamp(new Date().getTime());
		assertNull(abstractEvent.getLogDate());
		
		abstractEvent = abstractEvent.addDate(now);
		assertNotNull(abstractEvent);
		assertNotNull(abstractEvent.getLogDate());
		assertEquals(now, abstractEvent.getLogDate());
		
		abstractEvent = abstractEvent.addDate(null);
		assertNotNull(abstractEvent);
		assertNull(abstractEvent.getLogDate());
	}

	@Test
	public void givenEmptyObjectWhenCopyParamsThenReturnDeepCopy() {
		Timestamp now = new Timestamp(new Date().getTime());
		abstractEvent.setLogDate(now);
		LogAbstract lae2 = new LogAbstractEventTest();
		
		abstractEvent.copyParams(lae2);
		
		assertArrayEquals(messageParams, lae2.getMessageParams());
		assertEquals(now, lae2.getLogDate());
		assertEquals("start task: \"{}\" login: \"{}\" taskId: {}", lae2.getMessageBase());
	}
	
	@Test(expected = NullPointerException.class)
	public void givenNullObjectWhenCopyParamsThenThrowNullPointerException() {
		abstractEvent.copyParams(null);
	}
}
