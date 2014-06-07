package com.onyas.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

//@Transactional中有这个注解可以被子类继承，所以加在父类上面，所有的方法都会加下事务，相反，如果只在子类加，父类方法不会有事务
@SuppressWarnings("unchecked")
@Transactional
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz;

	
	public BaseDaoImpl() {
		//在创建子类的时候通过反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class) pt.getActualTypeArguments()[0];
		System.out.println(clazz.getName());
	}
	
	@Override
	public void save(T entity) {
		getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public void delete(Long id) {
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
	}

	@Override
	public T getById(Long id) {
		return (T) getSession().get(clazz, id);
	}

	@Override
	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}
		return getSession().createQuery(
				"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")
				.setParameterList("ids", ids).list();
	}

	@Override
	public List<T> findAll() {
		return getSession().createQuery("FROM " + clazz.getSimpleName()).list();
	}

	/**
	 * 获得当前可用的Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
