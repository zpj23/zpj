package com.jl.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jl.common.BaseDao;
import com.jl.sys.dao.SgxmDao;
import com.jl.sys.pojo.PayrollInfo;
import com.jl.sys.pojo.SgxmInfo;
import com.jl.util.DateHelper;
@Repository
public class SgxmDaoImpl extends BaseDao<SgxmInfo> implements SgxmDao {
	
	public void saveInfo(SgxmInfo pi){
		SgxmInfo temp=this.get(pi.getId());
		if(null!=temp){
			this.merge(pi, pi.getId());
		}else{
			this.save(pi);
		}
	}
	
	public SgxmInfo findById(String id){
		return this.get(id);
	}
	
	public void delInfo(String id){
		this.delete(findById(id));
	}
	
	public List findList(int page,int rows,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from jl_sgxm_tj_info a  where 1=1  ");
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and  a.xm like ").append("'%"+param.get("username")+"%'  ");
		}
		if(null!=param.get("yuefen")&&!"".equalsIgnoreCase(param.get("yuefen").toString())){
			sql.append(" and  a.yf = ").append("'"+param.get("yuefen").toString()+"'  ");
		}
		if(null!=param.get("departmentname")&&!"".equalsIgnoreCase(param.get("departmentname").toString())){
			sql.append(" and  a.gd like ").append("'%"+param.get("departmentname")+"%'  ");
		}
		if(null!=param.get("sgxm")&&!"".equalsIgnoreCase(param.get("sgxm").toString())){
			sql.append(" and  a.sgxm like ").append("'%"+param.get("sgxm")+"%'  ");
		}
		sql.append(" order by yf desc ");
		List list=this.findMapObjBySql(sql.toString(), null, page, rows);
		return list;
	}
	
	public Map findCount(Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(a.id) zs ,SUM(chuqin) cq,SUM(jiaban) jb,SUM(zonggongshi) zgs,SUM(zgz) zgz,SUM(yfgzy) yfgz,SUM(sygz) sygz from jl_sgxm_tj_info a  where 1=1  ");
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and  a.xm like ").append("'%"+param.get("username")+"%'  ");
		}
		if(null!=param.get("yuefen")&&!"".equalsIgnoreCase(param.get("yuefen").toString())){
			sql.append(" and  a.yf = ").append("'"+param.get("yuefen").toString()+"'  ");
		}
		if(null!=param.get("departmentname")&&!"".equalsIgnoreCase(param.get("departmentname").toString())){
			sql.append(" and  a.gd like ").append("'%"+param.get("departmentname")+"%'  ");
		}
		if(null!=param.get("sgxm")&&!"".equalsIgnoreCase(param.get("sgxm").toString())){
			sql.append(" and  a.sgxm like ").append("'%"+param.get("sgxm")+"%'  ");
		}
		List<Map> list=this.findMapObjBySql(sql.toString(), null, 1, 10000);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	
	public List<Map> invokeCall(String startime,String endtime,String staffname){
		
		List list=this.findMapObjBySql(" call sumsomethingBytime('"+startime+"','"+endtime+"','"+staffname+"') ");
		return list;
	}
	
	public void updateMultiInfo(PayrollInfo pi){
		try{
			String firstDay=DateHelper.getFirstdayOfMonth(pi.getYf(), "yyyy-MM");
			String lastDay=DateHelper.getLastdayOfMonth(pi.getYf(), "yyyy-MM");
			List<Map> list=invokeCall(firstDay, lastDay,pi.getXm());
			StringBuffer sql=new StringBuffer(500);
			
			List<Map> alreadylist=this.findMapObjBySql("select * from jl_sgxm_tj_info where yf='"+pi.getYf()+"' and xm='"+pi.getXm()+"' ");
			StringBuffer nameBuffer;
			StringBuffer sgxmBuffer;
			StringBuffer dpBuffer;
			boolean flag=false;//判断是否存在新的
			for(int q=0;q<list.size();q++){
				flag=false;
				nameBuffer=new StringBuffer(100).append(list.get(q).get("t1"));
				sgxmBuffer=new StringBuffer(100).append(list.get(q).get("sgxm"));
				dpBuffer=new StringBuffer(100).append(list.get(q).get("t2"));
				if(null!=alreadylist&&alreadylist.size()>0){
					for(int p=0;p<alreadylist.size();p++){
						if(alreadylist.get(p).get("xm").toString().equalsIgnoreCase(nameBuffer.toString())&&
								alreadylist.get(p).get("sgxm").toString().equalsIgnoreCase(sgxmBuffer.toString())&&
								alreadylist.get(p).get("gd").toString().equalsIgnoreCase(dpBuffer.toString())){
							this.executeUpdateOrDelete(" update jl_sgxm_tj_info set chuqin='"+list.get(q).get("t3")+"',jiaban='"+list.get(q).get("t4")+"',zonggongshi='"+list.get(q).get("t5")+"' ,lhbt='"+list.get(q).get("t6")+"',gjby='"+pi.getGjby()+"', jbgz='"+pi.getJbgz()+"'  where yf='"+pi.getYf()+"' and xm='"+nameBuffer+"' and sgxm='"+sgxmBuffer+"' and gd='"+dpBuffer+"' ");
							flag=true;
							alreadylist.remove(p);
							break;
						}
						
					}
				}
				if(!flag){
					this.executeUpdateOrDelete("insert into jl_sgxm_tj_info (id,xm,yf,gd,chuqin,jiaban,zonggongshi,gjby,jbgz,jbgzhjj,yfgz,lhbt,fybt,mq,qtkk,zgz,yfgzy,sygz,sgxm) values(UUID(),'"+nameBuffer+"','"+pi.getYf()+"','"+dpBuffer+"','"+list.get(q).get("t3")+"','"+list.get(q).get("t4")+"','"+list.get(q).get("t5")+"','"+pi.getGjby()+"','"+pi.getJbgz()+"','0','0','"+list.get(q).get("t6")+"','0','0','0','0','0','0','"+sgxmBuffer+"' )");
				}
			}
			StringBuffer ids=new StringBuffer(200);
			if(null!=alreadylist&&alreadylist.size()>0){
				for(int n=0;n<alreadylist.size();n++){
					if(n>0){
						ids.append(",");
					}
					ids.append("'"+alreadylist.get(n).get("id")+"'");
				}
				this.executeUpdateOrDelete("delete from jl_sgxm_tj_info where id in ("+ids+")");
			}
			
		}catch (Exception e) {
			throw new RuntimeException();
		}
		
	}
	
