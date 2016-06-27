package com.me.base.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtils extends org.apache.commons.lang.StringUtils {
	//MD5加密
	public static String encryptMD5(String str) {
		try{
			if(str == null){
				return "";
			}
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("utf-8"));
			byte[] b = md5.digest();
			int i;
			StringBuffer buff = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buff.append("0");
				}
				buff.append(Integer.toHexString(i));
			}
			return buff.toString();
		}catch (Exception e) {
			return str;
		}
	}
	
	/**
	 * map中-Ljava.lang.String转换成java.lang.String型
	 * @param params
	 * @author wangzb
	 */
	public static Map<String, Object> stringTypeConversion(Map<String, Object> params) {
		for(String key:params.keySet()){
			String paramskey = key.toString();
			String paramsvalue = ((String[]) params.get(key))[0];
			params.put(paramskey, paramsvalue);
		}
		return params;
	}
	
	public static String base64Encode(byte[] bytes){
		return new BASE64Encoder().encode(bytes);
	}
	
	public static byte[] base64Decode(String str){
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(str);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static boolean checkEmail(String email){
	    boolean flag = false;
	    try{
	        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	        Pattern regex = Pattern.compile(check);
	        Matcher matcher = regex.matcher(email);
	        flag = matcher.matches();
	    }catch(Exception e){
	    	flag = false;
	    }
	    return flag;
	}

	/**
	 * 获取img或者src里面值
	 * @param newImgUrl
	 * @param oldImgUrl
	 * @return
	 */
	public static Map<String, List<String>> getImgUrl(String newImgUrl,String oldImgUrl){
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		List<String> newList = new ArrayList<String>();
		List<String> oldList = new ArrayList<String>();
		if(!StringUtils.isEmpty(newImgUrl)){
			Matcher newSrc = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(newImgUrl);
			while(newSrc.find()){
				newList.add(newSrc.group(1));
				result.put("newImgUrl",newList);
			}
		}
		
		if(!StringUtils.isEmpty(oldImgUrl)){
			Matcher oldSrc = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>").matcher(oldImgUrl);
			while(oldSrc.find()){
				oldList.add(oldSrc.group(1));
				result.put("oldImgUrl", oldList);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String s1 = "safsadf123<img src='aaa'/>11<img src='bbb'/>111";
		String s2 = "safsadf123<img src='111'/>11<img src='333'/>111";
//		Map<String, List<String>> result = StringUtils.getImgUrl(null, s2,0);
//		Integer len = result.get("oldImgUrl").size();
//		
//		if(len > 0){
//			for (int i = 0; i < len; i++) {
//				String url = result.get("oldImgUrl").get(i).split("src='")[1];
//				System.out.println(result.get("oldImgUrl").get(i).split("src='")[0]+"src='"+"http://www.3jidi.com/"+url);
//			}
//		}
		
		if(s2.contains("<img")){
			String s[] = s2.split("src='");
			String src = s2.split("src='")[0];
			for (int i = 1; i < s.length; i++) {
				src +=  "src='"+ "http://www.3jidi.com/"+s[i];
			}
			System.out.println(src);
		}
	}
}
