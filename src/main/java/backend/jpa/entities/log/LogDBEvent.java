package backend.jpa.entities.log;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Customizer;

import backend.jpa.AutoIncrementEntry;
import backend.jpa.customizer.LogDBEventCustomizer;

@Customizer(LogDBEventCustomizer.class)
public final class LogDBEvent {

	private static final long serialVersionUID = 8713643790887917978L;
	
	public static final char NAMES_CONCATENATION_CHAR = ':';
	
	@Transient
	private Long sessionRefernceId;
	@Transient
	private String sessionId;
	
	@Column(name="DAO_FUNCTION")
	private Long daoFunction;
	
	@Transient
	private String daoFunctionTxt;
	
	@Column(name="FILE_METHOD")
	private Long fileMethod;
	
	@Transient
	private String fileMethodTxt;
	
	@Column(name="LINE_NUMBER")
	private Long lineNumber;
	
	@Transient
	private List<LogThrowable> throwableList;
	
	//is needed for JPA
	public LogDBEvent() {}
	
	public Long getSessionRefernceId() {
		return sessionRefernceId;
	}
	public void setSessionRefernceId(Long sessionRefernceId) {
		this.sessionRefernceId = sessionRefernceId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public Long getDaoFunction() {
		return daoFunction;
	}
	public void setDaoFunction(Long daoFunction) {
		this.daoFunction = daoFunction;
	}

	public void setFileMethod(Long fileMethod) {
		this.fileMethod = fileMethod;
	}
	public Long getFileMethod() {
		return fileMethod;
	}
	
	public void setDaoFunctionTxt(String wholeDaoFunction) {
		this.daoFunctionTxt = wholeDaoFunction;
	}
	public void setDaoFunction(String className, String functionName) {
		this.daoFunctionTxt = className + NAMES_CONCATENATION_CHAR + functionName;
	}
	
	
	public void setFileMethodTxt(String fileMethodTxt) {
		this.fileMethodTxt = fileMethodTxt;
	}
	public void setFileNameAndMethodName(String fileName, String methodName) {
		this.fileMethodTxt = fileName + NAMES_CONCATENATION_CHAR + methodName;
	}
	
	public Long getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		try {
			this.lineNumber = Long.parseLong(lineNumber);
		} catch (NumberFormatException nfe) {
			//just ignore
		}
	}

	public List<LogThrowable> getThrowableList() {
		return throwableList;
	}
	public void setThrowableList(List<LogThrowable> throwableList) {
		this.throwableList = throwableList;
	}
	//TODO remove?
	public boolean hasThrowable() {
		return throwableList != null && throwableList.size() > 0;
	}
	public void addThrowableMessage(int position, String throwableMessage) {
		if (throwableList == null) {
			throwableList = new ArrayList<LogThrowable>();
		}
		throwableList.add(position, new LogThrowable(throwableMessage, position));
	}
	
}
