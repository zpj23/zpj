package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.FunctionOrgHibernate;
import com.goldenweb.sys.pojo.SysFunctionorganization;

@Repository
public class FunctionOrgHibernateDao extends BaseDao<SysFunctionorganization> implements FunctionOrgHibernate{

	@Autowired
	private SessionFactory sessionFactory;	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/*****************************************************************/
		
	/**
	 * 依据功能id查询出机构
	 * @param funids
	 * @return
	 */
	@Override
	public List<Object[]> findSomeOrg(String funid) {
		try {
			String sql = " select a.orgid,b.org_Name from Sys_Functionorganization  a  " +
			" inner join Sys_Organization  b on a.orgid=b.id where a.functionid = ?";
			List<Object[]> list = this.findBySql(sql, funid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * 删除功能-组织结构的关联关系
	 * @param funid
	 */
	@Override
	public void delFunOrg(String funid){
		String sql = "delete from sys_functionorganization where functionid ="+funid;
		this.executeUpdateOrDelete(sql);
	}
	

}
