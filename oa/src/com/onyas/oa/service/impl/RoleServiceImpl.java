package com.onyas.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onyas.oa.dao.RoleDao;
import com.onyas.oa.domain.Role;
import com.onyas.oa.service.RoleService;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleDao roleDao;
	
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Override
	public void delete(Long id) {
		roleDao.delete(id);
	}

	@Override
	public void save(Role role) {
		roleDao.save(role);
	}

	@Override
	public Role getById(Long id) {
		return roleDao.getById(id);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

}
