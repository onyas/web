package com.onyas.oa.test;

import org.hibernate.SessionFactory;
import org.jbpm.api.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	private ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	/**
	 * 测试Spring的SessionFactory
	 * 可以生成相应的表
	 * @throws Exception
	 */
	@Test
	public void testSessionFactory() throws Exception {
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory);
		//创建表的时候，因为jbpm的原因会报错，所以手动建表，建表语句见createTable.txt
	}

	/**
	 * 测试Spring的事务
	 * @throws Exception
	 */
	@Test
	public void testTransaction() throws Exception {
		TestService testService = (TestService) ac.getBean("testService");
		testService.saveUsers();
	}
	
	

	
	// 测试ProcessEngine
	@Test
	public void testProcessEngine() throws Exception {
		ProcessEngine processEngine = (ProcessEngine) ac.getBean("processEngine");
		System.out.println("---> " + processEngine);
	}
}
