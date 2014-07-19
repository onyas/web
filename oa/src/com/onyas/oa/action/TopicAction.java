package com.onyas.oa.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.onyas.oa.base.BaseAction;
import com.onyas.oa.domain.Forum;
import com.onyas.oa.domain.Topic;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic> {

	private Long forumId;
	
	/** 显示单个主题(主贴加回贴列表) */
	public String show() throws Exception {
		return "show";
	}

	/** 添加主题页面 */
	public String addUI() throws Exception {
		//准备数据:forum
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum",forum);
		return "addUI";
	}

	/** 添加主题 */
	public String add() throws Exception {
		// 封装
		// >> 表单中的字段, 已经封装了 title, content, faceIcon
		model.setForum(forumService.getById(forumId));

		// >> 当前可以直接获取的信息
		model.setAuthor(getCurrentUser()); // 作者，当前登录的用户
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr()); // IP地址，当前请求中的IP信息
		model.setPostTime(new Date()); // 发表时间，当前时间

		// >> 应放到业务方法中的一些其他设置
		// model.setType(type);
		// model.setReplyCount(replyCount);
		// model.setLastReply(lastReply);
		// model.setLastUpdateTime(lastUpdateTime);

		// 保存
		topicService.save(model);

		return "toShow"; // 转到新主题的显示页面
	}

	
	
	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
}
