package com.schedfox.dashboard.bootstrapper.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

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
        return properties;
    }

    @Bean(name = "dataSource")
    public Jdbc3PoolingDataSource dataSource() {
        try {
            
            Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
            source.setDataSourceName("schedfox-ds1");
            source.setServerName("66.196.247.157");
            source.setDatabaseName("unique_manage");
            source.setUser("dbuser");
            source.setPassword("goldenwest");
            source.setPortNumber(5432);
            source.setMaxConnections(10);
            return source;
//            ComboPooledDataSource ds = new ComboPooledDataSource();
//            ds.setDriverClass(driverClassName);
//            ds.setJdbcUrl(url);
//            ds.setUser(userName);
//            ds.setPassword(password);
//            ds.setAcquireIncrement(5);
//            ds.setIdleConnectionTestPeriod(60);
//            ds.setMaxPoolSize(100);
//            ds.setMaxStatements(50);
//            ds.setMinPoolSize(10);
//            ds.setAcquireRetryAttempts(50);
//            ds.setTestConnectionOnCheckout(true);
//            ds.setTestConnectionOnCheckin(true);
//            ds.setMaxPoolSize(30);
//            ds.setMinPoolSize(5);
//            ds.setPreferredTestQuery("Select 1;");
//            return ds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
        sessionFactoryBuilder.scanPackages("com.schedfox.dashboard.entity").addProperties(getHibernateProperties());
        return sessionFactoryBuilder.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
