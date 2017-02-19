package backend.jpa.entities.log;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import backend.jpa.Entry;

@MappedSuperclass
public abstract class LogSequenceEntry extends Entry {
	private static final long serialVersionUID = 7987152447354287976L;
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="LOG_ENTRY_ID_GENERATOR", sequenceName="LOG_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_ENTRY_ID_GENERATOR")
	@Access(AccessType.PROPERTY)
	public Long getId() {
		return super.getId();
	}
	public void setId(Long id) {
		super.setId(id);
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
	
	@Override
	public void update(Entry entry) {
		super.update(entry);
	}
}
