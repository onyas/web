package com.onyas.oa.service;

import java.util.List;

import com.onyas.oa.base.BaseDao;
import com.onyas.oa.domain.Privilege;

public interface PrivilegeService extends BaseDao<Privilege>{

	List<Privilege> findTopList();

	/**
	 * 查询所有权限URL的集合(不能重复，不能包含null)
	 * @return
	 */
	List<Privilege> findAllPrivilegeList();

}
