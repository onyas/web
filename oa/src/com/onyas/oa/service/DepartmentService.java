package com.onyas.oa.service;

import java.util.List;

import com.onyas.oa.domain.Department;

public interface DepartmentService {

	List<Department> findAll();

	void delete(Long id);

	void save(Department model);

	Department getById(Long id);

	void update(Department department);

}
