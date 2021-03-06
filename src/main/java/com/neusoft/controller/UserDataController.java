package com.neusoft.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neusoft.entity.UserData;
import com.neusoft.service.UserDataService;
import com.neusoft.service.UserDataServiceImpl;

@Controller
@RequestMapping("/user")
public class UserDataController {

	@Resource
	private UserDataService userDataServive;

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ModelAndView userCount() {
		int count = userDataServive.findAll().size();
		ModelAndView mv = new ModelAndView();
		mv.addObject("userCount", count);
		mv.setViewName("user/userCount");
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getUserlist(Model model) {
		ModelAndView mv = new ModelAndView();
		List<UserData> userList = userDataServive.findAll();
		System.out.println("log======table 'user' all records:"+ userList.size());
		mv.addObject("userList", userList);
		mv.setViewName("user/list");
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView getAdd() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/add");
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("user") UserData user) {
		userDataServive.create(user);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/show/{userid}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable Long userid) {
		UserData user = userDataServive.findOne(userid);
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("user/detail");
		return mv;
	}

	@RequestMapping(value = "/del/{userid}", method = RequestMethod.GET)
	public String del(@PathVariable Long userid) {
		userDataServive.deleteById(userid);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/edit/{userid}", method = RequestMethod.GET)
	public ModelAndView getEdit(@PathVariable Long userid, Model model) {
		UserData user = userDataServive.findOne(userid);
		model.addAttribute("userAttribute", user);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/edit");
		return mv;
	}

	@RequestMapping(value = "/save/{userid}", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("userAttribute") UserData user,
			@PathVariable Long userid) {
		userDataServive.update(user);
		return "redirect:/user/list";
	}
}
