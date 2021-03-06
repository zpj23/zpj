/** 
 * @Description: TODO(表单接口)
 * @Title: DynamicForm.java
 * @Package com.goldenweb.core.frame.dao
 * @author Lee
 * @date 2014-2-26 下午01:40:03
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.pojo.SysUserinfo;


/**
 * @Description: TODO(表单接口)
 * @ClassName: DynamicForm
 * @author Lee 
 * @date 2014-2-26 下午01:40:03
 *
 */
public interface DynamicFormDao{
	
	/**
     * 执行SQL语句
     * @Title createSQL
     * @param sql
     * @return boolean  
     * @author Lee
     * @time 2014-3-21 上午11:13:13
     */
    public boolean createSQL(String sql);
    
    /**
     * 查询动态表数据对象
     * @Title queryForm
     * @param tableName 表名
     * @param guid 主键
     * @return Map
     * @author Lee
     * @time 2014-3-21 下午04:14:17
     */
    public Map queryForm(String tableName, String guid);
    
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
	 * @param tableName_ 表名称
	 * @return List  
	 * @author Lee
	 * @time 2014-2-22 上午09:19:20
	 */
	public List getChildNode(String nodeId, String tableName_);
	
	/**
	 * 查询动态表列
	 * @Title queryFromColumn
	 * @param tableName
	 * @return List  
	 * @author Lee
	 * @time 2014-3-24 上午11:01:27
	 */
	public List queryFromColumn(String tableName);
	
	/**
	 * 查询动态表数据
	 * @Title queryFromData
	 * @param tableName 表名
	 * @param page 分页信息
	 * @param searchColumnStr 模糊检索
	 * @param sysUserinfo 登录用户对象
	 * @param orgGuid_ 组织机构主键
	 * @return Page  
	 * @author Lee
	 * @time 2014-3-24 上午11:01:27
	 */
	public Page queryFromData(String tableName, Page page, String searchColumnStr, SysUserinfo sysUserinfo, String orgGuid_);
	
	/**
	 * 查询动态表数据
	 * @param tableName
	 * @param page
	 * @param searchColumnStr
	 * @return Page
	 */
	public Page queryFromData(String tableName, Page page, String searchColumnStr);
	
	/**
	 * 删除动态表数据
	 * @Title delFormTable
	 * @param tableName
	 * @param tableGuid
	 * @return boolean  
	 * @author Lee
	 * @time 2014-3-24 上午11:03:30
	 */
	public boolean delFormTable(String tableName, String tableGuid);
	
	/**
	 * 根据业务ID查询表名
	 * @Title queryColumnById
	 * @param orgId
	 * @return Map  
	 * @author Lee
	 * @time 2014-4-9 下午01:43:58
	 */
	public Map queryColumnById(String orgId);
	
	/**
	 * 查询子表数据集合
	 * @Title queryChildForm
	 * @param tableName 表名称
	 * @param tableGuid_ 数据主键
	 * @return List
	 * @throws UnsupportedEncodingException
	 * @throws SQLException Map  
	 * @author Lee
	 * @time 2014-4-21 下午03:12:31
	 */
	public List queryChildForm(String tableName, String tableGuid_) throws UnsupportedEncodingException, SQLException;
	
	/**
	 * 删除子表信息
	 * @Title delChildrenTable
	 * @param tableName
	 * @param tableGuid
	 * @return boolean  
	 * @author Lee
	 * @time 2014-4-22 下午02:50:30
	 */
	public boolean delChildrenTable(String tableName, String tableGuid);
	
	/**
	 * 更新表单状态
	 * @Title updateFormStatus
	 * @param guid
	 * @param tableName
	 * @param UserId
	 * @param status_
	 * @return boolean  
	 * @author Lee
	 * @time 2014-5-7 下午04:13:10
	 */
	public boolean updateFormStatus(String guid, String tableName, String UserId, String status_);
	
	/**
	 * 信息状态集合
	 * @Title queryFromByStatus
	 * @param tableName
	 * @param createuser
	 * @param sw_status_
	 * @param page
	 * @param searchColumnStr
	 * @return Page  
	 * @author Lee
	 * @time 2014-5-7 下午06:50:03
	 */
	public Page queryFromByStatus(String tableName, String createuser,
			String sw_status_, Page page, String searchColumnStr);
	
	/**
     * 待办列表
     * @param businessKeyList 业务ID集合
     * @param tableName 表名称
     * @param searchColumnStr 检索字段
     * @param page 分页
     * @throws SQLException 
	 * @throws UnsupportedEncodingException 
     * @return Page
     */
	public Page showExecutionList(List businessKeyList, String tableName,
			String searchColumnStr, Page page);
	
	/**
	 * 首页预警集合
	 * 
	 * @param tableName 表名称
	 * @param rowCount 展示行数
	 * @return List
	 */
	public List queryHomeYuJing(String tableName, int rowCount);
	
	/**
	 * 首页待办集合
	 * 
	 * @param businessKeyList 业务ID集合
     * @param tableName 表名称
     * @param searchColumnStr 检索字段
     * @param page 分页
     * @throws SQLException 
	 * @throws UnsupportedEncodingException 
     * @return Page
	 */
	public Page queryHomeList(List businessKeyList, String tableName,
			String searchColumnStr, Page page);
	
	/**
	 * 更新督查督办
	 * 
	 * @param guid
	 * @param tableName
	 * @param swdb
	 * @return boolean
	 */
	public boolean updateSwdb(String guid, String tableName, String swdb);

	/**
	 * 查询流程关联数据
	 * @param tableName
	 * @param createuser
	 * @param sw_status_
	 * @param page
	 * @param string
	 * @param linkflag
	 * @return
	 */
	public Page queryFromByLinkflag(String tableName, String createuser,
			String sw_status_, Page page, String searchColumnStr, String linkflag);
}
