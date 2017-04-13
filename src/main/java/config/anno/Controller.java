/*
 * @(#) test.java 2015年7月27日
 *
 * Copyright (c) 2015, HaoniuSoft Technology. All Rights Reserved.
 * HaoniuSoft  Technology. CONFIDENTIAL
 */
package config.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Description 
 * 
 * @author mn.qiao
 * @version 1.0
 * @since 2015年7月27日
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Controller {

	/**
	 * 名字
	 * 
	 * @author mn.qiao
	 * @since 2015年7月27日
	 * @return
	 */
	String value();
	
	
}
