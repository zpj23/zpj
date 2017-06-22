package com.jl.sys.dao;

import java.util.List;
import java.util.Map;

import com.jl.sys.pojo.CheckInfo;
import com.jl.sys.pojo.UserInfo;

public interface ManualInfoDao {
	/**
	 * 保存
	 * @Title saveInfo
	 * @param cInfo
	 * @author zpj
	 * @time 2017-6-17 下午3:50:13
	 */
	public void saveInfo(CheckInfo cInfo) throws Exception;
	
	
	/**
	 * 列表查询
	 * @Title findList
	 * @param user
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 * @author zpj
	 * @time 2017-6-17 下午3:50:23
	 */
	public List findList(UserInfo user,int page,int rows,Map<String,String> param);
	
	
	/**
	 * 查询总数
	 * @Title findCount
	 * @param user
	 * @param param
	 * @return
	 * @author zpj
	 * @time 2017-6-17 下午4:04:29
	 */
	public int findCount(UserInfo user,Map<String,String> param);
	
	/**
	 * 根据id查询对象
	 * @Title findById
	 * @param id
	 * @return
	 * @author zpj
	 * @time 2017-6-19 下午3:43:54
	 */
	public CheckInfo findById(String id);
	
	public void delInfo(String id) throws Exception;
	
}
