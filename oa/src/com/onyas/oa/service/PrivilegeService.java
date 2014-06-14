package com.onyas.oa.service;

import java.util.List;

import com.onyas.oa.base.BaseDao;
import com.onyas.oa.domain.Privilege;

public interface PrivilegeService extends BaseDao<Privilege>{

	List<Privilege> findTopList();

}
