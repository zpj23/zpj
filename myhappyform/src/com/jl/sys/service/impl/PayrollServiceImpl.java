package com.jl.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.sys.dao.PayrollDao;
import com.jl.sys.pojo.PayrollInfo;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.PayrollService;
@Service
public class PayrollServiceImpl implements PayrollService{
		
	@Autowired
	private PayrollDao payrollDao; 
	
	public void saveInfo(PayrollInfo pi){
		payrollDao.saveInfo(pi);
	}
	public PayrollInfo findById(String id){
		return payrollDao.findById(id);
	}
	public void delInfo(String id){
		payrollDao.delInfo(id);
	}
	
	public Map findList(int page,int rows,Map<String,String> param){
		List<Map> list=payrollDao.findList(page,rows,param);
//		double total_zgz=0;//总工资
//		double total_yfgzy=0;//预付工资
//		double total_sygz=0;//剩余工资
//		double zgz=0;
//		double yfgzy=0;
//		double sygz=0;
//		for(int i=0,length=list.size();i<length;i++){
//			zgz=0;
//			yfgzy=0;
//			sygz=0;
//			zgz=Double.valueOf(((Map)(list.get(i))).get("zgz").toString());
//			yfgzy=Double.valueOf(((Map)(list.get(i))).get("yfgzy").toString());
//			sygz=Double.valueOf(((Map)(list.get(i))).get("sygz").toString());
//			total_zgz+=zgz;
//			total_yfgzy+=yfgzy;
//			total_sygz+=sygz;
//		}
		Map countMap=payrollDao.findCount(param); 
		Map map=new HashMap();
		map.put("list", list);
		map.put("count", countMap.get("zs"));//总数
		map.put("chuqin", countMap.get("cq"));//出勤和
		map.put("jiaban", countMap.get("jb"));//加班和
		map.put("zonggongshi", countMap.get("zgs"));//总工时和
		map.put("total_zgz", countMap.get("zgz"));//总工资
		map.put("total_yfgzy", countMap.get("yfgz"));//预发工资
		map.put("total_sygz", countMap.get("sygz"));//剩余工资
		return map;
	}
}
