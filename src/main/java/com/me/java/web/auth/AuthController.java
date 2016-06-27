package com.me.java.web.auth;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.me.base.utils.WebUtils;
import com.me.java.entity.User;
import com.me.java.service.UserService;

/**
 * 用户登陆权限验证。
 * @author John
 * 2016-5-3上午10:59:20
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	//
	@RequestMapping("/index")
	public String index() throws Exception{
		return "auth/index";
	}
	
	//用户权限验证
	@RequestMapping("/auth/do")
	@ResponseBody
	public void auth(@RequestParam("loginId") String loginId, @RequestParam("passwd") String passwd) throws Exception{
		
		String result = "";
		
		if(StringUtils.isEmpty(loginId) ||
				StringUtils.isEmpty(passwd)){
			result = "{\"code\":1}";
			WebUtils.renderJson(result);
			return;
		}
		
		User user = userService.findUserByLogin(loginId);

//		if(user == null ||
//				!StringUtils.equals(DigestUtils.md5Hex(passwd), user.getPasswd())){
//			result = "{\"code\":2}";
//			WebUtils.renderJson(result);
//			return;
//		}
		
		//登录处理
		SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getLoginid(), user.getPasswd()));
		
		result = "{\"code\":-1}";
		WebUtils.renderJson(result);
		return;
	}
	
	//退出
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public void logout(){
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息  
		 SecurityUtils.getSubject().logout();
		 System.out.println("已安全退出");
	}
}
