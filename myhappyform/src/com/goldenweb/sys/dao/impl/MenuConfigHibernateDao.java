package com.goldenweb.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.MenuConfigHibernate;
import com.goldenweb.sys.pojo.SysMenuConfig;

@Repository
public class MenuConfigHibernateDao extends BaseDao<SysMenuConfig> implements MenuConfigHibernate{

	
	@Override
	public void saveMenuConfig(SysMenuConfig config) {
		this.saveOrUpdate(config);
	}

	
	@Override
	public SysMenuConfig findOneMenuConfig(String userid) {
		return this.get(userid);
	}


	
	@Override
	public void del(String userid) {
		String sql = "delete from Sys_MenuConfig where userid in ("+userid+")";
		 this.executeUpdateOrDelete(sql);	
	}
  
}
