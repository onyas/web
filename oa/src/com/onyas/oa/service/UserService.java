package com.onyas.oa.service;

import com.onyas.oa.base.BaseDao;
import com.onyas.oa.domain.User;

public interface UserService extends BaseDao<User>{

	User getUserByLoginNameAndPwd(String loginName, String password);

}
