package com.onyas.oa.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.onyas.oa.base.BaseDaoImpl;
import com.onyas.oa.domain.Department;
import com.onyas.oa.service.DepartmentService;

@SuppressWarnings("unchecked")
@Service
public class DepartmentServiceImpl extends BaseDaoImpl<Department> implements DepartmentService {
	
	public List<Department> findTopList() {
		return getSession().createQuery(//
				"FROM Department d WHERE d.parent IS NULL")//
				.list();
	}

	public List<Department> findChildren(Long parentId) {
		return getSession().createQuery(//
				"FROM Department d WHERE d.parent.id=?")//
				.setParameter(0, parentId)//
				.list();
	}
}
