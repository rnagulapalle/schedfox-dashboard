package com.schedfox.dashboard.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ProfitAnalysisResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String branchName;
	private int branchId;
	private Map branchMetrics;
	private List<Location> locations;
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public Map getBranchMetrics() {
		return branchMetrics;
	}
	public void setBranchMetrics(Map branchMetrics) {
		this.branchMetrics = branchMetrics;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

}
