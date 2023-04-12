package cw.javaweb.jsp;

import cw.javaweb.jsp.tool.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet({"/dept/add","/dept/delete","/dept/detail","/dept/list","/dept/modify"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username")!= null){
        String servletPath = req.getServletPath();
        if("/dept/add".equals(servletPath)){doAdd(req,resp);}
        else if ("/dept/delete".equals(servletPath)) {
            doDel(req,resp);
        } else if ("/dept/detail".equals(servletPath)){
            doDetail(req,resp);
        } else if ("/dept/list".equals(servletPath)) {
            doList(req,resp);
        }else if("/dept/modify".equals(servletPath)) {
            doModify(req,resp);
        }}
        else {
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }
    }
    private void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String deptno = req.getParameter("deptno");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");
        Connection con = null;
        PreparedStatement pre = null;
        try {
            con = DBUtil.getConnect();
            String sql = "insert into dept values(?,?,?)";
            pre = con.prepareStatement(sql);
            pre.setString(1,deptno);
            pre.setString(2,dname);
            pre.setString(3,loc);
            int i = pre.executeUpdate();
            if ( i == 1){
                resp.sendRedirect(req.getContextPath()+"/dept/list");
            }
            if (i != 1){
                resp.sendRedirect(req.getContextPath()+"/error.html");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtil.close(con,pre,null);
        }
    }
    private void doDel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String deptno = req.getParameter("deptno");
        Connection conn = null;
        PreparedStatement pre = null;
        try {
            conn = DBUtil.getConnect();
            String sql = "delete from dept where deptno = ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1,deptno);
            int i = pre.executeUpdate();
            if( i == 1){
                resp.sendRedirect(req.getContextPath()+"/dept/list");
            }
            if (i != 1){
                resp.sendRedirect(req.getContextPath()+"/error.html");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }finally {
            DBUtil.close(conn,pre,null);
        }
    }
    private void doDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String deptno = req.getParameter("deptno");
        // 连接数据库，根据部门编号查询部门信息。
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnect();
            String sql = "select dname,loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, deptno);
            rs = ps.executeQuery();
            // 这个结果集一定只有一条记录。
            if(rs.next()){
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                Dept dept = new Dept(deptno, dname, loc);
                req.setAttribute("dept",dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        // 获取是修改还是查看详情
        String f = req.getParameter("f");
        if ("m".equals(f)) {
            req.getRequestDispatcher("/Edit.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/Detail.jsp").forward(req, resp);
        }

    }
    private void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{ // 连接数据库，查询所有的部门
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Dept> depts = new ArrayList<Dept>();
        try {
            // 获取连接
            conn = DBUtil.getConnect();
            // 获取预编译的数据库操作对象
            String sql = "select deptno as a,dname,loc from dept";
            ps = conn.prepareStatement(sql);
            // 执行SQL语句
            rs = ps.executeQuery();
            // 处理结果集
            int i = 0;
            while(rs.next()){
                String deptno = rs.getString("a");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                Dept dept = new Dept(deptno, dname, loc);
                depts.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            DBUtil.close(conn, ps, rs);
        }

        req.setAttribute("deptList",depts);

        req.getRequestDispatcher("/List.jsp").forward(req, resp);
    }
    private void doModify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("utf8");
        resp.setContentType("text/html;charset=utf-8");
        String dname= req.getParameter("dname");
        String loc =  req.getParameter("loc");
        String deptno = req.getParameter("deptno");
        Connection conn = null;
        PreparedStatement pre = null;
        try{
            conn = DBUtil.getConnect();
            String sql = "update dept set dname = ?,loc = ? where deptno = ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1,dname);
            pre.setString(2,loc);
            pre.setString(3,deptno);
            int i = pre.executeUpdate();
            if(i >= 1){
                resp.sendRedirect(req.getContextPath()+"/dept/list");
            }else {
                resp.sendRedirect(req.getContextPath()+"/error.html");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,pre,null);
        }
    }
}
