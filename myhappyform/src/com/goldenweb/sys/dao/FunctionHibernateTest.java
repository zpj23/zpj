/**  
  * @Title: FunctionHibernate.java
  * @Package com.goldenweb.sys.dao
  * @Description: TODO(用一句话描述该文件做什么)
  * @author Lee 
  * @date 2014-2-12 上午10:46:28
  * @version V1.0  
  */ 
package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.sys.pojo.SysFunction;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Lee 
 * @date 2014-2-12 上午10:46:28
 */
public interface FunctionHibernateTest extends GenericDao<SysFunction, String> {
 
	/**
	 * @Title findFunctionTest
	 * @Description TODO(菜单集合)
	 * @return List<SysFunction>
	 * @author Lee
	 * @time 2014-2-12 上午10:55:46
	 */
	public List<SysFunction> findFunctionTest();
}
