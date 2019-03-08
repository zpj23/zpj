package com.jl.sys.dao;

import java.util.List;
import java.util.Map;

import com.jl.sys.pojo.PayrollInfo;

public interface PayrollDao {
	
	public void saveInfo(PayrollInfo pi);
	
	
	public List findList(int page,int rows,Map<String,String> param);
	
	public Map findCount(Map<String,String> param);
	
	public PayrollInfo findById(String id);
	
	public void delInfo(String id);

}
