package backend.jpa.entities.log;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.helpers.MessageFormatter;

import backend.helper.MessageHelper;
import backend.jpa.AutoIncrementEntry;

public abstract class LogAbstractEvent extends AutoIncrementEntry {
	
	public static final char PARAMS_CONCATENATION_CHAR = '~';

	private static final long serialVersionUID = 7460552191622310799L;
	
	protected String messageBase;
	protected String[] messageParams;
	
	protected Date date;
	
	LogAbstractEvent() {}
	LogAbstractEvent(String key) {
		this.messageBase = MessageHelper.getMessage(key, null);
    }
    LogAbstractEvent(String key, String ... parameters) {
    	this(key);
    	this.messageParams = parameters;
    }
	
    public String getMessageBase() {
		return messageBase;
	}
	public String[] getMessageParams() {
		return messageParams;
	}
	
	public LogAbstractEvent addExtraParameter(String param) {
		if (messageParams == null || messageParams.length == 0) {
			messageParams = new String[] {param};
		} else {
			messageParams = (String[]) ArrayUtils.add(messageParams, param);
		}
		
		return this;
	}
	
	public String getConcatenatedMessageParams() {
		return concatenate(messageParams);
	}
	public void setConcatenatedMessageParams(String params) {
		if (params == null || params.length() == 0) {
			messageParams = null;
			return;
		}
		messageParams = params.split("" + PARAMS_CONCATENATION_CHAR);
	}
	
	public static String concatenate(String[] params) {
		if (params  == null || params.length == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		for(String param : params) {
			sb.append(param).append(PARAMS_CONCATENATION_CHAR);
		}
		
		return sb.toString();
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public LogAbstractEvent addDate(Date date) {
		this.date = date;
		return this;
	}

	public void loadLazy() {}

	@Override
	public String toString() {
		if (messageParams == null) {
			return messageBase;
		} else {
			return MessageFormatter.arrayFormat(messageBase, messageParams).getMessage();
		}
	}
	
	public void copyParams(LogAbstractEvent event) {
		event.messageBase = this.messageBase;
		event.messageParams = this.messageParams;
		event.date = this.date;
	}
}
