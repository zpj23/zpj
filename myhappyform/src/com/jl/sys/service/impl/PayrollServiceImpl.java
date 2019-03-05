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
//		double wzgs=0;
//		double ozgs=0;
//		double workduringtime=0;
//		double overtime=0;
//		for(int i=0,length=list.size();i<length;i++){
//			workduringtime=0;
//			overtime=0;
//			list.get(i).put("workdate", ((Map)(list.get(i))).get("workdate").toString().substring(0, 10));
//			Object str=((Map)(list.get(i))).get("workduringtime");
//			workduringtime=Double.valueOf(str.toString());
//			overtime=Double.valueOf(((Map)(list.get(i))).get("overtime").toString());
//			wzgs+=workduringtime;
//			ozgs+=overtime;
//		}
//		System.out.println(zgs+">>>>>>>循环数组");
//		System.out.println(System.currentTimeMillis());
		int count=payrollDao.findCount(param); 
		Map map=new HashMap();
		map.put("list", list);
		map.put("count", count);
//		map.put("wzgs", wzgs);
//		map.put("ozgs", ozgs);
//		map.put("zgs", wzgs+ozgs);
		return map;
	}
}
