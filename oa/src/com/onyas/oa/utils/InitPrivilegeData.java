package com.onyas.oa.utils;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.onyas.oa.domain.Privilege;
import com.onyas.oa.domain.User;

/**
 * 初始化权限数据
 * @author Administrator
 *
 */
@Component
public class InitPrivilegeData {

	@Resource
	private SessionFactory sessionFactory;
	
	/**
	 * 方法名一定是public的才能有事务功能，不然会报错
	 *  No Hibernate Session bound to thread, 
	 *  and configuration does not allow creation of non-transactional one here
	 */
	@Transactional
	public void install() {
		Session session = sessionFactory.getCurrentSession();
		//====================
		//1、添加超级管理员
		User superUser = new User();
		superUser.setName("超级管理员");
		superUser.setLoginName("admin");
		superUser.setPassword(DigestUtils.md5Hex("admin"));
		session.save(superUser);//保存用户
		
		//2、添加权限数据
		Privilege menu,menu1,menu2,menu3,menu4,menu5;
		
		//------------------------
		menu = new Privilege("系统管理", null, "FUNC20082.gif", null);
		menu1 = new Privilege("岗位管理", "roleAction_list", null, menu);
		menu2 = new Privilege("部门管理", "departmentAction_list", null, menu);
		menu3 = new Privilege("用户管理", "userAction_list", null, menu);
		
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);
		
		session.save(new Privilege("岗位添加", "roleAction_add", null, menu1));
		session.save(new Privilege("岗位删除", "roleAction_delete", null, menu1));
		session.save(new Privilege("岗位修改", "roleAction_edit", null, menu1));
		session.save(new Privilege("岗位列表", "roleAction_list", null, menu1));
		
		session.save(new Privilege("部门添加", "departmentAction_add", null, menu2));
		session.save(new Privilege("部门删除", "departmentAction_delete", null, menu2));
		session.save(new Privilege("部门修改", "departmentAction_edit", null, menu2));
		session.save(new Privilege("部门列表", "departmentAction_list", null, menu2));
		
		session.save(new Privilege("用户添加", "userAction_add", null, menu3));
		session.save(new Privilege("用户删除", "userAction_delete", null, menu3));
		session.save(new Privilege("用户修改", "userAction_edit", null, menu3));
		session.save(new Privilege("用户列表", "userAction_list", null, menu3));
		session.save(new Privilege("用户初始化密码", "userAction_initPassword", null, menu3));
		
		//--------------------
		
		menu = new Privilege("网上交流", null, "FUNC20064.gif", null);
		menu1 = new Privilege("论坛管理", "forumManagerAction_list", null, menu);
		menu2 = new Privilege("论坛", "forumAction_list", null, menu);
	
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		//---------------------
		
		menu = new Privilege("审批流转", null, "FUNC20057.gif", null);
		menu1 = new Privilege("审批流程管理", "processDefinitionAction_list", null, menu);
		menu2 = new Privilege("申请模板管理", "applicationTemplateAction_list", null, menu);
		menu3 = new Privilege("起草申请", "flowAction_applicationTemplatelist", null, menu);
		menu4 = new Privilege("待我审批", "flowAction_myTasklist", null, menu);
		menu5 = new Privilege("我的申请查询", "flowAction_myApplicationlist", null, menu);
		
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);
		session.save(menu4);
		session.save(menu5);
		
	}
	
	public static void main(String[] args) {
		System.out.println("正在初始化权限数据");
		
		ApplicationContext ac = new ClassPathXmlApplicationContext(
		"applicationContext.xml");
		InitPrivilegeData init = (InitPrivilegeData) ac.getBean("initPrivilegeData");
		init.install();
		System.out.println("权限初化数据完成");
	}
	
}
