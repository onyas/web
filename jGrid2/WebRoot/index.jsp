<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java"%>
<%@ page import="com.mysql.jdbc.Driver"%>
<%@ page import="java.sql.*"%>
<%@ page import="domain.*"%>
<%@ page import="com.googlecode.jsonplugin.JSONUtil"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="tools.JsonUtils"%>
<%@ page import="java.util.HashMap"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title></title>
	</head>
	<body>
		<%
		
			int rowsNum = Integer.parseInt(request.getParameter("rows"));//每页多少条
			int pageNum = Integer.parseInt(request.getParameter("page"));//第几页
			int start = (pageNum-1)*rowsNum;
		
			String sidx = request.getParameter("sidx");//按哪一列排序
			String sord = request.getParameter("sord");//升序还是降序
			
			
			//驱动程序名
			String driverName = "com.mysql.jdbc.Driver";
			//数据库用户名
			String userName = "root";
			//密码
			String userPasswd = "123456";
			//数据库名
			String dbName = "jgrid";
			//表名
			String tableName = "invheader";
			//联结字符串
			String url = "jdbc:mysql://localhost/" + dbName + "?user="
					+ userName + "&password=" + userPasswd;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(url);
			Statement statement = connection.createStatement();
			String sql1="SELECT COUNT(*) AS COUNT FROM " + tableName;
			ResultSet rs1 = statement.executeQuery(sql1);
			rs1.next();
			int rescount = rs1.getInt(1);//从数据库中得到一共多少记录
			
			
			String sql = "SELECT * FROM " + tableName+" ORDER BY "+sidx+" "+sord+" LIMIT "+start+","+rowsNum;
			ResultSet rs = statement.executeQuery(sql);
			//获得数据结果集合
			ResultSetMetaData rmeta = rs.getMetaData();
			//确定数据集的列数，亦字段数
			int numColumns = rmeta.getColumnCount();
			
			ArrayList<Invheader> lists = new ArrayList<Invheader>();
			String invid, invdate, client_id, amount, tax, total, note;
			while (rs.next()) {
				invid = rs.getString("invid");
				invdate = rs.getString("invdate");
				client_id = rs.getString("client_id");
				amount = rs.getString("amount");
				tax = rs.getString("tax");
				total = rs.getString("total");
				note = rs.getString("note");

				Invheader header1 = new Invheader(invid, invdate, client_id,
						amount, tax, total, note);
				lists.add(header1);
				header1 = null;
			}
			
			rs1.close();
			rs.close();
			statement.close();
			connection.close();

			
			int resTotalPages;
			int respage=pageNum;
			if(rescount>0){
				resTotalPages=rescount/rowsNum; //一共多少页
			}else{
				resTotalPages=0;
			}
			if(respage>resTotalPages)
				respage=resTotalPages;
			
			//要返回给前端的数据
			HashMap m = new HashMap();
			m.put("rows",lists);//数据的List
			m.put("page",respage);//所选中的哪一页
			m.put("total",resTotalPages);//一共多少页
			m.put("records",rescount);//一共有多少条记录
			
			//将全部要返回的数据转化为json字符串
			String result = JSONUtil.serialize(m);
			//JSon字符串转JSon对象;使JSP页面直接返回JSON对象
			JsonUtils.renderJson(response, result.toString());
		%>
	</body>
</html>
