package backend.helper;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import backend.helper.BigDecimalHelper;

public class BigDecimalHelperUnitTest {

	@Test
	public void whenCurrencyNumberToBigDecimalThenProperResult() {
		assertEquals(null, BigDecimalHelper.currencyNumberToBigDecimal(null));
		assertEquals(new BigDecimal("10.00"), BigDecimalHelper.currencyNumberToBigDecimal(10));
		assertEquals(new BigDecimal("2.60"), BigDecimalHelper.currencyNumberToBigDecimal(2.6));
		assertEquals(new BigDecimal("0.00"), BigDecimalHelper.currencyNumberToBigDecimal(0));
		assertEquals(new BigDecimal("-3.00"), BigDecimalHelper.currencyNumberToBigDecimal(-3));
	}
	
	@Test
	public void whenDivideCurrencyThenProperResult() {
		BigDecimal d1 = new BigDecimal(10);
		BigDecimal d2 = new BigDecimal(2);
		assertEquals(new BigDecimal("5.00"), BigDecimalHelper.divideCurrency(d1, d2));
		d1 = new BigDecimal(5);
		assertEquals(new BigDecimal("2.50"), BigDecimalHelper.divideCurrency(d1, d2));
	}
	
	@Test
	public void whenCurrencyDoubleToBigDecimalThenProperResult() {
		assertEquals(null, BigDecimalHelper.currencyDoubleToBigDecimal(null));
		assertEquals(new BigDecimal("10.00"), BigDecimalHelper.currencyDoubleToBigDecimal(10d));
		assertEquals(new BigDecimal("5.50"), BigDecimalHelper.currencyDoubleToBigDecimal(5.5d));
	}

	@Test
	public void whenAddCurrencyWithNullsThenProperResult() {
		assertEquals(new BigDecimal("0.00"), BigDecimalHelper.addCurrencyWithNulls(null, null));
		BigDecimal d1 = new BigDecimal(5);
		BigDecimal d2 = new BigDecimal(2);
		assertEquals(new BigDecimal("5.00"), BigDecimalHelper.addCurrencyWithNulls(d1, null));
		assertEquals(new BigDecimal("7.00"), BigDecimalHelper.addCurrencyWithNulls(d1, d2));
		assertEquals(new BigDecimal("2.00"), BigDecimalHelper.addCurrencyWithNulls(null, d2));
	}
	
}
