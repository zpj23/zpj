package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysRoleorganization;

public interface RoleOrgHibernate {

	void save(SysRoleorganization roleorg);

	List<Object[]> findOrgByRoleid(String roleid);

	void delLinkByRoleid(String roleid);

}
