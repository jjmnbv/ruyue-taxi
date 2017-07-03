/**
 * 
 */
package com.szyciov.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName ValidateRule 
 * @author Efy Shu
 * @Description 校验规则注解
 * @date 2017年3月7日 下午6:56:28 
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
public @interface ValidateRule {
	String msg() default "";
}
