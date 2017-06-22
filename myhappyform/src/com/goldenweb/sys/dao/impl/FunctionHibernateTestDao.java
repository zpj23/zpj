package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.sys.dao.FunctionHibernateTest;
import com.goldenweb.sys.pojo.SysFunction;

@Repository("functionhibernate")
public class FunctionHibernateTestDao extends GenericDaoHibernate<SysFunction, String> implements FunctionHibernateTest  {
	
	public FunctionHibernateTestDao() {
		super(SysFunction.class);
	}
	 
	@Override
	public List<SysFunction> findFunctionTest(){
		String hql = "from SysFunction as a where" +
		" operateType is null or operateType like '%,%' order by a.funOrder ";
		try {
			List<SysFunction> list = this.getSession().createQuery(hql).list(); 
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
