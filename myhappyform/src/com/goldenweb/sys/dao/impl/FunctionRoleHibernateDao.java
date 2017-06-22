package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.FunctionRoleHibernate;
import com.goldenweb.sys.pojo.SysRolefunction;

@Repository
public class FunctionRoleHibernateDao extends BaseDao<SysRolefunction> implements FunctionRoleHibernate{

	@Autowired
	private SessionFactory sessionFactory;	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**********************************************************************/
	/**
	 * 查询与功能id相关联的角色
	 * @param funid
	 * @return
	 */
	@Override
	public List<Object[]> findSomeRole(String funid) {
		try {
			String sql = " select a.roleid,b.rolename from sys_rolefunction  a  " +
			" inner join sys_role  b on a.roleid=b.id where a.functionid = ?";
			List<Object[]> list = this.findBySql(sql, funid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 删除功能人员关联关系 
	 */
	@Override
	public void delFunRole(String funid){
		String sql = "delete from sys_rolefunction where functionid ="+funid;
		this.executeUpdateOrDelete(sql);
	}
	
	
	@Override
	public void delLinkByRoleid(String roleid) {
		String hql=" delete from SysRolefunction where roleid="+roleid;
		this.updateordelete(hql,null);
	}

}
