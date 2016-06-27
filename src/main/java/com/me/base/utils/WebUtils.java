package com.me.base.utils;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.me.base.web.LocalWebContextHolder;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class WebUtils {
	private static Logger logger = LoggerFactory.getLogger(WebUtils.class);
	
	public static final String LOGINID = "loginid";
	public static final String DEFAULT_NOT_LOGINED_URL = "/index";
	
	//-- header 常量定义 --//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	//-- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	
	private static final String[] PAGE_INCLUDE = new String[] { "autoCount", "first", "hasNext", "hasPre", "nextPage",
		"order", "orderBy", "orderBySetted", "pageNo", "pageSize", "prePage", "totalCount", "totalPages" };
	
	/**
	 * 把对象转换成json格式字符串，并输出到应用流
	 * @param object 要转换输出的对象
	 * @param headers
	 */
	public static void renderJson(final Object object,final String... headers) {
        String jsonString = new JSONSerializer().exclude("*.class").include("*").serialize(object);
        render(TEXT_TYPE, jsonString, headers);
	}
	
	//-- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.

	 * eg.
	 * render("text/plain", "hello", "encoding:GBK");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final String content, final String... headers) {
		try {
			//分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}

			HttpServletResponse response = getResponse();

			//设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				setNoCacheHeader(response);
			}

			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		return LocalWebContextHolder.getRequest().getSession();
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getRequest() {
		return LocalWebContextHolder.getRequest();
	}

	/**
	 * 取得HttpResponse的简化函数.
	 */
	public static HttpServletResponse getResponse() {
		
		return LocalWebContextHolder.getResponse();
	}
	
	/**
	 * 获取ServletContext简化函数
	 * @return
	 */
	public static ServletContext getServletContext(){
		return getSession().getServletContext();
	}

	/**
	 * 取得Request Parameter的简化方法.
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}
	
	public static String getRealPath() {
		return getServletContext().getRealPath("/");
	}
	
	/**
	 * 设置客户端无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 0);
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}
	
	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString, final String... headers) {
		render(TEXT_TYPE, jsonString, headers);
	}
	
	/**
	 * 把对象转换成json格式字符串，并输出到应用流
	 * @param dateNames 需格式化的日期属性集
	 * @param datePattern 日期格式
	 * @param headers
	 * @author ldh
	 */
	public static void renderJsonEach(final Object object,final String[] dateNames,final String datePattern,final String... headers) {
        String jsonString = new JSONSerializer().exclude("*.class").include("*").transform(
				new DateTransformer(datePattern), dateNames).serialize(object);
        render(TEXT_TYPE, jsonString, headers);
	}
	
	/**
	 * 把对象转换成json格式字符串，并输出到应用流
	 * @param object 要转换输出的对象
	 * @param includes 包含的属性集，如：new String[]{"result.id","result.name","result.region","result.category"}
	 * @param headers
	 */
	public static void renderJsonIncludes(final Object object, final String[] includes,final String... headers) {
        String jsonString = new JSONSerializer().include(includes).exclude("*").serialize(object);
        render(TEXT_TYPE, jsonString, headers);
	}
	
	/**
	 * 把对象转换成json格式字符串，并输出到应用流
	 * @param object 要转换输出的对象
	 * @param includes 包含的属性集，如：new String[]{"result.id","result.name","result.region","result.category"}
	 * @param dateNames 需格式化的日期属性集
	 * @param datePattern 日期格式
	 * @param headers
	 */
	public static void renderJsonIncludes(final Object object, final String[] includes, final String[] dateNames,
			final String datePattern,
			final String... headers) {

		String jsonString = new JSONSerializer().include(includes).exclude("*").transform(
				new DateTransformer(datePattern), dateNames).serialize(object);

		render(TEXT_TYPE, jsonString, headers);
	}
	
	/**
	 * 输出Page对象json串(支持日期格式化),非Page对象不要用此方法,请使用renderJson开头的相应方法
	 * 
	 * @param object
	 *            ：输出对象
	 * @param includes
	 *            ：包含属性,如：String[]{"id","name","school.name"}
	 * @param dateNames
	 *            :需要格式化的日期字段集,如String[]{"result.startTime","result.endTime"}
	 * @param datePattern
	 *            :日期格式,如：yyyy-MM-dd
	 * @param headers
	 */
	public static void renderPageJson(final Object object, String[] includes,String[] dateNames,
			String datePattern, final String... headers) {
		
		includes = mergeIncludes(includes);

		String jsonString = new JSONSerializer().include(includes).exclude("*").transform(
				new DateTransformer(datePattern), dateNames).serialize(object);

		render(TEXT_TYPE, jsonString, headers);
	}
	
	/**
	 * 输出Page对象json串,非Page对象不要用此方法,请使用renderJson开头的相应方法
	 * 
	 * @param object
	 *            ：输出对象
	 * @param includes
	 *            ：包含属性,如：String[]{"id","name","school.name"}
	 * @param headers
	 */
	public static void renderPageJson(final Object object, String[] includes, final String... headers) {
		
		includes = mergeIncludes(includes);

		String jsonString = new JSONSerializer().include(includes).exclude("*").serialize(object);

		render(TEXT_TYPE, jsonString, headers);
	}
	
	/**
	 * 合并分页输出属性
	 * @param includes
	 * @return
	 */
	private static String[] mergeIncludes(String[] includes) {
		String[] allIncliudes = new String[PAGE_INCLUDE.length + includes.length];

		System.arraycopy(PAGE_INCLUDE, 0, allIncliudes, 0, PAGE_INCLUDE.length);
		System.arraycopy(includes, 0, allIncliudes, PAGE_INCLUDE.length, includes.length);
		return allIncliudes;
	}
	
	public static void forward(String url) throws Exception{
		getRequest().getRequestDispatcher(url).forward(getRequest(), getResponse());
	}
	
	public static void redirect(String url) throws Exception{
		getResponse().sendRedirect(url);
	}
	
	public static String convertJSON(Object object){
		String jsonString = new JSONSerializer().exclude("*.class").include("*").serialize(object);
		return jsonString;
	}
}
