package com.onyas.oa.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.onyas.oa.base.BaseAction;
import com.onyas.oa.domain.Topic;

@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic> {

	/** 显示单个主题(主贴加回贴列表) */
	public String show() throws Exception {
		return "show";
	}

	/** 添加主题页面 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/** 添加主题 */
	public String add() throws Exception {
		return "toShow";// 转到新主题的显示界面
	}

}
