package com.goldenweb.fxpg.frame.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import com.goldenweb.fxpg.frame.dao.DynamicDataThird;
import com.goldenweb.fxpg.frame.pojo.DYDataThird;

@Component
public class DynamicDataThirdImpl extends GenericDaoHibernate<DYDataThird, Serializable> implements DynamicDataThird<DYDataThird>{

	
	@Override
	public void saveDyDataThird(DYDataThird configData) {
		if(("basicData".equals(configData.getDataType()) || configData.getDataType()==null)&&
				configData.getChildDataGuid()!=null&&!"".equals(configData.getChildDataGuid())){
			//将上级数据存入dataGuid
			String sql = "select a.type_code,a.id from sys_resource_type a left join sys_resource_type b "+
                 " on a.id = b.parent_typeid  where b.type_code = '"+configData.getChildDataGuid()+"'";
			String dataGuid = (String) this.queryListBySql(sql).get(0)[0];
			configData.setDataGuid(dataGuid);
			configData.setBusinesscode(null);
			this.savereturn(configData);
		}else if("businessData".equals(configData.getDataType())&&configData.getBusinesscode()!=null&&!"".equals(configData.getBusinesscode())){
			configData.setDataGuid(null);
			configData.setChildDataGuid(null);
			this.savereturn(configData);
		}else if("orgData".equals(configData.getDataType())&&configData.getOrgid()!=null&&!"".equals(configData.getOrgid())){
			configData.setDataGuid(null);
			configData.setChildDataGuid(null);
			this.savereturn(configData);
		}
		else{
			//this.remove(configData.getCdGuid());
			Query query = this.createQuery("delete DYDataThird where cdGuid ='"+configData.getCdGuid()+"'",null );
			query.executeUpdate();
		}
		
	}

	

	@Override
	public DYDataThird findPluginData(String colGuid) {
		return (DYDataThird) this.createQuery("from DYDataThird where colGuid =?", new String[]{colGuid}).uniqueResult();
	}
	
	@Override
	public List<Object[]> findPluginAllInfo(String colGuid){
		String sql = " select a.cd_guid,a.col_guid,a.data_guid,a.child_data_guid,b.type_name c1,c.type_name c2,a.data_type,a.businesscode,a.orgid  from dy_data_third a"+ 
		 " left join sys_resource_type b on a.data_guid = b.type_code "+
		 " left join sys_resource_type c on a.child_data_guid = c.type_code "+
		 " where a.col_guid='"+colGuid+"'";
		return this.queryListBySql(sql);
	}

}
