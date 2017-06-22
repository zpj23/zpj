/** 
 * @Description: TODO(有数据源处理对象)
 * @Title: HDataColumn.java
 * @Package com.goldenweb.core.frame.model
 * @author Lee
 * @date 2014-2-28 下午02:21:31
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO(有数据源处理对象)
 * @ClassName: HDataColumn
 * @author Lee 
 * @date 2014-2-28 下午02:21:31
 *
 */
public class DataColumn implements Serializable {

    
	// 控件绑定数据源对象
	private DYDataThird dyDataThird;
	
    // 列对象 
	private DYColumn dyColumn;
	
	// 绑数据源列Map
	private Map<String,Object> h_map = new HashMap<String, Object>();

	// 常规列Map
	private Map<String,Object> n_map = new HashMap<String, Object>();

	public DataColumn(){
		super();
	}
	
	public DataColumn(String pluginGuid, DYColumn dyColumn) {
		super();
		dyColumn.setPluginGuid(pluginGuid);
		this.dyColumn = dyColumn;
	}
	
	public DataColumn(String pluginGuid, DYDataThird dyDataThird,
			DYColumn dyColumn) {
		super();
		dyDataThird.setColGuid(dyColumn.getColGuid());
		dyColumn.setPluginGuid(pluginGuid);
		this.dyDataThird = dyDataThird;
		this.dyColumn = dyColumn;
	} 
	
	public DYDataThird getDyDataThird() {
		return dyDataThird;
	}

	public void setDyDataThird(DYDataThird dyDataThird) {
		this.dyDataThird = dyDataThird;
	}

	public DYColumn getDyColumn() {
		return dyColumn;
	}

	public void setDyColumn(DYColumn dyColumn) {
		this.dyColumn = dyColumn;
	}

	public Map<String, Object> getH_map() {
		h_map.put("dyDataThird", dyDataThird);
		h_map.put("dyColumn", dyColumn);
		return h_map;
	}

	public void setH_map(Map<String, Object> h_map) {
		this.h_map = h_map;
	}
	
	public Map<String, Object> getN_map() {
		n_map.put("dyColumn", dyColumn);
		return n_map;
	}

	public void setN_map(Map<String, Object> n_map) {
		this.n_map = n_map;
	}
}
