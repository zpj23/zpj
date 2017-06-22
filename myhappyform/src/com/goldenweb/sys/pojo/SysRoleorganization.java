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

/**
 * SysRoleorganization entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_ROLEORGANIZATION")
public class SysRoleorganization implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer roleid;
	private Integer orgid;
	private Date createTime=new Date();

	// Constructors

	/** default constructor */
	public SysRoleorganization() {
	}

	/** minimal constructor */
	public SysRoleorganization(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SysRoleorganization(Integer id, Integer roleid,
			Integer orgid, Date createTime) {
		this.id = id;
		this.roleid = roleid;
		this.orgid = orgid;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",  nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ROLEID", precision = 22, scale = 0)
	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	@Column(name = "ORGID", precision = 22, scale = 0)
	public Integer getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}