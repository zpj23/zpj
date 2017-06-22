package com.goldenweb.sys.pojo;

import java.io.Serializable;
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
@Table(name = "SYS_FOCUS")
public class SysFocus implements Serializable{
	
	public SysFocus() {
		
	}
	public SysFocus(Integer fid, Integer userid, String isFocus,
			String eventInfo, Date createTime) {
		super();
		this.fid = fid;
		this.userid = userid;
		this.isFocus = isFocus;
		this.eventInfo = eventInfo;
		this.createTime = createTime;
	}
	private Integer fid;
	private Integer userid;
	private String isFocus;
	private String eventInfo;
	private String type;
	
	@Column(name = "TYPE", length = 10)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private Date createTime =new Date();
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "FID", nullable = false , precision = 22, scale = 0)
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;}
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length=7)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "ISFOCUS", length = 10)
	public String getIsFocus() {
		return isFocus;
	}
	
	public void setIsFocus(String isFocus) {
		this.isFocus = isFocus;
	}
	
	@Column(name = "USERID", precision = 22, scale = 0)
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	@Column(name = "EVENTINFO", length = 50)
	public String getEventInfo() {
		return eventInfo;
	}
	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}
	

}
