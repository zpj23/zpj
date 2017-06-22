package com.jl.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.sys.dao.ManualInfoDao;
import com.jl.sys.pojo.CheckInfo;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.ManualInfoService;
@Service
public class ManualInfoServiceImpl implements ManualInfoService {
	@Autowired
	private ManualInfoDao mDao;
	
	public void saveInfo(CheckInfo cInfo){
		try{
			mDao.saveInfo(cInfo);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();  
		}
	}
	
	public Map findList(UserInfo user,int page,int rows,Map<String,String> param){
		List list=mDao.findList(user,page,rows,param);
		int count=mDao.findCount(user,param); 
		Map map=new HashMap();
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	
	public CheckInfo findById(String id){
		return mDao.findById(id);
	}
	
	public void delInfo(String id){
		try{
			mDao.delInfo(id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();  
		}
	}
}
