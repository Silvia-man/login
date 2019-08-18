package com.damo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionInterception extends HandlerInterceptorAdapter {
    private static final long MAX_INACTIVE_SESSION_TIME = 1 * 10000;
    @Autowired
    private HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pre handle method - starting time");
        long startTime = System.currentTimeMillis();
        request.setAttribute("executionTime", startTime);
        if (UserInterception.isUserLogged()) {
            System.out.println("Time since last request in this session" + (System.currentTimeMillis() - request.getSession().getLastAccessedTime()) + "ms");
            if (System.currentTimeMillis() - session.getLastAccessedTime() > MAX_INACTIVE_SESSION_TIME) {
                System.out.println("Logging out, due to inactive session");
                request.logout();
                response.sendRedirect("/dharma_logout");
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("executionTime");
        System.out.println("Execution time for handling request was: " + startTime);
    }

}
