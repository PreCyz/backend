package backend.jpa;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

@MappedSuperclass
@TableGenerator(name="tableGenerator", initialValue = 0, allocationSize = 1, schema = "springDB")
public class AutoIncrementEntry implements Entry, Serializable {
	
	private static final long serialVersionUID = -6893249505923633181L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
	protected Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public void update(Entry entry) {
		this.id = entry.getId();
	}
	
}