package com.ami.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author: Amit Khandelwal
 * Date: 2/4/17
 */

public class LoggingFilter implements javax.servlet.Filter {
	private static final Logger log = Logger.getLogger(LoggingFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		log.info("request "+ httpServletRequest.getPathInfo().toString());
		chain.doFilter(request,response);
	}

	@Override
	public void destroy() {
	}
}
