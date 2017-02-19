package backend.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalHelper {
	
	public static BigDecimal currencyNumberToBigDecimal(Number value) {
		if (value == null) {
			return null;
		}
		return new BigDecimal(value.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal divideCurrency(BigDecimal d1, BigDecimal d2) {
		return d1.divide(d2, 2, RoundingMode.HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal currencyDoubleToBigDecimal(Double value) {
		if (value == null) {
			return null;
		}
		return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal addCurrencyWithNulls(BigDecimal d1, BigDecimal d2) {
		if (d1 == null) d1 = BigDecimal.ZERO;
		if (d2 == null) d2 = BigDecimal.ZERO;
		
		return d1.add(d2).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
}
