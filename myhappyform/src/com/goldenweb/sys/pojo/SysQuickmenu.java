package com.goldenweb.sys.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysUserrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_Quickmenu")
public class SysQuickmenu implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer menuid;
	private Integer roleid;
	private String picpath;

	// Constructors

	/** default constructor */
	public SysQuickmenu() {
	}

	/** minimal constructor */
	public SysQuickmenu(Integer id) {
		this.id = id;
	}
	
	public SysQuickmenu(Integer menuid, Integer roleid,String picpath) {
		this.menuid = menuid;
		this.roleid = roleid;
		this.picpath = picpath;
	}

	/** full constructor */
	public SysQuickmenu(Integer id, Integer menuid, Integer roleid,String picpath) {
		this.id = id;
		this.menuid = menuid;
		this.roleid = roleid;
		this.picpath = picpath;
	}
	
	
	public SysQuickmenu(Integer menuid, Integer roleid) {
		this.menuid = menuid;
		this.roleid = roleid;
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

	@Column(name = "menuid", precision = 22, scale = 0)
	public Integer getMenuid() {
		return menuid;
	}
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	

	@Column(name = "ROLEID", precision = 22, scale = 0)
	public Integer getRoleid() {
		return this.roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	
	@Column(name = "picpath", length = 100)
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	
	
	

}