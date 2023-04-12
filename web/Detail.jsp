<%--
  Created by IntelliJ IDEA.
  User: 朱文峰
  Date: 2023/3/18
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="cw.javaweb.jsp.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Dept dept = (Dept) request.getAttribute("dept");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>部门详情</title>
</head>
<body>
<h1>部门详情</h1>
<hr>
<p>部门编号：<%= dept.getDeptno() %></p>
<p>部门名称：<%= dept.getDname() %></p>
<p>部门位置：<%= dept.getLoc() %></p>
<input type="button" value="后退" onclick="window.history.back()">
</body>
</html>
