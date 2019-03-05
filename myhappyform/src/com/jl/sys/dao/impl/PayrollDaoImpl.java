package com.jl.sys.dao.impl;

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
	
	public int findCount(Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(a.id) from jl_payroll_info a  where 1=1  ");
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
		int count=this.findListCountBySql(sql.toString(), null);
		return count;
		
	}
	
}
