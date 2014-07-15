package com.onyas.oa.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户
 * 
 * @author tyg
 * 
 */
public class User {
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();

	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明

	/**
	 * 判断用户是否有指定名称的权限
	 * 
	 * @return
	 */
	public boolean hasPrivilegeByName(String prvilegeName) {
		//如果是超级管理员，则有所有权限
		if(isAdmin()){
			return true;
		}
		//如果是其他用户，则进行判断
		for (Role role : roles) {
			for (Privilege privilege : role.getPrivileges()) {
				if (privilege.getName().equals(prvilegeName)) {
					return true;
				}
			}
		}
		return false;
	}

	
	
	/**
	 * 判断用户是否有指定URL的权限
	 * 
	 * @return
	 */
	public boolean hasPrivilegeByUrl(String prvilegeUrl) {
		//如果是超级管理员，则有所有权限
		if(isAdmin()){
			return true;
		}
		
		//如果以UI结尾，就去掉UI后缀，以得到对应的权限（例如：addUI与add是同一权限）
		if(prvilegeUrl.endsWith("UI")){
			prvilegeUrl = prvilegeUrl.substring(0,prvilegeUrl.length()-2);
		}
		
		//如果是其他用户，则进行判断
		List<String> allPrivilegeUrl = (List<String>) ActionContext.getContext().getApplication().get("allPrivilegeList");
		if(!allPrivilegeUrl.contains(prvilegeUrl)){//如果不是要控制的功能，则所有用户都可以使用(URL不在数据库中)
			return true;
		}else{//如果是要控制的功能，则有权限才能使用
			for (Role role : roles) {
				for (Privilege privilege : role.getPrivileges()) {
					if (prvilegeUrl.equals(privilege.getUrl())) {
						return true;
					}
				}
			}
			return false;
		}
		
	}
	
	
	private boolean isAdmin() {
		return "admin".equals(loginName);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
