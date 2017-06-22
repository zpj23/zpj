package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.FunctionUserHibernate;
import com.goldenweb.sys.pojo.SysFunctionuser;

@Repository
public class FunctionUserHibernateDao extends BaseDao<SysFunctionuser> implements FunctionUserHibernate{

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
	 * 删除功能人员关联关系 
	 */
	@Override
	public void delFunUser(String funid){
		String sql = "delete from sys_functionuser where functionid ="+funid;
		this.executeUpdateOrDelete(sql);
	}
		
	
	/**
	 * 查询与功能id相关联的人员
	 * @param funid
	 * @return
	 */
	@Override
	public List<Object[]> findSomeUser(String funid) {
		try {
			String sql = " select a.userid,b.username from sys_functionuser  a  " +
			" inner join Sys_Userinfo  b on a.userid=b.id where a.functionid = ?";
			List<Object[]> list = this.findBySql(sql, funid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
