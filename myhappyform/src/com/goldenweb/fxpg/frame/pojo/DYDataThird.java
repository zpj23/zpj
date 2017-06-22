package com.goldenweb.fxpg.frame.pojo;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "DY_DATA_THIRD")
public class DYDataThird implements Serializable{
  
	// 主键
	private String cdGuid;
	
	// 列主键
	private String colGuid;
	
	// 基础数据主键
	private String dataGuid;	
	
	// 子级数据主键
	private String childDataGuid;
	
	private String dataType;  // 基础数据和业务数据
	private String businesscode; // 业务数据代码
	private String orgid;  //机构数据id
	
	// 数据Map
	private Map dataMap;

	public DYDataThird() {
		super();
	}

	public DYDataThird(String colGuid, String dataGuid, String childDataGuid) {
		super();
		this.colGuid = colGuid;
		this.dataGuid = dataGuid;
		this.childDataGuid = childDataGuid;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "guid")
	@Column(name = "CD_GUID")
	public String getCdGuid() {
		return cdGuid;
	}

	public void setCdGuid(String cdGuid) {
		this.cdGuid = cdGuid;
	}
 
	@Column(name = "COL_GUID")
	public String getColGuid() {
		return colGuid;
	}

	public void setColGuid(String colGuid) {
		this.colGuid = colGuid;
	}

	@Column(name = "DATA_GUID")
	public String getDataGuid() {
		return dataGuid;
	}

	public void setDataGuid(String dataGuid) {
		this.dataGuid = dataGuid;
	}

	@Column(name = "CHILD_DATA_GUID")
	public String getChildDataGuid() {
		return childDataGuid;
	}

	public void setChildDataGuid(String childDataGuid) {
		this.childDataGuid = childDataGuid;
	}
	
	@Column(name = "DATA_TYPE",length=50)
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	

	@Column(name = "businesscode",length=50)
	public String getBusinesscode() {
		return businesscode;
	}
	public void setBusinesscode(String businesscode) {
		this.businesscode = businesscode;
	}
	
	@Column(name = "orgid",length=50)
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	

	@Transient
	public Map getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	
	
	
	
}
