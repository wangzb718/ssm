package com.me.base.web.shiro;

import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.me.java.entity.User;
import com.me.java.service.UserService;

/**
 * Shiro 
 * @author John
 * 2016-5-26下午12:20:15
 */
public class AuthorizingShiroRealm extends AuthorizingRealm{

	@Autowired
	private UserService userService; 
	
	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		
		Collection<?> collection = principalCollection.fromRealm(getName());
		
		if(CollectionUtils.isEmpty(collection)) return null;
		
		//获取登录时输入的用户名 
		String shiroUser = (String) collection.iterator().next();
		
		//到数据库查是否有此对象 
		User user = userService.findUserByLogin(shiroUser);
		
		if(user != null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();  
            info.addRole(user.getId());
            return info;  
		}
		
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		
		 UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;  
		 
		 User user = userService.findUserByLogin(token.getUsername());
		
		 //若存在，将此用户存放到登录认证info中  
		 if(user != null)   return new SimpleAuthenticationInfo(user.getLoginid(), user.getPasswd(), getName());  
		 
		return null;
	}

}
