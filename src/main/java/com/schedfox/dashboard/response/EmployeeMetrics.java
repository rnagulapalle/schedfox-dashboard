package com.schedfox.dashboard.response;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/**
 * 
 * @author rnagulapalle
 *
 */
@Component
public class EmployeeMetrics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String employeeName;
	private BigDecimal paidAmount;
	private BigDecimal billAmount;
	private BigDecimal percent;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public BigDecimal getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
}
