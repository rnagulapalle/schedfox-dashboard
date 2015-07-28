package com.schedfox.dashboard.model;

import java.util.Date;
import java.util.List;

import com.schedfox.dashboard.domain.Branch;

public interface BranchRepository {
	public List<Branch> getBranchDetails();

	public String getBranch();
	
	public List getBranchList();
	
	public Object getBranchMetrics(String branchId, Date startDate, Date endDate);
	
	public List getBranchLocationsAndMetrics(String branchId, Date startDate, Date endDate);
	
	public List getLocationEmplyeeMetrics(String branchId, String locationId, Date startDate, Date endDate);
}