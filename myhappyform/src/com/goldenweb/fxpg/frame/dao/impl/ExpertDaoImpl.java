package com.goldenweb.fxpg.frame.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.fxpg.frame.dao.ExpertDao;
import com.goldenweb.fxpg.frame.pojo.Expert;
import com.goldenweb.sys.pojo.SysUserinfo;

@Repository
public class ExpertDaoImpl extends BaseDao<Expert> implements ExpertDao{
	
	@Override
	public List<Object[]>  findExpertPageList(String xm, String yjly, String zyyjfx,
			int page, int rows,SysUserinfo user,String orgid,String nianling,String danwei,String zhiwu,String startcsny,String endcsny) {
		StringBuffer sql=new StringBuffer();
		/*if(user.getRoles().indexOf("HNWWB")>-1){
			sql.append("select id,xingming,xingbie,minzu,zhichen,yanjiulingyu,zhuyaoyanjiufangxiang,nianling from Expert where 1=1  ");
		}else{
			sql.append("select id,xingming,xingbie,minzu,zhichen,yanjiulingyu,zhuyaoyanjiufangxiang,nianling from Expert where 1=1 and create_user='"+user.getId()+"' ");
		}*/
		//省看省的专家，市看市的
		sql.append("select id,xingming,xingbie,minzu,zhichen,yanjiulingyu,zhuyaoyanjiufangxiang,nianling,chushengnianyue,danweizhiwu from Expert where 1=1 and create_user='"+user.getId()+"' ");
		if(orgid!=null&&!orgid.equalsIgnoreCase("")){
			sql.append(" and org_guid = '"+orgid+"' ");
//			sql.append(" and  create_user in (select id from SysUserinfo where mainOrgid = ")
//			.append(orgid).append(") ");
		}
		if(null!=xm&&!xm.equalsIgnoreCase("")){
			sql.append(" and xingming LIKE '%"+xm+"%' ");
		}
		if(null!=yjly&&!yjly.equalsIgnoreCase("")){
			sql.append(" and yanjiulingyu LIKE '%"+yjly+"%'");
		}
		if(null!=zyyjfx&&!zyyjfx.equalsIgnoreCase("")){
			sql.append(" and zhuyaoyanjiufangxiang LIKE '%"+zyyjfx+"%'");
		}
		if(null!=nianling&&!nianling.equalsIgnoreCase("")){
			sql.append(" and danweizhiwu like '%"+nianling+"%'");
		}
		if(null!=danwei&&!danwei.equalsIgnoreCase("")){
			sql.append(" and danwei like '%"+danwei+"%'");
		}
		if(null!=zhiwu&&!zhiwu.equalsIgnoreCase("")){
			sql.append(" and zhiwu like '%"+zhiwu+"%'");
		}
		if(null!=startcsny&&!startcsny.equalsIgnoreCase("")){
			sql.append(" and chushengnianyue >= ").append("'"+startcsny+"' ");
		}
		if(null!=endcsny&&!endcsny.equalsIgnoreCase("")){
			sql.append(" and  chushengnianyue <= ").append("'"+endcsny+"' ");
		}
		sql.append(" order by create_date desc ");
		return this.findListToPageByHql(sql.toString(), null, page, rows);
	}
	
	@Override
	public int findExpertCountNumber(String xm, String yjly, String zyyjfx,
			int page, int rows,SysUserinfo user,String orgid,String nianling,String danwei,String zhiwu,String startcsny,String endcsny) {
		StringBuffer sql=new StringBuffer();
//		if(user.getRoles().indexOf("HNWWB")>-1){
//			sql.append("select count(*) from Expert where 1=1  ");
//		}else{
//			sql.append("select count(*) from Expert where 1=1 and create_user='"+user.getId()+"' ");
//		}
		sql.append("select count(*) from Expert where 1=1 and create_user='"+user.getId()+"' ");
		if(orgid!=null&&!orgid.equalsIgnoreCase("")){
			sql.append(" and org_guid = '"+orgid+"' ");
//			if(orgid!=null&&!orgid.equalsIgnoreCase("")){
//				sql.append(" and  create_user in (select id from SysUserinfo where mainOrgid = ")
//				.append(orgid).append(") ");
//			}
		}
		if(null!=xm&&!xm.equalsIgnoreCase("")){
			sql.append(" and xingming LIKE '%"+xm+"%' ");
		}
		if(null!=yjly&&!yjly.equalsIgnoreCase("")){
			sql.append(" and yanjiulingyu LIKE '%"+yjly+"%'");
		}
		if(null!=zyyjfx&&!zyyjfx.equalsIgnoreCase("")){
			sql.append(" and zhuyaoyanjiufangxiang LIKE '%"+zyyjfx+"%'");
		}
		if(null!=nianling&&!nianling.equalsIgnoreCase("")){
			sql.append(" and danweizhiwu like '%"+nianling+"%'");
		}
		if(null!=danwei&&!danwei.equalsIgnoreCase("")){
			sql.append(" and danwei like '%"+danwei+"%'");
		}
		if(null!=zhiwu&&!zhiwu.equalsIgnoreCase("")){
			sql.append(" and zhiwu like '%"+zhiwu+"%'");
		}
		if(null!=startcsny&&!startcsny.equalsIgnoreCase("")){
			sql.append(" and chushengnianyue >= ").append("'"+startcsny+"' ");
		}
		if(null!=endcsny&&!endcsny.equalsIgnoreCase("")){
			sql.append(" and  chushengnianyue <= ").append("'"+endcsny+"' ");
		}
		return this.findListCount(sql.toString(), null);
	}
	
	
	@Override
	public void saveExpert(Expert expert){
		this.save(expert);
	}
	
