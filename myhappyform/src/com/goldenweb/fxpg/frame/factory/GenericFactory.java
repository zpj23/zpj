/** 
 * @Description: TODO(具体工厂)
 * @Title: factoryTable.java
 * @Package com.goldenweb.core.frame.factory
 * @author Lee
 * @date 2014-2-26 下午01:52:53
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.goldenweb.fxpg.frame.dao.ConcreteColumn;
import com.goldenweb.fxpg.frame.dao.DynamicColumn;
import com.goldenweb.fxpg.frame.dao.DynamicFormDao;
import com.goldenweb.fxpg.frame.dao.DynamicTable;
import com.goldenweb.fxpg.frame.pojo.DYColumn;
import com.goldenweb.fxpg.frame.pojo.DYTable;

/**
 * @Description: TODO(具体工厂)
 * @ClassName: factoryTable
 * @author Lee 
 * @date 2014-2-26 下午01:52:53
 *
 */
@Component
public class GenericFactory extends AbstractFactory {
	
	@Autowired
	private DynamicTable dynamicTable;
	
	@Autowired
	private DynamicColumn dynamicColumn;
	
	@Autowired
	private ConcreteColumn concreteColumn;
	
	@Autowired
	private DynamicFormDao dynamicForm;

	@Override
	public DynamicTable<DYTable> showDynamicTable() {
		return this.dynamicTable;
	}

	@Override
	public DynamicColumn<DYColumn> showDynamicColumn() {
		return this.dynamicColumn;
	}

	@Override
	public ConcreteColumn showConcreteColumn() {
		return this.concreteColumn;
	}

	@Override
	public DynamicFormDao showDynamicForm() {
		return this.dynamicForm;
	}
}
