package com.onyas.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onyas.oa.base.BaseDaoImpl;
import com.onyas.oa.domain.Privilege;
import com.onyas.oa.service.PrivilegeService;
@Service
public class PrivilegeServiceImpl extends BaseDaoImpl<Privilege> implements PrivilegeService{

	@Override
	public List<Privilege> findTopList() {
		return getSession().createQuery("FROM Privilege p where p.parent IS NULL").list();
	}

}
