package com.goldenweb.sys.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.goldenweb.sys.pojo.SysRole;

public interface RoleHibernate {

	public int saveOrUpdateRole(SysRole sr);
	
	public void delRoleOrg(int roleid);
	
	public void delRoleUser(int roleid);
	
	public void delRole(String id);
	
	public List<Object[]> findRoleOrg(String id);
		
	public List<Object[]> findRoleUser(String id);
	
	public List<Object[]> findRoleUserByCode(String code);
	
	public List<Object[]> checkRoleCode(String roleid, String rolecode);
	
	public List<Object[]> findUsersOfRole(String rolecode);
	
	public List<Object[]> findProcessRoles();
	
	public int isExistsGroup(String rolecode);
	
	public int isExistsUser(String userid);
	
	public void setPageData(HttpServletRequest request, String sql, List<String> conditions , String page, int pageSize);

	public SysRole getEntity(int id);

	public List<SysRole> findAll();

	public List<Object[]> findRolePageList(String rolename, Integer page,
			Integer rows);

	public int findCountNumber(String rolename);

	public List<SysRole> findRoleByName(String rolename);
}
