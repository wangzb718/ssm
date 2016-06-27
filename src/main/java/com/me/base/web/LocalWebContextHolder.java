package com.me.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocalWebContextHolder {
	private static final ThreadLocal<Context> attributes = new ThreadLocal<Context>();
	
	public static void setAttributes(HttpServletRequest request,HttpServletResponse response){
		attributes.set((new LocalWebContextHolder()).new Context(request,response));
	}
	
	public static void removeAttributes(){
		attributes.remove();
	}
	
	public static HttpServletRequest getRequest(){
		return attributes.get().getRequest();
	}
	
	public static HttpServletResponse getResponse(){
		return attributes.get().getResponse();
	}
	
	private class Context{
		private HttpServletRequest request;
		private HttpServletResponse response;
		
		public Context(HttpServletRequest request,HttpServletResponse response){
			this.request = request;
			this.response = response;
		}
		
		public HttpServletRequest getRequest() {
			return request;
		}
		
		public HttpServletResponse getResponse() {
			return response;
		}
	}
	
}
