package com.goldenweb.fxpg.frame.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "DY_BUSINESS")
public class DYBusiness implements Serializable{
  
	// 主键
	private String busGuid;
	
	// 业务名称
	private String busName;
	
	// 业务类型
	private String busType;	
	
	// 业务代码
	private String busCode;

	public DYBusiness() {
		super();
	}

	public DYBusiness(String busGuid, String busName, String busType,
			String busCode) {
		super();
		this.busGuid = busGuid;
		this.busName = busName;
		this.busType = busType;
		this.busCode = busCode;
	}

	@Id
	@Column(name = "BUS_GUID")
	public String getBusGuid() {
		return busGuid;
	}

	public void setBusGuid(String busGuid) {
		this.busGuid = busGuid;
	}

	@Column(name = "BUS_NAME")
	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	@Column(name = "BUS_TYPE")
	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	@Column(name = "BUS_CODE")
	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
}
