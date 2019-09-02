package com.jl.sys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.sys.dao.PayrollDao;
import com.jl.sys.dao.SgxmDao;
import com.jl.sys.pojo.LogInfo;
import com.jl.sys.pojo.PayrollInfo;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.LogInfoService;
import com.jl.sys.service.PayrollService;
@Service
public class PayrollServiceImpl implements PayrollService{
		
	@Autowired
	private PayrollDao payrollDao; 
	@Autowired
	private SgxmDao sgxmDao;
	
	@Autowired
	public LogInfoService jlLogInfoService;
	
	public void saveInfo(PayrollInfo pi){
		
		calculateSgxmInfo(pi);
		payrollDao.saveInfo(pi);
	}
	
	/**
	 * 施工项目的造价信息
	 * @Title calculateSgxmInfo
	 * @author zpj
	 * @time 2019年6月24日 下午2:34:50
	 */
	public synchronized void calculateSgxmInfo(PayrollInfo pi){
		

//		List<PayrollInfo> piList=payrollDao.findByYFAndXM(yuefen, username);
//		PayrollInfo pi=null;
//		if(null!=piList&&piList.size()==1){
//			pi=piList.get(0);
//		}
		sgxmDao.updateMultiInfo(pi);
		
	}
	
	
	public PayrollInfo findById(String id){
		return payrollDao.findById(id);
	}
	public void delInfo(String id){
		payrollDao.delInfo(id);
	}
	
	public Map findList(int page,int rows,Map<String,String> param){
		List<Map> list=payrollDao.findList(page,rows,param);
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
	public List findListByYf(String yf){
		List<PayrollInfo> list=payrollDao.findListByYf(yf);
		return list;
	}
	public boolean updateSgxmListByYf(String yf){
		boolean flag=false;
		try {
			List<PayrollInfo> list=payrollDao.findListByYf(yf);
			if(null!=list&&list.size()>0){
				for(int m=0;m<list.size();m++){
					calculateSgxmInfo(list.get(m));
				}
			}
			flag =true;
		} catch (Exception e) {
			e.printStackTrace();
			flag =false;
			throw new RuntimeException();
		}
		return flag;
	}
	
	
	
	
	public Map calculateInfo(String yuefen,String username,UserInfo user){
//		List<PayrollInfo> list =payrollDao.findByYFAndXM(yuefen,username);
		Map retMap=new HashMap();
//		retMap.put("flag", false);
//		retMap.put("msg", "");
//		try{
//			if(list.size()==0){
//				//判断没有存在对应人员对应月份的数据，需要新增（统计当前月份当前人员的考勤数据）
//				//insert into jl_payroll_info(id,xm,yf,gd,chuqin,jiaban,zonggongshi,gjby,jbgz,jbgzhjj,yfgz,lhbt,fybt,mq,qtkk,zgz,yfgzy,sygz) SELECT UUID(),t1,'2',t2,t3,t4,t5,'0','0','0','0','0','0','0','0','0','0','0' from yuefen2
//				payrollDao.insertPayrollData(yuefen,username);
//				retMap.put("flag", true);
//				retMap.put("msg", "新增成功");
//				insertLog(user,"新增工资单信息","审核完成后，自动新增工资单对应的数据(没有当前月的数据的时候新增)"+"月份："+yuefen+"，人员："+username);
//				
//			}else if(list.size()==1){
//				//有且仅有1一条对应人员对应月份的数据，需要编辑（重新统计当前月份当前人员的考勤数据）
//				payrollDao.updatePayrollData(yuefen,username);
//				retMap.put("flag", true);
//				retMap.put("msg", "修改成功");
//				insertLog(user,"修改工资单信息","审核完成后，自动更新工资单对应的数据(没有当前月的数据的时候新增)"+"月份："+yuefen+"，人员："+username);
//			}else if(list.size()>1){
//				//说明有重复的数据
//				retMap.put("flag", false);
//				retMap.put("msg", yuefen+"月份"+username+"信息有"+list.size()+"条");
//				insertLog(user,"工资单信息问题",yuefen+"月份"+username+"信息有"+list.size()+"条");
//			}
//		}catch (Exception e) {
//			retMap.put("flag", false);
//			retMap.put("msg", "更新失败");
//			throw new RuntimeException();
//		}
		
		
		return retMap;
	}
	
	
}
