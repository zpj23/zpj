package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.UserRoleHibernate;
import com.goldenweb.sys.pojo.SysUserrole;

@Repository
public class UserRoleHibernateDao extends BaseDao<SysUserrole> implements UserRoleHibernate{
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void delLinkByUserid(String userid) {
		String sql = "delete from sys_userrole where userid="+userid;
		this.executeUpdateOrDelete(sql);
	}
	
	@Override
	public List<Object[]> findUserByRoleid(String roleid) {
		String sql = "select b.id,b.username from sys_userrole  a inner join sys_userinfo b on a.userid=b.id  where a.roleid=? ";
		return this.findBySql(sql, roleid);
	}
	
	@Override
	public void delLinkByRoleid(String roleid) {
		String hql=" delete from SysUserrole where roleid="+roleid;
		this.updateordelete(hql,null);
	}

}
