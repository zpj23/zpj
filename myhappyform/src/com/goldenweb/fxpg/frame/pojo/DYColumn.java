package com.goldenweb.fxpg.frame.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "DY_COLUMN")
public class DYColumn implements Serializable{
  
	// 主键
	private String colGuid;
	
	// 表格ID
	private String tableGuid;
	
	// 表名
	private String tableName;
	
	// 控件ID
	private String pluginGuid;
	
	// 字段名
	private String colName;
	
	// 更新字段名
	private String colNewName;
	
	// 字段类型
	private String colType;
	
	// 字段长度
	private Integer colLength=0;
	
	// 列备注
	private String colRemark="";
	
	// 排序
	private Integer sort;
	
	// 是否必填(1、是 2、否)
	private String isMust="";
	
	// 是否隐藏(1、是 2、否)
	private String isHid;
	
	// 是否列表显示(1、是 2、否)
	private String isListshow;
	
	// 是否只读(1、是 2、否)
	private String isRead;
	
	// 是否查询字段(1、是 2、否)
	private String isSelect;
	
	// 初始化数据code
	private String isInit;
	
	//验证信息
	private String verifyinfo; 
	
	// 跨列
	private String formstyleColspan;
	
	// 跨行
	private String formstyleRowspan;
	
	// 显示宽(比例)
	private String widthPro;
	
	// 布局样式
	private String layoutStyle;
	
	// 机构主键
	private String orgGuid;
	
	// 创建人
	private String createUser;
	
	// 创建时间
	private Date createDate;
	
	// 更新人
	private String updateUser;
	
	// 更新时间
	private Date updateDate;

	public DYColumn() {
		super();
	}

	public DYColumn(String pluginGuid) {
		super();
		this.pluginGuid = pluginGuid;
	}
	
	public DYColumn(String tableGuid, String tableName, String pluginGuid,
			String colName, String colType, Integer colLength, Integer sort,
			String createUser, Date createDate,String colRemark, String colGuid) {
		super();
		this.tableGuid = tableGuid;
		this.tableName = tableName;
		this.pluginGuid = pluginGuid;
		this.colName = colName;
		this.colType = colType;
		this.colLength = colLength;
		this.sort = sort;
		this.createUser = createUser;
		this.createDate = createDate;
		this.colRemark = colRemark;
		this.colGuid = colGuid;
	}

	public DYColumn(String colGuid, String tableGuid, String pluginGuid,
			String colName, Integer colLength, String colRemark,
			Integer sort, String isMust, String createUser, Date createDate,
			String updateUser, Date updateDate) {
		super();
		this.colGuid = colGuid;
		this.tableGuid = tableGuid;
		this.pluginGuid = pluginGuid;
		this.colName = colName;
		this.colLength = colLength;
		this.colRemark = colRemark;
		this.sort = sort;
		this.isMust = isMust;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}

	@Id
	@Column(name = "COL_GUID", length = 40)
	public String getColGuid() {
		return colGuid;
	}

	public void setColGuid(String colGuid) {
		this.colGuid = colGuid;
	}

	@Column(name = "TABLE_GUID")
	public String getTableGuid() {
		return tableGuid;
	}

	public void setTableGuid(String tableGuid) {
		this.tableGuid = tableGuid;
	}

	@Column(name = "TABLE_NAME")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "PLUGIN_GUID")
	public String getPluginGuid() {
		return pluginGuid;
	}

	public void setPluginGuid(String pluginGuid) {
		this.pluginGuid = pluginGuid;
	}

	@Column(name = "COL_NAME")
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@Column(name = "COL_NEW_NAME")
	public String getColNewName() {
		return colNewName;
	}

	public void setColNewName(String colNewName) {
		this.colNewName = colNewName;
	}

	@Column(name = "COL_TYPE")
	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	@Column(name = "COL_LENGTH")
	public Integer getColLength() {
		return colLength;
	}

	public void setColLength(Integer colLength) {
		this.colLength = colLength;
	}

	@Column(name = "COL_REMARK")
	public String getColRemark() {
		return colRemark;
	}

	public void setColRemark(String colRemark) {
		this.colRemark = colRemark;
	}

	@Column(name = "SORT")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "IS_MUST")
	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	

	@Column(name = "IS_HID")
	public String getIsHid() {
		return isHid;
	}

	public void setIsHid(String isHid) {
		this.isHid = isHid;
	}

	@Column(name = "IS_LISTSHOW")
	public String getIsListshow() {
		return isListshow;
	}

	public void setIsListshow(String isListshow) {
		this.isListshow = isListshow;
	}

	@Column(name = "IS_READ")
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@Column(name = "IS_SELECT")
	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	@Column(name = "FORMSTYLE_COLSPAN")
	public String getFormstyleColspan() {
		return formstyleColspan;
	}

	public void setFormstyleColspan(String formstyleColspan) {
		this.formstyleColspan = formstyleColspan;
	}

	@Column(name = "FORMSTYLE_ROWSPAN")
	public String getFormstyleRowspan() {
		return formstyleRowspan;
	}

	public void setFormstyleRowspan(String formstyleRowspan) {
		this.formstyleRowspan = formstyleRowspan;
	}

	@Column(name = "WIDTH_PRO")
	public String getWidthPro() {
		return widthPro;
	}

	public void setWidthPro(String widthPro) {
		this.widthPro = widthPro;
	}

	@Column(name = "LAYOUT_STYLE")
	public String getLayoutStyle() {
		return layoutStyle;
	}

	public void setLayoutStyle(String layoutStyle) {
		this.layoutStyle = layoutStyle;
	}

	@Column(name = "ORG_GUID")
	public String getOrgGuid() {
		return orgGuid;
	}

	public void setOrgGuid(String orgGuid) {
		this.orgGuid = orgGuid;
	}

	@Column(name = "CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_USER")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
	@Column(name = "IS_INIT")
	public String getIsInit() {
		return isInit;
	}
	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	@Column(name = "VERIFYINFO", length = 50)
	public String getVerifyinfo() {
		return verifyinfo;
	}
	public void setVerifyinfo(String verifyinfo) {
		this.verifyinfo = verifyinfo;
	}
	
	
	
	
}
