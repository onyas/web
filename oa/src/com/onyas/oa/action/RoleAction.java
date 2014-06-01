package com.onyas.oa.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class RoleAction extends ActionSupport{

	/**
	 * 列出所有
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		return "list";
	}
	
	/**
	 * 添加页面
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		return "addUI";
	}
	
	/**
	 * 添加成功
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		return "toList";
	}
	
	/**
	 * 更新
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		return "editUI";
	}
	/**
	 * 修改成功
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		return "toList";
	}
	
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		return "toList";
	}
}
