package com.schedfox.dashboard.model;

import java.util.List;

import com.schedfox.dashboard.domain.Branch;
import java.util.Date;

public interface BranchRepository {

    public List<Branch> getBranchDetails();

    public String getBranch();

    public List<Branch> getBranchList(Date startDate, Date endDate);

    public Object getBranchMetrics(String branchId, Date startDate, Date endDate);

    public List getBranchLocationsAndMetrics(String branchId);

    public List getLocationEmplyeeMetrics(String branchId, String locationId);
}
