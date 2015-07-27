package com.schedfox.dashboard.model;

import java.util.List;

import com.schedfox.dashboard.domain.Branch;

public interface BranchRepository {
	public List<Branch> getBranchDetails();

	public String getBranch();
	
	public List getBranchList();
}