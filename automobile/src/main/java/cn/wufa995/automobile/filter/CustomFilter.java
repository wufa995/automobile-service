/**
 * @author: wufa995[wufa995@qq.com]
 * @date: 2018年10月30日 14时07分
 */
package cn.wufa995.automobile.filter;

import cn.wufa995.automobile.entity.User;
import cn.wufa995.common.util.CheckParam;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletRresponse = (HttpServletResponse) response;
//        String url = httpServletRequest.getRequestURI();
//        if (url.contains("/invoice") || url.contains("/processs/apply")
//                || url.contains("/login") || url.contains("/register")
//                || url.contains("/logout") || url.contains("/schedules/query")) {
//            chain.doFilter(request, response);
//            return;
//        }
//        if(url.contains("/processs") || url.contains("/users") || url.contains("/schedules") || url.contains("/repairs")) {
//            HttpSession session = httpServletRequest.getSession();
//            User user = (User)session.getAttribute("user");
//            if(CheckParam.isEmpty(user)) {
//                httpServletRresponse.sendRedirect("/ams/users/login");
//                return;
//            }
//        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}