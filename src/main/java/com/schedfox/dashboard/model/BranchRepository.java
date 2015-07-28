package com.schedfox.dashboard.model;

import java.util.List;

import com.schedfox.dashboard.domain.Branch;

public interface BranchRepository {
	public List<Branch> getBranchDetails();

	public String getBranch();
	
	public List getBranchList();
	
	public Object getBranchMetrics(String branchId);
	
	public List getBranchLocationsAndMetrics(String branchId);
	
	public List getLocationEmplyeeMetrics(String branchId, String locationId);
}