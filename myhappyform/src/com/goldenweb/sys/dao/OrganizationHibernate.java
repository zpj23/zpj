package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysOrganization;

public interface OrganizationHibernate {

	//保存
	public int saveOrg(SysOrganization orginfo);
	
	//查询所有机构下的人员，人员未关联机构的不显示
	public List<Object[]> findUsers();
	
	//查询所有流程人员
	public List<Object[]> findProcessUsers();
	
	//保存流程群组
	public void saveGroup(String orgid,String orgname);
	
	//依据上级id获取org
	public List findOrgByParentorgid(String id);
	
	//依据部门id获取org
	public List findOrgByParentdeptid(String id);
	
	//删除流程中的群组
	public void delGroup(int id);
	
	//部门下的人员
	public List<Object[]> findUserByOrgid(int orgid);

	//获取单个实体
	public SysOrganization getEntity(int id);
    
	//删除实体
	public void deleteEntity(int id);
    
	//查询所有
	public List<SysOrganization> findAll();

	//更新
	public void update(SysOrganization orginfo);
	 
	/**
	 * @Description TODO(获取一级节点)
	 * @Title getANode
	 * @return List  
	 * @author Lee
	 * @time 2014-2-22 上午09:19:07
	 */
	public List getANode();
	
	/**
	 * @Description TODO(获取子节点)
	 * @Title getChildNode
	 * @param nodeId
	 * @return List  
	 * @author Lee
	 * @time 2014-2-22 上午09:19:20
	 */
	public List getChildNode(String nodeId);

	public List<SysOrganization> findOrgLvOne();

	public void saveOrUpdateResource(SysOrganization org);

	public void deleleOrg(String id);

	public List<Object[]> checkOrgCode(String id, String orgCode);

	public List<Object[]> checkChildOrgData(String id);

	public List<Object[]> findUserBySearchname(String searchname);

	public String findOrgidByCode(String code);

	public List<Object[]> searchoorg(String selectword);

	

}
