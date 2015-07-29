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
import com.schedfox.dashboard.response.EmployeeMetrics;
import com.schedfox.dashboard.response.Location;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.hibernate.Query;

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
    public List getBranchList(Date startDate, Date endDate) {
        logger.info("Inside branch list");

        Query query = getSession().createSQLQuery("SELECT branch_id, branch_name FROM control_db.branch WHERE branch_management_id = ?;");
        query.setInteger(0, 1);
        List tempList = query.list();
        
        ArrayList<Branch> retVal = new ArrayList<Branch>();
        for (int t = 0; t < tempList.size(); t++) {
            Branch currBranch = new Branch();
            currBranch.setBranchName((((Object[])(tempList.get(t)))[1]).toString());
            currBranch.setBranchId(Integer.parseInt((((Object[])(tempList.get(t)))[0]).toString()));
            retVal.add(currBranch);
        }

        StringBuilder allDataQuery = new StringBuilder();
        allDataQuery.append("SELECT * FROM ( ");
        allDataQuery.append("SELECT client.branch_id, client.client_name, client.client_id, ");
        allDataQuery.append("SUM((COALESCE(paid_amount, 0)) / (greatest(COALESCE (bill_amount, 0), 1)) * 100) AS percent, ");
        allDataQuery.append("SUM(paid_amount) as paidamt, SUM(bill_amount) AS billamt, ");
        allDataQuery.append("employee_first_name || ' ' || employee_last_name as empname, employee.employee_id ");
        allDataQuery.append("FROM (SELECT DATE(date_trunc('week', doy)) AS my_date FROM champion_db.generate_date_series(?, ?) ");
        allDataQuery.append("GROUP BY DATE(date_trunc('week', doy)) ORDER BY DATE(date_trunc('week', doy))) as mydates ");
        allDataQuery.append("LEFT JOIN champion_db.client ON 1 = 1 AND client.client_name != '' AND client.client_is_deleted != 1 ");
        allDataQuery.append("LEFT JOIN champion_db.get_client_pay_amounts(?, ?, -1, null\\:\\:integer[]) AS amt ");
        allDataQuery.append("   ON amt.week_started = mydates.my_date AND client.client_id = cid ");
        allDataQuery.append("LEFT JOIN champion_db.employee ON employee.employee_id = amt.eid ");
        allDataQuery.append("GROUP BY client.branch_id, client.client_name, client.client_id, employee_first_name || ' ' || employee_last_name, employee.employee_id) a ");
        allDataQuery.append("WHERE ");
        allDataQuery.append("a.paidamt != 0.00 or a.paidamt is not null or a.billamt != 0.00 or a.billamt is not null");
        SQLQuery sqlQuery = getSession().createSQLQuery(allDataQuery.toString());
        sqlQuery.setDate(0, startDate);
        sqlQuery.setDate(1, endDate);
        sqlQuery.setDate(2, startDate);
        sqlQuery.setDate(3, endDate);

        try {
            sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List allRatios = sqlQuery.list();
            
            HashMap<Integer, Location> locationHash = new HashMap<Integer, Location>();
            
            for (int a = 0; a < allRatios.size(); a++) {
                HashMap currRatio = (HashMap)allRatios.get(a);
                for (int b = 0; b < retVal.size(); b++) {
                    Branch currBranch = retVal.get(b);
                    if (currBranch.getBranchId().equals(currRatio.get("branch_id"))) {
                        Location location = new Location();
                        if (locationHash.containsKey((Integer)currRatio.get("client_id"))) {
                            location = locationHash.get((Integer)currRatio.get("client_id"));
                        } else {
                            location.setLocationId((Integer)currRatio.get("client_id"));
                            location.setBranchId(currRatio.get("branch_id").toString());
                            location.setLocationName(currRatio.get("client_name").toString());
                            currBranch.getLocations().add(location);
                        }
                        
                        EmployeeMetrics empMetrics = new EmployeeMetrics();
                        empMetrics.setEmployeeName(currRatio.get("empname").toString());
                        empMetrics.setBillAmount(((BigDecimal)currRatio.get("billamt")));
                        empMetrics.setPaidAmount(((BigDecimal)currRatio.get("paidamt")));
                        location.getEmployeeMetricsList().add(empMetrics);
                    }
                }
            }
            System.out.println("Here");
        } catch (Exception exe) {
            exe.printStackTrace();
        }
        return retVal;
    }


}
