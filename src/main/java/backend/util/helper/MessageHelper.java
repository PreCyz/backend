package backend.util.helper;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageHelper {
	
	public static final String LOG_BUNDLE_NAME = "loggerKeys.log";
	
	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(LOG_BUNDLE_NAME, new Locale("en"));
	}
	
	public static String getMessage(String key, String[] messageParams) {
		ResourceBundle bundle = getResourceBundle();
		if (bundle == null) {
			return String.format("?????%s?????", key);
		}
		String string = null;
		try {
			string = bundle.getString(key);
		} catch (MissingResourceException e) {
		}
		
		if (string != null) {
			if (messageParams != null) {
				return MessageFormat.format(putNumbersToBrackets(string, 0), (Object[]) messageParams);
			}
			return string;
		} else {
			return "?????" + key + "?????";
		}
	}
	
	public static String getMessageOrNull(String key, String[] messageParams) {
		try{
			return getMessage(key, messageParams);
		} catch(NullPointerException ex){
			return null;
		}
	}
	
	protected static String putNumbersToBrackets(String value, int number) {
		int index = value.indexOf("{}"); 
		if(index != -1) {
			value = value.substring(0, index + 1) + number + value.substring(index + 1);
			value = putNumbersToBrackets(value, ++number);
		}
		return value;
	}
}
