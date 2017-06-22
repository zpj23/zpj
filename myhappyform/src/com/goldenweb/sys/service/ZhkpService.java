package com.goldenweb.sys.service;

import java.util.Map;

import com.goldenweb.sys.pojo.SysKhfz;

public interface ZhkpService {
	/**
	 * 保存配置新消息
	 * @Title saveConfig
	 * @param khfz
	 * @author zpj
	 * @time 2015-10-9 下午3:24:17
	 */
	public void saveConfig(SysKhfz khfz);
	
	/**
	 * 根据主键id查询
	 * @Title findById
	 * @param id
	 * @return
	 * @author zpj
	 * @time 2015-10-9 下午3:24:34
	 */
	public SysKhfz findById(String id);
	
	/**
	 * 年终排名
	 * @Title statisNzpmColumn
	 * @param params
	 * @return
	 * @author zpj
	 * @time 2015-10-9 上午9:03:59
	 */
	String statisNzpmColumn(Map params);
}
