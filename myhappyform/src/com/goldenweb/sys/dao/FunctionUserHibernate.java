package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysFunctionuser;

public interface FunctionUserHibernate {

	//删除功能人员关联关系 
	public void delFunUser(String funid);
	
	//查询与功能id相关联的人员
	public List<Object[]> findSomeUser(String funid);

	public void save(SysFunctionuser fu);

}
