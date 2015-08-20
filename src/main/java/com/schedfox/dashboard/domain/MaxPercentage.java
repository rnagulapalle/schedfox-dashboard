package com.schedfox.dashboard.domain;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class MaxPercentage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long accountId;
	private double percentage;
	private String type;
	private long employeeId;
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
    public String toString() {
        return "MaxPercentage [accountId=" + accountId + ", percentage=" + percentage + ", type = " + type + " , employeeId = " + employeeId +"]";
    }
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
}
