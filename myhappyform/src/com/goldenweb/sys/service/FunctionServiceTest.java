/**  
  * @Title: FunctionServiceTest.java
  * @Package com.goldenweb.sys.service
  * @Description: TODO(用一句话描述该文件做什么)
  * @author Lee 
  * @date 2014-2-12 上午10:37:30
  * @version V1.0  
  */ 
package com.goldenweb.sys.service;

import java.io.Serializable;
import java.util.List;

import com.goldenweb.fxpg.frame.service.GenericService;
import com.goldenweb.sys.pojo.SysFunction;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Lee 
 * @date 2014-2-12 上午10:37:30
 */
public interface FunctionServiceTest extends GenericService<SysFunction, Serializable> {

	/**
	 * @Title findFunctionTest
	 * @Description TODO(菜单集合)
	 * @return List<SysFunction>
	 * @author Lee
	 * @time 2014-2-12 上午10:55:46
	 */
	public List<SysFunction> findFunctionTest();
	
}
