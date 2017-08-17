/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.demo.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import com.demo.utils.*;;







/**
 * Main configuration class for the application.
 * Turns on @Component scanning, loads externalized application.properties, and sets up the database.
 * 
 */

@Configuration
@ComponentScan("com.demo")
@PropertySource("demo")
@EnableTransactionManagement
@EnableScheduling
@EnableAspectJAutoProxy
public class MainConfig {
	
	@Inject
	private Environment environment;
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		if(environment == null)  {
			System.out.println("Null");
			System.out.println("Mod");
		}
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(environment.getProperty("jdbc.demo.url"));
		dataSource.setDriverClassName(environment.getProperty("jdbc.demo.dbdriver"));
		dataSource.setUsername(environment.getProperty("jdbc.demo.username"));
		dataSource.setPassword(environment.getProperty("jdbc.demo.password"));
		dataSource.setMaxActive(200);
		dataSource.setMinIdle(5);
		dataSource.setMaxIdle(10);
		dataSource.setMaxWait(10000);
		dataSource.setInitialSize(10);
		dataSource.setTimeBetweenEvictionRunsMillis(35000);
		dataSource.setMinEvictableIdleTimeMillis(30000);
		dataSource.setRemoveAbandonedTimeout(60);
		dataSource.setMinEvictableIdleTimeMillis(35000);
		dataSource.setMinIdle(10);
		dataSource.setLogAbandoned(true);
		dataSource.setRemoveAbandoned(true);
		dataSource.setTestWhileIdle(false);
		dataSource.setTestOnBorrow(true);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setTestOnReturn(false);

		return dataSource;
	}
 
	
	public @Bean(name="manager") OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
		final OpenEntityManagerInViewFilter entityManagerInViewFilter = new OpenEntityManagerInViewFilter();
		entityManagerInViewFilter.setEntityManagerFactoryBeanName("manager");
		return entityManagerInViewFilter;
		}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { 
				new ClassPathResource("demo.properties") };
		propertySources.setLocations(resources);
		propertySources.setIgnoreUnresolvablePlaceholders(true);
		return propertySources;

	}
	
	@Bean
	public static PropertyUtil properties() {
		PropertyUtil propertySources = new PropertyUtil();
		propertySources.setSystemPropertiesMode(PropertyUtil.SYSTEM_PROPERTIES_MODE_NEVER);
		Resource[] resources = new ClassPathResource[] { 
				new ClassPathResource("demo.properties")};
		propertySources.setLocations(resources);
		propertySources.setIgnoreUnresolvablePlaceholders(true);
		return propertySources;
	}
	
	
	@Bean
    public PlatformTransactionManager jpaTransMan() {
    	
    	EntityManagerFactory factory = this.getEntityManagerFactoryBean().getObject();
    	JpaTransactionManager jtManager = new JpaTransactionManager(factory);
        return jtManager;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(this.dataSource());
        lcemfb.setPackagesToScan("com.demo.model.*");
        lcemfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        lcemfb.setPersistenceUnitName("localContainerEntity");

        LoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        lcemfb.setLoadTimeWeaver(loadTimeWeaver);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "true");
       

        lcemfb.setJpaProperties(properties);
        lcemfb.afterPropertiesSet(); 
        return lcemfb;
    }
    
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setCorePoolSize(10);
    pool.setMaxPoolSize(25);
    pool.setWaitForTasksToCompleteOnShutdown(true);
    return pool;
    }
    
    

}
