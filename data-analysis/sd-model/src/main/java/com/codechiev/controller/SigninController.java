package com.codechiev.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotSerializeTransactionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codechiev.model.User;
import com.codechiev.model.exception.GeneralException;
import com.codechiev.service.LocalizationService;
import com.codechiev.service.SessionService;
import com.codechiev.service.UserService;
import com.codechiev.util.RsaUtils;

@Controller
public class SigninController {
	@Autowired
	private SessionService sessionService;
	@Autowired
	private UserService userService;
	@Autowired
	private LocalizationService localizationService;
	
	private static final String kPublicKey = "publicKey";
	private static final String kUsername = "username";
	private static final String kPassword = "password";

	@RequestMapping("/signin")
	public String signin(@RequestParam(value = "referer", required = false) String referer, Model model) {
		
		model.addAttribute(kPublicKey, RsaUtils.gBase64PublicKey);
		
		if (referer == null || referer.isEmpty() || referer.equals("none") || referer.contains("/user/login")
				|| referer.contains("register")) {
			return "login";
		} else {
			model.addAttribute("referer", referer);
			return "login";
		}
	}
	
	@RequestMapping(value = "/signin.post", method = RequestMethod.POST)
	public @ResponseBody String signinpost(@RequestParam(kUsername) String username, 
			@RequestParam(kPassword) String password,
			@RequestHeader(value = "X-Real-IP", defaultValue = "127.0.0.1") String ip, 
			HttpServletRequest request, HttpServletResponse response) {
		String sid = request.getRequestedSessionId();
		User user = userService.authenticate(username, password, sid);
		if (user == null) {
			return "null";
		}
		
		response.addCookie(SessionService.GetCookie(sid, sessionService.sessionExpired));
		return "ok";
	}

	@RequestMapping(value = "/unsigned", method = RequestMethod.GET)
	public String unsigned() {
		return "unsigned";
	}

	@RequestMapping(value = "/signout.get", method = RequestMethod.GET)
	public @ResponseBody String signout() {
		sessionService.removeSession();
		return "ok";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute(kPublicKey, RsaUtils.gBase64PublicKey);	
		return "register";
	}
	
	@RequestMapping(value = "/signup.post", method = RequestMethod.POST)
	public @ResponseBody String signuppost(@RequestHeader(value = "X-Real-IP", defaultValue = "127.0.0.1") String ip,
			HttpServletRequest request, HttpServletResponse response,
			User user) {
		String sid = request.getSession().getId();
		try {
			userService.register(user, sid);
		} catch (GeneralException e) {
			return  localizationService.getLocalString(e.getMessage());
		}
		response.addCookie(SessionService.GetCookie(sid, sessionService.sessionExpired));
		return "ok";
	}

	@ExceptionHandler(CannotSerializeTransactionException.class)
	public @ResponseBody String handleCustomException(CannotSerializeTransactionException ex) {
		return ex.getLocalizedMessage();
	}
}
