package com.onyas.oa.base;

import java.util.List;

import com.onyas.oa.domain.PageBean;
import com.onyas.oa.utils.HqlHelper;


public interface BaseDao<T> {

	/**
	 * 增加实体
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * 删除实体
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * 根据Id查找
	 */
	T getById(Long id);
	
	/**
	 * 根据Ids查找
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);
	
	/**
	 * 查询所有实体
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 提取公共的分页方法
	 * @param pageNum
	 * @param hql
	 * @param parameter
	 * @return
	 */
	PageBean getPageBean(int pageNum, String hql, Object[] parameter);


	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param pageNum
	 * @param hqlHelper
	 *            查询条件（HQL语句与参数列表）
	 * @return
	 */
	PageBean getPageBean(int pageNum, HqlHelper hqlHelper);
}
