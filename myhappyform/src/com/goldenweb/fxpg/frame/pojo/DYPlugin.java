package com.goldenweb.fxpg.frame.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "DY_PLUGIN")
public class DYPlugin implements Serializable{
  
	// 主键
	private String pluginGuid;
	
	// 控件名称
	private String pluginName;
	
	// 控件HTML标签
	private String pluginTag;
	
	// 控件类型
	private String pluginType;
	
	// 默认值
	private String pluginDefaultvalue;
	
	// 数据库类型
	private String pluginDbType;

	public DYPlugin() {
		super();
	}

	public DYPlugin(String pluginName, String pluginTag, String pluginDefaultvalue) {
		super();
		this.pluginName = pluginName;
		this.pluginTag = pluginTag;
		this.pluginDefaultvalue = pluginDefaultvalue;
	}

	@Id
	@Column(name = "PLUGIN_GUID")
	public String getPluginGuid() {
		return pluginGuid;
	}

	public void setPluginGuid(String pluginGuid) {
		this.pluginGuid = pluginGuid;
	}

	@Column(name = "PLUGIN_NAME")
	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	@Column(name = "PLUGIN_TAG")
	public String getPluginTag() {
		return pluginTag;
	}

	public void setPluginTag(String pluginTag) {
		this.pluginTag = pluginTag;
	}

	@Column(name = "PLUGIN_TYPE")
	public String getPluginType() {
		return pluginType;
	}

	public void setPluginType(String pluginType) {
		this.pluginType = pluginType;
	}

	@Column(name = "PLUGIN_DEFAULTVALUE")
	public String getPluginDefaultvalue() {
		return pluginDefaultvalue;
	}

	public void setPluginDefaultvalue(String pluginDefaultvalue) {
		this.pluginDefaultvalue = pluginDefaultvalue;
	}

	@Column(name = "PLUGIN_DB_TYPE")
	public String getPluginDbType() {
		return pluginDbType;
	}

	public void setPluginDbType(String pluginDbType) {
		this.pluginDbType = pluginDbType;
	}
}
