package backend.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import backend.jpa.entities.log.LogEvent;
import backend.util.BackendConstants;

public class UllaDateHelper {
	
	private static final Log logger = LogFactory.getLog(UllaDateHelper.class);
	
	public static int dateToUlla(Date date) {
		if (date == null) {
			return 0;
		}
		int first = Integer.parseInt(BackendConstants.DEFAULT_ULLA_DATE_FORMATTER.format(date));
		int second = dateToUllaSecondary(date);
		int third = (int)(dateToUllaDateTime(date) / 1000000);
		
		if (first != second || first != third) {
			logger.error(new LogEvent("error.daoHelper.dateToUllaConversionFails", "" + first, "" + second, "" + third));
		}
		
		return second;
	}
	
	private static int dateToUllaSecondary(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return 10000 * cal.get(Calendar.YEAR) + 100 * (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH);
	}
	
	private static long dateToUllaDateTime(Date date) {
		if (date == null) {
			return 0;
		}
		return Long.parseLong(BackendConstants.DEFAULT_ULLA_DATE_TIME_FORMATTER.format(date));
	}
	
	public static int dateToUllaTime(Date date) {
		if (date == null) {
			return 0;
		}
		return Integer.parseInt(BackendConstants.DEFAULT_ULLA_TIME_FORMATTER.format(date));
	}

}
