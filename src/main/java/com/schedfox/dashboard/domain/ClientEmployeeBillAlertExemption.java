package com.schedfox.dashboard.domain;

import java.io.Serializable;

/**
 * 
 * @author rnagulapalle
 *
 */
public class ClientEmployeeBillAlertExemption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int clientId;
	private int empoyeeId;
	private double exemptAmount;
	
	public int getClientId() {
		return clientId;
	}
	
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	public int getEmpoyeeId() {
		return empoyeeId;
	}
	
	public void setEmpoyeeId(int empoyeeId) {
		this.empoyeeId = empoyeeId;
	}
	
	public double getExemptAmount() {
		return exemptAmount;
	}
	
	public void setExemptAmount(double exemptAmount) {
		this.exemptAmount = exemptAmount;
	}
	
	@Override
    public String toString() {
        return "ClientEmployeeBillAlertExemption [clientId=" + clientId + ", empoyeeId=" + empoyeeId + ", exemptAmount = " + exemptAmount + "]";
    }
}
