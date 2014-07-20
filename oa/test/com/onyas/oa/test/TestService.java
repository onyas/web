package com.onyas.oa.test;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onyas.oa.domain.User;

@Service("testService")
public class TestService {

	@Resource
	private SessionFactory sessionFactory;
	
	@Transactional
	public void saveTwoUsers(){
		Session session=sessionFactory.getCurrentSession();
		session.save(new User());
//		int a = 1/0;
		session.save(new User());
	}
	
	@Transactional
	public void saveUsers(){
		Session session=sessionFactory.getCurrentSession();
		for(int i=0;i<24;i++){
			User user = new User();
			user.setName("test="+i);
			session.save(user);
		}
	}
}
