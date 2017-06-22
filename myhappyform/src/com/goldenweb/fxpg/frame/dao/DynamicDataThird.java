/** 
 * @Description: TODO(工厂接口)
 * @Title: dynamicTable.java
 * @Package com.goldenweb.core.frame.factory.table
 * @author Lee
 * @date 2014-2-26 下午01:40:03
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.dao;

import java.util.List;

import com.goldenweb.fxpg.frame.pojo.DYDataThird;

/**
 * @Description: TODO(工厂接口)
 * @ClassName: dynamicTable
 * @author Lee 
 * @date 2014-2-26 下午01:40:03
 *
 */
public interface DynamicDataThird <T>{

	

	public void saveDyDataThird(DYDataThird configData);

	public T findPluginData(String colGuid);

	public List<Object[]> findPluginAllInfo(String colGuid);
}
