package com.onyas.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onyas.oa.base.BaseDaoImpl;
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

}
