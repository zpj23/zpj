package com.goldenweb.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.sys.dao.PorletRoleHibernate;
import com.goldenweb.sys.pojo.PorletRole;
@Repository
public class PorletRoleHibernateDao extends GenericDaoHibernate<PorletRole, Serializable>
implements PorletRoleHibernate{

	
	@Override
	public List<PorletRole> findProletStrByRoleid(String roleid) {
		String hql = " from PorletRole where roleid = '"+roleid+"'";
		return find(hql, null);
	}

	
	@Override
	public void saveInfo(PorletRole pr) {
		save(pr);
	}
	
	@Override
	public void deleteByRoleid(String roleid){
		String hqlUpdate =" delete from  sys_Portal_role where roleid='"+roleid+"' ";
		this.getSession().createSQLQuery(hqlUpdate).executeUpdate();
	}

	
}
