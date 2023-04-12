<%--
  Created by IntelliJ IDEA.
  User: 朱文峰
  Date: 2023/3/15
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="cw.javaweb.jsp.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Object deptList = request.getAttribute("deptList");
    ArrayList<Dept> depts = (ArrayList<Dept>) deptList;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>部门列表页面</title>
</head>
<body>
<h2>欢迎 <%= request.getSession().getAttribute("username") %></h2>
<a href="<%=request.getContextPath()%>/user/logout">退出登录</a>
<hr>
<h1 align="center">部门列表</h1>
<hr>
<table align="center" border="1px solid black" width="50%">
    <thead>
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>

    <%
        for (int i=0; i<depts.size(); i++) {
            Dept dept = depts.get(i);
    %>
    <tr>
        <td><%= (i+1) %></td>
        <td><%= dept.getDeptno()%></td>
        <td><%= dept.getDname()%></td>
        <td>
            <a href="javascript:void(0)" onclick="del(<%= dept.getDeptno() %>)" >删除</a>
            <%-- 由于修改和详情页面只是页面显示不同，数据的查询逻辑类似，所以数据的查询使用相同的逻辑 --%>
            <a href="<%= request.getContextPath() %>/dept/detail?f=m&deptno=<%= dept.getDeptno() %>">修改</a>
            <a href="<%= request.getContextPath() %>/dept/detail?f=d&deptno=<%= dept.getDeptno() %>">详情</a>
        </td>
    </tr>
    <%
        }
    %>



    </tbody>
</table>
<hr>
<a href="<%= request.getContextPath() %>/Add.jsp">新增部门</a>
</body>
<script type="text/javascript">
    function del(dno){
        if(window.confirm("亲，删了不可恢复哦！")){
            document.location.href = "/ServletJsp/dept/delete?deptno=" + dno;
        }
    }
</script>
</html>
