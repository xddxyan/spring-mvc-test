package com.codechiev.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechiev.model.Session;
import com.codechiev.util.JsonLite;

@Controller
public class IndexController {
	@RequestMapping(value = { "/", "/#*", "/metadata/*"})
	public String get(HttpServletRequest request) {
		Object obj = request.getAttribute("session");
		Session session = (Session) obj;
		request.setAttribute("session", session);
		return "index";
	}
	
	/**
	 * 返回登陆信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/info")
	@ResponseBody
	public String info(HttpServletRequest request) {
		JsonLite json = new JsonLite();
		Object obj = request.getAttribute("session");
		if (null != obj && obj instanceof Session) {
			Session session = (Session) obj;
			json.appendKeyValue("signined", "1");
			json.appendKeyValue("username", session.getUsername());
			json.appendKeyValue("userid", session.getUserid());
		} else {
			json.appendKeyValue("signined", "0");
		}

		return json.convert2String();
	}
}
