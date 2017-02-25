package backend.jpa.newlog.entities;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import backend.jpa.AutoIncrementEntry;
import backend.jpa.LazyLoading;

@Entity
@Table(name = "LOGGER_LOGIN")
@Access(AccessType.FIELD)
public class LoggerLogin extends AutoIncrementEntry implements LazyLoading {

	private static final long serialVersionUID = 6427616661843775840L;
	
	@Column(name = "USER_LOGIN", nullable = false, length = 40)
	private String userName;
	
	@Column(name = "AGENT_NAME", nullable = true, length = 100)
	private String agentName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOG_DATE", nullable = false, columnDefinition = "DATETIME")
	private Calendar logDate;
	
	@OneToMany(mappedBy = "loggerLogin", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<LoggerEvent> loggerEvents;
	
	public LoggerLogin() {
		super();
	}

	@Override
	public void loadLazy() {
		getLoggerEvents().size();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Calendar getLogDate() {
		return logDate;
	}

	public void setLogDate(Calendar logDate) {
		this.logDate = logDate;
	}

	public List<LoggerEvent> getLoggerEvents() {
		return loggerEvents;
	}

	public void setLoggerEvents(List<LoggerEvent> loggerEvents) {
		this.loggerEvents = loggerEvents;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((agentName == null) ? 0 : agentName.hashCode());
		result = prime * result + ((logDate == null) ? 0 : logDate.hashCode());
		result = prime * result + ((loggerEvents == null) ? 0 : loggerEvents.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoggerLogin other = (LoggerLogin) obj;
		if (agentName == null) {
			if (other.agentName != null)
				return false;
		} else if (!agentName.equals(other.agentName))
			return false;
		if (logDate == null) {
			if (other.logDate != null)
				return false;
		} else if (!logDate.equals(other.logDate))
			return false;
		if (loggerEvents == null) {
			if (other.loggerEvents != null)
				return false;
		} else if (!loggerEvents.equals(other.loggerEvents))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
