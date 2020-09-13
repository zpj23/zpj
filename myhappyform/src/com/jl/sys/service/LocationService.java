package com.jl.sys.service;

import java.util.List;
import java.util.Map;

import com.jl.sys.pojo.LocationInfo;
import com.jl.sys.pojo.UserInfo;

public interface LocationService {
	void updateLocation(LocationInfo l);
	
	List findJson(Map param);
	
	public Map findList(UserInfo user,int page,int rows,Map<String,String> param);

}
