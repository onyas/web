package com.onyas.oa.action;

import java.util.List;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.onyas.oa.base.BaseAction;
import com.onyas.oa.domain.Department;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{
	
	public String list() throws Exception {
		List<Department> departmentList = departmentService.findAll();
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}
	
	public String delete() throws Exception {
		departmentService.delete(model.getId());
		return "toList";
	}
	
	public String addUI() throws Exception {
		return "addUI";
	}

	public String add() throws Exception {
		departmentService.save(model);
		return "toList";
	}
	
	public String editUI() throws Exception {
		Department department = departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		return "editUI";
	}
	
	public String edit() throws Exception {
		Department department = departmentService.getById(model.getId());
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		departmentService.update(department);
		return "toList";
	}

}
