package com.onyas.oa.interceptor;

import com.onyas.oa.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {


	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		//获取当前用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		
		//获取当前访问的URL
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String privilegeUrl = null;
		if(namespace.endsWith("/")){
			privilegeUrl = namespace+actionName;
		}else{
			privilegeUrl = namespace+"/"+actionName;
		}

		privilegeUrl = privilegeUrl.substring(1);//去掉开头的"/"
		
		if(user==null){//如果未登陆用户，转到登陆页面
			if(privilegeUrl.endsWith("userAction_login")){//如果正在使用登陆功能，放行
				return invocation.invoke();
			}else{//如果不是登陆，转到登陆；
				return "loginUI";
			}
		}else{//如果已登陆，就判断权限
			if(user.hasPrivilegeByUrl(privilegeUrl)){//如果有权限，放行
				return invocation.invoke();
			}else{//如果没有，则提示没有权限
				return "noPrivilegeError";
			}
		}
		
	}

}
