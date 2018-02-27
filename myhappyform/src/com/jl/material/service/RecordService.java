package com.jl.material.service;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jl.material.pojo.RecordInfo;
import com.jl.sys.pojo.UserInfo;

public interface RecordService {

	
	
	public Map findList(UserInfo user,int page,int rows,Map<String,String> param);
	
	public RecordInfo findById(String id);
	
	public int saveRecord(RecordInfo goods);
	
	public int delRecord(String id);
	
}
