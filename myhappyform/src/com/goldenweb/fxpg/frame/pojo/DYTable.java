package com.goldenweb.fxpg.frame.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "DY_TABLE")
public class DYTable implements Serializable{
  
	// 主键
	private String tableGuid;
	
	// 子表主键
	private String childGuid;
	
	// 业务主键
	private String busGuid;	
	
	// 表名
	private String tableName;
	
	// 表备注
	private String remark;
	
	// 整体布局类型
	private String layoutStyle="table";
	
	// 创建人
	private String createUser;
	
	// 创建时间
	private Date createDate;
	
	// 更新人
	private String updateUser;
	
	// 更新时间
	private Date updateDate;

	public DYTable() {
		super();
	}

	public DYTable(String tableGuid, String childGuid, String busGuid,
			String tableName, String remark, String createUser,
			Date createDate, String updateUser, Date updateDate) {
		super();
		this.tableGuid = tableGuid;
		this.childGuid = childGuid;
		this.busGuid = busGuid;
		this.tableName = tableName;
		this.remark = remark;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
	}
	
	public DYTable(String tableGuid, String createUser, Date createDate) {
		super();
		this.tableGuid = tableGuid;
		this.createUser = createUser;
		this.createDate = createDate;
	}

	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "guid")
	@Column(name = "TABLE_GUID", unique = true, nullable = false, length = 40)
	public String getTableGuid() {
		return tableGuid;
	}

	public void setTableGuid(String tableGuid) {
		this.tableGuid = tableGuid;
	}

	@Column(name = "CHILD_GUID")
	public String getChildGuid() {
		return childGuid;
	}

	public void setChildGuid(String childGuid) {
		this.childGuid = childGuid;
	}

	@Column(name = "BUS_GUID")
	public String getBusGuid() {
		return busGuid;
	}

	public void setBusGuid(String busGuid) {
		this.busGuid = busGuid;
	}

	@Column(name = "TABLE_NAME")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "LAYOUT_STYLE")
	public String getLayoutStyle() {
		return layoutStyle;
	}

	public void setLayoutStyle(String layoutStyle) {
		this.layoutStyle = layoutStyle;
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
}
