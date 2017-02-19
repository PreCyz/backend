package backend.util;

import java.text.ParseException;
import java.util.Date;

public class TimeService {	
	private static Long ullaDate;
	private static long realStartDay;

	private static final long DAY = 1000*60*60*24;
	public static long currentTimeMillis() {
		if (ullaDate != null) {
			return ullaDate + ((System.currentTimeMillis() - realStartDay) % DAY);
		} else {
			return System.currentTimeMillis();
		}
	}
	
	public Date getCurrentTime() {
		if (ullaDate != null) {
			return new Date(ullaDate + ((System.currentTimeMillis() - realStartDay) % DAY));
		} else {
			return new Date(System.currentTimeMillis());
		}
	}
	
	public Date getUllaDate() {
		if (ullaDate == null) {
			return null;
		}
		return new Date(ullaDate);
	}
	
	public static void setUllaDate(Date date) {
		ullaDate = date == null ? null : date.getTime();
		if (ullaDate != null) {
			try {
				realStartDay = BackendConstants.DEFAULT_HTML_DATE_FORMATTER.parse(BackendConstants.DEFAULT_HTML_DATE_FORMATTER.format(new Date())).getTime();
			} catch (ParseException e) {}
		}
	}
	
}
