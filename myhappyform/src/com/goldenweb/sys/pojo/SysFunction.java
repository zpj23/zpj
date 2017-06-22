package com.goldenweb.sys.pojo;

import java.sql.Blob;
import java.sql.Clob;
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
 * SysFunction entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_FUNCTION")
public class SysFunction implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String url;
	private Integer parentFunid;
	private String remark;
	private String operateType;
	private Date createTime;
	private Integer funOrder;
	private String isDisable;
	private String isPopup;
	private String picture;
	private Integer isMenu=1;  //菜单 or 按钮 (默认值1：菜单)
	
	private Clob contentclob; // clob测试
	
	private Blob contentblob; // blob测试
	 
	@Column(name = "CONTENTCLOB",columnDefinition="CLOB")
	public Clob getContentclob() {
		return contentclob;
	}

	public void setContentclob(Clob contentclob) {
		this.contentclob = contentclob;
	}
 
	@Column(name = "CONTENTBLOB",columnDefinition="BLOB")
	public Blob getContentblob() {
		return contentblob;
	}

	public void setContentblob(Blob contentblob) {
		this.contentblob = contentblob;
	}
	

	// Constructors

	/** default constructor */
	public SysFunction() {
	}

	/** minimal constructor */
	public SysFunction(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SysFunction(Integer id, String title, String url,
			Integer parentFunid, String remark, String operateType,
			Date createTime, Integer funOrder, String isDisable,
			String isPopup,String picture,Integer isMenu) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.parentFunid = parentFunid;
		this.remark = remark;
		this.operateType = operateType;
		this.createTime = createTime;
		this.funOrder = funOrder;
		this.isDisable = isDisable;
		this.isPopup = isPopup;
		this.picture = picture;
		this.isMenu = isMenu;
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

	@Column(name = "TITLE", length = 500)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "URL", length = 2000)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "PARENT_FUNID", precision = 22, scale = 0)
	public Integer getParentFunid() {
		return this.parentFunid;
	}

	public void setParentFunid(Integer parentFunid) {
		this.parentFunid = parentFunid;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "OPERATE_TYPE", length = 500)
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

	@Column(name = "FUN_ORDER", precision = 22, scale = 0)
	public Integer getFunOrder() {
		return this.funOrder;
	}

	public void setFunOrder(Integer funOrder) {
		this.funOrder = funOrder;
	}

	@Column(name = "IS_DISABLE", length = 10)
	public String getIsDisable() {
		return this.isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

	@Column(name = "IS_POPUP", length = 10)
	public String getIsPopup() {
		return this.isPopup;
	}

	public void setIsPopup(String isPopup) {
		this.isPopup = isPopup;
	}
	
	
	@Column(name = "PICTURE", length = 100)
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Column(name = "IS_MENU", precision = 2, scale = 0)
	public Integer getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}





	private String urlPurview;
	@Transient
	public String getUrlPurview() {
		return urlPurview;
	}
	public void setUrlPurview(String urlPurview) {
		this.urlPurview = urlPurview;
	}

}