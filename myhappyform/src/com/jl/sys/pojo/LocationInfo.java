package com.jl.sys.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "jl_location_info")
public class LocationInfo implements java.io.Serializable{
	private int id;//主键id
	private int userid;//用户id;
	private String zuobiao;//坐标
	private Date updatetime;//更新时间
	private String address;//地址
	private String ltime;//微信小程序上传来的时间
	private String username;//姓名
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false ,precision = 22, scale = 0)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name = "userid", precision = 22, scale = 0)
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	@Column(name = "zuobiao",  length=50)
	public String getZuobiao() {
		return zuobiao;
	}
	public void setZuobiao(String zuobiao) {
		this.zuobiao = zuobiao;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime", length=7)
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Column(name = "address",  length=500)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "ltime",  length=20)
	public String getLtime() {
		return ltime;
	}
	public void setLtime(String ltime) {
		this.ltime = ltime;
	}
	@Column(name = "username",  length=30)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
}
