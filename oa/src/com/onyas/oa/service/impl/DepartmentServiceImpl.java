package com.onyas.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onyas.oa.dao.DepartmentDao;
import com.onyas.oa.domain.Department;
import com.onyas.oa.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Resource
	private DepartmentDao departmentDao;

	@Override
	public List<Department> findAll() {
		return departmentDao.findAll();
	}

	@Override
	public void delete(Long id) {
		departmentDao.delete(id);
	}
	
	@Override
	public Department getById(Long id) {
		return departmentDao.getById(id);
	}

	@Override
	public void save(Department department) {
		departmentDao.save(department);
	}

	@Override
	public void update(Department department) {
		departmentDao.update(department);
	}
	
	
}
