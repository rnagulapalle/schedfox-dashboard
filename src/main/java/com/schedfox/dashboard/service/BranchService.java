package com.schedfox.dashboard.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schedfox.dashboard.bootstrapper.controllers.HomeController;
import com.schedfox.dashboard.domain.Branch;
import com.schedfox.dashboard.model.BranchRepository;

@Service("branchService")
public class BranchService {

	private static final Logger logger = LoggerFactory.getLogger(BranchService.class);
	@Autowired
	BranchRepository branchRepo;

	public List<Branch> getBranchDetails() {
		List<Branch> list = branchRepo.getBranchDetails();
		return list;
	}

	@Transactional
	public List getBranch() {
		logger.info("YYYYYYYYYYYYYYYYYYY");
		List branchName = branchRepo.getBranchList();
		logger.info(String.valueOf(branchName));
		return branchName;
	}
}