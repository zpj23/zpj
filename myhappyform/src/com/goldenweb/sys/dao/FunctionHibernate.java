package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysFunction;

public interface FunctionHibernate {

	//保存菜单并返回主键
	public int saveFunction(SysFunction fun);
	
	//查询功能list
	public List<Object[]> findFunction();
	
	//依据角色id查询功能角色关系
	public List<Object[]> findFunRole(String roleid);
	
	// 依据父节点id查询子节点功能
	public List<Object[]> findChildFun(int parentid,String type);
	
	//删除
	public String delFunction(int id);
	
	//删除角色功能关系
	public void delRoleFun(String roleid);
	
	//查询功能list
	public List<Object[]> findFunByParentid(String funid);
	

	public SysFunction getEntity(int id);

	public void save(SysFunction sf);

	public void update(SysFunction fun);

	public void delete(SysFunction sfn);

	public List<Object[]> findMenuByPid(String pid,String userid);

	//获取子级的功能菜单json
	public List<SysFunction> findFunctionByPid(int pid);

	public void saveOrUpdateFunction(SysFunction menu);

	public List<Object[]> findMenuJson(String roleid);

	public List<Object[]> configData();

	public List<Object[]> findRoleMenuJson(String roleid);
	
	

	
}
