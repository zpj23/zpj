package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysRolefunction;

public interface FunctionRoleHibernate {

	//查询与功能id相关联的角色
	public List<Object[]> findSomeRole(String funid);
	
	//删除功能人员关联关系
	public void delFunRole(String funid);

	public void save(SysRolefunction rf);

	public void delLinkByRoleid(String roleid);
}
