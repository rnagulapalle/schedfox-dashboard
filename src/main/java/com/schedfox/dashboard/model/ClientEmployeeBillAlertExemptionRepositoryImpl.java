package com.schedfox.dashboard.model;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.schedfox.dashboard.domain.ClientEmployeeBillAlertExemption;

@Repository
public class ClientEmployeeBillAlertExemptionRepositoryImpl implements ClientEmployeeBillAlertExemptionRepository {

	private static final Logger logger = LoggerFactory.getLogger(ClientEmployeeBillAlertExemptionRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public void saveOrUpdate(ClientEmployeeBillAlertExemption clientEmployeeBillAlertExemption) {
		
		if (clientEmployeeBillAlertExemption != null) {
			//check whether record already exists
			//if exists update record
			logger.info("Inside saveOrUpdate of ClientEmployeeBillAlertExemptionRepositoryImpl");
			Query selectQuery = getSession().createSQLQuery("SELECT client_id, employee_id FROM champion_db.client_employee_bill_alert_exemptions WHERE client_id = ? AND employee_id = ?;");
			selectQuery.setInteger(0, clientEmployeeBillAlertExemption.getClientId());
			selectQuery.setInteger(1, clientEmployeeBillAlertExemption.getEmpoyeeId());
			selectQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			Map resultSet = (Map)selectQuery.uniqueResult();
			
			if(resultSet == null) {
			
				Query query = getSession().createSQLQuery(
						"INSERT INTO champion_db.client_employee_bill_alert_exemptions (client_id, employee_id, exempt_amount) VALUES (?, ?, ?)");
				query.setInteger(0, clientEmployeeBillAlertExemption.getClientId());
				query.setInteger(1, clientEmployeeBillAlertExemption.getEmpoyeeId());
				query.setDouble(2, clientEmployeeBillAlertExemption.getExemptAmount());
				query.executeUpdate();
				logger.info("Insertion into client_employee_bill_alert_exemption table completed!");
			} else{
				logger.info("Record exists in table client_employee_bill_alert_exemption and updating!");
				Query query = getSession().createSQLQuery(
						"UPDATE champion_db.client_employee_bill_alert_exemptions SET exempt_amount = ? WHERE client_id = ? AND employee_id = ?;");
				query.setDouble(0, clientEmployeeBillAlertExemption.getExemptAmount());
				query.setInteger(1, clientEmployeeBillAlertExemption.getClientId());
				query.setInteger(2, clientEmployeeBillAlertExemption.getEmpoyeeId());
				query.executeUpdate();
			}
			
		}
	}

}
