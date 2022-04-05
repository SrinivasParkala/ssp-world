package com.ssp.rmx.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ServiceInterceptor implements HandlerInterceptor {
	public static final char USER_SEPARATOR = '_';
	public static final String TENANT_ID = "TENANT_ID";
	public static final String USER = "USER";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		retrieveTenantAndUser(request);
		System.out.println("Pre Handle method is Calling:");
		return true;
	}

	private void retrieveTenantAndUser(HttpServletRequest request) {
		String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		int index = principal.indexOf(USER_SEPARATOR);

		if (index == -1) {
			throw new UsernameNotFoundException("Username and tenanat must be provided");
		}

		request.setAttribute(ServiceInterceptor.TENANT_ID, principal.substring(0, index));
		request.setAttribute(ServiceInterceptor.USER, principal.substring(index + 1, principal.length()));
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		System.out.println("Post Handle method is Calling");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {

		System.out.println("Request and Response is completed");
	}
}
