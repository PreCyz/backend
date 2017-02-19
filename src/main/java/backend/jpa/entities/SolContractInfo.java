package backend.jpa.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import backend.jpa.converters.BooleanToStringConverter;

@Entity
@Table(name="SOL_CONTRACT_INFO")
@NamedQuery(name="SolContractInfo.findAll", query="SELECT s FROM SolContractInfo s")
@Access(AccessType.FIELD)
public class SolContractInfo implements Serializable {
	private static final long serialVersionUID = -4309729642654916105L;

	@Id
	@Column(name="CONTRACTNO")
	private String contractNumber;
	
	@Column(name="FULL_SURRENDER_ORDERED")
	@Convert(converter=BooleanToStringConverter.class)
	private Boolean fullSurrenderOrdered = Boolean.FALSE;
	
	public SolContractInfo() {}
	
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public Boolean isFullSurrenderOrdered() {
		return fullSurrenderOrdered;
	}
	public void setFullSurrenderOrdered(Boolean fullSurrenderOrdered) {
		this.fullSurrenderOrdered = fullSurrenderOrdered;
	}
	
}
