/** 
 * @Description: TODO(实现表单接口)
 * @Title: DynamicTableImpl.java
 * @Package com.goldenweb.core.frame.dao.impl
 * @author Lee
 * @date 2014-2-26 下午01:47:11
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.dao.impl;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.goldenweb.fxpg.frame.dao.DynamicFormDao;
import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.service.UserinfoService;
/**
 * @Description: TODO(实现表单工厂接口)
 * @ClassName: DynamicFormImpl
 * @author Lee 
 * @date 2014-2-26 下午01:47:11
 *
 */
@Component("dynamicFormDao")
public class DynamicFormDaoImpl extends GenericDaoHibernate implements DynamicFormDao{
	@Autowired
	private UserinfoService userinfoService;	
	@Override
	public boolean createSQL(String sql){
		//System.out.println("sql : "+sql);
		Integer integer =  this.getSession().createSQLQuery(sql).executeUpdate();
		return new Boolean(integer.intValue()>0);
	}

	@Override
	@Transactional
	public Map queryForm(String tableName, String guid) {
		if(tableName!=null){
		String sql = "select * from "+tableName+" where "+tableName+"_guid = '"+guid+"'";
		Map map = (Map) this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		return map;
		}
		return null;
	}

	@Override
	public List getANode() {
		String sql ="select a.id,a.org_name orgName,nvl(b.subtotal,0) subtotal from sys_organization a " +
		"left join third_count b on b.org_guid = a.id where parent_orgid is null";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		List organizationActionList = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return organizationActionList;
	}

	@Override
	public List getChildNode(String nodeId, String tableName_) {		
		int levelnum = userinfoService.toDeptLvById(nodeId);
		//机构的命名是MC2+2+2+3+3+3来定的
		if(levelnum<=10){
			levelnum=levelnum-2;
		}else{
			levelnum=levelnum-1;
		}
		String str_ = "select t2.inum subtotal,t1.icode from (select distinct a.org_guid,b.item_code,substr(item_code,0,"+levelnum+") icode from "+tableName_
				+" a  left join sys_organization b on a.org_guid = b.id) t1 "+
		"left join (select count(a.org_guid) inum ,substr(b.item_code,0,"+levelnum+") icode from "+tableName_+
		" a  left join sys_organization b on a.org_guid = b.id group by substr(b.item_code,0,"+levelnum+")) t2 on t1.icode=t2.icode";
		
		/*String sql = "select a.id,a.org_name orgName,nvl(b.subtotal,0) subtotal from( " +
				" select * from sys_organization where parent_orgid =:nodeId start with id =:nodeId connect by prior id = parent_orgid) a " +
				" left join ("+str_+") b on b.org_guid = a.id order by id ";*/
		String sql = "select distinct a.id,a.org_name orgName,nvl(b.subtotal,0) subtotal from( " +
				" select * from sys_organization where parent_orgid =:nodeId ) a " +
				" left join ("+str_+") b on b.icode = substr(a.item_code,0,"+levelnum+") order by id ";
		
		
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		sqlQuery.setParameter("nodeId", nodeId);
		List organizationActionList = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return organizationActionList;
	}

