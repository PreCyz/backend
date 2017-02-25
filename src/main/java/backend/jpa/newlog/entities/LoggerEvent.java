package backend.jpa.newlog.entities;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import backend.jpa.AutoIncrementEntry;

@Entity
@Table(name = "LOGGER_EVENT")
@Access(AccessType.FIELD)
public class LoggerEvent extends AutoIncrementEntry {

	private static final long serialVersionUID = 2203669897287893893L;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "log_date", nullable = false)
	private Date logDate;
	
	@Column(name = "DETAILS", nullable = false, length = 500)
	private String eventDetails;
	
	@ManyToOne
	@JoinColumn(name = "LOGGER_LOGIN_ID", nullable = false)
	private LoggerLogin loggerLogin;
	
	public LoggerEvent() {
		super();
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
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
