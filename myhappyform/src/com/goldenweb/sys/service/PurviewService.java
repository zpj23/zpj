package com.goldenweb.sys.service;

import java.util.List;
import java.util.Map;

import com.goldenweb.sys.pojo.SysFunction;

public interface PurviewService {
    
	
	public String findOperateByFunid(String funid);
	
	//依据功能id查询出机构
	public List<Object[]> findSomeOrg(String funid);
	
	//保存组织机构
	public void saveOrgs(String orgids,String funids);
	
	//保存与人员关联关系
	public void saveUsers(String userids, String funids);
	
	//查询与功能id相关联的人员
	public List<Object[]> findSomeUser(String funid);
	
	//查询与功能id相关联的角色
	public List<Object[]> findSomeRole(String funid);
	
	//保存与角色关联关系
	public void saveRoles(String roleids, String funids);
	
	//保存与3者关联关系
	public void saveAll(String roleids, String orgids, String userids,
			String funids);

	//菜单树(all)
	public String findMenuJson(String roleid);

	//保存设置信息
	public void saveSetData(String roleid, String menuid);

	//生成配置信息
	public void saveConfigData();	
	
	//查询某人的权限配置信息
	public String findOneMenuConfig(String userid);
	
	//查询某人的权限配置信息返回菜单按钮的实体类集合
	public List<SysFunction> findUserMenuList(String userid);
	
	//删除某人的配置信息
	public void delOneMenuConfig(String userid);

	public Map<String, List<SysFunction>> findUserButtonMap(List<SysFunction> userFunctionList);

	public String findButtonByMenuid(Map<String, List<SysFunction>> map,String menuid);

	public String findRoleMenuJson(String roleid);

	public void saveQuickmenu(String roleid, String menuid,String picpath);
	
	public List<Object[]> findQuickmenuByRoleid(String roleid);
}
