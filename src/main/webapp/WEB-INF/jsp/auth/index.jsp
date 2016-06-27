<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<title>三及第业务支撑系统登录</title>
		<link rel="stylesheet" href="${ctx }/css/reset.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="${ctx }/css/style.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="${ctx }/css/invalid.css" type="text/css" media="screen" />	
		<script type="text/javascript" src="${ctx }/js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${ctx }/js/simpla.jquery.configuration.js"></script>
		<script type="text/javascript" src="${ctx }/js/facebox.js"></script>
		<script type="text/javascript" src="${ctx }/js/jquery.wysiwyg.js"></script>
		<script type="text/javascript" src="${ctx }/js/auth/auth.js"></script>
	</head>
  
	<body id="login">
		
		<div id="login-wrapper" class="png_bg">
			<div id="login-top">
				<input type="hidden" value=${ctx } id="domain"/>
				<!-- Logo (221px width) -->
				<img id="logo" src="${ctx }/images/logo.png" alt="Simpla Admin logo" />
			</div> <!-- End #logn-top -->
				<div style="text-align:center;color:red;" id="loginError"></div><br/>			
			<div id="login-content">
				<form action="#">
					<p>
						<label>用户名：</label>
						<input class="text-input" type="text" id="loginId" />
					</p>
					<div class="clear"></div>
					<p>
						<label>密码：</label>
						<input class="text-input" type="password" id="loginPasswd" onkeydown="change(event);"/>
					</p>
					<div class="clear"></div>
					<!--<p id="remember-password">
						<input type="checkbox" />记住我
					</p>
					<div class="clear"></div>-->
					<p>
						<input class="button" type="button" value="登陆"  id="loginButton" onclick="loginClick();"/>
					</p>
					
				</form>
			</div> <!-- End #login-content -->
			
		</div> <!-- End #login-wrapper -->
		
  </body>
  </html>
