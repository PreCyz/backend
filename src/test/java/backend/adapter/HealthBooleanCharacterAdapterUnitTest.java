package backend.adapter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.adapter.HealthBooleanCharacterAdapter;

public class HealthBooleanCharacterAdapterUnitTest {
	private HealthBooleanCharacterAdapter adapter;

	@Before
	public void setUp() throws Exception {
		adapter = new HealthBooleanCharacterAdapter();
	}

	@After
	public void tearDown() throws Exception {
		adapter = null;
	}

	@Test
	public void givenBooleanValueWhenMarshalThenReturnProperString() throws Exception {
		assertEquals("I", adapter.marshal(Boolean.TRUE));
		assertEquals("H", adapter.marshal(Boolean.FALSE));
		assertEquals("H", adapter.marshal(null));
	}
	
	@Test
	public void givenStringValueEqHWhenUnmarshalThenReturnFALSE() throws Exception {
		assertEquals(Boolean.FALSE, adapter.unmarshal("H"));
	}
	
	@Test
	public void givenStringValueNotEqHWhenUnmarshalThenReturnTRUE() throws Exception {
		assertEquals(Boolean.TRUE, adapter.unmarshal(null));
		assertEquals(Boolean.TRUE, adapter.unmarshal(""));
		assertEquals(Boolean.TRUE, adapter.unmarshal("h"));
		assertEquals(Boolean.TRUE, adapter.unmarshal("Ha"));
		assertEquals(Boolean.TRUE, adapter.unmarshal("I"));
		assertEquals(Boolean.TRUE, adapter.unmarshal("someString"));
		assertEquals(Boolean.TRUE, adapter.unmarshal("123"));
	}
}
