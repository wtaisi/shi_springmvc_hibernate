package com.neusoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neusoft.entity.User;
import com.neusoft.service.PasswordHelper;

/**
 * 
 * @author Neusoft
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Resource
    private PasswordHelper passwordHelper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "user/login";
	}
	
	@RequestMapping(value = "/main")
	public ModelAndView login(User user,HttpSession session, HttpServletRequest request) {

		ModelAndView modelView = new ModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		token.setRememberMe(true);
		try {
			currentUser.login(token);
		} catch (AuthenticationException e) {
			modelView.addObject("message", "login errors");
			modelView.setViewName("user/login");
		}
		if(currentUser.isAuthenticated()){
			session.setAttribute("userinfo", user);
			modelView.setViewName("user/list");
		}else{
			modelView.addObject("message", "login errors");
			modelView.setViewName("user/login");
		}
		return modelView;
	}

}
