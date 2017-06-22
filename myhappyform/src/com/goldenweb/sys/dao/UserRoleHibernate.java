package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysUserrole;

public interface UserRoleHibernate {

	void save(SysUserrole userrole);

	void delLinkByUserid(String userid);

	List<Object[]> findUserByRoleid(String roleid);

	void delLinkByRoleid(String roleid);

}
