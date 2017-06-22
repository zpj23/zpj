package com.goldenweb.sys.dao;

import com.goldenweb.sys.pojo.SysKhfz;

public interface ZhkpDao {

	public abstract void saveConfig(SysKhfz khfz);
	public SysKhfz findById(String id);
	
}