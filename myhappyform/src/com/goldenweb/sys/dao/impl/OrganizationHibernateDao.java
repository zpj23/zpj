package com.goldenweb.sys.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.OrganizationHibernate;
import com.goldenweb.sys.pojo.SysOrganization;

@Repository
public class OrganizationHibernateDao extends BaseDao<SysOrganization> implements OrganizationHibernate{

	/***********************************************************************/

	/**
	 * 保存机构并返回主键
	 */
	@Override
	public int saveOrg(SysOrganization orginfo) {		
		Session session =null;
		try {
			session=sessionFactory.getCurrentSession();
			Serializable id = session.save(orginfo);
			return (Integer) id;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return 0;	
	}


	/**
	 * 查询所有机构下的人员，人员未关联机构的不显示
	 * @return
	 */
	@Override
	public List<Object[]> findUsers() {
		try {
			String hql = "select id,username from SysUserinfo where mainOrgid is not null and isDel=0";
			List<Object[]> list = this.findByHql2(hql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}


	/**
	 * 查询所有流程人员
	 * @return
	 */
	@Override
	public List<Object[]> findProcessUsers() {
		try {
			String sql = "select id_,first_ from act_id_user ";
			List<Object[]> list = this.findBySql2(sql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	
	@Override
	public void saveGroup(String orgid,String orgname){
		StringBuffer sql =new StringBuffer();
		sql.append(" insert into act_id_group values('").append(orgid).append("','1','")
		.append(orgname).append("','security-role')");
		this.executeUpdateOrDelete(sql.toString());
	}
	
	
	@Override
	public List findOrgByParentorgid(String id){		
		String hql=" from SysOrganization where parentOrgid = ?";
		try {
			List list = this.findByHql(hql,String.valueOf(id) );
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	
	@Override
	public List findOrgByParentdeptid(String id){		
		String hql=" from SysOrganization where parentDeptid = ?";
		try {
			List list = this.findByHql(hql,String.valueOf(id) );
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}

	
	
	
	/**
	 * 删除流程中的群组
	 * @param id
	 */
	@Override
	public void delGroup(int id){
		String sql = "delete from act_id_group where id_ = '"+id+"' ";
		this.executeUpdateOrDelete(sql);
	}
	

	/**
	 * 部门下的人员
	 * @param orgid
	 * @return
	 */
	@Override
	public List<Object[]> findUserByOrgid(int orgid) {
		String hql = "select id,username from SysUserinfo where mainOrgid=? order by num ";
		List<Object[]> list;
		try {
			list = this.findByHql(hql, orgid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	@Override
	public SysOrganization getEntity(int id) {
		return this.get(id);
	}
	
	@Override
	public void deleteEntity(int  id) {
		String sql = "delete from sys_organization where id ="+id;	
		executeUpdateOrDelete(sql);
	}

	@Override
	public List getANode(){
		String sql ="select a.id,a.org_name orgName,nvl(b.subtotal,0) subtotal from sys_organization a " +
				"left join third_count b on b.org_guid = a.id where parent_orgid is null";
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List organizationActionList = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return organizationActionList;
	}

	@Override
	public List getChildNode(String nodeId) {
		String sql ="select a.id,a.org_name orgName,nvl(b.subtotal,0) subtotal from( " +
		"select * from sys_organization where parent_orgid =:nodeId or id =:nodeId start with id =:nodeId connect by prior id = parent_orgid) a " +
		"left join third_count b on b.org_guid = a.id order by id";
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
		sqlQuery.setParameter("nodeId", nodeId);
		List organizationActionList = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return organizationActionList;
	}


	@Override
	public List<SysOrganization> findOrgLvOne() {
		String sql = "select id,org_name,parent_orgid from sys_organization where parent_orgid is null";
		List<Object[]> list = findBySql2(sql);
		if(list!=null&&list.size()>0){
		SysOrganization org =null;
		List<SysOrganization> lt = new ArrayList<SysOrganization>();
		for(int i=0;i<list.size();i++){
			org = new SysOrganization();
			org.setId(Integer.parseInt(list.get(i)[0].toString()));
			org.setOrgName(list.get(i)[1].toString());
			org.setParentOrgid(null);
			lt.add(org);
		}
		return lt;
		}
		return null;
		
	}


	@Override
	public void saveOrUpdateResource(SysOrganization org) {
		saveOrUpdate(org);
	}


	@Override
	public void deleleOrg(String id) {
		String sql = "delete from sys_organization where id ="+id;
		this.executeUpdateOrDelete(sql);
	}


	@Override
	public List<Object[]> checkOrgCode(String id, String orgCode) {
		List<Object[]> list=null;
		try {
			if(id==null||"".equals(id)){
				//新增时
				String sql = " select id from sys_organization Where item_code=? ";
				list = this.findBySql(sql, orgCode);
			}else{
				//修改时
				String sql = " select id From sys_organization where item_code=? and id <> ? " ;
				list = this.findBySql(sql, orgCode,id);
			}				
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<Object[]> checkChildOrgData(String id) {
		String hql = " from SysOrganization where parentOrgid=?";
		return this.findByHql(hql, id);
	}


	@Override
	public List<Object[]> findUserBySearchname(String searchname) {
		String hql = "select id,username from SysUserinfo where isDel = 0 and (loginname like '%"+searchname+
		"%' or username like '%"+searchname+"%') order by num ";
		return this.findByHql2(hql);
	}


	@Override
	public String findOrgidByCode(String code) {
		String sql = "select id,item_code from sys_organization where item_code=?";
		List<Object[]> list = findBySql(sql, code);
		return list.get(0)[0].toString();
	}

	
	@Override
	public List<Object[]> searchoorg(String selectword) {
		String sql =" select distinct parent_orgid,null from sys_organization where org_name like '%"+selectword+"%' and item_code like 'MC3206%'";
		return this.findBySql2(sql);
	}


}
