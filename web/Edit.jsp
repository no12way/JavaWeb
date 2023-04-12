<%--
  Created by IntelliJ IDEA.
  User: 朱文峰
  Date: 2023/3/18
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="cw.javaweb.jsp.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Dept dept = (Dept) request.getAttribute("dept");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>修改部门</title>
</head>
<body>
<h1>修改部门</h1>
<hr >
<form action="<%= request.getContextPath() %>/dept/modify" method="get">
  <!-- 部门编号不允许修改 -->
  部门编号<input type="text" name="deptno" value="<%= dept.getDeptno() %>" readonly /><br>
  部门名称<input type="text" name="dname" value="<%= dept.getDname() %>"/><br>
  部门位置<input type="text" name="loc" value="<%= dept.getLoc() %>"/><br>
  <input type="submit" value="修改"/><br>
</form>
</body>
</html>