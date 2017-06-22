package com.jl.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jl.common.BaseDao;
import com.jl.sys.dao.ManualInfoDao;
import com.jl.sys.pojo.CheckInfo;
import com.jl.sys.pojo.UserInfo;
@Repository
public class ManualInfoDaoImpl extends BaseDao<CheckInfo> implements ManualInfoDao {
	
	public void saveInfo(CheckInfo cInfo) throws Exception{
		CheckInfo temp =this.get(cInfo.getId());
			if(temp!=null){
				//编辑
				this.merge(cInfo, cInfo.getId());
			}else{
				//新增
				this.save(cInfo);
			}
	}
	
	
	public List findList(UserInfo user,int page,int rows,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from jl_check_info a where 1=1  ");
		if(null!=param.get("datemin")&&!"".equalsIgnoreCase(param.get("datemin").toString())){
			sql.append(" and workdate >= ").append("'"+param.get("datemin")+"'");
		}
		if(null!=param.get("datemax")&&!"".equalsIgnoreCase(param.get("datemax").toString())){
			sql.append(" and workdate <= ").append("'"+param.get("datemax")+"'");
		}
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and  staffname like ").append("'%"+param.get("username")+"%'  ");
		}
		if(null!=param.get("address")&&!"".equalsIgnoreCase(param.get("address").toString())){
			sql.append(" and  address like ").append("'%"+param.get("address")+"%'  ");
		}
		if(null!=param.get("workcontent")&&!"".equalsIgnoreCase(param.get("workcontent").toString())){
			sql.append(" and  workcontent like ").append("'%"+param.get("workcontent")+"%'  ");
		}
		if(null!=param.get("departmentid")&&!"".equalsIgnoreCase(param.get("departmentid").toString())){
			sql.append(" and departmentcode = ").append("'"+param.get("departmentid")+"'");
		}
		sql.append(" order by workdate desc");
		List list=this.findMapObjBySql(sql.toString(), null, page, rows);
		return list;
	}
	
	public int findCount(UserInfo user,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(id) from CheckInfo where 1=1  ");
		if(null!=param.get("datemin")&&!"".equalsIgnoreCase(param.get("datemin").toString())){
			sql.append(" and workdate >= ").append("'"+param.get("datemin")+"'");
		}
		if(null!=param.get("datemax")&&!"".equalsIgnoreCase(param.get("datemax").toString())){
			sql.append(" and workdate <= ").append("'"+param.get("datemax")+"'");
		}
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and  staffname like ").append("'%"+param.get("username")+"%'  ");
		}
		if(null!=param.get("address")&&!"".equalsIgnoreCase(param.get("address").toString())){
			sql.append(" and  address like ").append("'%"+param.get("address")+"%'  ");
		}
		if(null!=param.get("workcontent")&&!"".equalsIgnoreCase(param.get("workcontent").toString())){
			sql.append(" and  workcontent like ").append("'%"+param.get("workcontent")+"%'  ");
		}
		if(null!=param.get("departmentid")&&!"".equalsIgnoreCase(param.get("departmentid").toString())){
			sql.append(" and departmentcode = ").append("'"+param.get("departmentid")+"'");
		}
		int count=this.findListCount(sql.toString(), null);
		return count;
		
	}
	
	public CheckInfo findById(String id){
		return this.get(id);
	}
	
	public void delInfo(String id) throws Exception{
		CheckInfo c=this.get(id);
		if(c!=null)
			this.delete(c);
	}
}
