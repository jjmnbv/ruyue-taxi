/**
 * 
 */
package com.szyciov.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName NeedRelease 
 * @author Efy Shu
 * @Description 标记需要释放的资源(当资源被标记时,调用释放方法会进行释放)
 * @date 2017年4月7日 下午11:30:15 
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.FIELD})
public @interface NeedRelease {

}
