package com.goldenweb.sys.service;

import java.util.List;

import com.goldenweb.sys.pojo.SysOrganization;

public interface OrganizationService {

	//构建组织机构的tree
	//public String findOrgJson();
	
	//
	public String findDeptJson(String nodeid);
	
	//用于组织机构多选
	public String findDeptsJson();
	
	//查询所有机构下的人员，人员未关联机构的不显示
	public List<Object[]> findUsers();
	
	//查询所有流程人员
	public List<Object[]> findProcessUsers();
	
	//单个组织机构信息
	public SysOrganization showOneOrg(int id);
	
	//保存/编辑组织机构
	public Object saveOrg(List<SysOrganization> list);
	
	//删除组织机构
	public String delOrg(int id);
	
	//获取部门下的人员
	public String showUserOfOrg(int orgid);
	
	/**
	 * @Description TODO(获取一级节点)
	 * @Title getANode
	 * @return List  
	 * @author Lee
	 * @time 2014-2-22 上午09:19:07
	 */
	public String getANode();
	
	/**
	 * @Description TODO(获取子节点)
	 * @Title getChildNode
	 * @param nodeId
	 * @return String
	 * @author Lee
	 * @time 2014-2-21 下午01:07:05
	 */
	public String getChildNode(String nodeId);

	/**
	 * 显示组织机构节点下的orgjosn
	 * @param id
	 * @return
	 */
	public String showOrgJson(String id);

	public List<Object[]> checkOrgCode(String id, String orgCode);

	public SysOrganization saveOrg(SysOrganization org);

	public List<Object[]> checkChildOrgData(String id);

	public void deleleOrg(String id);

	public String findDeptJsonEasyui(String string,String selectid);

	public List<SysOrganization> findDeptJsonEasyui2(String id);

	public List<Object[]> findDept(String orgid);

	public String showUserOfSearchname(String searchname);

	public String findDeptsByOrgidJson(List<Object []> list, String selectid);

	public String findOrgCodeJsonEasyui(String string,String selectid);
	
	public String getOrgDeptName(String orgCode,String deptCode);

	public String searchoorg(String selectword);
		
}
