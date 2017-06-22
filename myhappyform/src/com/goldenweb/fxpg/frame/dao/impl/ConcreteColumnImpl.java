/** 
 * @Description: TODO(列详细操作实现)
 * @Title: ConcreteColumnImpl.java
 * @Package com.goldenweb.core.frame.dao.impl
 * @author Lee
 * @date 2014-2-28 上午11:00:30
 * @version V1.0  
 * CopyRight (c) 江苏海盟软件
 */ 
package com.goldenweb.fxpg.frame.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.goldenweb.fxpg.cache.impl.OrginfoCache;
import com.goldenweb.fxpg.frame.dao.ConcreteColumn;
import com.goldenweb.fxpg.frame.factory.GenericFactory;
import com.goldenweb.fxpg.frame.pojo.DYColumn;
import com.goldenweb.fxpg.frame.pojo.DYDataThird;
import com.goldenweb.fxpg.frame.pojo.DYPlugin;
import com.goldenweb.fxpg.frame.pojo.DYTable;
import com.goldenweb.fxpg.frame.pojo.DataColumn;
import com.goldenweb.sys.util.tag.CoderUtil;

/**
 * @Description: TODO(列详细操作实现)
 * @ClassName: ConcreteColumnImpl
 * @author Lee 
 * @date 2014-2-28 上午11:00:30
 *
 */
@Component
public class ConcreteColumnImpl extends GenericDaoHibernate<DYColumn, Serializable> implements ConcreteColumn{
	
	@Autowired
	private GenericFactory genericFactory;
	
	private String pluginGuid = "";
	
	@Override
	@Transactional
	public String query_h_dataColumn(String columnGuid, String dataFormat) {
		// 列对象
		DYColumn dyColumn = genericFactory.showDynamicColumn().queryColumn(columnGuid);
		pluginGuid = dyColumn.getPluginGuid();
		// 控件对象
		DYPlugin dyPlugin = (DYPlugin) showObjet("DYPlugin", pluginGuid);
		// 数据源
		List<DYDataThird> list = this.createQuery("from DYDataThird where pluginGuid =?", new String[]{dyColumn.getColGuid()}).list();
		
		String gsonObj = this.queryGson(this.formaterMap(dyPlugin, list, dyColumn), dataFormat);
		return gsonObj;
	}

	@Override
	@Transactional
	public String query_n_dataColumn(String columnGuid, String dataFormat) {
		// 列对象
		DYColumn dyColumn = genericFactory.showDynamicColumn().queryColumn(columnGuid);
		pluginGuid = dyColumn.getPluginGuid();
		// 控件对象
		DYPlugin dyPlugin = (DYPlugin) showObjet("DYPlugin", pluginGuid);
		String gsonObj = this.queryGson(this.formaterMap(dyPlugin, null, dyColumn), dataFormat);
		return gsonObj;
	}

	@Override
	@Transactional
	public String query_otherColumn(String columnGuid, String dataFormat) {
		return this.query_n_dataColumn(columnGuid, dataFormat);
	}

	@Override
	@Transactional
	public boolean save_h_dataColumn(DataColumn dataColumn){
		this.saveMap(dataColumn.getH_map(),dataColumn.getDyColumn());
		
		return true;
	}

	@Override
	@Transactional
	public boolean save_n_dataColumn(DataColumn dataColumn) {
		this.saveMap(dataColumn.getN_map(),dataColumn.getDyColumn());
		return true;
	}
	
	@Override
	@Transactional
	public String query_form(String tableGuid,String tableName, String dataFormat) {
		Map<String, Object> map = new HashMap<String, Object>();
		List newList = new ArrayList();
		// 主表对象
		DYTable dyTable = (DYTable) this.getSession().createQuery("from DYTable where tableGuid =? or tableName =?")
		.setParameters(new String[]{tableGuid,tableName},new Type[]{StringType.INSTANCE,StringType.INSTANCE})
		.uniqueResult();
		// 子表集合
		if(dyTable!=null && !"".equals(dyTable)){
			List<DYTable> mapList = this.getSession().createQuery("from DYTable where childGuid =?")
			.setParameters(new String[]{dyTable.getTableGuid()},new Type[]{StringType.INSTANCE}).list();
			// 表列集合
			List<DYColumn> list = genericFactory.showDynamicColumn().queryColumns(dyTable.getTableGuid());
	        // 主表、子表所有数据
			map = showMap(list, dyTable);
			for (DYTable newDyTable : mapList) {
				list = genericFactory.showDynamicColumn().queryColumns(newDyTable.getTableGuid());
				if(newDyTable!=null && !"".equals(newDyTable)){
					newList.add(showMap(list, newDyTable));
				}
			}
			// 主表所有数据
			if(mapList.size()==0){map = showMap(list, dyTable);}
		}
		map.put("childResult",newList);
		// MAP转换JSON输出
		String gsonObj = this.queryGson(map, dataFormat);
		return gsonObj;
	}
	
	private Map<String, Object> showMap(List<DYColumn> list, DYTable dyTable){
		List<Map> newList = new ArrayList<Map>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (DYColumn dyColumn : list) {
			pluginGuid = dyColumn.getPluginGuid();
			// 控件对象
			DYPlugin dyPlugin = (DYPlugin) showObjet("DYPlugin", pluginGuid);
			// 数据源
			List<DYDataThird> dataList = this.createQuery("from DYDataThird where colGuid =?", new String[]{dyColumn.getColGuid()}).list();
			// 解析绑定源数据列
			for (DYDataThird dyDataThird : dataList) {
				Map newMap = new HashMap();
				if(dyDataThird.getOrgid()!=null && !"".equals(dyDataThird.getOrgid())){
					newMap = OrginfoCache.getCodeMapByType(dyDataThird.getOrgid());
				}else if("businessData".equals(dyDataThird.getDataType())&&"同级部门".equals(dyDataThird.getBusinesscode())){
					String sql =" select t1.id,t1.item_name,t1.item_code from sys_resource_item  t1 inner join sys_resource_type t2 on t1.typeid =t2.id "+
							 "  where t2.type_code ='AA01'  and  item_code like 'AA%' and length(item_code)=10 " ;
					List<Object[]> lt = queryListBySql(sql);
					if(lt!=null&&lt.size()>0){
						for(int i=0;i<lt.size();i++){
							newMap.put(lt.get(i)[2], lt.get(i)[1]);
						}
					}
				}else{
				    newMap = CoderUtil.getCodeMapByType(dyDataThird.getChildDataGuid());
				}
				dyDataThird.setDataMap(newMap);
			}
			// JSON输出格式
			Map<String, Object> formaterMap =this.formaterMap(dyPlugin, dataList, dyColumn);
			newList.add(formaterMap);
		}
		map.put("layoutResult", dyTable.getLayoutStyle());
		map.put("dataResult", newList);
		map.put("dataForm", dyTable);
		return map;
	}
	
	private void saveMap(Map<String, Object> properties,DYColumn dyColumn){
		for(Object key : properties.keySet()){
			this.getSession().merge(properties.get(key));
		}
		this.genericFactory.showDynamicColumn().saveColumn(dyColumn);
	}
	
	private Object showObjet(String modelName,String param){
		List<Object> list = this.createQuery("from "+modelName+" where pluginGuid =?", new String[]{param}).list();
		if(list!=null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	private Map<String, Object> formaterMap(DYPlugin dyPlugin, List list, DYColumn dyColumn){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("plugObj", dyPlugin);
		map.put("dataArray", list);
		map.put("columnObj", dyColumn);
		return map;
	}
}
