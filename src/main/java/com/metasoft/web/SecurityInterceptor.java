package com.metasoft.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.metasoft.model.Session;
import com.metasoft.service.SessionService;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SessionService sessionService;
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	private List<Pattern> freeList = new ArrayList<Pattern>();

	/**
	 * provided for mvc configuration
	 * @param freeList
	 */
	public void setFreeList(List<String> freeList) {
		for (String url : freeList) {
			this.freeList.add(Pattern.compile(url));
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		sessionService.bind(request);
		//白名单
		String url = urlPathHelper.getLookupPathForRequest(request);
		for (Pattern pattern : freeList) {
			if (pattern.matcher(url).find()) {
				return true;
			}
		}
		//登录及权限判定
		Session session = sessionService.getSession();
		if (session == null) {
			//1,redirect way
			//response.sendRedirect(request.getContextPath() + Constant.UNSIGNED_URL + url);
			//return false;
			//2,use modelview equivalent to controller
			//ModelAndView mav = new ModelAndView("unsigned");
			//throw new ModelAndViewDefiningException(mav);
			/*3,simple and brutal way*/
			if(request.getMethod().equals(RequestMethod.POST.name())){
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain;charset=UTF-8");//"text/html"将会被sitemesh过滤
				response.getWriter().write("session.expired");
				response.getWriter().flush();
				response.getWriter().close();
			}else{
				request.getRequestDispatcher("/views/unsigned.jsp").forward(request, response);
			}    
            return false;
		}
		/*
		List<Authority> authList = authService.getByUrl(url);
		if(authList.size()==0){//如果没有权限控制放行
			return true;
		}
		Authority access = authList.get(0);
		if((session.getPower()&access.getPower())==0){
			response.sendRedirect(request.getContextPath() + Constant.MAIN_HTTP);
		}*/

		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		sessionService.unbind();
	}
}
