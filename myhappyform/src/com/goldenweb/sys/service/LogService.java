package com.goldenweb.sys.service;

import java.util.List;

import com.goldenweb.fxpg.frame.tools.Page;

public interface LogService {

	/*//分页列表
	public void findLogList(HttpServletRequest request, String selectType,
			String selectUser, String begintime, String endtime, String page,
			int pageSize);*/
	
	//获取日志类型
	public List<String> findLogTypes();
	
	public Page getPageList(String logType,String beginTime,String endTime,String userName,int pageSize,int page);
}
