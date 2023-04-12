package cw.javaweb.jsp;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Filter1 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 请求与响应对象进行强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //登录验证
        HttpSession session = request.getSession(false);
        Object username = session.getAttribute("username");
        if(session != null && username != null){
            System.out.println("这里执行了过滤器,拦截请求");
            //验证通过
            filterChain.doFilter(request,response);
        }else {
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    public void destroy() {

    }
}
