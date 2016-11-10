package com.metasoft.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import com.metasoft.model.Constant;

public class ApplicationFilter implements Filter {
	protected PathMatcher pathMatcher = new AntPathMatcher();
	protected UrlPathHelper urlPathHelper = new UrlPathHelper();
	private List<String> resourcePaths = new ArrayList<String>(); 
	private ServletContext servletContext;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
	}
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String url = urlPathHelper.getLookupPathForRequest(request);
		if (!isResourcePath(url)) {
			String contextPath = request.getContextPath();
			String requestURL = request.getRequestURL().toString();
			request.setAttribute("contextPath", contextPath);
			request.setAttribute("requestURL", requestURL);
			request.setCharacterEncoding(Constant.kCharset);
			//由servlet-mapping中的app sevelt处理 , 然后将请求派送给interceptor
			servletContext.getRequestDispatcher("/app" + url).forward(request,servletResponse);
		} else {
			//继续过滤
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	/**
	 * 检测url是否为静态资源
	 * @param url
	 * @return
	 */
	protected boolean isResourcePath(String url) {
		for(String path:resourcePaths){
			if( url.indexOf(path)==0 )
				return true;
		}
		return false;
	}

	public void setResourcePaths(List<String> resourcePaths) {
		for (String path : resourcePaths) {
			this.resourcePaths.add(path);
		}
	}
	


}