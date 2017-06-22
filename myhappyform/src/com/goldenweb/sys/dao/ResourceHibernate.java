package com.goldenweb.sys.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.goldenweb.sys.pojo.SysOrganization;
import com.goldenweb.sys.pojo.SysResourceType;

public interface ResourceHibernate {

	//查询一级数据字典
	public List<Object []> findTypeLevelOne(String selectName,String selectCode,int parentid);
		
	//依据2级code查询内容
	public List<Object[]> findContentByCode(String code);
	
	//查询所有的字典数据
	public List<Object []> findItemAll();
	
	//check code 
	public List<Object[]> checkTypeCode(String typeid, String typecode);
	
	//检查是否有下级数据，有就不能删除
	public List<Object[]> checkChildData(String id);
	
	//查询节点下的配置信息
	public List<Object[]> findNodeUsers();

	public void setPageData(HttpServletRequest request, String sql,
			List<String> conditions, String page, int pageSize);

	public void saveOrUpdate(SysResourceType restype);

	public SysResourceType getEntity(int id);

	public void deleteData(String id);
	
	public void saveOrUpdateResource(SysResourceType restype);

	public List<SysResourceType> findResourceLvOne(String pid);

	public List<SysResourceType> findResourceType();

	public List<Object[]> findItemByParentid(String id);

	public void delItem(String id);

	public List<SysOrganization> findOrgAll();

	public List<Object[]> findSomeUser(String rolecode,String orgcode);

	public List<Object[]> findDeptdataByLevel(String level);

	/** 
	* @Method: getMaxItemCode 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param itemCode
	* @return 参数 
	* @return String    返回类型 
	* @throws 
	*/
	public String getMaxItemCode(String itemCode);

	
	
}
