package com.me.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.base.utils.StringUtils;
import com.me.java.entity.User;
import com.me.java.mapper.UserMapper;

/**
 * 用户相关业务
 * @author John
 * 2016-4-18上午11:49:58
 */
@Service
public class UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	public List<User> findUserList(){
		List<User> list = userMapper.selectByExample(null); 
		System.out.println(list);
		return list;
	}
	
	@Transactional
	public User findUserByLogin(String loginId){
		if(StringUtils.isEmpty(loginId)) return null;
		
		return userMapper.selectByLoginId(loginId);
				
	}
}
