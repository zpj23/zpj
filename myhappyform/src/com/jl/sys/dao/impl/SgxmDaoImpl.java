package com.jl.sys.dao.impl;

import java.text.DecimalFormat;
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
	
	public List findList(Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select xm,yf,gd,sgxm,chuqin,jiaban,zonggongshi,gjby,jbgz,jbgzhjj,yfgz,lhbt,fybt,mq,qtkk,zgz,yfgzy,sygz,qz,bz from jl_sgxm_tj_info a  where 1=1  ");
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
//		sql.append(" order by yf desc ");
		sql.append(" order by str_to_date(yf, '%Y-%c') desc ");
		List list=this.findBySql2(sql.toString());
		return list;
	}
	
	public Map findCount(Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(a.id) zs ,SUM(chuqin) cq,SUM(jiaban) jb,SUM(zonggongshi) zgs,convert(SUM(zgz),decimal(15,1)) zgz,convert(SUM(yfgz),decimal(15,1)) as yfgz,convert(SUM(yfgzy),decimal(15,1)) yfgzy,convert(SUM(sygz),decimal(15,1)) sygz,convert(SUM(fybt),decimal(15,1)) fybt,convert(SUM(mq),decimal(15,1)) mq,convert(SUM(qtkk),decimal(15,1)) qtkk from jl_sgxm_tj_info a  where 1=1  ");
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
//		List<Map> list=this.findMapObjBySql(sql.toString(), null, 1, 10000);
		List<Map> list=this.findMapObjBySql(sql.toString());
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
	
	public synchronized void updateMultiInfo(PayrollInfo pi){
		try{
			String firstDay=DateHelper.getFirstdayOfMonth(pi.getYf(), "yyyy-MM");
			String lastDay=DateHelper.getLastdayOfMonth(pi.getYf(), "yyyy-MM");
			List<Map> list=invokeCall(firstDay, lastDay,pi.getXm());
//			StringBuffer sql=new StringBuffer(500);
			DecimalFormat df = new DecimalFormat ("#.00");
			List<Map> alreadylist=this.findMapObjBySql("select * from jl_sgxm_tj_info where yf='"+pi.getYf()+"' and xm='"+pi.getXm()+"' ");
			StringBuffer nameBuffer;
			StringBuffer sgxmBuffer;
			StringBuffer dpBuffer;
			//平均每个小时的总工资占比 工资
			float ehgz=0;
			if(Float.parseFloat(pi.getZgz())!=0&&Float.parseFloat(pi.getZonggongshi())!=0){
				ehgz=(Float.parseFloat(pi.getZgz()))/(Float.parseFloat(pi.getZonggongshi()));
			}
			//平均每小时费用补贴占比
			float efybt=0;
			if(Float.parseFloat(pi.getFybt())!=0&&Float.parseFloat(pi.getZonggongshi())!=0){
				efybt=(Float.parseFloat(pi.getFybt()))/(Float.parseFloat(pi.getZonggongshi()));
			}
			//平均每个工时满勤的占比
			float emq=0;
			if(Float.parseFloat(pi.getMq())!=0&&Float.parseFloat(pi.getZonggongshi())!=0){
				emq=(Float.parseFloat(pi.getMq()))/(Float.parseFloat(pi.getZonggongshi()));
			}
			
			
			//平均每个工时其他扣款的占比
			float eqtkk=0;
			if(Float.parseFloat(pi.getQtkk())!=0&&Float.parseFloat(pi.getZonggongshi())!=0){
				eqtkk=(Float.parseFloat(pi.getQtkk()))/(Float.parseFloat(pi.getZonggongshi()));
			}
			
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
//							System.out.println("修改现有的项目工资信息");
							double zgs=(double)list.get(q).get("t5");
							//计算应发工资=总工时*工价包月
							double yfgz=zgs*Float.parseFloat(pi.getGjby());
							//总工资平均分到每个工时里面去的总工时 用每个小时的工资*小时数
							double zgz =zgs*ehgz;
							//费用补贴分到每个工时里面*总工时
							double fybt=zgs*efybt;
							//满勤分到每个工时*总工时
							double mq=zgs*emq;
							//其他扣款分到每个工时里面*总工时
							double qtkk=zgs*eqtkk;
							
							this.executeUpdateOrDelete(" update jl_sgxm_tj_info set chuqin='"+list.get(q).get("t3")+"',jiaban='"+list.get(q).get("t4")+"',zonggongshi='"+list.get(q).get("t5")+"' ,lhbt='"+list.get(q).get("t6")+"',gjby='"+pi.getGjby()+"', jbgz='"+pi.getJbgz()+"',yfgz='"+df.format(yfgz)+"',fybt='"+df.format(fybt)+"',mq='"+df.format(mq)+"',qtkk='"+df.format(qtkk)+"',zgz='"+df.format(zgz)+"'  where yf='"+pi.getYf()+"' and xm='"+nameBuffer+"' and sgxm='"+sgxmBuffer+"' and gd='"+dpBuffer+"' ");
							flag=true;
							alreadylist.remove(p);
							break;
						}
						
					}
				}
				if(!flag){
//					System.out.println("新增项目工资信息");
					double zgs=(double)list.get(q).get("t5");
					//计算应发工资=总工时*工价包月
					double yfgz=zgs*Float.parseFloat(pi.getGjby());
					//总工资平均分到每个工时里面去的总工时 用每个小时的工资*小时数
					double zgz =zgs*ehgz;
					//费用补贴分到每个工时里面*总工时
					double fybt=zgs*efybt;
					//满勤分到每个工时*总工时
					double mq=zgs*emq;
					//其他扣款分到每个工时里面*总工时
					double qtkk=zgs*eqtkk;
					
					this.executeUpdateOrDelete("insert into jl_sgxm_tj_info (id,xm,yf,gd,chuqin,jiaban,zonggongshi,gjby,jbgz,jbgzhjj,yfgz,lhbt,fybt,mq,qtkk,zgz,yfgzy,sygz,sgxm) values(UUID(),'"+nameBuffer+"','"+pi.getYf()+"','"+dpBuffer+"','"+list.get(q).get("t3")+"','"+list.get(q).get("t4")+"','"+list.get(q).get("t5")+"','"+pi.getGjby()+"','"+pi.getJbgz()+"','0','"+df.format(yfgz)+"','"+list.get(q).get("t6")+"','"+df.format(fybt)+"','"+df.format(mq)+"','"+df.format(qtkk)+"','"+df.format(zgz)+"','0','0','"+sgxmBuffer+"' )");
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
//				System.out.println("删除已经不存在的项目工资信息");
				this.executeUpdateOrDelete("delete from jl_sgxm_tj_info where id in ("+ids+")");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	
}
