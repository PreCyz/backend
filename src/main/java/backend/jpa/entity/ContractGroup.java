package backend.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


//@Entity
@Table(name="CONTRACT_GROUP")
@NamedQuery(name="ContractGroup.findAll", query="SELECT c FROM ContractGroup c")
public class ContractGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTRACT_GROUP_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_GROUP_ID_GENERATOR")
	private long id;

	private String description;

	private String name;

	//bi-directional many-to-one association to ContractCompanyRight
	@OneToMany(mappedBy="contractGroup")
	private List<ContractCompanyRight> contractCompanyRights;

	//bi-directional many-to-many association to ContractRole
	@ManyToMany(mappedBy="contractGroups")
	private List<ContractRole> contractRoles;

	public ContractGroup() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContractCompanyRight> getContractCompanyRights() {
		return this.contractCompanyRights;
	}

	public void setContractCompanyRights(List<ContractCompanyRight> contractCompanyRights) {
		this.contractCompanyRights = contractCompanyRights;
	}

	public ContractCompanyRight addContractCompanyRight(ContractCompanyRight contractCompanyRight) {
		getContractCompanyRights().add(contractCompanyRight);
		contractCompanyRight.setContractGroup(this);

		return contractCompanyRight;
	}

	public ContractCompanyRight removeContractCompanyRight(ContractCompanyRight contractCompanyRight) {
		getContractCompanyRights().remove(contractCompanyRight);
		contractCompanyRight.setContractGroup(null);

		return contractCompanyRight;
	}

	public List<ContractRole> getContractRoles() {
		return this.contractRoles;
	}

	public void setContractRoles(List<ContractRole> contractRoles) {
		this.contractRoles = contractRoles;
	}

}