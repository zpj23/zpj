package com.goldenweb.sys.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.dao.LogHibernate;
import com.goldenweb.sys.pojo.SysLog;

@Repository
public class LogHibernateDao extends GenericDaoHibernate<SysLog, Serializable>
		implements LogHibernate {

	@Override
	public Page getPageList(String logType, String beginTime, String endTime,
			String userName, int pageSize, int page) {
		// 构建分页条件
		Page queryPage = new Page<SysLog>();
		queryPage.setPageSize(pageSize);
		queryPage.setNowPage(page);

		// 构建hql
		String hql = " from SysLog where 1=1 ";
		// tring hql = " from SysLog where  operateType = '"+logType+"'";
		// operateDate
		// 构建查询条件
		Map<String, Object> values = new HashMap<String, Object>();

		if (logType != null && !logType.equals("")) {

			hql += " and operateType = :operateType ";
			values.put("operateType", logType);
		}
		if (beginTime != null && !beginTime.equals("")) {

			hql += " and operateDate >= :operateDate ";

			java.sql.Date dd = java.sql.Date.valueOf(beginTime);
			values.put("operateDate", dd);
		}
		if (endTime != null && !endTime.equals("")) {

			hql += " and operateDate <= :operateDate2 ";
			java.sql.Date dd = java.sql.Date.valueOf(endTime);
			values.put("operateDate2", dd);
		}
		if (userName != null && !userName.equals("")) {

			hql += " and operator like :operator ";
			values.put("operator", "%" + userName + "%");

		}
		hql += " order by operateDate desc ";
		

		return this.hqlQueryPage(hql, queryPage, values);
	}

	/*********************************************************************/

	/**
	 * 获取日志类型
	 * 
	 * @return
	 */
	@Override
	public List<String> findLogTypes() {
		/*
		 * String sql = " select distinct operate_type from sys_log ";
		 * List<Object[]> list = this.findBySql2(sql);
		 * if(list!=null&&list.size()>0){ return list; }else{ return null; }
		 */
		List<String> li = new ArrayList<String>();
		li.add("登陆");
		return li;
	}

}
