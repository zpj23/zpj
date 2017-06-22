/** 
 * @Description: TODO(列详细操作接口)
 * @Title: ConcreteColumn.java
 * @Package com.goldenweb.core.frame.dao
 * @author Lee
 * @date 2014-2-26 下午01:40:03
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.dao;

import com.goldenweb.fxpg.frame.pojo.DataColumn;


/**
 * @Description: TODO(列详细操作接口)
 * @ClassName: ConcreteColumn
 * @author Lee 
 * @date 2014-2-26 下午01:40:03
 *
 */
public interface ConcreteColumn {
	
	/**
	 * 查询有数据源列
	 * @Title h_dataColumn
	 * @param columnGuid
	 * @return String  
	 * @author Lee
	 * @time 2014-2-28 上午10:58:01
	 */
	public String query_h_dataColumn(String columnGuid, String dataFormat);
	
	/**
	 * 保存有数据源列
	 * @Title save_h_dataColumn
	 * @param dataColumn
	 * @return boolean 
	 * @author Lee
	 * @time 2014-2-28 上午11:38:43
	 */
	public boolean save_h_dataColumn(DataColumn dataColumn);
	
	/**
	 * 常规列
	 * @Title n_dataColumn
	 * @param columnGuid
	 * @return String  
	 * @author Lee
	 * @time 2014-2-28 上午10:58:05
	 */
	public String query_n_dataColumn(String columnGuid, String dataFormat);
	
	/**
	 * 保存常规列
	 * @Title save_n_dataColumn
	 * @param dataColumn  
	 * @return boolean 
	 * @author Lee
	 * @time 2014-2-28 上午11:46:10
	 */
	public boolean save_n_dataColumn(DataColumn dataColumn);
	
	/**
	 * 其他列
	 * @Title otherColumn
	 * @param columnGuid
	 * @return String  
	 * @author Lee
	 * @time 2014-2-28 上午10:58:10
	 */
	public String query_otherColumn(String columnGuid, String dataFormat);
	
	/**
	 * 查询表单内容
	 * @Title query_form
	 * @param tableGuid 表格ID
	 * @param tableName 表格名称
	 * @param dataFormat 日期格式
	 * @return String  
	 * @author Lee
	 * @time 2014-3-7 下午03:52:24
	 */
	public String query_form(String tableGuid,String tableName, String dataFormat);
}
