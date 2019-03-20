package com.qinzi123.controller;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenguifeng on 2019/3/11.
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/manage/*", filterName= "loginFilter")
public class LoginFilter implements Filter {

	public static final String SESSION_KEY = "user";
	public static final String LOGIN = "/login.html";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		Object object = request.getSession().getAttribute(SESSION_KEY);
		if (object == null) response.sendRedirect(LOGIN);
		System.out.println("check auth success...");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
