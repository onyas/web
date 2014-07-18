package com.onyas.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onyas.oa.base.BaseDaoImpl;
import com.onyas.oa.domain.Forum;
import com.onyas.oa.service.ForumService;

@SuppressWarnings("unchecked")
@Service
public class ForumServiceImpl extends BaseDaoImpl<Forum> implements
		ForumService {

	@Override
	public void moveDown(long id) {
		// 获取要交换的两个Forum
		Forum self = getById(id);
		Forum other = (Forum) getSession().createQuery(
				"FROM Forum f where f.position >? ORDER BY f.position ASC")
				.setParameter(0, self.getPosition())
				.setFirstResult(0)
				.setMaxResults(1)
				.uniqueResult();

		//如果已经在最上面，则直接返回
		if(other==null){
			return ;
		}
		
		// 交换position的值
		int temp = self.getPosition();
		self.setPosition(other.getPosition());
		other.setPosition(temp);

		// 更新到数据库中
		// 因为是持久化状态，所以不需要调用update方法
	}

	@Override
	public void moveUp(long id) {
		// 获取要交换的两个Forum
		Forum self = getById(id);
		Forum other = (Forum) getSession().createQuery(
				"FROM Forum f where f.position <? ORDER BY f.position DESC")
				.setParameter(0, self.getPosition())
				.setFirstResult(0)
				.setMaxResults(1)
				.uniqueResult();

		//如果已经在最上面，则直接返回
		if(other==null){
			return ;
		}
		
		// 交换position的值
		int temp = self.getPosition();
		self.setPosition(other.getPosition());
		other.setPosition(temp);

		// 更新到数据库中
		// 因为是持久化状态，所以不需要调用update方法
	}

	@Override
	public List<Forum> findAll() {
		return getSession().createQuery("FROM Forum f order by f.position ASC")
				.list();
	}

	@Override
	public void save(Forum forum) {
		// 保存到DB，会生成相应id的值
		getSession().save(forum);

		// 指定position的值为最大
		forum.setPosition(forum.getId().intValue());
	}

}
