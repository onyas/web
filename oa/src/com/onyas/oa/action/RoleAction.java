package com.onyas.oa.action;

import java.util.HashSet;
import java.util.List;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.onyas.oa.base.BaseAction;
import com.onyas.oa.domain.Privilege;
import com.onyas.oa.domain.Role;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>  {

	private Long[] privilegeIds;
	
	/**
	 * 列出所有
	 * 
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
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/**
	 * 添加成功
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// 1、得到参数，封装成对象
		// Role role = new Role();
		// role.setName(name);
		// role.setDescription(description);
		// 2、保存到数据库
		roleService.save(model);
		return "toList";
	}

	/**
	 * 更新
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		Role role = roleService.getById(model.getId());
		// this.name = role.getName();
		// this.description = role.getDescription();
		ActionContext.getContext().getValueStack().push(role);// 放到栈顶
		return "editUI";
	}

	/**
	 * 修改成功
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		// 1、从数据库中取出原对象
		Role role = roleService.getById(model.getId());
		// 2、设置要修改的属性
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		// 3、更新到数据库
		roleService.update(role);
		return "toList";
	}

	
	/**
	 * 设置权限页面
	 * @return
	 * @throws Exception
	 */
	public String setPrivilegeUI() throws Exception {
		//准备数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().put("role", role);
		
		List<Privilege> topPrivilegeList = privilegeService.findTopList();
		ActionContext.getContext().put("topPrivilegeList", topPrivilegeList);
		//准备回显数据
		privilegeIds = new Long[role.getPrivileges().size()];
		int index=0;
		for(Privilege privilege:role.getPrivileges()){
			privilegeIds[index++]=privilege.getId();
		}
		
		return "setPrivilegeUI";
	}

	/**
	 * 设置权限
	 * @return
	 * @throws Exception
	 */
	public String setPrivilege() throws Exception {
		// 1、从数据库中取出原对象
		Role role = roleService.getById(model.getId());
		// 2、设置要修改的属性
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		// 3、更新到数据库
		roleService.update(role);
		return "toList";
	}
	
	
	
	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		roleService.delete(model.getId());
		return "toList";
	}

	
	//============
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
}
