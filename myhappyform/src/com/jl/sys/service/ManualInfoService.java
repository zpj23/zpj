package com.jl.sys.service;

import java.util.Map;

import com.jl.sys.pojo.CheckInfo;
import com.jl.sys.pojo.UserInfo;

public interface ManualInfoService {
	public void saveInfo(CheckInfo cInfo);
	
	/**
	 * 列表查询
	 * @Title findList
	 * @param user
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 * @author zpj
	 * @time 2017-6-17 下午4:09:44
	 */
	public Map findList(UserInfo user,int page,int rows,Map<String,String> param);
	
	/**
	 * 根据id查询考勤对象
	 * @Title findById
	 * @param id
	 * @return
	 * @author zpj
	 * @time 2017-6-19 下午3:42:46
	 */
	public CheckInfo findById(String id);
	
	/**
	 * 删除信息
	 * @Title delInfo
	 * @param id
	 * @author zpj
	 * @time 2017-6-19 下午5:01:37
	 */
	public void delInfo(String id);
}
