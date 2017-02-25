package backend.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


//@Entity
@Table(name="CONTRACT_ROLE")
@NamedQuery(name="ContractRole.findAll", query="SELECT c FROM ContractRole c")
public class ContractRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTRACT_ROLE_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_ROLE_ID_GENERATOR")
	private long id;

	private String name;

	//bi-directional many-to-many association to ContractGroup
	@ManyToMany
	@JoinTable(
			name="CONTRACT_GROUP_ROLE", 
			joinColumns={@JoinColumn(name="CONTRACT_ROLE_ID")}, 
			inverseJoinColumns={@JoinColumn(name="CONTRACT_GROUP_ID")}
	)
	private List<ContractGroup> contractGroups;

	public ContractRole() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContractGroup> getContractGroups() {
		return this.contractGroups;
	}

	public void setContractGroups(List<ContractGroup> contractGroups) {
		this.contractGroups = contractGroups;
	}

}