package com.goldenweb.sys.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SYS_MENUCONFIG")
public class SysMenuConfig implements java.io.Serializable{

	// Fields
	private String userid;
	private String menuinfo;
	private Date createTime=new Date();
	
	
	/** default constructor */
    public SysMenuConfig() {
    }
	
	
	
	@Id
    @Column(name = "userid", nullable = false, length=50)
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	 @Column(name = "menuinfo", columnDefinition = "CLOB")
	public String getMenuinfo() {
		return menuinfo;
	}
	public void setMenuinfo(String menuinfo) {
		this.menuinfo = menuinfo;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 7)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
