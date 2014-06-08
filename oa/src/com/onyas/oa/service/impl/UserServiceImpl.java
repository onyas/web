package com.onyas.oa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onyas.oa.base.BaseDaoImpl;
import com.onyas.oa.domain.User;
import com.onyas.oa.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseDaoImpl<User> implements UserService{

}
