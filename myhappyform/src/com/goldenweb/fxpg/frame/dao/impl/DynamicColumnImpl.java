/** 
 * @Description: TODO(实现工厂)
 * @Title: DynamicTableImpl.java
 * @Package com.goldenweb.core.frame.factory.table.impl
 * @author Lee
 * @date 2014-2-26 下午01:47:11
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import com.goldenweb.fxpg.frame.dao.DynamicColumn;
import com.goldenweb.fxpg.frame.pojo.DYColumn;
import com.goldenweb.fxpg.frame.tools.DataType;
/**
 * @Description: TODO(实现列工厂)
 * @ClassName: DynamicTableImpl
 * @author Lee 
 * @date 2014-2-26 下午01:47:11
 *
 */
@Component
public class DynamicColumnImpl extends GenericDaoHibernate<DYColumn, Serializable> implements DynamicColumn<DYColumn>{

	//@Transactional
	@Override
	public DYColumn queryColumn(String tableColumnId) {
		return (DYColumn) this.createQuery("from DYColumn where colGuid =?", new String[]{tableColumnId}).uniqueResult();
	}

	//@Transactional
	@Override
	public List queryColumns(String tableId) {
		return this.createQuery("from DYColumn where tableGuid =?  order by sort", new String[]{tableId}).list();
	}

	//@Transactional
	@Override
	public boolean removeColumn(DYColumn object) {
		Query query = this.createQuery("delete DYColumn where colGuid =?", new String[]{object.getColGuid()});
		Integer integer = query.executeUpdate();
		this.dropColumn(object);
		return new Boolean(integer.intValue()>0);
	}

	//@Transactional
	@Override
	public DYColumn updateColumn(DYColumn object) {
		DYColumn dyColumn = this.queryColumn(object.getColGuid());
		BeanUtils.copyProperties(object, dyColumn, new String[]{"tableName","tableGuid","busGuid","createUser","createDate","orgGuid"});
		this.modifyColumn(dyColumn);
		
		if(!"".equals(object.getColNewName())&&!object.getColName().equals(object.getColNewName())){
		dyColumn.setColName(dyColumn.getColNewName());
		dyColumn.setColNewName(null); 
		}
		//this.getSession().update(dyColumn);
		this.getSession().merge(dyColumn);
		return dyColumn;
	}

	//@Transactional
	@Override
	public DYColumn saveColumn(DYColumn object) {		
		this.createColumn(object);
		return this.savereturn(object);
	}
	 
	public void createColumn(DYColumn object){
		String isnull = "";//"1".equals(object.getIsMust())?"not null":"";
		String sql = "alter table  "+object.getTableName()+" add  "+object.getColName()+" "+ DataType.getDateType(object.getColType(),object.getColLength())+" "+isnull+"";
		this.getSession().createSQLQuery(sql).executeUpdate();
		sql ="comment on column "+object.getTableName()+"."+object.getColName()+" is '"+object.getColRemark()+"'";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void dropColumn(DYColumn object){
		 String sql ="alter table  "+object.getTableName()+" drop column "+object.getColName();
		 this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void modifyColumn(DYColumn object){
		 if("blob".equalsIgnoreCase(object.getColType())||"clob".equalsIgnoreCase(object.getColType())){
			 // clob,blob 不支持修改，只能新增
			 if(!"".equals(object.getColNewName())&&!object.getColName().equals(object.getColNewName())){//新列名
				 //创建新列
				 String sql =" alter table "+object.getTableName()+" add  "+object.getColNewName()+" "+object.getColType()+" ";
				 this.getSession().createSQLQuery(sql).executeUpdate();
				 //将原列数据移植
				  sql =" update   "+object.getTableName()+" set   "+object.getColNewName()+" = "+object.getColName()+" ";
				 this.getSession().createSQLQuery(sql).executeUpdate(); 
				 //删除原列
				  sql =" alter table  "+object.getTableName()+" drop column "+object.getColName();
				 this.getSession().createSQLQuery(sql).executeUpdate();
				 //新列说明
				 sql ="comment on column "+object.getTableName()+"."+object.getColNewName()+" is '"+object.getColRemark()+"'";
			     this.getSession().createSQLQuery(sql).executeUpdate();
			 }else{//原列名
				 object.setColNewName(object.getColName()+"temp");
				 String sql =" alter table "+object.getTableName()+" add  "+object.getColNewName()+" "+object.getColType()+" ";
				 this.getSession().createSQLQuery(sql).executeUpdate();
				 //将原列数据移植
				  sql =" update   "+object.getTableName()+" set   "+object.getColNewName()+" = "+object.getColName()+" ";
				 this.getSession().createSQLQuery(sql).executeUpdate(); 
				 //删除原列
				  sql =" alter table  "+object.getTableName()+" drop column "+object.getColName();
				 this.getSession().createSQLQuery(sql).executeUpdate(); 
				 //将名字改回
				 sql = "alter table "+object.getTableName()+" rename column "+object.getColNewName()+" to "+object.getColName();
				 this.getSession().createSQLQuery(sql).executeUpdate();	 
			 }
			 
		 }else{
		 String sql ="alter table "+object.getTableName()+" modify  "+object.getColName()+" "+DataType.getDateType(object.getColType(),object.getColLength())+" ";
		 this.getSession().createSQLQuery(sql).executeUpdate();
				
		 if(!"".equals(object.getColNewName())&&!object.getColName().equals(object.getColNewName())){
			 sql = "alter table "+object.getTableName()+" rename column "+object.getColName()+" to "+object.getColNewName();
			 this.getSession().createSQLQuery(sql).executeUpdate();		 
			 sql ="comment on column "+object.getTableName()+"."+object.getColNewName()+" is '"+object.getColRemark()+"'";
		     this.getSession().createSQLQuery(sql).executeUpdate();
		  }
		 }
	}

	
	@Override
	public List<Object[]> findPageColumn(String tableid, Integer page,
			Integer rows) {
		String hql = "select colGuid,colName,colNewName,pluginGuid,colType,colLength,isMust," +
				" layoutStyle,colRemark,sort from DYColumn where tableGuid='"+tableid+"' order by sort ";
		return this.findListToPageByHql(hql, null, page, rows);
	}	
}
