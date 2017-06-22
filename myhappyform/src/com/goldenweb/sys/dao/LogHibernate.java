package com.goldenweb.sys.dao;

import java.io.Serializable;
import java.util.List;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.pojo.SysLog;

public interface LogHibernate extends GenericDao<SysLog, Serializable> {

	//获取日志类型
	public List<String> findLogTypes();

	/*public void setPageData(HttpServletRequest request, String sql,
			List<String> conditions, String page, int pageSize);*/
	
	public Page getPageList(String logType,String beginTime,String endTime,String userName,int pageSize,int page);
	
	
}
