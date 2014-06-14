package com.onyas.oa.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.onyas.oa.base.BaseDaoImpl;
import com.onyas.oa.domain.User;
import com.onyas.oa.service.UserService;

@Service
public class UserServiceImpl extends BaseDaoImpl<User> implements UserService{

	@Override
	public User getUserByLoginNameAndPwd(String loginName, String password) {

		return (User) getSession().createQuery(
				"From User u WHERE u.loginName = ? AND u.password = ?")
				.setParameter(0, loginName)
				.setParameter(1, DigestUtils.md5Hex(password))//需要加密后匹配
				.uniqueResult();
	}

}
