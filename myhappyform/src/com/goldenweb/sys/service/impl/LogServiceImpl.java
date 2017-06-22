package com.goldenweb.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.comm.BaseService;
import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.dao.LogHibernate;
import com.goldenweb.sys.pojo.SysLog;
import com.goldenweb.sys.service.LogService;

@Service
@Component("logService")
public class LogServiceImpl extends BaseService<SysLog> implements LogService{

	@Autowired
	private LogHibernate logDao;

	@Override
	public Page getPageList(String logType, String beginTime,
			String endTime, String userName, int pageSize, int page) {
		// TODO Auto-generated method stub
		return logDao.getPageList(logType, beginTime, endTime, userName, pageSize, page);
	}

	
	/**********************************************************************/

	/**
	 * 分页列表
	 */
	/*public void findLogList(HttpServletRequest request, String selectType,
			String selectUser, String begintime, String endtime, String page,
			int pageSize) {
		StringBuffer hql = new StringBuffer();
		hql.append("select operateDate,operateDescription,operateType,operator from SysLog  where id is not null  ");
		if (null != selectType && !"".equals(selectType.trim())) {
			hql.append(" and operateType like '%" + selectType + "%'  ");
		}
		if (null != selectUser && !"".equals(selectUser.trim())) {
			hql.append(" and operator like '%" + selectUser + "%'  ");
		}
		if (null != begintime && !"".equals(begintime)) {
			hql.append(" and  operateDate >= to_date('").append(begintime).append("'||'00:00:00','yyyy-mm-dd hh24:mi:ss')");
		}
		if (null != endtime && !"".equals(endtime)) {
			hql.append(" and  operateDate <= to_date('").append(endtime).append("'||'23:59:59','yyyy-mm-dd hh24:mi:ss')");
		}
		hql.append(" order by operateDate desc ");
		// 传递分页数据
		logDao.setPageData(request, hql.toString(), null, page, pageSize);
	}*/



	/**
	 * 获取日志类型
	 * @return
	 */
	@Override
	public List<String> findLogTypes() {
		List<String> list = logDao.findLogTypes();		
		return list;
	}
}
