package com.me.base.web.springmvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.me.base.annotation.AuthAN;
import com.me.base.annotation.NotifyTypeAN.NotifyType;
import com.me.base.utils.WebUtils;

/**
 * 拦截器
 * @author John
 * 2015-4-2上午10:19:12
 */
public class ControlerHandlerInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod target = ((HandlerMethod) handler);
		
		AuthAN annotation = null;
		
		if(target.getBean().getClass().isAnnotationPresent(AuthAN.class)){
			annotation = target.getBean().getClass().getAnnotation(AuthAN.class);
		}else if(target.getMethod().isAnnotationPresent(AuthAN.class)){
			annotation = target.getMethod().getAnnotation(AuthAN.class);
		}
		
		if(annotation != null){
			//if(!WebUtils.isLogin()){
				if(annotation.type().equals(NotifyType.AJAX)){
					Map<String,Integer> map = new HashMap<String,Integer>();
			    	map.put("unlogined", 1);
			    	WebUtils.renderJson(map);
			    	return false;
				}
			//}
		}
		return true;
	}
}
