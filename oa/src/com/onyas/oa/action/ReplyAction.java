package com.onyas.oa.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.onyas.oa.base.BaseAction;
import com.onyas.oa.domain.Reply;

@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply> {

	/** 发表新回复页面 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/** 发表新回复页面 */
	public String add() throws Exception {
		return "toTopicShow";// 转到新回复所属主题的显示页面
	}
}