	@Override
	public List queryFromColumn(String tableName) {
		String sql = "select a.col_name,a.col_type, a.col_remark,a.table_guid,b.child_data_guid,'' as dydata,a.is_select,a.is_listshow,d.plugin_type,b.orgid " +
				" from DY_COLUMN a,DY_DATA_THIRD b,dy_plugin d " +
				" where a.table_name =:tableName and a.COL_GUID = b.COL_GUID(+) " +
				" and d.plugin_guid = a.plugin_guid order by a.sort";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		sqlQuery.setParameter("tableName", tableName);
		List list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	@Override
	public Page queryFromData(String tableName, Page page, String searchColumnStr, SysUserinfo sysUserinfo, String orgGuid_) {
		StringBuffer stringBuffer = new StringBuffer();
		/*stringBuffer.append("select * from ( ")
		.append("select * from "+tableName+" W where 1 = 1 and exists (select id,main_orgid from (select id,main_orgid ")
		.append("from sys_userinfo where main_orgid = '"+orgGuid_+"' or id = '"+sysUserinfo.getId()+"' and is_del = 0) where ")
		.append("W.CREATE_USER = id) ")
		.append(searchColumnStr)
		.append(" )")
		.append("order by create_date desc");*/
		//暂存的数据非本人不可见   and  w.status = '暂存'
		String conn = " and  ((w.create_user = "+sysUserinfo.getId()+" ) or (w.create_user <> "+sysUserinfo.getId()+" and w.status <> '暂存') ) ";
		//查询条件
		stringBuffer.append("select * from ( ")
		.append("select * from "+tableName+" W where  exists (select id from sys_organization where item_code like '"+orgGuid_+
				"%' and ")
		.append("W.org_guid = id ) ")
		.append(searchColumnStr);
		if("weiwxx".equals(tableName)){
			//涉稳采集
		  stringBuffer.append(conn);
		}
		stringBuffer.append(" )")
		.append("order by create_date desc");
		//System.out.println(stringBuffer.toString());
		SQLQuery sqlQuery = this.getSession().createSQLQuery(stringBuffer.toString());
		page.setTotalResult(sqlQuery.list().size());
		sqlQuery.setFirstResult(page.firstResult());
		sqlQuery.setMaxResults(page.getPageSize());
		List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setResults(list);
		return page;
	}
	
	@Override
	public Page queryFromData(String tableName, Page page, String searchColumnStr) {
		String sql = "select * from "+tableName+" where 1=1 ";
		sql+=searchColumnStr;
		sql+=" order by create_date desc";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		page.setTotalResult(sqlQuery.list().size());
		sqlQuery.setFirstResult(page.firstResult());
		sqlQuery.setMaxResults(page.getPageSize());
		List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setResults(list);
		return page;
	}

	@Override
	public boolean delFormTable(String tableName, String tableGuid) {
		String sql = "delete "+tableName+" where "+tableName+"_guid = '"+tableGuid+"'";
		Integer integer = this.getSession().createSQLQuery(sql).executeUpdate();
		return new Boolean(integer>0);
	}
	
	@Override
	public boolean delChildrenTable(String tableName, String tableGuid) {
		String sql = "delete "+tableName+" where ORG_GUID = '"+tableGuid+"'";
		Integer integer = this.getSession().createSQLQuery(sql).executeUpdate();
		return new Boolean(integer>0);
	}

	@Override
	public Map queryColumnById(String orgId) {
		String sql = "select table_name from dy_table where bus_guid = :orgId";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		sqlQuery.setParameter("orgId", orgId);
		Map map = (Map) sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		return map;
	}

	@Override
	public List queryChildForm(String tableName, String tableGuid_) throws UnsupportedEncodingException,
			SQLException {
		String sql = "select * from "+tableName+" where ORG_GUID = '"+tableGuid_+"'";
		/*if("STABLE_SWRY".equals(tableName)){//涉稳人员此处写死，关联主键不能用ORG_GUID这个字段
		       sql = "select * from "+tableName+" where LINK_GUID = '"+tableGuid_+"'";
		}*/
		List mapList = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return mapList;
	}

	@Override
	public boolean updateFormStatus(String guid, String tableName, String UserId, String status_) {
		String sql = "update "+tableName+" set status ='"+status_+"', UPDATE_USER='"+UserId+"', UPDATE_DATE=sysdate where "+tableName+"_guid = '"+guid+"'";
		Integer integer = this.getSession().createSQLQuery(sql).executeUpdate();
		return new Boolean(integer>0);
	}

	@Override
	public Page queryFromByStatus(String tableName, String createuser,
			String sw_status_, Page page, String searchColumnStr) {
		String sql = "select * from "+tableName+" where UPDATE_USER = '"+createuser+"'";
		if(searchColumnStr!=null && !"".equals(searchColumnStr)){
			sql+=searchColumnStr;
		}
		sql+=" order by UPDATE_DATE desc";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		page.setTotalResult(sqlQuery.list().size());
		sqlQuery.setFirstResult(page.firstResult());
		sqlQuery.setMaxResults(page.getPageSize());
		List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setResults(list);
		return page;
	}

	@Override
	public Page showExecutionList(List businessKeyList, String tableName,
			String searchColumnStr, Page page) {
		String sql = "select * from "+tableName+" where "+tableName+"_guid in(:businessKeyList)";
		if(searchColumnStr!=null && !"".equals(searchColumnStr)){
			sql+=searchColumnStr;
		}
		sql+=" order by CREATE_DATE desc";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		sqlQuery.setParameterList("businessKeyList", businessKeyList);
		page.setTotalResult(sqlQuery.list().size());
		sqlQuery.setFirstResult(page.firstResult());
		sqlQuery.setMaxResults(page.getPageSize());
		List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setResults(list);
		return page;
	}

	@Override
	public List queryHomeYuJing(String tableName, int rowCount) {
		String sql = "select max(weiwxx_guid) weiwxx_guid, shidmc, max(fenlxx) fenlxx, max(guankjb) guankjb, max(create_date) create_date from ( "
				+ " select a.weiwxx_guid,a.shidmc,a.fenlxx,c.guankjb,a.create_date from "+tableName+" a, act_con_business b, yanpfx c "
				+ " where a.weiwxx_guid = b.business_guid_ and c.yanpfx_guid = b.business_con_guid_"
				+ " order by a.create_date desc ) group by shidmc order by create_date desc";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(rowCount);
		List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	@Override
	public Page queryHomeList(List businessKeyList, String tableName,
			String searchColumnStr, Page page) {
		if(businessKeyList.isEmpty()){
			return page;
		}
		String sql = "select * from "+tableName+" where "+tableName+"_guid in(:businessKeyList)";
		if(searchColumnStr!=null && !"".equals(searchColumnStr)){
			sql+=searchColumnStr;
		}
		sql+=" order by CREATE_DATE desc";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		sqlQuery.setParameterList("businessKeyList", businessKeyList);
		page.setTotalResult(sqlQuery.list().size());
		sqlQuery.setFirstResult(page.firstResult());
		sqlQuery.setMaxResults(page.getPageSize());
		sqlQuery
		.addScalar("WEIWXX_GUID", StringType.INSTANCE)
		.addScalar("SHIDMC", StringType.INSTANCE)
		.addScalar("CREATE_DATE", StringType.INSTANCE)
		.addScalar("STATUS", StringType.INSTANCE)
		.addScalar("CAIJDW", StringType.INSTANCE);
		List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setResults(list);
		return page;
	}
	
	@Override
	public boolean updateSwdb(String guid, String tableName, String swdb) {
		String sql = "update "+tableName+" set SWDB ='"+swdb+"' where "+tableName+"_guid = '"+guid+"'";
		Integer integer = this.getSession().createSQLQuery(sql).executeUpdate();
		return new Boolean(integer>0);
	}

	
	@Override
	public Page queryFromByLinkflag(String tableName, String createuser,
			String sw_status_, Page page, String searchColumnStr, String linkflag) {
		String sql = "select * from "+tableName+" where   "+
			"   exists (select tableid from WORKFLOW_PROCESSLINK where tableid= weiwxx_guid and userid = '"+createuser+"') ";
		if(searchColumnStr!=null && !"".equals(searchColumnStr)){
			sql+=searchColumnStr;
		}
		sql+=" order by UPDATE_DATE desc";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		page.setTotalResult(sqlQuery.list().size());
		sqlQuery.setFirstResult(page.firstResult());
		sqlQuery.setMaxResults(page.getPageSize());
		List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setResults(list);
		return page;
	}

}
