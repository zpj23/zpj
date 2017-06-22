package com.goldenweb.sys.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * SysOrganization entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_ORGANIZATION")
public class SysOrganization implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orgName;
	private String parentOrgid;
	private String parentDeptid;
	private String parentDeptname;
	private Integer orgLevel;
	private Integer orgType;
	private String businessType;
	private String deptType;
	private String phone;
	private String orgidsPath;
	private String orgnamesPath;
	private String deptidsPath;
	private String deptnamesPath;
	private String description;
	private Date createTime;
	private Date updateTime;
	
	private Integer subtotal; // 小计
	
	private String nodeId; // 节点名称
	
	private String orgCode;
	
	@Transient
	public Integer getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}
	
	@Transient
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	

	// Constructors

	

	/** default constructor */
	public SysOrganization() {
	}

	/** minimal constructor */
	public SysOrganization(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SysOrganization(Integer id, String orgName, String parentOrgid,
			String parentDeptid,String parentDeptname, Integer orgLevel, Integer orgType,
			String businessType, String deptType, String phone,
			String orgidsPath, String orgnamesPath, String deptidsPath,
			String deptnamesPath, String description, Date createTime,
			Date updateTime) {
		this.id = id;
		this.orgName = orgName;
		this.parentOrgid = parentOrgid;
		this.parentDeptid = parentDeptid;
		this.parentDeptname = parentDeptname;
		this.orgLevel = orgLevel;
		this.orgType = orgType;
		this.businessType = businessType;
		this.deptType = deptType;
		this.phone = phone;
		this.orgidsPath = orgidsPath;
		this.orgnamesPath = orgnamesPath;
		this.deptidsPath = deptidsPath;
		this.deptnamesPath = deptnamesPath;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	
	public SysOrganization(Integer id,String orgName,Integer subtotal){
		this.id = id;
		this.orgName = orgName;
		this.subtotal = subtotal;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ORG_NAME", length = 500)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "PARENT_ORGID", length = 500)
	public String getParentOrgid() {
		return this.parentOrgid;
	}

	public void setParentOrgid(String parentOrgid) {
		this.parentOrgid = parentOrgid;
	}

	@Column(name = "PARENT_DEPTID", length = 500)
	public String getParentDeptid() {
		return this.parentDeptid;
	}

	public void setParentDeptid(String parentDeptid) {
		this.parentDeptid = parentDeptid;
	}
	
	@Column(name = "PARENT_DEPTNAME", length = 500)
	public String getParentDeptname() {
		return this.parentDeptname;
	}

	public void setParentDeptname(String parentDeptname) {
		this.parentDeptname = parentDeptname;
	}

	@Column(name = "ORG_LEVEL", precision = 22, scale = 0)
	public Integer getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	@Column(name = "ORG_TYPE", precision = 22, scale = 0)
	public Integer getOrgType() {
		return this.orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	@Column(name = "BUSINESS_TYPE", length = 50)
	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "DEPT_TYPE", length = 50)
	public String getDeptType() {
		return this.deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	@Column(name = "PHONE", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ORGIDS_PATH", length = 500)
	public String getOrgidsPath() {
		return this.orgidsPath;
	}

	public void setOrgidsPath(String orgidsPath) {
		this.orgidsPath = orgidsPath;
	}

	@Column(name = "ORGNAMES_PATH", length = 500)
	public String getOrgnamesPath() {
		return this.orgnamesPath;
	}

	public void setOrgnamesPath(String orgnamesPath) {
		this.orgnamesPath = orgnamesPath;
	}

	@Column(name = "DEPTIDS_PATH", length = 2000)
	public String getDeptidsPath() {
		return this.deptidsPath;
	}

	public void setDeptidsPath(String deptidsPath) {
		this.deptidsPath = deptidsPath;
	}

	@Column(name = "DEPTNAMES_PATH", length = 2000)
	public String getDeptnamesPath() {
		return this.deptnamesPath;
	}

	public void setDeptnamesPath(String deptnamesPath) {
		this.deptnamesPath = deptnamesPath;
	}

	@Column(name = "DESCRIPTION", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name = "ITEM_CODE", length = 100)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}