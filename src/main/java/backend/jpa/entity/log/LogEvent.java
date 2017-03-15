package backend.jpa.entity.log;

import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import backend.jpa.AutoIncrementEntry;
import backend.jpa.Entry;

@Entity
@Table(name = "LOG_EVENT", schema = "springDB")
@Access(AccessType.FIELD)
public class LogEvent extends AutoIncrementEntry {

	private static final long serialVersionUID = 2203669897287893893L;
	
	@Column(name = "LOG_DATE", nullable = false, columnDefinition = "DATETIME")
	private LocalDateTime logDate;
	
	@Column(name = "DETAILS", nullable = false, length = 500)
	private String eventDetails;
	
	@Column(name = "CLASS_NAME", nullable = false, length = 500)
	private String className;
	
	@Column(name = "EXCEPTION_DETAILS", nullable = true, length = 1000)
	private String exceptionDetails;
	
	@ManyToOne
	@JoinColumn(name = "LOGGER_LOGIN_ID", nullable = false, updatable = true, insertable = true)
	private LogLogin logLogin;
	
	public LogEvent() {
		super();
	}

	public LocalDateTime getLogDate() {
		return logDate;
	}

	public void setLogDate(LocalDateTime logDate) {
		this.logDate = logDate;
	}

	public LogLogin getLoggerLogin() {
		return logLogin;
	}

	public void setLoggerLogin(LogLogin logLogin) {
		this.logLogin = logLogin;
	}

	public String getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	@Override
	public void update(Entry entry) {
		super.update(entry);
		LogEvent logEvent = (LogEvent) entry;
		this.eventDetails = logEvent.getEventDetails();
		this.logDate = logEvent.getLogDate();
		this.logLogin = logEvent.getLoggerLogin();
		this.exceptionDetails = logEvent.getExceptionDetails();
		this.className = logEvent.getClassName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((eventDetails == null) ? 0 : eventDetails.hashCode());
		result = prime * result + ((exceptionDetails == null) ? 0 : exceptionDetails.hashCode());
		result = prime * result + ((logDate == null) ? 0 : logDate.hashCode());
		result = prime * result + ((logLogin == null) ? 0 : logLogin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogEvent other = (LogEvent) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (eventDetails == null) {
			if (other.eventDetails != null)
				return false;
		} else if (!eventDetails.equals(other.eventDetails))
			return false;
		if (exceptionDetails == null) {
			if (other.exceptionDetails != null)
				return false;
		} else if (!exceptionDetails.equals(other.exceptionDetails))
			return false;
		if (logDate == null) {
			if (other.logDate != null)
				return false;
		} else if (!logDate.equals(other.logDate))
			return false;
		if (logLogin == null) {
			if (other.logLogin != null)
				return false;
		} else if (!logLogin.equals(other.logLogin))
			return false;
		return true;
	}

}
