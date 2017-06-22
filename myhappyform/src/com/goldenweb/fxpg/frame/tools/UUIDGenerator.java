package com.goldenweb.fxpg.frame.tools;

import java.util.UUID;

/**
 * @ClassName: UUIDGenerator
 * @Description: TODO(UUID的生成类)
 * @author Lee
 * @date 2014-2-10 下午3:06:16
 */
public final class UUIDGenerator {
 
	/**
	 * @Title generate36UUID
	 * @Description 生成36位的UUID
	 * @return String
	 * @author Lee
	 * @time 2014-2-10 下午3:06:52
	 */
	synchronized final public static String generate36UUID(){
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	/**
	 * @Title generate32UUID
	 * @Description 生成32位的UUID
	 * @return String
	 * @author Lee
	 * @time 2014-2-10 下午3:10:08
	 */
	synchronized final public static String generate32UUID(){
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}
}
