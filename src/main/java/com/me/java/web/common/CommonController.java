package com.me.java.web.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/common")
public class CommonController {

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	@ResponseBody
	public void find(){
		System.out.println("common");
	}
}
