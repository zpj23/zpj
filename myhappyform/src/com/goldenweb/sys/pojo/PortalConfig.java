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
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-5 上午10:02:12
 */
@Entity
@Table(name = "SYS_PORTAL_CONFIG")
public class PortalConfig {
	private Integer id;
	private String oid;
	private String objectType;
	private String objectInfo;
	private String objectConfigInfo;
	private Integer columnNum;
	private Date createTime=new Date();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "oid",length=20)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	@Column(name = "objectType",length=20)
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	@Column(name = "objectInfo",length=200)
	public String getObjectInfo() {
		return objectInfo;
	}
	public void setObjectInfo(String objectInfo) {
		this.objectInfo = objectInfo;
	}
	
	@Column(name = "objectConfigInfo",length=200)
	public String getObjectConfigInfo() {
		return objectConfigInfo;
	}
	public void setObjectConfigInfo(String objectConfigInfo) {
		this.objectConfigInfo = objectConfigInfo;
	}

	@Column(name = "columnNum")
	public Integer getColumnNum() {
		return columnNum;
	}
	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
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
