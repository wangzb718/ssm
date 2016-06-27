package com.me.java.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.me.java.service.UserService;

/**
 * 用户相关控制器 
 * @author John
 * 2016-4-18下午1:20:49
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/list/find", method = RequestMethod.GET)
	@ResponseBody
	public void findUserList() throws Exception{
		userService.findUserList();
		System.out.println("sdafds");
	}
}
