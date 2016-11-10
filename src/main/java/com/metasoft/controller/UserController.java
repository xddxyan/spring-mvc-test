package com.metasoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metasoft.service.SessionService;
import com.metasoft.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;

	@RequestMapping("/success")
	public String success(Model model) {
		String username = sessionService.getSession().getUsername();
		model.addAttribute("username", username);
		return "success";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public String changePassword(@RequestParam("newp") String newp,
			@RequestParam("oldp") String oldp,
			@RequestParam("repeatp") String repeatp) {
		long uid = Long.parseLong(sessionService.getSession().getUserid());
		String rs = userService.checkPasswd(oldp, newp, repeatp);
		if(rs.equals("ok")) {
			userService.changePassword(uid, newp);
		}
		return rs;
	}

}