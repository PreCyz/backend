package backend.jpa.entities.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="LOG_THROWABLE")
@NamedQuery(name="LogThrowable.findAll", query="SELECT l FROM LogThrowable l")
public class LogThrowable implements Serializable {
	private static final long serialVersionUID = -4301596839618302131L;

	public LogThrowable() {}
	
	public LogThrowable(String message, long position) {
		this.message = message;
		this.position = position;
	}
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="THROWABLE_ID_GENERATOR", sequenceName="LOG_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="THROWABLE_ID_GENERATOR")
	private Long id;
	
	@Column(name="I")
	private Long position;
	
	@Column(name="\"MESSAGE\"")
	private String message;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPosition() {
		return position;
	}
	public void setPosition(Long position) {
		this.position = position;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "LogThrowable [id=" + id + ", position=" + position
				+ ", message=" + message + "]";
	}
	
}
