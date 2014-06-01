package com.onyas.oa.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.onyas.oa.domain.Role;
import com.onyas.oa.service.RoleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class RoleAction extends ActionSupport{

	@Resource
	private RoleService roleService;
	private Long id;
	
	/**
	 * 列出所有
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
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
		roleService.delete(id);
		return "toList";
	}

	
	
	
	//-----------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
