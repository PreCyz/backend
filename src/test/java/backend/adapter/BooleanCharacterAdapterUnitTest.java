package backend.adapter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.adapter.BooleanCharacterAdapter;

public class BooleanCharacterAdapterUnitTest {
	
	private BooleanCharacterAdapter adapter;

	@Before
	public void setUp() throws Exception {
		adapter = new BooleanCharacterAdapter();
	}

	@After
	public void tearDown() throws Exception {
		adapter = null;
	}

	@Test
	public void givenAdapterWhenMarshalThenReturnProperValue() throws Exception {
		assertNull(adapter.marshal(null));
		assertEquals("Y", adapter.marshal(Boolean.TRUE));
		assertEquals("N", adapter.marshal(Boolean.FALSE));
	}

	@Test
	public void givenAdapterWhenUnmarshalThenReturnProperBoolean() throws Exception {
		assertEquals(Boolean.TRUE, adapter.unmarshal("Y"));
		assertEquals(Boolean.FALSE, adapter.unmarshal("N"));
		assertNull( adapter.unmarshal(null));
	}
	
	@Test
	public void givenAdapterWhenUnmarshalThenReturnNull() throws Exception{
		for(int i = 0; i < 256; i++){
			boolean isYorN = (char)i == 'Y' || (char)i == 'N';
			if(!isYorN){
				String value = String.format("%c", (char) i );
				System.out.println(i);
				assertNull(adapter.unmarshal(value));
			}
		}
	}
}
