<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'index.jsp' starting page</title>
	
  </head>
  
  <body>
  <%-- 
  	<s:iterator value="#roleList">
  		<s:property value="id"/>
  		<s:property value="name"/>
  		<s:property value="description"/>
  		<a href="role_delete.action?id=<s:property value='id'/>" onclick="return confirm('确定要删除该条记录?')">删除</a>
  		<s:a action="role_delete?id=%{id}">删除</s:a>
  		<br/>
  	</s:iterator>
  	--%>
  	<s:a action="role_addUI">添加</s:a>
  	<br/>
  	<s:iterator value="#roleList">
  		${id}
  		${name}
  		${description}
  		<a href="role_delete.action?id=${id}" onclick="return confirm('确定要删除该条记录?')">删除</a>
  		<br/>
  	</s:iterator>
  </body>
</html>
