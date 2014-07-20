package com.onyas.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.onyas.oa.cfg.Configuration;
import com.onyas.oa.domain.PageBean;
import com.onyas.oa.utils.HqlHelper;

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

	@Override
	public PageBean getPageBean(int pageNum, String hql, Object[] parameter) {
		System.out.println("===========================BaseDaoImpl.getPageBean()");
		//读取配置文件得到每页多少条
		int pageSize = Configuration.getPageSize();
		
		//获取分页数据
		Query listQuery = getSession().createQuery(hql);
		if(parameter!=null&& parameter.length>=1){
			for(int i=0;i<parameter.length;i++){
				listQuery.setParameter(i,parameter[i]);
			}
		}
		listQuery.setFirstResult((pageNum-1)*pageSize);
		listQuery.setMaxResults(pageSize);
		List recordList=listQuery.list();;
		
		//查询总记录数
		Query countQuery = getSession().createQuery("SELECT COUNT(*) "+hql);
		if(parameter!=null&& parameter.length>=1){
			for(int i=0;i<parameter.length;i++){
				countQuery.setParameter(i,parameter[i]);
			}
		}
		Long count=(Long)countQuery.uniqueResult();
		
		return new PageBean(recordList, count.intValue(), pageNum, pageSize);
	}
	
	
	
	
	// 最终版
	public PageBean getPageBean(int pageNum, HqlHelper hqlHelper) {
		System.out.println("--------------> BaseDaoImpl.getPageBean( int pageNum, HqlHelper hqlHelper )");
		int pageSize = Configuration.getPageSize();
		List<Object> parameters = hqlHelper.getParameters();

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(hqlHelper.getQueryListHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数
		Query countQuery = getSession().createQuery(hqlHelper.getQueryCountHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(list, count.intValue(), pageNum, pageSize);
	}

	
	
	
	
}
