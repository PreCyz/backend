package backend.jpa;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class SequenceEntry implements Entry, Serializable {
	
	private static final long serialVersionUID = 7719134198391161976L;
	
	@Id
	@SequenceGenerator(name="ENTRY_ID_GENERATOR", sequenceName="SEQUENCE_NAME")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENTRY_ID_GENERATOR")
	protected Long id;
	
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
		SequenceEntry other = (SequenceEntry) obj;
		if (id == null) {
			return false;
		} else if (!id.equals(other.getId())) return false;
		return true;
	}
	
	public void update(SequenceEntry entry) {
		this.id = entry.getId();
	}
}
