package com.onyas.oa.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.onyas.oa.domain.Privilege;
import com.onyas.oa.service.PrivilegeService;
import com.sun.accessibility.internal.resources.accessibility;

public class InitServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		//得到Service的实体对象
		ApplicationContext ac=WebApplicationContextUtils.getWebApplicationContext(application);
		PrivilegeService privilegeService =(PrivilegeService) ac.getBean("privilegeServiceImpl");
		//准备所有顶级权限的集合（顶级菜单）
		List<Privilege> topPrivilegeList =privilegeService.findTopList();
		application.setAttribute("topPrivilegeList", topPrivilegeList);
		System.out.println("已准备好顶级权限的数据");
	}

}
