package org.nico.mocker.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.nico.mocker.consts.Constants;
import org.nico.mocker.utils.HttpContextUtils;
import org.springframework.stereotype.Component;

@Component
public class MockerFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestUrl = httpRequest.getRequestURI();
		String httpMethod = httpRequest.getMethod();

		String swaggerDataKey = httpRequest.getHeader(Constants.swagger_data_key);
		if(StringUtils.isEmpty(swaggerDataKey)){
			swaggerDataKey = httpRequest.getParameter(Constants.swagger_data_key);
		}
		if("/m/apis".equalsIgnoreCase(requestUrl)) {
			chain.doFilter(httpRequest, response);
		}else if(requestUrl.startsWith("/data")
				&& Objects.nonNull(swaggerDataKey)&&swaggerDataKey.equalsIgnoreCase("Y")
		) {
			chain.doFilter(httpRequest, response);
		}else {
			httpRequest.setAttribute("requestUrl", requestUrl);
			httpRequest.setAttribute("httpMethod", httpMethod);
			httpRequest.getRequestDispatcher("/m/mock").forward(request,response);
		}
	}

	@Override
	public void destroy() {
		HttpContextUtils.clear();
	}

}
