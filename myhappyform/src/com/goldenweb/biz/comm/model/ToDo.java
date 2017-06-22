package com.goldenweb.biz.comm.model;

import java.util.Date;

/**  
 * @Description: TODO(首页待办)
 * @author ljn  
 * @date 2014-5-26 上午9:36:58
 */
public class ToDo {
	private String id;
	private String title;
	private String stts;
	private String stts2;
	private String type;
	private Date sendDate;
	private String sendOrgDept;
	private String dealUrl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStts() {
		return stts;
	}
	public void setStts(String stts) {
		this.stts = stts;
	}
	public String getStts2() {
		return stts2;
	}
	public void setStts2(String stts2) {
		this.stts2 = stts2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendOrgDept() {
		return sendOrgDept;
	}
	public void setSendOrgDept(String sendOrgDept) {
		this.sendOrgDept = sendOrgDept;
	}
	public String getDealUrl() {
		return dealUrl;
	}
	public void setDealUrl(String dealUrl) {
		this.dealUrl = dealUrl;
	}
}
