package com.onyas.oa.service;

import java.util.List;

import com.onyas.oa.domain.Role;

public interface RoleService {

	List<Role> findAll();

	void delete(Long id);

	void save(Role role);

	Role getById(Long id);

	void update(Role role);

}
