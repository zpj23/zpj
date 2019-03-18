package com.jl.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.jl.common.BaseDao;
import com.jl.sys.dao.PayrollDao;
import com.jl.sys.pojo.PayrollInfo;

@Repository
public class PayrollDaoImpl extends BaseDao<PayrollInfo> implements PayrollDao{
	public void saveInfo(PayrollInfo pi){
		PayrollInfo temp=this.get(pi.getId());
		if(null!=temp){
			this.merge(pi, pi.getId());
		}else{
			this.save(pi);
		}
	}
	public PayrollInfo findById(String id){
		return this.get(id);
	}
	
	public void delInfo(String id){
		this.delete(findById(id));
	}
	
	public List findList(int page,int rows,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from jl_payroll_info a  where 1=1  ");
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and  a.xm like ").append("'%"+param.get("username")+"%'  ");
		}
		if(null!=param.get("yuefen")&&!"".equalsIgnoreCase(param.get("yuefen").toString())){
			String yuefen=param.get("yuefen").toString();
			if(yuefen.substring(0,1).equalsIgnoreCase("0")){
				sql.append(" and  a.yf = ").append("'"+yuefen.substring(1,2)+"'  ");
			}else{
				sql.append(" and  a.yf = ").append("'"+yuefen.substring(0,2)+"'  ");
			}
		}
		if(null!=param.get("departmentname")&&!"".equalsIgnoreCase(param.get("departmentname").toString())){
			sql.append(" and  a.gd like ").append("'%"+param.get("departmentname")+"%'  ");
		}
		sql.append(" order by ABS(yf) asc ");
		List list=this.findMapObjBySql(sql.toString(), null, page, rows);
		return list;
	}
	
	public Map findCount(Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(a.id) zs ,SUM(chuqin) cq,SUM(jiaban) jb,SUM(zonggongshi) zgs,SUM(zgz) zgz,SUM(yfgzy) yfgz,SUM(sygz) sygz from jl_payroll_info a  where 1=1  ");
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and  a.xm like ").append("'%"+param.get("username")+"%'  ");
		}
		if(null!=param.get("yuefen")&&!"".equalsIgnoreCase(param.get("yuefen").toString())){
			String yuefen=param.get("yuefen").toString();
			if(yuefen.substring(0,1).equalsIgnoreCase("0")){
				sql.append(" and  a.yf = ").append("'"+yuefen.substring(1,2)+"'  ");
			}else{
				sql.append(" and  a.yf = ").append("'"+yuefen.substring(0,2)+"'  ");
			}
		}
		if(null!=param.get("departmentname")&&!"".equalsIgnoreCase(param.get("departmentname").toString())){
			sql.append(" and  a.gd like ").append("'%"+param.get("departmentname")+"%'  ");
		}
		List<Map> list=this.findMapObjBySql(sql.toString(), null, 1, 10000);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	
	public List<PayrollInfo> findByYFAndXM(String yuefen,String xm){
		List<PayrollInfo> list=this.find("from PayrollInfo where yf=? and xm=?", yuefen,xm);
		if(null!=list){
			return list;
		}
		return new ArrayList<PayrollInfo>();
	}
	
	
	public void insertPayrollData(String yuefen,String xm){
		this.executeSql("insert into jl_payroll_info(id,xm,yf,gd,chuqin,jiaban,zonggongshi,gjby,jbgz,jbgzhjj,yfgz,lhbt,fybt,mq,qtkk,zgz,yfgzy,sygz) ( SELECT UUID(),t1,'2',t2,t3,t4,t5,'0','0','0','0','0','0','0','0','0','0','0' from yuefen"+yuefen+" where where t1='"+xm+"' )");
	}
	
	public void updatePayrollData(String yuefen,String xm){
		List list=this.findMapObjBySql("select t3 as chuqin,t4 as jiaban,t5 as zonggongshi from yuefen"+yuefen+" where t1='"+xm+"'", null, 1, 1);
		if(null!=list&&list.size()>0){
			Map map=(Map)list.get(0);
			this.executeSql("update jl_payroll_info set chuqin='"+map.get("chuqin")+"',jiaban='"+map.get("jiaban")+"',zonggongshi='"+map.get("jiaban")+"' where xm='"+xm+"' and yf='"+yuefen+"'");
		}
	}
	
}
