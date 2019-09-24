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
	
	public List<PayrollInfo> findByYFAndXM(String yuefen,String xm);
	
	public void insertPayrollData(String yuefen,String xm);
	
	
	public void updatePayrollData(String yuefen,String xm);
	
	public List<PayrollInfo> findListByYf(String yf);
	
	
	/**
	 * 根据人员分类汇总信息
	 * @Title findListByGroupUser
	 * @return
	 * @author zpj
	 * @time 2019年9月18日 下午1:38:30
	 */
	public List<Map> findListByGroupUser(String nianfen);

}
