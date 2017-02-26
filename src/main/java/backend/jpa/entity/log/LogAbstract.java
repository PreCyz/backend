package backend.jpa.entity.log;

import java.sql.Timestamp;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.helpers.MessageFormatter;

import backend.helper.MessageHelper;

//@MappedSuperclass
@Access(AccessType.FIELD)
public class LogAbstract extends LogSequenceEntry {
	
	public static final char PARAMS_CONCATENATION_CHAR = '~';

	@Transient
	protected String messageBase;
	@Transient
	protected String[] messageParams;
	
	@Version
	@Column(name="LOG_DATE")
	protected Timestamp logDate;
	
	@Column(name="MSG_TEMPLATE")
	protected Long msgTemplate;
	
	public LogAbstract() {}
	
	public LogAbstract(String key) {
		this.messageBase = MessageHelper.getMessage(key, null);
    }
    public LogAbstract(String key, String ... parameters) {
    	this(key);
    	this.messageParams = parameters;
    }
	
    public final String getMessageBase() {
		return messageBase;
	}
	public String[] getMessageParams() {
		return messageParams;
	}
	
	public LogAbstract addExtraParameter(String param) {
		if (messageParams == null || messageParams.length == 0) {
			messageParams = new String[] {param};
		} else {
			messageParams = (String[]) ArrayUtils.add(messageParams, param);
		}
		
		return this;
	}
	
	@Access(AccessType.PROPERTY)
	@Column(name = "PARAMS")
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
	
	public Timestamp getLogDate() {
		return logDate;
	}
	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}
	public LogAbstract addDate(Timestamp date) {
		this.logDate = date;
		return this;
	}

	public Long getMsgTemplate() {
		return msgTemplate;
	}
	public void setMsgTemplate(Long msgTemplate) {
		this.msgTemplate = msgTemplate;
	}
	@Override
	public String toString() {
		if (messageParams == null) {
			return messageBase;
		} else {
			return MessageFormatter.arrayFormat(messageBase, messageParams).getMessage();
		}
	}
	
	public void copyParams(LogAbstract event) {
		event.messageBase = this.messageBase;
		event.messageParams = this.messageParams;
		event.logDate = this.logDate;
	}
	
	public void update(LogSequenceEntry entry) {
		LogAbstract newEntry = (LogAbstract) entry;
		this.msgTemplate = newEntry.getMsgTemplate();
		this.messageParams = newEntry.getMessageParams();
		this.logDate = newEntry.getLogDate();
	}
}
