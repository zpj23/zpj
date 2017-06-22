package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysFunctionorganization;

public interface FunctionOrgHibernate {
	
	//依据功能id查询出机构
	public List<Object[]> findSomeOrg(String funid);
	
	//删除功能-组织结构的关联关系
	public void delFunOrg(String funid);

	public void save(SysFunctionorganization fo);

}
