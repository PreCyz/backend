package backend.example;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name="partialSurrenderParameters")
@JsonRootName(value="partialSurrenderParameters")
public class PartialSurrenderParameter {
	private double partialSurrenderAmount;
	private String partialSurrenderAccount;
	private String partialSurrenderType;

	public PartialSurrenderParameter() {}

	@JsonCreator
	public PartialSurrenderParameter(
			@JsonProperty("partialSurrenderAmount") double partialSurrenderAmount, 
			@JsonProperty("partialSurrenderAccount") String partialSurrenderAccount, 
			@JsonProperty("partialSurrenderType") String partialSurrenderType
	) {
		this.partialSurrenderAmount = partialSurrenderAmount;
		this.partialSurrenderAccount = partialSurrenderAccount;
		this.partialSurrenderType = partialSurrenderType;
	}

	public double getPartialSurrenderAmount() {
		return partialSurrenderAmount;
	}

	public void setPartialSurrenderAmount(double partialSurrenderAmount) {
		this.partialSurrenderAmount = partialSurrenderAmount;
	}

	public String getPartialSurrenderAccount() {
		return partialSurrenderAccount;
	}

	public void setPartialSurrenderAccount(String partialSurrenderAccount) {
		this.partialSurrenderAccount = partialSurrenderAccount;
	}

	public String getPartialSurrenderType() {
		return partialSurrenderType;
	}

	public void setPartialSurrenderType(String partialSurrenderType) {
		this.partialSurrenderType = partialSurrenderType;
	}
}