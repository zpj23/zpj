/** 
 * @Description: TODO(抽象工厂)
 * @Title: factoryTable.java
 * @Package com.goldenweb.core.frame.factory
 * @author Lee
 * @date 2014-2-26 下午01:52:53
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.factory;

import com.goldenweb.fxpg.frame.dao.ConcreteColumn;
import com.goldenweb.fxpg.frame.dao.DynamicColumn;
import com.goldenweb.fxpg.frame.dao.DynamicFormDao;
import com.goldenweb.fxpg.frame.dao.DynamicTable;

/**
 * @Description: TODO(抽象工厂)
 * @ClassName: AbstractFactory
 * @author Lee 
 * @date 2014-2-26 下午06:35:56
 *
 * @param <T>
 */
public abstract class AbstractFactory<T> {
	
	/**
	 * 抽象表
	 * @Title showDynamicTable
	 * @return DynamicTable<T>  
	 * @author Lee
	 * @time 2014-2-26 下午06:35:43
	 */
	public abstract DynamicTable<T> showDynamicTable();
	
	/**
	 * 抽象列
	 * @Title showDynamicColumn
	 * @return DynamicColumn<T>  
	 * @author Lee
	 * @time 2014-2-26 下午06:35:46
	 */
	public abstract DynamicColumn<T> showDynamicColumn();
	
	/**
	 * 列详细操作
	 * @Title showConcreteColumn
	 * @return ConcreteColumn<T>  
	 * @author Lee
	 * @time 2014-2-26 下午06:35:46
	 */
	public abstract ConcreteColumn showConcreteColumn();
	
	/**
	 * 抽象表单
	 * @Title showDynamicForm
	 * @return DynamicForm  
	 * @author Lee
	 * @time 2014-3-21 下午03:54:49
	 */
	public abstract DynamicFormDao showDynamicForm();
}
