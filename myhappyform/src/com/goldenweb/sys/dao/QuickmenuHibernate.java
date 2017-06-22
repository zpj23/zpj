package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysQuickmenu;

public interface QuickmenuHibernate {

	void delLinkByRoleid(String roleid);

	void save(SysQuickmenu qm);

	List<Object[]> findQuickmenuByRoleid(String roleid);

}