//	public void updateMultiInfo1(List list,PayrollInfo pi,String yuefen,String username){
//		try{
//			this.executeSql("delete from jl_sgxm_tj_info where yf='"+yuefen+"' and xm='"+username+"' ");
//			StringBuffer sqls=new StringBuffer(1000);
//			sqls.append("insert into jl_sgxm_tj_info (id,xm,yf,gd,chuqin,jiaban,zonggongshi,gjby,jbgz,jbgzhjj,yfgz,lhbt,fybt,mq,qtkk,zgz,yfgzy,sygz) values ");
//			Map temp;
//			for(int m=0;m<list.size();m++){
//				temp=(Map)list.get(m);
//				if(m>0){
//					sqls.append(",");
//				}
//				sqls.append("(UUID(),t1,'"+yuefen+"',t2,t3,t4,t5,'"+pi.getGjby()+"','"+pi.getJbgz()+"','0','0',t6,'0','0','0','0','0','0' )");
//			}
//			if(null!=list&&list.size()>0){
//				int state=this.executeUpdateOrDelete(sqls.toString());
//			}
//			
//		}catch (Exception e) {
//			throw new RuntimeException();
//		}
//		
//	}
	
	
//	public List<SgxmInfo> findByYFAndXMAndSgxmBm(String yuefen,String xm){
//		List<SgxmInfo> list=this.find("from SgxmInfo where yf=? and xm=?", yuefen,xm);
//		if(null!=list){
//			return list;
//		}
//		return new ArrayList<SgxmInfo>();
//	}
	
	
	
	
//	public void insertSgxmData(String yuefen,String xm,String sgxm){
//		String view_id=yuefen.split("-")[1];
//		StringBuffer sql=new StringBuffer("insert into jl_sgxm_tj_info (id,xm,yf,gd,chuqin,jiaban,zonggongshi,gjby,jbgz,jbgzhjj,yfgz,lhbt,fybt,mq,qtkk,zgz,yfgzy,sygz,sgxm) ( SELECT UUID(),t1,'"+yuefen+"',t2,t3,t4,t5,'0','0','0','0',t6,'0','0','0','0','0','0','"+sgxm+"' from sgxm"+view_id+" where t1='"+xm+"' )");
//		this.executeSql(sql.toString());
//	}
//	
//	public void updateSgxmData(String yuefen,String xm,String sgxm){
//		String view_id=yuefen.split("-")[1];
//		List list=this.findMapObjBySql("select t3 as chuqin,t4 as jiaban,t5 as zonggongshi,t6 as lhbt from yuefen"+view_id+" where t1='"+xm+"'", null, 1, 1);
//		if(null!=list&&list.size()>0){
//			Map map=(Map)list.get(0);
//			StringBuffer sql=new StringBuffer("update jl_sgxm_tj_info set chuqin='"+map.get("chuqin")+"',jiaban='"+map.get("jiaban")+"',zonggongshi='"+map.get("zonggongshi")+"' ,lhbt='"+map.get("lhbt")+"',sgxm='"+sgxm+"' where xm='"+xm+"' and yf='"+yuefen+"'");
//			this.executeSql(sql.toString());
//		}
//	}
	
}
