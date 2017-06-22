package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.PorletRole;

public interface PorletRoleHibernate {

	List<PorletRole> findProletStrByRoleid(String roleid);

	public void saveInfo(PorletRole pr);

	public void deleteByRoleid(String roleid);
}
