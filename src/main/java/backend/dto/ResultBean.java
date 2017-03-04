package backend.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ResultBean {
	
	private String result;
	private String result2;
	private Date resultDate;
	private BigDecimal resultBigDecimal;
	private Double resultDouble;
	
	public ResultBean() {}

	public ResultBean(String result, String result2) {
		this.result = result;
		this.result2 = result2;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String answer) {
		this.result = answer;
	}
	public String getResult2() {
		return result2;
	}
	public void setResult2(String result2) {
		this.result2 = result2;
	}
	public Date getResultDate() {
		return resultDate;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	public BigDecimal getResultBigDecimal() {
		return resultBigDecimal;
	}
	public void setResultBigDecimal(BigDecimal resultBigDecimal) {
		this.resultBigDecimal = resultBigDecimal;
	}
	public Double getResultDouble() {
		return resultDouble;
	}
	public void setResultDouble(Double resultDouble) {
		this.resultDouble = resultDouble;
	}
	@Override
	public String toString() {
		return "ResultBean{result=" + result + ";resultDate=" + resultDate + ";}";
	}
}