package backend.jpa.newlog.entity;

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
@Table(name = "LOGGER_EVENT")
@Access(AccessType.FIELD)
public class LoggerEvent extends AutoIncrementEntry {

	private static final long serialVersionUID = 2203669897287893893L;
	
	@Column(name = "LOG_DATE", nullable = false, columnDefinition = "DATETIME")
	private LocalDateTime logDate;
	
	@Column(name = "DETAILS", nullable = false, length = 500)
	private String eventDetails;
	
	@ManyToOne
	@JoinColumn(name = "LOGGER_LOGIN_ID", nullable = false, updatable = true, insertable = true)
	private LoggerLogin loggerLogin;
	
	public LoggerEvent() {
		super();
	}

	public LocalDateTime getLogDate() {
		return logDate;
	}

	public void setLogDate(LocalDateTime logDate) {
		this.logDate = logDate;
	}

	public LoggerLogin getLoggerLogin() {
		return loggerLogin;
	}

	public void setLoggerLogin(LoggerLogin loggerLogin) {
		this.loggerLogin = loggerLogin;
	}

	public String getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}
	
	@Override
	public void update(Entry entry) {
		super.update(entry);
		LoggerEvent loggerEvent = (LoggerEvent) entry;
		this.eventDetails = loggerEvent.getEventDetails();
		this.logDate = loggerEvent.getLogDate();
		this.loggerLogin = loggerEvent.getLoggerLogin();
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((eventDetails == null) ? 0 : eventDetails.hashCode());
		result = prime * result + ((logDate == null) ? 0 : logDate.hashCode());
		result = prime * result + ((loggerLogin == null) ? 0 : loggerLogin.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoggerEvent other = (LoggerEvent) obj;
		if (eventDetails == null) {
			if (other.eventDetails != null)
				return false;
		} else if (!eventDetails.equals(other.eventDetails))
			return false;
		if (logDate == null) {
			if (other.logDate != null)
				return false;
		} else if (!logDate.equals(other.logDate))
			return false;
		if (loggerLogin == null) {
			if (other.loggerLogin != null)
				return false;
		} else if (!loggerLogin.equals(other.loggerLogin))
			return false;
		return true;
	}
	
}
