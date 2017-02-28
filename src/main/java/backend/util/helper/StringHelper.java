package backend.util.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringHelper {
	
	public static String safeUpperCase(String string) {
		return string == null ? null : string.toUpperCase();
	}
	
	public static String cutStringToLength(String string, int max) {
		if (string == null || string.length() <= max || max < 0) {
			return string;
		}
		return string.substring(0, max);
	}
	
	public static boolean empty(String string) {
		return string == null || string.length() == 0;
	}
	
	public static boolean notEmpty(String string) {
		return string != null && string.length() > 0;
	}
	
	public static String nullify(String string) {
		if (string == null || string.length() == 0) {
			return null;
		}
		return string;
	}
	
	public static String nullToEmpty(String string) {
		return string == null ? "" : string;
	}
	public static double nullToZero(Double value) {
		return value == null ? 0d : value;
	}
	
	public static boolean equalsWithNulls(String string1, String string2) {
		if (string1 == null) {
			return string2 == null;
		}
		return string1.equals(string2);
	}
	public static boolean isStringChanged(String s1, String s2) {
		if (StringHelper.empty(s1)) {
			return StringHelper.notEmpty(s2);
		}
		return !s1.equals(s2);
	}
	
	public static String encodeURLParam(String urlString) {
		if (StringHelper.empty(urlString)) {
			return urlString;
		}
		
		try {
			return URLEncoder.encode(urlString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);//should not happen
		}
	}
	
	public static String encode(String s, String symbol, String replacement) {
		if (s == null || s.trim().length() == 0)
			return s;

		return s.replaceAll(symbol, replacement);
	}
	
	public static String safeSubString(String string, int beginIndex, int endIndex) {
		int length = string.length();
		beginIndex = (beginIndex < 0) ? 0 : (beginIndex > length ? length : beginIndex);
		endIndex = (endIndex < beginIndex) ? beginIndex : (endIndex > length ? length : endIndex);
				
		return string.substring(beginIndex,	endIndex);
	}

	public static boolean extractBoolean(String value) {
		return "true".equalsIgnoreCase(value);
	}
}
