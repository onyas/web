package com.onyas.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onyas.oa.base.BaseDaoImpl;
import com.onyas.oa.domain.Forum;
import com.onyas.oa.domain.Topic;
import com.onyas.oa.service.TopicService;

@Service
public class TopicServicImpl extends BaseDaoImpl<Topic> implements TopicService {

	@Override
	public List<Topic> findByForum(Forum forum) {
		return getSession()
		//TODO:排序:所有置顶贴都在最上面，然后把最新状态的主题显示到前面
		.createQuery("FROM Topic t WHERE t.forum=? ORDER BY t.type DESC,t.lastUpdateTime DESC")
		.setParameter(0, forum)
		.list();
	}

}
