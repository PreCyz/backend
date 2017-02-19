package backend.jpa.entities.log;

import java.util.Date;

import javax.persistence.Entity;

import org.apache.log4j.Level;

@Entity
public class LogEvent extends LogAbstract {

	private static final long serialVersionUID = 1936259711546518641L;
	
	protected String contractNumber;
	protected String ullaTaskId;
	protected String explicitMessage;
	protected boolean ignoreStack;
	protected String messageBase;
	protected String[] messageParams;
	protected String key;
	protected Date logDate;
	protected Level level;
	
	public LogEvent() {
		super();
	}
	
	public LogEvent(String key) {
		this.key = key;
	}
	
	public LogEvent(String key, String ... parameters) {
		this.key = key;
		this.messageParams = parameters;
	}
	
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	
	public String getUllaTaskId() {
		return ullaTaskId;
	}

	public void setUllaTaskId(String ullaTaskId) {
		this.ullaTaskId = ullaTaskId;
	}

	public boolean isIgnoreStack() {
		return ignoreStack;
	}

	public void setIgnoreStack(boolean ignoreStack) {
		this.ignoreStack = ignoreStack;
	}

	@Override
	public void loadLazy() {
		// TODO Auto-generated method stub
		
	}

	public LogEvent addContractNumber(String contractNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public LogEvent addUllaTaskId(String ullaTaskId) {
		// TODO Auto-generated method stub
		return null;
	}

	public LogEvent ignoreStack() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getExplicitMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public LogEvent addExplicitMessage(String explicitMessage2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void copyParams(LogEvent copy) {
		// TODO Auto-generated method stub
		
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	
	public void setLevel(Level level) {
		// TODO Auto-generated method stub
		
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getLogLoginId() {
		// TODO Auto-generated method stub
		return null;
	}

	public LogLogin getLogLogin() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setMsgTemplate(Long msgTemplate) {
		// TODO Auto-generated method stub
		
	}

	public void setLevel(String level) {
		// TODO Auto-generated method stub
		
	}
	
	public void setMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	public void setConcatenatedMessageParams(String params) {
		// TODO Auto-generated method stub
		
	}

	public void setLogLogin(LogLogin newLogLogin) {
		// TODO Auto-generated method stub
		
	}

	public Object getLevel() {
		// TODO Auto-generated method stub
		return null;
	}

}
