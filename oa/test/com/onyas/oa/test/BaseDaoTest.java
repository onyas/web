package com.onyas.oa.test;

import org.junit.Test;

import com.onyas.oa.dao.RoleDao;
import com.onyas.oa.dao.UserDao;
import com.onyas.oa.dao.impl.RoleDaoImpl;
import com.onyas.oa.dao.impl.UserDaoImpl;

public class BaseDaoTest {

	@Test
	public void testBaseDao() throws Exception {
		UserDao userDao = new UserDaoImpl();
		RoleDao roleDao = new RoleDaoImpl();
	}
	
}
