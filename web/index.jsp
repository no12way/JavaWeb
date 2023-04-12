<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>欢迎使用OA系统</title>
</head>
<body>
<%--<a href="<%= request.getContextPath() %>/dept/list">查看部门列表</a>--%>
<h1>登录</h1>
<hr>
<form action="<%= request.getContextPath() %>/user/login" method="post">
  用户名：<input type="text" name="username"><br>
  密码：<input type="password" name="password"><br>
  <input type="checkbox" name="f" value="1">十天免登录<br>
  <input type="submit" value="登录">
</form>
</body>
</html>