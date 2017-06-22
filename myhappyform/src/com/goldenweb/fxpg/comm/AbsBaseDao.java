package com.goldenweb.fxpg.comm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.goldenweb.fxpg.frame.tools.Page;

public abstract class AbsBaseDao<T> extends BaseDao<Serializable>{
	
	/**
	 * 获取Hibernate Session
	 * @Title getSession
	 * @return Session
	 * @author Lee
	 * @time 2015年3月20日 上午9:47:11
	 */
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	/**
	 * HQL分页
	 * @Title getPaging
	 * @param hql
	 * @param page
	 * @return Page<T>
	 * @author Lee
	 * @time 2015年3月20日 上午9:46:55
	 */
	public Page<T> getHqlPaging(String hql, Page<T> page) {
		Query query = this.getSession().createQuery(hql);
		page.setTotalResult(query.list().size());
		query.setFirstResult(page.firstResult());
		query.setMaxResults(page.getPageSize());
		page.setResults(query.list());
		return page;
	}
	
	
	/**
	 * SQL分页
	 * @Title getSqlPaging
	 * @param sql
	 * @param page
	 * @return Page<T>
	 * @author Lee
	 * @time 2015年3月20日 上午11:25:44
	 */
	public Page<T> getSqlPaging(String sql, Page<T> page) {
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		List<Map> mapList = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setTotalResult(mapList.size());
		sqlQuery.setFirstResult(page.firstResult());
		sqlQuery.setMaxResults(page.getPageSize());
		page.setResults(sqlQuery.list());
		return page;
	}
}
