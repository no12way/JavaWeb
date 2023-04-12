package cw.javaweb.jsp.tool;

import java.sql.*;

public class DBUtil {
    //设置私有属性,防止被篡改
    private static String driver;
    private static String url;
    private static String user;
    private static  String password;
    //静态代码块里给私有属性赋值
    static {
        try{
            driver = "com.mysql.cj.jdbc.Driver";
            url = "jdbc:mysql://127.0.0.1:3306/user01?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=true";
            user = "root";
            password = "zwf";
            Class.forName(driver);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //静态方法 连接数据库
    public static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
    //关闭数据库连接资源
    public static void close(Connection con, Statement state, ResultSet res){
        try{
            if(con != null){con.close();}
            if(state != null){state.close();}
            if(res != null){res.close();}
        }
        catch(Exception e){e.printStackTrace();}
    }
}
