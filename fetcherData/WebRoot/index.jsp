<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>搜索网页</title>
    
	<link rel="stylesheet" type="text/css" href="css/search.css"/>
	<script src="<%=path%>/js/jquery-1.5.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">
			
	var tip = '多个网址用英文逗号分隔';
	var keytip = '多个关键字用空格分隔';
	var tipColor = '#999999';
	var inputColor = '#000000';
	var ttime = 5;
	function find(){	
		if($('#website').val()!=tip && $('#key').val()!=keytip && $('#website').val()!='' && $('#key').val()!=''){		
			jQuery('#form').submit();
		}else{		
			return;
		}		
	}
	
	function validate(){			
			if($('#website').val()!=tip && $('#key').val()!=keytip && $('#website').val()!='' && $('#key').val()!=''){				
				return true;
			}else{		
				alert('请输入网址或关键字');
				return false;
			}
	}
	function setT(t){	   
	   var url = "interval";
       var pars = 'intervaltime='+t+'&flag=S';       
       $.ajax({
			   type: "POST",
			   url: url,
			   data: pars,
			   success: function(req){			  
			     	alert("设置成功");                         
			   }
			});
	  window.setInterval("find()",t*60000);
	}		
		
	jQuery(function(){
		if($('#website').val()==''){		
			$('#website').val(tip);
			$('#website').css("color",tipColor);
		}
		if($('#key').val()==''){		
			$('#key').val(keytip);
			$('#key').css("color",tipColor);
		}
			
			var url = "interval";
	       	var pars = 'flag=G';
	       	var time;	
	       	$.ajax({
				   type: "POST",
				   url: url,
				   data: pars,
				   async: false, //同步
				   success: function(req){	
					   req = req.replace(/[\r\n]/g,"");
					   if(req==null){
					   		$('#minute').val(ttime);
					   		time = ttime*60000;					  	  
					   }else{	
					   		$('#minute').val(req);
					 	    time=req*60000;						 	   			  	  	 
					   }					    
				   }
				});
		
		window.setInterval("find()",time);
		jQuery('input[name="website"]').focus(function(){
				if(tip==jQuery(this).val()){
					jQuery(this).val('');
					jQuery(this).css("color",inputColor);
				}
		});
		jQuery('input[name="website"]').blur(function(){
				if(""==jQuery(this).val()){
					jQuery(this).val(tip);
					jQuery(this).css("color",tipColor);
				}else{
					jQuery(this).css("color",inputColor);
				}
		});	
		jQuery('input[name="key"]').focus(function(){
				if(keytip==jQuery(this).val()){
					jQuery(this).val('');
					jQuery(this).css("color",inputColor);
				}
		});
		jQuery('input[name="key"]').blur(function(){
				if(""==jQuery(this).val()){
					jQuery(this).val(keytip);
					jQuery(this).css("color",tipColor);
				}else{
					jQuery(this).css("color",inputColor);
				}
		});	
	
	});	
	
	</script>
  </head>
  
  <body>
   <div id="page">
	<div id="banner">
	</div>	
	  <div id="auto_message">
	    <form name="form" id="form" action="url" method="post" onsubmit="return validate();">
	        <div class="auto_message_con" ><div class="con_title">设置时间 ：</div><div class="con_time">
	           <select name="minute" id='minute'>	          	 
	              <option value="5">5分钟</option>
	              <option value="10">10分钟</option>
	              <option value="15">15分钟</option>
	              <option value="25">25分钟</option>
	              <option value="45">45分钟</option>
	              <option value="60">60分钟</option>
	           </select>
	           <input type="button" name="设置" value="设置" size="10" onclick="setT(minute.value)"/></div></div>      
	        <div class="auto_message_con"><div class="con_title">网&nbsp;&nbsp;&nbsp;&nbsp;址：</div><div class="con_search"><input type="text" id="website" name="website" value="${website }" size="60" /></div></div>      
	        <div class="auto_message_con"><div class="con_title">关键字：</div><div class="con_search"><input type="text" id="key" name="key" value="${key }" size="60"/><img src="images/search_icon.png" onclick="$('form').submit()"/></div></div>	        
	    </form>
    </div>
    <div id="mainbody" style="background-color:rgb(199,237,204)">
		<ul>
	    	<c:if test="${fn:length(list)>0}">
		        <c:forEach items="${list}" var="item" varStatus="status">
		        			
						<li class="${status.index%2==0?'odd':'even' }">${status.index+1 }.<a href="${item.href }"  target="_blank"> ${item.content } </a> (来自 ${item.site })
						<div>&nbsp;&nbsp;&nbsp;&nbsp;<a href="${item.href }"  target="_blank">${item.href }</a></div> 
						</li>				
				
				</c:forEach>
		     </c:if>
		     <c:if test="${error==true}">
		     	<div align="center"><li class='odd'><font color='red'>${errorMess }</font></li></div>   	
		     </c:if>	     
		     <c:if test="${list!=null && fn:length(list)==0 }">		     
		        <div align="center"><li class='odd'>没有数据</li></div>
		     </c:if>
		     <c:if test="${fn:length(list)==0}">
	        	 <c:forEach begin='1' end="9" var="item" varStatus="status">
						<li class="${status.index%2==0?'odd':'even' }"></li>							
				</c:forEach>	       	 
		     </c:if>
	   </ul>
	</div>  	
	
	<div id="footer">版权所有</div>
</div>
    
  </body>
</html>
