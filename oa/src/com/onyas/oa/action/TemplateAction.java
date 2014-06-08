package com.onyas.oa.action;

import java.util.List;

import com.onyas.oa.base.BaseAction;
import com.onyas.oa.domain.User;

public class TemplateAction extends BaseAction<User> {

	public String list() throws Exception {
		List<User> userList = userService.findAll();
		return "list";
	}

	public String addUI() throws Exception {
		return "addUI";
	}

	public String add() throws Exception {
		return "toList";
	}

	public String editUI() throws Exception {
		return "editUI";
	}

	public String eidt() throws Exception {
		return "toList";
	}

	public String delete() throws Exception {
		return "toList";
	}

}
