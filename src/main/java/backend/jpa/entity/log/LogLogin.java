package backend.jpa.entity.log;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import backend.jpa.AutoIncrementEntry;
import backend.jpa.Entry;
import backend.jpa.LazyLoading;

@Entity
@Table(name = "LOGGER_LOGIN")
@Access(AccessType.FIELD)
//@Customizer(LogDBEventCustomizer.class)
public class LogLogin extends AutoIncrementEntry implements LazyLoading {

	private static final long serialVersionUID = 6427616661843775840L;
	
	@Column(name = "USER_LOGIN", nullable = false, length = 40)
	private String userName;
	
	@Column(name = "AGENT_NAME", nullable = true, length = 100)
	private String agentName;
	
	@Column(name = "LOG_DATE", nullable = false, columnDefinition = "DATETIME")
	private LocalDateTime logDate;
	
	@OneToMany(mappedBy = "logLogin", fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<LogEvent> logEvents;
	
	public LogLogin() {
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

	public LocalDateTime getLogDate() {
		return logDate;
	}

	public void setLogDate(LocalDateTime logDate) {
		this.logDate = logDate;
	}

	public List<LogEvent> getLoggerEvents() {
		return logEvents;
	}

	public void setLoggerEvents(List<LogEvent> logEvents) {
		this.logEvents = logEvents;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	@Override
	public void update(Entry entry) {
		super.update(entry);
		LogLogin logLogin = (LogLogin) entry;
		this.logDate = logLogin.getLogDate();
		this.agentName = logLogin.getAgentName();
		this.userName = logLogin.getUserName();
		this.logEvents = logLogin.getLoggerEvents();
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((agentName == null) ? 0 : agentName.hashCode());
		result = prime * result + ((logDate == null) ? 0 : logDate.hashCode());
		result = prime * result + ((logEvents == null) ? 0 : logEvents.hashCode());
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
		LogLogin other = (LogLogin) obj;
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
		if (logEvents == null) {
			if (other.logEvents != null)
				return false;
		} else if (!logEvents.equals(other.logEvents))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
