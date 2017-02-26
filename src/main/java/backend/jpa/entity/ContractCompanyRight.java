package backend.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


//@Entity
@Table(name="CONTRACT_COMPANY_RIGHT")
@NamedQuery(name="ContractCompanyRight.findAll", query="SELECT c FROM ContractCompanyRight c")
public class ContractCompanyRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTRACT_COMPANY_RIGHT_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRACT_COMPANY_RIGHT_ID_GENERATOR")
	private long id;

	private BigDecimal levels;

	//bi-directional many-to-one association to CompanyRight
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="COMPANY_RIGHTS_ID")
	private CompanyRight companyRight;

	//bi-directional many-to-one association to ContractGroup
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CONTRACT_GROUP_ID")
	private ContractGroup contractGroup;

	public ContractCompanyRight() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getLevels() {
		return this.levels;
	}

	public void setLevels(BigDecimal levels) {
		this.levels = levels;
	}

	public CompanyRight getCompanyRight() {
		return this.companyRight;
	}

	public void setCompanyRight(CompanyRight companyRight) {
		this.companyRight = companyRight;
	}

	public ContractGroup getContractGroup() {
		return this.contractGroup;
	}

	public void setContractGroup(ContractGroup contractGroup) {
		this.contractGroup = contractGroup;
	}

}