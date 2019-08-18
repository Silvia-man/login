package com.damo.login;

import org.omg.IOP.ServiceContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterception extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isUserLogged()) {
            addToModeUserDetails(request.getSession());
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !isRedirectView(modelAndView) ) {
            if (isUserLogged()) {
                addToModeUserDetails(modelAndView);
            }
        }
    }

    private void addToModeUserDetails(HttpSession session) {
        String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        session.setAttribute("username", loggedUserName);
        System.out.println("user( " + loggedUserName + ") session: " + session);
    }

    private void addToModeUserDetails(ModelAndView modelAndView) {

        String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        modelAndView.addObject("loggedUserName", loggedUserName);
        System.out.println("session: " + modelAndView.getModel());
    }


    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isRedirectView(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();
        if (viewName.startsWith("redirect:/")) {
            return true;
        }
        View view = modelAndView.getView();
        return view != null && view instanceof SmartView && ((SmartView)view).isRedirectView();
    }


}
