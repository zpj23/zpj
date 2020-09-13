package com.jl.sys.dao;

import java.util.List;
import java.util.Map;

import com.jl.sys.pojo.LocationInfo;
import com.jl.sys.pojo.UserInfo;

public interface LocationDao {
	
	void updateLocation(LocationInfo l);
	public List findJson(Map param);
	
	public List findList(UserInfo user,int page,int rows,Map<String,String> param);

	public int findCount(UserInfo user,Map<String,String> param);
}
