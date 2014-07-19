package com.onyas.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onyas.oa.base.BaseDaoImpl;
import com.onyas.oa.domain.Forum;
import com.onyas.oa.domain.Reply;
import com.onyas.oa.domain.Topic;
import com.onyas.oa.service.ReplyService;

@SuppressWarnings("unchecked")
@Service
public class ReplyServicImpl extends BaseDaoImpl<Reply> implements ReplyService{

	@Override
	public List<Reply> findByTopic(Topic topic) {
		return getSession()
		.createQuery("FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")
		.setParameter(0,topic)
		.list();
	}

	@Override
	public void save(Reply reply) {
		//保存到DB
		getSession().save(reply);
		//维护相关的信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();
		
		forum.setArticleCount(forum.getArticleCount()+1);//版块的文章数（主题加回复）
		topic.setReplyCount(topic.getReplyCount()+1);//主题的回复数
		topic.setLastReply(reply);//主题的最后发表的回复
		topic.setLastUpdateTime(reply.getPostTime());//主题的最后更新的时间(主题的发表时间或是最后回复时间)
		
		
		getSession().update(topic);
		getSession().update(forum);
	}
	
}
