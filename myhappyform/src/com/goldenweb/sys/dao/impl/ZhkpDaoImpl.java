package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.ZhkpDao;
import com.goldenweb.sys.pojo.SysKhfz;

@Repository
public class ZhkpDaoImpl extends BaseDao<SysKhfz> implements ZhkpDao {
	/**
	 * 保存
	 * @Title saveConfig
	 * @param khfz
	 * @author zpj
	 * @time 2015-10-9 下午3:02:35
	 */
	@Override
	public void saveConfig(SysKhfz khfz){
		this.saveOrUpdate(khfz);
	}
	
	@Override
	public SysKhfz findById(String id){
		List<SysKhfz> list=this.findAll();
		SysKhfz sk=null;
		if(list.size()>0){
			sk=list.get(0);
		}
		return sk;
	}
	
}
