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
	public double findListSum(UserInfo user,int page,int rows,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select workduringtime,overtime from jl_check_info a where 1=1  ");
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
		if(null!=param.get("shenhe")&&!"".equalsIgnoreCase(param.get("shenhe").toString())){
			sql.append(" and  shenhe =").append("'"+param.get("shenhe")+"'  ");
		}
		//判断是否是管理员用户
		if(!user.getIsAdmin().equalsIgnoreCase("1")){
			//不是管理员
			sql.append(" and  createuserid="+user.getId());
		}
		List<Object[]> list=this.findBySql2("select SUM(workduringtime)+sum(overtime) as zs from ( "+sql+" ) t1 ");
		
		double zs=(Double)list.get(0)[0];
		return zs;
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
		if(null!=param.get("shenhe")&&!"".equalsIgnoreCase(param.get("shenhe").toString())){
			sql.append(" and  shenhe =").append("'"+param.get("shenhe")+"'  ");
		}
		//判断是否是管理员用户
		if(!user.getIsAdmin().equalsIgnoreCase("1")){
			//不是管理员
			sql.append(" and  createuserid="+user.getId());
		}
		sql.append(" order by workdate desc,adddate desc ");
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
		if(null!=param.get("shenhe")&&!"".equalsIgnoreCase(param.get("shenhe").toString())){
			sql.append(" and  shenhe =").append("'"+param.get("shenhe")+"'  ");
		}
		//判断是否是管理员用户
		if(!user.getIsAdmin().equalsIgnoreCase("1")){
			//不是管理员
			sql.append(" and  createuserid="+user.getId());
		}
		int count=this.findListCount(sql.toString(), null);
		return count;
		
	}
	
	public List findListObjectArray(UserInfo user,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		String str="所属区域,施工项目及区域,工作内容,施工人员,施工日期,出勤时间,加班时间,备注";
		
		sql.append(" select a.departmentname,a.address,a.workcontent,a.staffname,DATE_FORMAT(a.workdate, '%Y-%m-%d') as workdate,a.workduringtime,a.overtime,a.remark from jl_check_info a where 1=1  ");
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
		//判断是否是管理员用户
		if(!user.getIsAdmin().equalsIgnoreCase("1")){
			//不是管理员
			sql.append(" and  createuserid="+user.getId());
		}
		sql.append(" order by workdate desc,adddate desc");
		List list=this.findBySql2(sql.toString());
		return list;
	}
	
	
	public CheckInfo findById(String id){
		return this.get(id);
	}
	
	public void delInfo(String id) throws Exception{
		CheckInfo c=this.get(id);
		if(c!=null)
			this.delete(c);
	}
	
	public List findChartByUser(Map param){
		StringBuffer sql=new  StringBuffer(1000);
		sql.append("SELECT DATE_FORMAT( workdate, '%Y-%m' ) as yuefen , SUM(workduringtime) as wdt , SUM(overtime) ot FROM jl_check_info where  ");
		sql.append(" shenhe ='1' ");
		if(null!=param.get("datemin")&&!"".equalsIgnoreCase(param.get("datemin").toString())){
			sql.append(" and workdate like ").append("'"+param.get("datemin")+"%'");
		}
//		if(null!=param.get("datemax")&&!"".equalsIgnoreCase(param.get("datemax").toString())){
//			sql.append(" and workdate <= ").append("'"+param.get("datemax")+"'");
//		}
		if(param.get("username")!=null&&!((String)param.get("username")).equalsIgnoreCase("")){
			sql.append(" and staffname ='"+(String)param.get("username")+"' ");
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
		sql.append(" GROUP BY DATE_FORMAT( workdate, '%Y-%m' ) ORDER BY yuefen asc ");
		List list=this.findMapObjBySql(sql.toString(), null, 1, 20);
		return list;
	}
}
