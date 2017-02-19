package backend.adapter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.adapter.PhoneAdapter;

public class PhoneAdapterUnitTest {
	private PhoneAdapter adapter; 

	@Before
	public void setUp() throws Exception {
		adapter = new PhoneAdapter();
	}

	@After
	public void tearDown() throws Exception {
		adapter = null;
	}

	@Test
	public void givenPhoneWithCountryNumber48WhenMarshalThenReturnPhoneWithoutCountry() throws Exception {
		String phoneNumber = "+48224602222";
		String expectedPhone = "224602222";
		String actualPhone = adapter.marshal(phoneNumber);
		assertEquals(expectedPhone, actualPhone);
	}
	
	@Test
	public void givenPhoneWithoutCountryNumberWhenMarshalThenReturnTheSamePhone() throws Exception {
		String phoneNumber = "224602222";
		String actualPhone = adapter.marshal(phoneNumber);
		assertEquals(phoneNumber, actualPhone);
	}
	
	@Test
	public void givenPhoneWithCountryNumberDifferentThan48WhenMarshalThenReturnTheSamePhone() throws Exception {
		String phoneNumber = "+001224602222";
		String actualPhone = adapter.marshal(phoneNumber);
		assertEquals(phoneNumber, actualPhone);
	}
	
	@Test
	public void givenNullPhoneWhenMarshalThenReturnNull() throws Exception {
		assertNull(adapter.marshal(null));
	}
	
	@Test
	public void givenEmptyPhoneWhenMarshalThenReturnEmptyPhone() throws Exception {
		assertEquals("", adapter.marshal(""));
	}
	
	@Test
	public void givenPhoneOnlyWithCountryNumber48WhenMarshalThenReturnEmptyPhone() throws Exception {
		assertEquals("", adapter.marshal("+48"));
	}
	
	@Test
	public void givenPhoneWithSpacesWhenMarshalThenReturnTheSamePhone() throws Exception {
		assertEquals("+4 8", adapter.marshal("+4 8"));
	}
	
	@Test
	public void givenSomeStringWhenMarshalThenReturnThisString() throws Exception {
		String someString = "someString";
		assertEquals(someString, adapter.marshal(someString));
	}

	@Test
	public void givenNullStringWhenUnmarshalThenReturnNull() throws Exception {
		assertNull(adapter.unmarshal(null));
	}
	
	@Test
	public void givenEmptyStringWhenUnmarshalThenReturnEmptyString() throws Exception {
		assertEquals("", adapter.unmarshal(""));
	}
	
	@Test
	public void givenNotEmptyStringWithLengthLowerThen9WhenUnmarshalThenReturnThisString() throws Exception {
		String someString = "someString";
		assertEquals(someString, adapter.unmarshal(someString));
	}
	
	@Test
	public void givenNotEmptyStringWithLengthEqual9WhenUnmarshalThenReturnStringWithCountryNumber48() throws Exception {
		String someString = "lengthEq9";
		String expectedString = String.format("+48%s", someString);
		assertEquals(expectedString, adapter.unmarshal(someString));
	}
	
	@Test
	public void givenNotEmptyNumberWithLengthEqual9WhenUnmarshalThenReturnPhoneNumberWithCountryNumber48() throws Exception {
		String someString = "+48456789";
		String expectedString = String.format("+48%s", someString);
		assertEquals(expectedString, adapter.unmarshal(someString));
	}
}
