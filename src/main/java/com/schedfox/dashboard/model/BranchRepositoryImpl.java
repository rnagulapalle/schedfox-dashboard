package com.schedfox.dashboard.model;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.schedfox.dashboard.domain.Branch;

@Repository
public class BranchRepositoryImpl implements BranchRepository {
	// private Logger log = LoggerFactory.getLogger(BranchRepositoryImpl.class);
	private static final Logger logger = LoggerFactory.getLogger(BranchRepositoryImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public List<Branch> getBranchDetails() {
		@SuppressWarnings("unchecked")
		List<Branch> branch = getSession().createSQLQuery("select * from control_db.branch where branch_id = 2").list();
		System.out.println("Branch details :: " + branch);
		return branch;
	}

	@Override
	public String getBranch() {
		String branch = getSession().createSQLQuery("select branch_name from control_db.branch where branch_id = 2")
				.toString();
		logger.info("Inside repo");
		logger.info(branch);
		return branch;
	}

	@Override
	public List getBranchList() {
		SQLQuery query = getSession().createSQLQuery("SELECT * FROM champion_db.client LIMIT 10");
				
		logger.info("Inside repo list");
		
		return query.list();
	}
}