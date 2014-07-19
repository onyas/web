package com.onyas.oa.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.onyas.oa.base.BaseAction;
import com.onyas.oa.domain.Forum;
import com.onyas.oa.domain.Topic;
import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum>{

	/**  版块列表*/ 
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}

	/**  显示单个版块*/ 
	public String show() throws Exception {
		//准备数据:forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum",forum);
		
		//准备数据:topicList
		List<Topic> topicList = topicService.findByForum(forum);
		ActionContext.getContext().put("topicList", topicList);
		return "show";
	}
	
}
