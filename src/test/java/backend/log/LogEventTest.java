package backend.log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.jpa.entities.log.LogEvent;

public class LogEventTest {
	
	private LogEvent event;

	@Before
	public void setUp() throws Exception {
		event = new LogEvent();
	}

	@After
	public void tearDown() throws Exception {
		event = null;
	}

	@Test
	public void givenLogEventWhenAddContractNumberThenReturnLogEventWithContractNumber() {
		final String contractNumber = "someContractNumber";

		event = event.addContractNumber(contractNumber);
		assertNotNull(event);
		assertEquals(contractNumber, event.getContractNumber());
		
		event = event.addContractNumber(null);
		assertNotNull(event);
		assertNull(event.getContractNumber());
	}

	@Test
	public void givenLogEventWhenAddUllaTaskIdThenReturnLogEventWithUllaTaskId() {
		final String ullaTaskId = "someUllaTaskId";
		
		event = event.addUllaTaskId(ullaTaskId);
		assertNotNull(event);
		assertEquals(ullaTaskId, event.getUllaTaskId());
		
		event = event.addUllaTaskId(null);
		assertNotNull(event);
		assertNull(event.getUllaTaskId());
	}

	@Test
	public void givenlogEventWhenIgnoreStackThenReturnLogEventWithStackIgnored() {
		assertTrue(!event.isIgnoreStack());
		
		event = event.ignoreStack();
		assertNotNull(event);
		assertTrue(event.isIgnoreStack());
	}

	@Test
	public void givenlogEventWhenAddExplicitMessageThenReturnLogEventWithExplicitMessage() {
		assertNull(event.getExplicitMessage());
		
		final String explicitMessage = "someMessageToAdd";
		event = event.addExplicitMessage(explicitMessage);
		assertNotNull(event);
		assertEquals(explicitMessage, event.getExplicitMessage());
	}

	@Test
	public void givenFullLogEventWhenCopyParamsThenReturnDeepCopy() {
		LogEvent copy = new LogEvent();
		assertNull(copy.getContractNumber());
		assertNull(copy.getExplicitMessage());
		assertTrue(!copy.isIgnoreStack());
		assertNull(copy.getUllaTaskId());
		
		final String explicitMessage = "someMessageToAdd";
		final String ullaTaskId = "someUllaTaskId";
		final String contractNumber = "someContractNumber";
		event = event
				.addContractNumber(contractNumber)
				.addExplicitMessage(explicitMessage)
				.addUllaTaskId(ullaTaskId).ignoreStack();
		
		event.copyParams(copy);
		
		assertEquals(contractNumber, copy.getContractNumber());
		assertEquals(explicitMessage, copy.getExplicitMessage());
		assertEquals(ullaTaskId, copy.getUllaTaskId());
		assertTrue(copy.isIgnoreStack());
	}
}
