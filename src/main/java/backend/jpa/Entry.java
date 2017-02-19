package backend.jpa;

import java.io.Serializable;

public abstract class Entry implements Serializable {
	
	private static final long serialVersionUID = -6893249505923633181L;

	protected Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public void update(Entry entry) {
		this.id = entry.getId();
	}
	
	public abstract void loadLazy();
}