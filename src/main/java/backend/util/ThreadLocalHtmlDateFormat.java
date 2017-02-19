package backend.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalHtmlDateFormat {
	
	private static final String DEFAULT_HTML_DATE_FORMAT = "yyyy-MM-dd";
	private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(DEFAULT_HTML_DATE_FORMAT);
		}
	};

	public synchronized String format(Date date) {
		return df.get().format(date);
	}

	public synchronized Date parse(String string) throws ParseException {
		return df.get().parse(string);
	}
}