package com.jl.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.common.BaseService.MethodLog2;
import com.jl.sys.dao.LocationDao;
import com.jl.sys.pojo.LocationInfo;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.LocationService;
@Service("locationService")
public class LocationServiceImpl implements LocationService {
	@Autowired
	private LocationDao locationDao;
	@Override
	@MethodLog2(remark="打卡",type="新增")
	public void updateLocation(LocationInfo locationInfo) {
		locationDao.updateLocation(locationInfo);
	}
	public List findJson(Map param){
		return locationDao.findJson(param);
	}
	
	
	public Map findList(UserInfo user,int page,int rows,Map<String,String> param){
		List<Map> list=locationDao.findList(user,page,rows,param);
		int count=locationDao.findCount(user,param);
		Map map=new HashMap();
		map.put("list", list);
		map.put("count", count);
		return map;
	}
	
}
