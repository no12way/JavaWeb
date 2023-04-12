package cw.javaweb.jsp;

import cw.javaweb.jsp.tool.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/welcome")
public class Welcome extends HttpServlet {
    //浏览器的默认请求为get
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取cookie
        Cookie[] cookies = req.getCookies();
        String username = null;
        String password = null;
        //如果cookie不为空
        //遍历获取用户名和密码
        if(cookies != null){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if("username".equals(name)){
                    username = cookie.getValue();
                }else if("password".equals(name))
                {
                    password = cookie.getValue();
                }
            }
        }
        if (username != null && password != null){
            //连接数据库查询
            Connection conn = null;
            PreparedStatement pre = null;
            ResultSet res = null;
            boolean flag = false;
            try{
                conn = DBUtil.getConnect();
                String sql = "select * from t_user where username = ? and password =?";
                pre = conn.prepareStatement(sql);
                pre.setString(1,username);
                pre.setString(2,password);
                res = pre.executeQuery();
                if (res.next()){
                    flag = true;

                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                DBUtil.close(conn,pre,res);
            }
            if(flag){
                //可以免登录
                //生成session对象,没有就创建一个
                HttpSession session = req.getSession(true);
                session.setAttribute("username",username);
                resp.sendRedirect(req.getContextPath()+"/dept/list");
            }else {
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
            }

        }
    }
}
