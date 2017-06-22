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
 * SysFunctionuser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_FUNCTIONUSER" )
public class SysFunctionuser implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userid;
	private Integer functionid;
	private String operateType;
	private Date createTime=new Date();

	// Constructors

	/** default constructor */
	public SysFunctionuser() {
	}

	/** minimal constructor */
	public SysFunctionuser(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SysFunctionuser(Integer id, Integer userid,
			Integer functionid, String operateType, Date createTime) {
		this.id = id;
		this.userid = userid;
		this.functionid = functionid;
		this.operateType = operateType;
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

	@Column(name = "USERID", precision = 22, scale = 0)
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "FUNCTIONID", precision = 22, scale = 0)
	public Integer getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(Integer functionid) {
		this.functionid = functionid;
	}

	@Column(name = "OPERATE_TYPE", length = 100)
	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
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