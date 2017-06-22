package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.RoleOrgHibernate;
import com.goldenweb.sys.pojo.SysRoleorganization;

@Repository
public class RoleOrgHibernateDao extends BaseDao<SysRoleorganization> implements RoleOrgHibernate{
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Object[]> findOrgByRoleid(String roleid) {
		String sql = "select b.id,b.org_name  from sys_roleorganization a inner join sys_organization b on a.orgid=b.id where a.roleid=?";
		return this.findBySql(sql, roleid);
	}
	
	@Override
	public void delLinkByRoleid(String roleid) {
		String hql=" delete from SysRoleorganization where roleid="+roleid;
		this.updateordelete(hql,null);
	}

}
