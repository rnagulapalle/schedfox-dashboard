package com.schedfox.dashboard.model;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
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

	/*
	 * this method gets all branches
	 * (non-Javadoc)
	 * @see com.schedfox.dashboard.model.BranchRepository#getBranchList()
	 */
	@Override
	public List getBranchList() {
		logger.info("Inside branch list");
		SQLQuery query = getSession().createSQLQuery("SELECT branch_id, branch_name FROM control_db.branch WHERE branch_management_id = 1 order by branch_name asc");
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	/**
	 * this method gets a branch metrics for barchart
	 */

	@Override
	public Map getBranchMetrics(String branchId) {
		// TODO Auto-generated method stub
		logger.info("Inside branch metrics..");
		
		StringBuilder query = new StringBuilder();
		query.append("select * from ( ");
		query.append("SELECT client.branch_id, ");
		query.append("(SUM(COALESCE(paid_amount, 0)) / SUM(greatest(COALESCE (bill_amount, 0), 1)) * 100) AS percent, ");
		query.append("SUM(paid_amount) as paidamt, SUM(bill_amount) as billamt ");
		query.append("FROM (SELECT DATE(date_trunc('week', doy)) as my_date FROM champion_db.generate_date_series('2015-07-06', '2015-07-14') ");
		query.append("GROUP BY DATE(date_trunc('week', doy)) ORDER BY DATE(date_trunc('week', doy))) as mydates ");
		query.append("LEFT JOIN champion_db.client ON 1 = 1 AND client.client_name != '' AND client.client_is_deleted != 1 AND client.branch_id != 4 ");
		query.append("LEFT JOIN champion_db.get_client_pay_amounts('2015-04-01', '2015-07-23',");
		query.append(branchId);
		query.append("\\:\\:integer, null\\:\\:integer[]) as amt ON amt.week_started = mydates.my_date ");
		query.append("AND client.client_id = cid ");
//		query.append("WHERE (client.branch_id  =");
//		query.append(branchId);
//		query.append(" OR ");
//		query.append(branchId);
//		query.append("=-1) ");
		query.append("GROUP BY client.branch_id ) a where  a.paidamt!=0.00 or a.paidamt is not null or a.billamt!=0.00 or a.billamt is not null");
		logger.info("Query to be run for branch metrics...");
		logger.info(query.toString());
		SQLQuery sqlQuery = getSession().createSQLQuery(query.toString());
		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return (Map) sqlQuery.uniqueResult();
	}

	/**
	 * this method gets branch location/clients and its metrics
	 */
	@Override
	public List getBranchLocationsAndMetrics(String branchId) {
		
		logger.info("fetching branch client locations and metrics");
		StringBuilder query = new StringBuilder();
		query.append("select * from ( ");
		query.append("SELECT client.client_name,client.branch_id,client.client_id,mydates.my_date,");
		query.append(" (SUM(COALESCE(paid_amount, 0)) / SUM(greatest(COALESCE (bill_amount, 0), 1)) * 100) AS percent, ");
		query.append("SUM(paid_amount) as paidamt, SUM(bill_amount) as billamt  ");
		query.append("FROM (SELECT DATE(date_trunc('week', doy)) as my_date FROM champion_db.generate_date_series('2014-06-01', '2015-06-01') ");
		query.append("GROUP BY DATE(date_trunc('week', doy)) ORDER BY DATE(date_trunc('week', doy))) as mydates ");
		query.append("LEFT JOIN champion_db.client ON 1 = 1 AND client.client_name != '' AND client.client_is_deleted != 1 AND client.branch_id != 4  ");
		query.append("LEFT JOIN champion_db.get_client_pay_amounts('2015-04-01','2015-07-23', ");
		query.append(branchId);
		query.append("\\:\\:integer, null\\:\\:integer[]) as amt ON amt.week_started = mydates.my_date ");
		query.append("AND client.client_id = cid ");
		query.append("GROUP BY client.client_name,client.client_id,mydates.my_date,client.branch_id ORDER BY client.client_name ASC,mydates.my_date) a where  a.paidamt!=0.00 or a.paidamt is not null or a.billamt!=0.00 or a.billamt is not null");
		
		logger.info("Query to be run for location metrics...");
		logger.info(query.toString());
		
		SQLQuery sqlQuery = getSession().createSQLQuery(query.toString());
		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}
	
	/**
	 * this method gets all location employees and metrics
	 */
	@Override
	public List<Object> getLocationEmplyeeMetrics(String branchId, String locationId) {
		logger.info("inside get employee metrics for branchId " + branchId + " and locationId " + locationId);
		StringBuilder query = new StringBuilder();
		query.append("select ");
		query.append("ename as employee_name, SUM(paid_amount) as paidamt, SUM(bill_amount) as billamt ,");
		query.append("(SUM(COALESCE(paid_amount, 0)) / SUM(greatest(COALESCE (bill_amount, 0), 1)) * 100) AS percent ");
		query.append("from champion_db.get_client_pay_amounts('2015-04-01', '2015-07-23'," + branchId +", null\\:\\:integer["+locationId+"]) amt ");
		query.append("inner join champion_db.employee as emp  ON amt.eid = emp.employee_id where employee_first_name != '' and  emp.employee_is_deleted != 1 ");
		query.append("GROUP BY ename order by ename asc");
		
		logger.info("Query to be run for employees by location metrics...");
		logger.info(query.toString());
		SQLQuery sqlQuery = getSession().createSQLQuery(query.toString());
		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return sqlQuery.list();
	}

	@Override
	public List<Branch> getBranchDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBranch() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}