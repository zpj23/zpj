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

/**
 * @Description: TODO(工厂接口)
 * @ClassName: dynamicTable
 * @author Lee 
 * @date 2014-2-26 下午01:40:03
 *
 */
public interface DynamicColumn <T>{
	
	/**
	 * 查询列
	 * @Title queryColumn
	 * @param tableColumnId
	 * @return T  
	 * @author Lee
	 * @time 2014-2-26 下午01:43:27
	 */
	public T queryColumn(String tableColumnId);
	
	/**
	 * 查询列
	 * @Title queryColumns
	 * @param tableId
	 * @return List  
	 * @author Lee
	 * @time 2014-2-26 下午01:43:27
	 */
	public List queryColumns(String tableId);
	
	/**
	 * 删除列
	 * @Title removeColumn
	 * @param object
	 * @return boolean  
	 * @author Lee
	 * @time 2014-2-26 下午01:43:24
	 */
	public boolean removeColumn(T object);
	 	
	/**
	 * 更新表
	 * @Title updateColumn
	 * @param T
	 * @return T  
	 * @author Lee
	 * @time 2014-2-26 下午01:43:14
	 */
	public T updateColumn(T object);
	
	/**
	 * 保存列
	 * @Title saveColumn
	 * @param object
	 * @return T  
	 * @author Lee
	 * @time 2014-2-26 下午01:43:19
	 */
	public T saveColumn(T object);

	
	/**
	 * 分页查询
	 * @param tableid
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Object[]> findPageColumn(String tableid, Integer page,
			Integer rows);
	
}
