package com.jl.sys.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jl.sys.pojo.PayrollInfo;
import com.jl.sys.pojo.SgxmInfo;

public interface SgxmService {
	public Map findList(int page,int rows,Map<String,String> param);
	
	/**
	 * 删除数据
	 * @Title delInfo
	 * @param id
	 * @author zpj
	 * @time 2019年3月5日 下午3:06:28
	 */
	public void delInfo(String id);
	
	public SgxmInfo findById(String id);
	
	public void saveInfo(SgxmInfo pi);
	
	
	public void exportExcel(Map<String,String> param,HttpServletRequest request, HttpServletResponse response);
}
