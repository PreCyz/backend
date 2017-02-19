package backend.util;

import java.text.ParseException;
import java.util.Date;

public final class BackendConstants {
	
	public static final String DEFAULT_HTML_DATE_FORMAT = "yyyy-MM-dd";
	public static final ThreadLocalHtmlDateFormat DEFAULT_HTML_DATE_FORMATTER = new ThreadLocalHtmlDateFormat();
	
	public static final String DEFAULT_ULLA_DATE_FORMAT = "yyyyMMdd";
	public static final String DEFAULT_ULLA_TIME_FORMAT = "HHmmss";
	public static final String DEFAULT_ULLA_DATE_TIME_FORMAT = DEFAULT_ULLA_DATE_FORMAT + DEFAULT_ULLA_TIME_FORMAT;
	public static final ThreadSafeDateFormat DEFAULT_ULLA_DATE_FORMATTER = new ThreadSafeDateFormat(DEFAULT_ULLA_DATE_FORMAT);
	public static final ThreadSafeDateFormat DEFAULT_ULLA_TIME_FORMATTER = new ThreadSafeDateFormat(DEFAULT_ULLA_TIME_FORMAT);
	public static final ThreadSafeDateFormat DEFAULT_ULLA_DATE_TIME_FORMATTER = new ThreadSafeDateFormat(DEFAULT_ULLA_DATE_TIME_FORMAT);
	
	public static Date ULLA_INFINITY_DATE_RANGE = null;
	static {
		try {
			ULLA_INFINITY_DATE_RANGE = DEFAULT_ULLA_DATE_FORMATTER.parse("29900101");
		} catch (ParseException e) {
			throw new RuntimeException();
		}
	}
	
	public static final String GSM_POLISH_AREA_CODE = "+48";
	
	public static final int LOG_MAX_DATA_SIZE = 4000 - 100;//100 zapasu na ~ itp; Ogólnie by nie było kłopotów
	
	private BackendConstants() {}
}
