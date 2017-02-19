package backend.helper;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

public class BankAccountHelper {
	
	//private static final Log logger = LogFactory.getLog(BankAccountHelper.class);
	private static final int ACCOUNT_LENGTH = 26;
	private static final String EMPTY_STRING = "";

	public static String createAccountFormat(String account) {
		if (account == null || EMPTY_STRING.equals(account)) {
			return EMPTY_STRING;
		}
		account = account.replaceAll("\\D", EMPTY_STRING);
		if (account.length() != ACCOUNT_LENGTH) {
			//String logMsg = String.format("Not correct length of account (%s). Length (%d)", account, account.length());
			//logger.error(logMsg);
			return EMPTY_STRING;
		}

		StringBuilder ret = new StringBuilder(EMPTY_STRING);
		int begin = 0;
		for (int i = 2; i <= ACCOUNT_LENGTH; i += 4) {
			ret.append(account.substring(begin, i)).append(" ");
			begin = i;
		}
		return ret.toString().trim();
	}

}
