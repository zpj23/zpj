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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.goldenweb.fxpg.frame.pojo.DYTable;

/**
 * @Description: TODO(工厂接口)
 * @ClassName: dynamicTable
 * @author Lee 
 * @date 2014-2-26 下午01:40:03
 *
 */
public interface DynamicTable <T>{

	/**
	 * 查询所有表
	 * @Title queryTables
	 * @return List  
	 * @author Lee
	 * @time 2014-2-26 下午01:43:01
	 */
	public List queryTables();
	
	/**
	 * 查询表对象
	 * @Title queryTable
	 * @param tableName
	 * @return T  
	 * @author Lee
	 * @time 2014-2-26 下午01:43:04
	 */
	public T queryTable(String tableName);
	
	/**
	 * 删除表
	 * @Title removeTable
	 * @param tableName
	 * @return boolean  
	 * @author Lee
	 * @time 2014-2-26 下午01:43:07
	 */
	public boolean removeTable(String tableName);
	
	/**
	 * 更新表
	 * @Title updateTable
	 * @param T
	 * @return T  
	 * @author Lee
	 * @throws NoSuchMethodException 
	 * @time 2014-2-26 下午01:43:14
	 */
	public T updateTable(T object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
	
	/**
	 * 保存表
	 * @Title saveTable
	 * @param object
	 * @return T  
	 * @author Lee
	 * @throws Exception 
	 * @time 2014-2-26 下午01:43:17
	 */
	public T saveTable(T object) throws Exception;
	
	
	public T queryTableById(String id);
	
	public boolean removeTableById(String id);

	public List<DYTable> queryTablesByName(String selectName);
}
