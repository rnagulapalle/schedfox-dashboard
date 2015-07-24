package com.schedfox.dashboard.bootstrapper.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.schedfox.dashboard.bootstrapper.config.AppConfiguration;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfiguration.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AppTest extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(AppTest.class);
//	@Autowired
//    private SessionFactory sessionFactory;
	
	private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
	
	@Test
	public void testConnection() {
		logger.info("inside test connection!");
//		SessionFactory sessionFactory = new Configuration().configure()
//				.buildSessionFactory();
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		System.out.println("this is test");
		System.out.println(this.emf.createEntityManager());
	}
}