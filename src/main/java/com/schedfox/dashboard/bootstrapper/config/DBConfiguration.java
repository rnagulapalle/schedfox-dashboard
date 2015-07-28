package com.schedfox.dashboard.bootstrapper.config;

import com.schedfox.dashboard.domain.Branch;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class DBConfiguration {

    @Value("${hibernate.format_sql}")
    private String formatSql;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${mysql.driver}")
    private String driverClassName;

    @Value("${mysql.user}")
    private String userName;

    @Value("${mysql.password}")
    private String password;

    @Value("${mysql.url}")
    private String url;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.format_sql", formatSql);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        return properties;
    }

    @Bean
    public BasicDataSource dataSource() {
        try {
//			BasicDataSource source = new BasicDataSource();
//			source.setDriverClassName("org.postgresql.Driver");
//			source.setUrl("jdbc:postgresql://schedfoxdb.schedfox.com/unique_manage");
//			source.setUsername("schedfox_user");
//			source.setPassword("sch3df0x1ng");
//			source.setMaxOpenPreparedStatements(-1);
//			source.setTestOnBorrow(true);
//			source.setValidationQuery("SELECT 1;");
////			source.setMaxActive(20);

            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(driverClassName);
            ds.setUrl(url);
            ds.setUsername(userName);
            ds.setPassword(password);
            ds.setValidationQuery("SELECT 1;");
            ds.setInitialSize(10);
            ds.setTestOnBorrow(true);
            ds.setMaxOpenPreparedStatements(-1);
//			 PGPoolingDataSource source = new PGPoolingDataSource();
//			 source.setDataSourceName("schedfox-ds1");
//			 source.setServerName("66.196.247.157");
//			 source.setDatabaseName("unique_manage");
//			 source.setUser("dbuser");
//			 source.setPassword("gate8844");
//			 source.setPortNumber(5432);
////			 source.setMaxConnections(10);
            return ds;
//			 ComboPooledDataSource ds = new ComboPooledDataSource();
//			 ds.setDriverClass(driverClassName);
//			 ds.setJdbcUrl(url);
//			 ds.setUser(userName);
//			 ds.setPassword(password);
//			 ds.setAcquireIncrement(5);
//			 ds.setIdleConnectionTestPeriod(60);
//			 ds.setMaxPoolSize(100);
//			 ds.setMaxStatements(50);
//			 ds.setMinPoolSize(10);
//			 ds.setAcquireRetryAttempts(50);
//			 ds.setTestConnectionOnCheckout(true);
//			 ds.setTestConnectionOnCheckin(true);
//			 ds.setMaxPoolSize(30);
//			 ds.setMinPoolSize(5);
//			 ds.setPreferredTestQuery("Select 1;");
//			 return ds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(dataSource());
//		// em.setPackagesToScan(new String[] { "com.schedfox.dashboard.model"
//		// });
//
//		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		em.setJpaVendorAdapter(vendorAdapter);
//		em.setJpaProperties(getHibernateProperties());
//
//		return em;
//	}
//	@Bean
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(emf);
//
//		return transactionManager;
//	}
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//		return new PersistenceExceptionTranslationPostProcessor();
//	}
    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
        sessionFactoryBuilder.scanPackages("com.schedfox.dashboard.domain").addProperties(getHibernateProperties());
        return sessionFactoryBuilder.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

//	@Bean
//	public JpaTransactionManager transactionManager() {
//		return new JpaTransactionManager();
//	}
}
