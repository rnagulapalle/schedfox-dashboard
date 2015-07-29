package com.schedfox.dashboard.model;

import java.util.List;

import com.schedfox.dashboard.domain.Branch;
import java.util.Date;

public interface BranchRepository {

    public List<Branch> getBranchList(Date startDate, Date endDate);

}
