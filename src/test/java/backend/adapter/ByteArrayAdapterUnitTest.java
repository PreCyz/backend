package backend.adapter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import backend.adapter.ByteArrayAdapter;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ByteArrayAdapterUnitTest {
	
	private ByteArrayAdapter adapter;
	private String string;
	private byte[] byteArray;
	
	private static final String UTF8 = "UTF-8";  

	@Before
	public void setUp() throws Exception {
		adapter = new ByteArrayAdapter();
		string = "someValue";
		byteArray = string.getBytes(UTF8);
	}

	@After
	public void tearDown() throws Exception {
		adapter = null;
		string = null;
		byteArray = null;
	}

	@Test
	public void givenInputByteArrayWhenMarshalThenReturnString() {
		try {
			String actual = adapter.marshal(byteArray);
			assertThat(actual, is( equalTo(string) ));
		} catch (Exception e) {
			fail("Test should pass, there is probably sth wrong with charset");
		}
	}
	
	@Test
	public void givenEmptyByteArrayWhenMarshalThenReturnNull() {
		try {
			String actual = adapter.marshal(new byte[]{});
			assertThat(actual, is( nullValue() ));
		} catch (Exception e) {
			fail("Test should pass, there is probably sth wrong with charset");
		}
	}
	
	@Test
	public void givenNullByteArrayWhenMarshalThenReturnNull() {
		try {
			String actual = adapter.marshal(null);
			assertThat(actual, is( nullValue() ));
		} catch (Exception e) {
			fail("Test should pass, there is probably sth wrong with charset");
		}
	}

	@Test
	public void givenInputStringWhenUnmarshalThenReturnByteArray() {
		try {
			byte[] actual = adapter.unmarshal(string);
			assertThat(actual, is( equalTo(byteArray) ));
		} catch (Exception e) {
			fail("Test should pass, there is probably sth wrong with charset");
		}
	}
	
	@Test
	public void givenNullInputStringWhenUnmarshalThenReturnNull() {
		try {
			byte[] actual = adapter.unmarshal(null);
			assertThat(actual, is( nullValue() ));
		} catch (Exception e) {
			fail("Test should pass, there is probably sth wrong with charset");
		}
	}
	
	@Test
	public void givenEmptyInputStringWhenUnmarshalThenReturnNull() {
		try {
			byte[] actual = adapter.unmarshal("");
			assertThat(actual, is( nullValue() ));
		} catch (Exception e) {
			fail("Test should pass, there is probably sth wrong with charset");
		}
	}

}
