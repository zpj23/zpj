package com.jl.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jl.common.BaseDao;
import com.jl.sys.dao.LocationDao;
import com.jl.sys.pojo.LocationInfo;
import com.jl.sys.pojo.UserInfo;
@Repository
public class LocationDaoImpl extends BaseDao<LocationInfo> implements LocationDao {

	@Override
	public void updateLocation(LocationInfo l) {
		this.save(l);
	}
	
	
	public List findList(UserInfo user,int page,int rows,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id,a.address,a.zuobiao,DATE_FORMAT(a.updatetime,'%Y-%m-%d %H:%i:%s') as updatetime,a.username from jl_location_info a where 1=1   ");
		if(null!=param.get("datemin")&&!"".equalsIgnoreCase(param.get("datemin").toString())){
			sql.append(" and a.updatetime >= ").append("'"+param.get("datemin")+"'");
		}
		if(null!=param.get("datemax")&&!"".equalsIgnoreCase(param.get("datemax").toString())){
			sql.append(" and a.updatetime <= ").append("'"+param.get("datemax")+"'");
		}
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and a.username like '%"+param.get("username")+"%' ");
		}
		if(!user.getIsAdmin().equalsIgnoreCase("1")){
			sql.append(" and a.userid='"+user.getId()+"'");
		}
		sql.append(" order by updatetime desc");
		List list=this.findMapObjBySql(sql.toString(), null, page, rows);
		return list;
	}
	
	public int findCount(UserInfo user,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(id) from jl_location_info a where 1=1 ");
		if(null!=param.get("datemin")&&!"".equalsIgnoreCase(param.get("datemin").toString())){
			sql.append(" and a.updatetime >= ").append("'"+param.get("datemin")+"'");
		}
		if(null!=param.get("datemax")&&!"".equalsIgnoreCase(param.get("datemax").toString())){
			sql.append(" and a.updatetime <= ").append("'"+param.get("datemax")+"'");
		}
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and a.username like '%"+param.get("username")+"%' ");
		}
		if(!user.getIsAdmin().equalsIgnoreCase("1")){
			sql.append(" and a.userid='"+user.getId()+"'");
		}
		int count=this.findListCountBySql(sql.toString(), null);
		return count;
		
	}
	
	
	
	
	public List findJson(Map param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,u.username from jl_location_info a left join jl_user_info u where 1=1  ");
		if(null!=param.get("datemin")&&!"".equalsIgnoreCase(param.get("datemin").toString())){
			sql.append(" and updatetime >= ").append("'"+param.get("datemin")+"'");
		}
		if(null!=param.get("datemax")&&!"".equalsIgnoreCase(param.get("datemax").toString())){
			sql.append(" and updatetime <= ").append("'"+param.get("datemax")+"'");
		}
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and u.username like '%"+param.get("username")+"%' ");
		}
		sql.append(" order by updatetime desc");
		List list=this.findMapObjBySql(sql.toString(), null, 1, 10);
		return list;
	}

}
