package backend.jpa.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the COMPANY_RIGHTS database table.
 * 
 */
@Entity
@Table(name="COMPANY_RIGHTS")
@NamedQuery(name="CompanyRight.findAll", query="SELECT c FROM CompanyRight c")
public class CompanyRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMPANY_RIGHTS_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPANY_RIGHTS_ID_GENERATOR")
	private long id;

	private String comments;

	@Column(name="ULLA_ID")
	private String ullaId;

	//bi-directional many-to-many association to Group
	@ManyToMany(mappedBy="companyRights")
	private List<Group> groups;

	//bi-directional many-to-one association to ContractCompanyRight
	@OneToMany(mappedBy="companyRight")
	private List<ContractCompanyRight> contractCompanyRights;

	public CompanyRight() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUllaId() {
		return this.ullaId;
	}

	public void setUllaId(String ullaId) {
		this.ullaId = ullaId;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<ContractCompanyRight> getContractCompanyRights() {
		return this.contractCompanyRights;
	}

	public void setContractCompanyRights(List<ContractCompanyRight> contractCompanyRights) {
		this.contractCompanyRights = contractCompanyRights;
	}

	public ContractCompanyRight addContractCompanyRight(ContractCompanyRight contractCompanyRight) {
		getContractCompanyRights().add(contractCompanyRight);
		contractCompanyRight.setCompanyRight(this);

		return contractCompanyRight;
	}

	public ContractCompanyRight removeContractCompanyRight(ContractCompanyRight contractCompanyRight) {
		getContractCompanyRights().remove(contractCompanyRight);
		contractCompanyRight.setCompanyRight(null);

		return contractCompanyRight;
	}

}