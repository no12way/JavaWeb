package cw.javaweb.jsp;



import cw.javaweb.jsp.tool.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet({"/user/login","/user/logout"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if("/user/login".equals(servletPath)){
            doLogin(req,resp);
        }else if ("/user/logout".equals(servletPath)){
            doLogout(req,resp);
        }
    }
    public void doLogin(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html/charset=utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet res = null;
        boolean flag = false;
        try{
            conn = DBUtil.getConnect();
            String sql = "select * from t_user where username = ? and password = ?";
            pre = conn.prepareStatement(sql);
            pre.setString(1,username);
            pre.setString(2,password);
            res = pre.executeQuery();
            if (res.next()){
                flag = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DBUtil.close(conn,pre,res);
        }
        if (flag){
            HttpSession session = req.getSession(true);
            session.setAttribute("username",username);
            //获取参数判断是否要免登录
            String f = req.getParameter("f");
            if("1".equals(f)){
                //创建cookie
                Cookie username1 = new Cookie("username", username);
                Cookie password1 = new Cookie("password", password);
                //设置失效时间
                username1.setMaxAge(60*60*24*10);
                password1.setMaxAge(60*60*24*10);
                //设置cookie作用的路径
                username1.setPath(req.getContextPath());
                password1.setPath(req.getContextPath());
                //把cookie放入响应对象中
                resp.addCookie(username1);
                resp.addCookie(password1);
            }
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else {
            resp.sendRedirect(req.getContextPath()+"/error.jsp");
        }
    }
    public void doLogout(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if(session != null){
            session.removeAttribute("username");
            session.invalidate();
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }
    }
}
