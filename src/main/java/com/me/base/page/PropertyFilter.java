package com.me.base.page;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.me.base.utils.ReflectionUtils;



/**
 * 与具体ORM实现无关的属性过滤条件封装类.
 * 主要记录页面中简单的搜索过滤条件.
 */
public class PropertyFilter {

	/**
	 * 多个属性间OR关系的分隔符.
	 */
	public static final String OR_SEPARATOR = "_OR_";
	public static final String AND_SEPARATOR = "_AND_";

	/**
	 * 属性比较类型.
     * LT小于、LE小于等于、GT大于、GE大于等于
	 */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, SLIKE, ELIKE, NOTEQ;
	}

	/**
	 * 属性数据类型.
	 */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private String[] propertyNames = null;
	private Object[] propertyValues = null;
	private Class<?> propertyType = null;
	private Object propertyValue = null;
	private MatchType matchType = null;
	private boolean isOr = false;
	private boolean isAnd = false;
	

	public PropertyFilter(){}

	/**
	 * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表. 
	 *                   eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value 待比较的值.
	 */
	public PropertyFilter(final String filterName, final String value) {

		String matchTypeStr = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(matchTypeStr, 0, matchTypeStr.length() - 1);
		String propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
		}

		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		
		propertyNames = propertyNameStr.split(OR_SEPARATOR);
		
		Assert.isTrue(propertyNames.length > 0, "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
		
        if(isMultiProperty() && 
        		(StringUtils.contains(value, OR_SEPARATOR) ||
        				StringUtils.contains(value, AND_SEPARATOR))){
        	throw new IllegalArgumentException("filter名称" + filterName + " 不支持多属性多值类型.");
		}
        
        Assert.isTrue(!(StringUtils.contains(value, OR_SEPARATOR) && StringUtils.contains(value, AND_SEPARATOR)), 
        		"filter名称" + filterName + "值没有按规则编写,不能同时出现_OR_,_AND_连接符.");
        
        if(!(StringUtils.contains(value, OR_SEPARATOR) ||
        				StringUtils.contains(value, AND_SEPARATOR))){//单值
        	//按entity property中的类型将字符串转化为实际类型.
    		propertyValue = ReflectionUtils.convertStringToObject(value, propertyType);
        }else{//多值
        	if(StringUtils.contains(value, OR_SEPARATOR)){
        		String[] values = value.split(OR_SEPARATOR);
        		propertyValues = new String[values.length];
        		for(int i=0;i<values.length;i++){
        			propertyValues[i] = ReflectionUtils.convertStringToObject(values[i], propertyType);
        		}
        		
        		isOr=true;
        	}
        	
        	if(StringUtils.contains(value, AND_SEPARATOR)){
        		String[] values = value.split(AND_SEPARATOR);
        		propertyValues = new String[values.length];
        		for(int i=0;i<values.length;i++){
        			propertyValues[i] = ReflectionUtils.convertStringToObject(values[i], propertyType);
        		}
        		
        		isAnd=true;
        	}
        }
		
	}
	
	/**
	 * @param filterName 比较属性字符串,含待比较的比较类型、属性值类型及属性列表. 
	 *                   eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value 待比较的值.
	 */
	public PropertyFilter(final String filterName, final Object value) {

		String matchTypeStr = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(matchTypeStr, 0, matchTypeStr.length() - 1);
		String propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性比较类型.", e);
		}

		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("filter名称" + filterName + "没有按规则编写,无法得到属性值类型.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		propertyNames = propertyNameStr.split(OR_SEPARATOR);
		
		Assert.isTrue(propertyNames.length > 0, "filter名称" + filterName + "没有按规则编写,无法得到属性名称.");
		//按entity property中的类型将字符串转化为实际类型.
		this.propertyValue = ReflectionUtils.convertValue(value, propertyType);
	}

	/**
	 * 是否比较多个属性.
	 */
	public boolean isMultiProperty() {
		return (propertyNames.length > 1);
	}
	
	public boolean isMultiValue(){
		return (propertyValues != null && propertyValues.length>1);
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}
	
	public Object[] getPropertyValues() {
		return propertyValues;
	}

	/**
	 * 获取唯一的比较属性名称.
	 */
	public String getPropertyName() {
		if (propertyNames.length > 1)
			throw new IllegalArgumentException("There are not only one property");
		return propertyNames[0];
	}
	
	/**
	 * 获取比较值.
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * 获取比较方式类型.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	/**
	 * @return the isOr
	 */
	public boolean getIsOr() {
		return isOr;
	}

	/**
	 * @return the isAnd
	 */
	public boolean getIsAnd() {
		return isAnd;
	}
	
}
