package backend.adapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import backend.helper.BigDecimalHelper;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

	public static final NumberFormat DEFAULT_XML_NUMBER_FORMAT = new DecimalFormat("###########0.##", new DecimalFormatSymbols(new Locale("en")));
	
	@Override
	public String marshal(BigDecimal v) throws Exception {
		return DEFAULT_XML_NUMBER_FORMAT.format(v);
	}

	@Override
	public BigDecimal unmarshal(String v) throws Exception {
		return BigDecimalHelper.currencyNumberToBigDecimal((Number)DEFAULT_XML_NUMBER_FORMAT.parse(v));
	}


}
