package backend.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.adapter.BigDecimalAdapter;

public class BigDecimalAdapterUnitTest {
	
	private BigDecimalAdapter adapter;

	@Before
	public void setUp() throws Exception {
		adapter = new BigDecimalAdapter();
	}

	@After
	public void tearDown() throws Exception {
		adapter = null;
	}

	@Test
	public void givenNumberFormatWhenCheckNumberFormatThenReturnProperFormat() {
		assertTrue(BigDecimalAdapter.DEFAULT_XML_NUMBER_FORMAT instanceof DecimalFormat);
		DecimalFormat format = (DecimalFormat) BigDecimalAdapter.DEFAULT_XML_NUMBER_FORMAT;
		
		assertEquals("#0.##", format.toPattern());
		assertEquals('.', format.getDecimalFormatSymbols().getMonetaryDecimalSeparator());
		assertEquals(',', format.getDecimalFormatSymbols().getGroupingSeparator());
		assertEquals(';', format.getDecimalFormatSymbols().getPatternSeparator());
	}
	
	@Test
	public void givenAdapterWhenUnmarshalStringThenReturnBigDecimal() throws Exception {
		String value = "100.0001";
		assertEquals("100.00", adapter.unmarshal(value).toString());
		value = "100.00001";
		assertEquals("100.00", adapter.unmarshal(value).toString());
		value = "100000.50501";
		assertEquals("100000.51", adapter.unmarshal(value).toString());
		value = "-100000.50401";
		assertEquals("-100000.50", adapter.unmarshal(value).toString());
		value = "0";
		assertEquals("0.00", adapter.unmarshal(value).toString());
		value = "-0";
		assertEquals("0.00", adapter.unmarshal(value).toString());
		value = "1234567891456123";
		assertEquals("1234567891456123.00", adapter.unmarshal(value).toString());
		value = "12345678914561239999999";
		assertNotEquals("12345678914561239999999.00", adapter.unmarshal(value).toString());
	}
	
	@Test
	public void givenAdapterWhenUnmarshalStringThenThrowException() throws Exception {
		try{
			adapter.unmarshal("");
			fail("Should be ParseException");
		} catch(ParseException ex){
			assertEquals("Unparseable number: \"\"", ex.getMessage());
		}
		try{
			adapter.unmarshal(null);
			fail("Should be NullPointerException");
		} catch(NullPointerException ex){
			assertNotNull(ex);
		}
	}

	@Test
	public void givenAdapterWhenMarshalThenReturnProperString() throws Exception {
		BigDecimal digit = new BigDecimal("0");
		assertEquals("0", adapter.marshal(digit));
		digit = new BigDecimal("0.0");
		assertEquals("0", adapter.marshal(digit));
		digit = new BigDecimal("1.0");
		assertEquals("1", adapter.marshal(digit));
		digit = new BigDecimal("-1.012");
		assertEquals("-1.01", adapter.marshal(digit));
		digit = new BigDecimal("15556666.55555");
		assertEquals("15556666.56", adapter.marshal(digit));
		digit = new BigDecimal("1234567891456123");
		assertEquals("1234567891456123", adapter.marshal(digit));
		digit = new BigDecimal("123456789145612356765677.678954321");
		assertEquals("123456789145612356765677.68", adapter.marshal(digit));
		digit = new BigDecimal("-123456789145612356765677.67895432135544");
		assertEquals("-123456789145612356765677.68", adapter.marshal(digit));
	}
	
	@Test
	public void givenNullDigitWhenMarshalThenThrowExceptions() throws Exception {
		try{
			adapter.marshal(null);
			fail("Should be IllegalArgumentException.");
		} catch(IllegalArgumentException ex){
			assertNotNull(ex);
		}
		
		try{
			adapter.marshal(null);
			fail("Should be IllegalArgumentException.");
		} catch(IllegalArgumentException ex){
			assertNotNull(ex);
		}
	}
}
