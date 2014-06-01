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
	private String name;
	private String description;
	
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
		//1、得到参数，封装成对象
		Role role = new Role();
		role.setName(name);
		role.setDescription(description);
		//2、保存到数据库
		roleService.save(role);
		return "toList";
	}
	
	/**
	 * 更新
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		Role role = roleService.getById(id);
		this.name = role.getName();
		this.description = role.getDescription();
		return "editUI";
	}
	/**
	 * 修改成功
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		//1、从数据库中取出原对象
		Role role = roleService.getById(id);
		//2、设置要修改的属性
		role.setName(name);
		role.setDescription(description);
		//3、更新到数据库
		roleService.update(role);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
