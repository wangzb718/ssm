package com.me.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.me.base.annotation.NotifyTypeAN.NotifyType;

/**
 * 简单权限注解类，标示方法是否需要Login验证
 * @author John
 * 2015-4-2上午10:11:26
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuthAN{
	NotifyType type();
}
