package com.onyas.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onyas.oa.base.BaseDaoImpl;
import com.onyas.oa.cfg.Configuration;
import com.onyas.oa.domain.Forum;
import com.onyas.oa.domain.PageBean;
import com.onyas.oa.domain.Topic;
import com.onyas.oa.service.TopicService;

@SuppressWarnings("unchecked")
@Service
public class TopicServicImpl extends BaseDaoImpl<Topic> implements TopicService {

	@Override
	public List<Topic> findByForum(Forum forum) {
		return getSession()
		.createQuery("FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC")
		.setParameter(0, forum)
		.list();
	}

	 @Override
	public void save(Topic topic) {
		//1、设置属性并保存
//		 model.setType(Topic.TYPE_NORMAL);//默认为0
//		 model.setReplyCount(0);//默认为0
//		 model.setLastReply(null);//默认为null
		 topic.setLastUpdateTime(topic.getPostTime());
		 getSession().save(topic);
		 
		 //2、维护相关的信息
		 Forum forum = topic.getForum();
		 forum.setArticleCount(forum.getArticleCount()+1);//设置文章数
		 forum.setTopicCount(forum.getTopicCount()+1);//设置主题数加1
		 forum.setLastTopic(topic);//设置最后发表的主题
		 getSession().update(forum);
	 }

	@Deprecated
	@Override
	public PageBean getPageBean(int pageNum, Forum forum) {
		//读取配置文件得到每页多少条
		int pageSize = Configuration.getPageSize();
		
		//获取分页数据
		List recordList=getSession()
		.createQuery("FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END ) DESC,t.lastUpdateTime DESC")
		.setParameter(0, forum)
		.setFirstResult((pageNum-1)*pageSize)
		.setMaxResults(pageSize)
		.list();;
		
		//查询总记录数
		Long count=(Long) getSession()
		.createQuery("SELECT COUNT(*) FROM Topic t WHERE t.forum=?")
		.setParameter(0, forum)
		.uniqueResult();
		
		return new PageBean(recordList, count.intValue(), pageNum, pageSize);
	}
	
}
