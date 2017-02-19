package backend.jpa;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class SequenceEntry extends Entry {
	private static final long serialVersionUID = 7719134198391161976L;
	
	@Id
	@SequenceGenerator(name="ENTRY_ID_GENERATOR", sequenceName="SEQUENCE_NAME")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ENTRY_ID_GENERATOR")
	@Access(AccessType.PROPERTY)
	public Long getId() {
		return super.getId();
	}
	public void setId(Long id) {
		super.setId(id);
	}

	public abstract void loadLazy();
	
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
	
	@Override
	public void update(Entry entry) {
		super.update(entry);
	}
}
