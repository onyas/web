package com.onyas.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.onyas.oa.domain.Role;
import com.onyas.oa.service.DepartmentService;
import com.onyas.oa.service.PrivilegeService;
import com.onyas.oa.service.RoleService;
import com.onyas.oa.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected UserService userService;
	@Resource
	protected PrivilegeService privilegeService;
	
	protected T model ;
	
	public BaseAction() {
		//在创建子类的时候通过反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class clazz = (Class) pt.getActualTypeArguments()[0];
		try {
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public T getModel() {
		return model;
	}
}
