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
 * SysRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_ROLE")
public class SysRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private String rolename;
	private String rolecode;
	private String remark;
	private Date createTime =new Date();
	private String isProcess; // y/n

	// Constructors

	/** default constructor */
	public SysRole() {
	}

	/** minimal constructor */
	public SysRole(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SysRole(Integer id, String rolename,String rolecode, String remark,
			Date createTime,String isProcess) {
		this.id = id;
		this.rolename = rolename;
		this.rolecode = rolecode;
		this.remark = remark;
		this.createTime = createTime;
		this.isProcess = isProcess;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ROLENAME", length = 100)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	@Column(name = "ROLECODE", length = 100)
	public String getRolecode() {
		return this.rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
		
	@Column(name = "is_process", length = 10)
	public String getIsProcess() {
		return this.isProcess;
	}

	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}

}