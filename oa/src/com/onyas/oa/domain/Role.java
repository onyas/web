package com.onyas.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 实体：岗位
 * @author Administrator
 *
 */
public class Role {

	private Long id;
	private Set<User> users = new HashSet<User>();
	
	private String name;
	private String description;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
