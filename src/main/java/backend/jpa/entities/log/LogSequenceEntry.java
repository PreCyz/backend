package backend.jpa.entities.log;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

//@MappedSuperclass
public abstract class LogSequenceEntry {
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="LOG_ENTRY_ID_GENERATOR", sequenceName="LOG_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_ENTRY_ID_GENERATOR")
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LogSequenceEntry other = (LogSequenceEntry) obj;
		if (id == null) {
			return false;
		} else if (!id.equals(other.getId())) return false;
		return true;
	}
	
}
