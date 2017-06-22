package com.goldenweb.sys.dao;

import com.goldenweb.sys.pojo.SysMenuConfig;

public interface MenuConfigHibernate {

	void saveMenuConfig(SysMenuConfig config);

	SysMenuConfig findOneMenuConfig(String userid);

	void del(String userid);

}
