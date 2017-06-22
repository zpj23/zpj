/** 
 * @Description: TODO(实现工厂接口)
 * @Title: DynamicTableImpl.java
 * @Package com.goldenweb.core.frame.factory.table.impl
 * @author Lee
 * @date 2014-2-26 下午01:47:11
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import com.goldenweb.fxpg.frame.dao.DynamicTable;
import com.goldenweb.fxpg.frame.pojo.DYTable;
import com.goldenweb.fxpg.frame.tools.CallProcedure;
/**
 * @Description: TODO(实现表工厂接口)
 * @ClassName: DynamicTableImpl
 * @author Lee 
 * @date 2014-2-26 下午01:47:11
 *
 */
@Component
public class DynamicTableImpl extends GenericDaoHibernate<DYTable, Serializable> implements DynamicTable<DYTable>{
 	
	//@Transactional
	@Override
	public List queryTables() {
		return this.find("from DYTable", null);
	}
	
	@Override
	public List queryTablesByName(String name) {
		String hql = "from DYTable";
		if(name!=null&&!"".equals(name)){
			hql+=" where tableName like '%"+name+"%'";
		}
		hql+=" order by updateDate desc ,createDate desc ";
		return this.find(hql,null);
	}

	//@Transactional
	@Override
	public DYTable queryTable(String tableName) {
		return (DYTable) this.createQuery("from DYTable where tableName =?", new String[]{tableName}).uniqueResult();
	}

	//@Transactional
	@Override
	public boolean removeTable(String tableName) {
		Query query = this.createQuery("delete from DYTable where tableName =?", new String[]{tableName});
		Integer integer = query.executeUpdate();
		this.dropTable(tableName);
		return new Boolean(integer.intValue()>0);
	}

	//@Transactional
	@Override
	public DYTable updateTable(DYTable object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		DYTable dyTable = this.queryTable(object.getTableName());
		BeanUtils.copyProperties(object, dyTable, new String[]{"tableName","tableGuid","createUser","createDate"});
		this.getSession().update(dyTable);
		String tableName = dyTable.getTableName();
		this.modifyTable(tableName, dyTable.getRemark());
		return dyTable;
	}

	//@Transactional
	@Override
	public DYTable saveTable(DYTable object) throws Exception {
		String tableName = object.getTableName();
		String tableRemark = object.getRemark();
		if(object!=null && !"".equals(tableName)){
			this.createTable(tableName, tableRemark);
		}
		return this.savereturn(object);
	}
	 
	public void createTable(String tablename, String tableRemark) throws Exception{
		List params = new ArrayList();  
		params.add(tablename); 
		params.add(tableRemark); 
		CallProcedure.callProcedure("{call createtable(?,?)}", params);
	}
	
	public void dropTable(String tablename){
		 String sql =" drop table "+tablename;
		 this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void modifyTable(String tablename,String tableRemark){
		 String sql ="comment on table "+tablename+" is '"+tableRemark+"'";
		 this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	//@Transactional
	@Override
	public DYTable queryTableById(String id) {
		return (DYTable) this.createQuery("from DYTable where tableGuid =?", new String[]{id}).uniqueResult();
	}
	
	//@Transactional
	@Override
	public boolean removeTableById(String tableGuid) {
		String tablename = queryTableById(tableGuid).getTableName();
		Query query = this.createQuery("delete from DYTable where tableGuid =?", new String[]{tableGuid});
		Integer integer = query.executeUpdate();
		
		this.dropTable(tablename);
		return new Boolean(integer.intValue()>0);
	}

}