	@Override
	public Expert findExpertById(String id){
		return this.get(id);
	}
	@Override
	public void mergeExpert(Expert expert,String id){
		this.merge(expert, id);
	}
	
	@Override
	public String findImagePath(String id){
		String sql = "select id,file_url from sys_uploadfile where table_uuid='"+id
				+"' order by upload_time desc";
		List<Object[]> list = findBySql2(sql);
		if(list!=null&&list.size()>0){
			return list.get(0)[1].toString();
		}
		return null;
	}
	
	@Override
	public void removeExpert(String id){
		Expert ep=this.get(id);
		this.delete(ep);
	}
	
	@Override
	public void importData(String sql){
		this.executeSql(sql);
	}
	@Override
	public List findExpertByIds(String id){
		StringBuffer sb = new StringBuffer();
		String[] ids=id.split(",");
		StringBuffer str=new StringBuffer();
		for(int i=0;i<ids.length;i++){
			if(i>0){
				str.append(",");
			}
			str.append("'"+ids[i]+"'");
		}
		sb.append("select xingming,xingbie,chushengnianyue,nianling,danwei,zhiwu,minzu,zhengzhimianmao,zhuanjianleibie,zhichen,danweizhiwu,yanjiulingyu,biyeyuanxiaojizhuanye,zuizhongxueli,tongxundizhi,youbian,bangongdianhua,chuanzhenhaoma,shouji,dianziyouxiang,jianyaogongzuojingli,zhuyaoyanjiufangxiang,suozaidanweiyijian,zhuguanbumenyjian,beizhu from Expert where id in ("+str+")");
		return this.findByHql2(sb.toString());
	}
	
	@Override
	public List findExpertByCondition(String xm,String yjly,String zyyjfx,String nianling,String danwei,String zhiwu,SysUserinfo user,String orgid){
		StringBuffer sql = new StringBuffer();
		if(user.getRoles().indexOf("HNWWB")>-1){
			sql.append("select xingming,xingbie,chushengnianyue,nianling,danwei,zhiwu,minzu,zhengzhimianmao,zhuanjianleibie,zhichen,danweizhiwu,yanjiulingyu,biyeyuanxiaojizhuanye,zuizhongxueli,tongxundizhi,youbian,bangongdianhua,chuanzhenhaoma,shouji,dianziyouxiang,jianyaogongzuojingli,zhuyaoyanjiufangxiang,suozaidanweiyijian,zhuguanbumenyjian,beizhu from Expert where 1=1 ");
		}else{
			sql.append("select xingming,xingbie,chushengnianyue,nianling,danwei,zhiwu,minzu,zhengzhimianmao,zhuanjianleibie,zhichen,danweizhiwu,yanjiulingyu,biyeyuanxiaojizhuanye,zuizhongxueli,tongxundizhi,youbian,bangongdianhua,chuanzhenhaoma,shouji,dianziyouxiang,jianyaogongzuojingli,zhuyaoyanjiufangxiang,suozaidanweiyijian,zhuguanbumenyjian,beizhu from Expert where 1=1 and create_user='"+user.getId()+"' ");
		}
		if(orgid!=null&&!orgid.equalsIgnoreCase("")){
			sql.append(" and org_guid = '"+orgid+"' ");
//			if(orgid!=null&&!orgid.equalsIgnoreCase("")){
//				sql.append(" and  create_user in (select id from SysUserinfo where mainOrgid = ")
//				.append(orgid).append(") ");
//			}
		}
		if(null!=xm&&!xm.equalsIgnoreCase("")){
			sql.append(" and xingming LIKE '%"+xm+"%' ");
		}
		if(null!=yjly&&!yjly.equalsIgnoreCase("")){
			sql.append(" and yanjiulingyu LIKE '%"+yjly+"%'");
		}
		if(null!=zyyjfx&&!zyyjfx.equalsIgnoreCase("")){
			sql.append(" and zhuyaoyanjiufangxiang LIKE '%"+zyyjfx+"%'");
		}
		if(null!=nianling&&!nianling.equalsIgnoreCase("")){
			sql.append(" and nianling = '"+nianling+"'");
		}
		if(null!=danwei&&!danwei.equalsIgnoreCase("")){
			sql.append(" and danwei like '%"+danwei+"%'");
		}
		if(null!=zhiwu&&!zhiwu.equalsIgnoreCase("")){
			sql.append(" and zhiwu like '%"+zhiwu+"%'");
		}
		
		return this.findByHql2(sql.toString());
	}
}
