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
 * SysUserrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_USERROLE")
public class SysUserrole implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userid;
	private Integer roleid;
	private Date createTime=new Date();

	// Constructors

	/** default constructor */
	public SysUserrole() {
	}

	/** minimal constructor */
	public SysUserrole(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SysUserrole(Integer id, Integer userid, Integer roleid,
			Date createTime) {
		this.id = id;
		this.userid = userid;
		this.roleid = roleid;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID" , nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "USERID", precision = 22, scale = 0)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "ROLEID", precision = 22, scale = 0)
	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
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