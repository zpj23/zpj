package com.goldenweb.fxpg.frame.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.pojo.SysUserinfo;


public interface DynamicFormService {
	
	/**
     * 执行SQL语句
     * @Title createSQL
     * @param sql
     * @param sysUserinfo 登录用户对象
     * @param blobObjArray_ 大字段数组
     * @param dateObjArray_ 日期数组
     * @param childSql_ 子表SQL
     * @return boolean  
     * @author Lee
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SQLException 
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
     * @time 2014-3-21 上午11:13:13
     */
	public boolean createSQL(String sql, SysUserinfo sysUserinfo,
			String blobObjArray_, String dateObjArray_, String childSql_)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException,
			UnsupportedEncodingException, SQLException, Exception;
	
	
	public boolean createSQLLinkid(String sql, SysUserinfo sysUserinfo,
			String blobObjArray_, String dateObjArray_, String childSql_)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException,
			UnsupportedEncodingException, SQLException, Exception;
    
    /**
     * 查询动态表数据对象
     * @Title queryForm
     * @param tableName 表名
     * @param guid 主键
     * @param blobObjArray_ 大字段列
     * @return Map
     * @author Lee
     * @throws SQLException 
     * @throws UnsupportedEncodingException 
     * @time 2014-3-21 下午04:14:17
     */
    public Map queryForm(String tableName, String guid, String blobObjArray_) throws UnsupportedEncodingException, SQLException;
	
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
	 * @param tableName_ 表名称
	 * @return String
	 * @author Lee
	 * @time 2014-2-21 下午01:07:05
	 */
	public String getChildNode(String nodeId, String tableName_);

	/**
	 * 显示组织机构节点下的orgjosn
	 * @param id
	 * @return
	 */
	public String showOrgJson(String id);
	
	public String findDeptJson(String nodeid);
	
	/**
	 * 查询动态表列
	 * @Title queryFromColumn
	 * @param tableName
	 * @return List  
	 * @author Lee
	 * @time 2014-3-24 上午11:01:27
	 */
	public String queryFromColumn(String tableName, Page page);
	
	/**
	 * 查询动态表数据
	 * @Title queryFromData
	 * @param tableName 表名
	 * @param page 分页信息
	 * @param formatterMap 列绑定数据Map
	 * @param searchColumnStr 模糊检索
	 * @param sysUserinfo 登录用户对象
	 * @param orgGuid_ 组织机构主键
	 * @param blobObjArray_ 大字段列
	 * @return String  
	 * @author Lee
	 * @throws SQLException 
	 * @throws UnsupportedEncodingException 
	 * @time 2014-3-24 上午11:01:27
	 */
	public String queryFromData(String tableName, Page page,
			String formatterMap, String searchColumnStr,
			SysUserinfo sysUserinfo, String orgGuid_, String blobObjArray_)
			throws UnsupportedEncodingException, SQLException;
	
	/**
	 * 查询动态表数据
	 * @param tableName
	 * @param page
	 * @param searchColumnStr
	 * @param formatterMap
	 * @param blobObjArray_
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @throws SQLException
	 */
	public String queryFromData(String tableName, Page page, String searchColumnStr,
			String formatterMap,String blobObjArray_)
			throws UnsupportedEncodingException, SQLException;
	
	/**
	 * 删除动态表数据
	 * @Title delFormTable
	 * @param tableName
	 * @param tableGuid
	 * @return boolean  
	 * @author Lee
	 * @throws Exception 
	 * @time 2014-3-24 上午11:03:30
	 */
	public boolean delFormTable(String tableName, String tableGuid) throws Exception;
	
	/**
	 * 根据业务ID查询表名
	 * @Title queryColumnById
	 * @param orgId
	 * @return String  
	 * @author Lee
	 * @time 2014-4-9 下午01:43:58
	 */
	public String queryColumnById(String orgId);
	
	/**
	 * 查询子表数据集合
	 * @Title queryChildForm
	 * @param tableName 表名称
	 * @param tableGuid_ 数据主键
	 * @return String
	 * @throws UnsupportedEncodingException
	 * @throws SQLException Map  
	 * @author Lee
	 * @time 2014-4-21 下午03:12:31
	 */
	public String queryChildForm(String tableName, String tableGuid_) throws UnsupportedEncodingException, SQLException;
	
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
	 * @param formatterMap 格式化字段
     * @param searchColumnStr 检索字段
     * @param blobObjArray_ 大字段
	 * @return String  
	 * @author Lee
	 * @throws SQLException 
	 * @throws UnsupportedEncodingException 
	 * @time 2014-5-7 下午06:50:03
	 */
	public String queryFromByStatus(String tableName, String createuser,
			String sw_status_, Page page, String formatterMap,
			String searchColumnStr, String blobObjArray_)
			throws UnsupportedEncodingException, SQLException;
	
    /**
     * 待办列表
     * @param businessKeyList 业务ID集合
     * @param tableName 表名称
     * @param formatterMap 格式化字段
     * @param searchColumnStr 检索字段
     * @param blobObjArray_ 大字段
     * @param page 分页
     * @throws SQLException 
	 * @throws UnsupportedEncodingException 
     * @return String
     */
	public String showExecutionList(List businessKeyList, String tableName,
			String formatterMap, String searchColumnStr, String blobObjArray_,
			Page page) throws UnsupportedEncodingException, SQLException;
	
	/**
	 * 首页预警集合
	 * 
	 * @param tableName 表名称
	 * @param rowCount 展示行数
	 * @param formatterMap 格式字段
	 * @return String
	 */
	public String queryHomeYuJing(String tableName, int rowCount, String formatterMap);
	
	/**
     * 首页待办集合
     * 
     * @param tableName 表名称
	 * @param page 分页
	 * @param sysUserinfo 用户对象
	 * @return String
	 * @throws ParseException 
     */
	public List showHomeWaitList(String tableName, Page page, SysUserinfo sysUserinfo) throws ParseException;
	
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
	 * 保存流程关联信息(关注等)
	 * @param processInstanceId
	 * @param linkflag
	 * @return
	 */
	public boolean addprocesslink(String tableid, String linkflag,String userid,String islink);

	public String queryFromByLinkflag(String tablename_, String string,
			String status_, Page pageObject, String formatterColumnStr,
			String searchColumnStr, String blobObjArray_,String linkflag) throws UnsupportedEncodingException, SQLException;

	
	public List<Object[]> findLinkinfo(String tableName_, String tableGuid_,
			String curuserid, String linkflag);

	public String importWord(String tablename, String guid);

	public String findFilename(String tablename, String guid);

	public List<Object[]> findFileList(String tablename, String guid);
	
}
