package com.goldenweb.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.goldenweb.sys.pojo.SysRole;




public interface RoleService {
	
	//角色列表，分页
	public void findRoleList(HttpServletRequest request, String selectName,
			String page, int pageSize);
	
	//依据主键查询单个角色信息
	public SysRole findOneRoleInfo(int id);
	
	//保存修改角色
	public void saveRole(SysRole role, String orgids, String userids);
	
	//删除角色
	public void delRole(String id);
	
	//查询该角色关联的机构
	public List<Object[]> findRoleOrg(String id);
	
	//查询该角色关联的人员
	public List<Object[]> findRoleUser(String id);
	
	//查询所有角色
	public List<SysRole> findAllRole();
	
	//依据code查询该角色下的人员
	public List<Object[]> findRoleUserByCode(String code);
	
	//检查角色代码是否唯一
	public List<Object[]> checkRoleCode(String roleid, String rolecode);
		
	//获取角色下的人员
	public String findUsersOfRole(String rolecode);
	
	//查询所有的流程角色
	public List<Object[]> findProcessRoles();
    
	//角色列表的json数据
	public String findRoleinfoJson(String rolename, Integer page, Integer rows);

	public void saveSetRoles(String userid, String roleids);

	//查询角色id下的用户json数据
	public String findUserJsonByRoleid(String roleid);
	//查询角色id下的机构json数据
	public String findOrgJsonByRoleid(String roleid);
	
	//查询角色id下的用户list
	public List<Object[]> findUserByRoleid(String roleid);
	//查询角色id下的机构list
	public List<Object[]> findOrgByRoleid(String roleid);

	public void saveSetUser(String roleid, String userids);

	public void saveSetOrg(String roleid, String orgids);

	public String findAllRoleJson(String rolename);

}
