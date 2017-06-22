package com.goldenweb.fxpg.frame.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import com.goldenweb.fxpg.frame.dao.DynamicPlugin;
import com.goldenweb.fxpg.frame.pojo.DYPlugin;

@Component
public class DynamicPluginImpl extends GenericDaoHibernate<DYPlugin, Serializable> implements DynamicPlugin<DYPlugin>{

	@Override
	public List findAllPlugin() {
		return this.find("from DYPlugin", null);
	}
	
	@Override
	public List findPagePlugin(int page,int rows){
		String hql ="select pluginGuid,pluginName,pluginDefaultvalue,pluginTag,pluginType,pluginDbType from DYPlugin";
		return this.findListToPageByHql(hql, null,page, rows);
	}

	@Override
	public Long findPluginNumber() {
		return this.getAllCount(" select count(*) from DYPlugin ",null);
	}

	@Override
	public void savePlugin(DYPlugin plugin) {
		this.savereturn(plugin);
	}

	@Override
	public List<Object[]> findPageStyle(Integer page, Integer rows) {
		String hql ="select formstyleGuid,formstyleName,formstyleColspan,formstyleRowspan,widthPro,layoutStyle," +
				"isSelect,isListshow,isRead,isHid from FormStyle";
		return this.findListToPageByHql(hql, null, page, rows);		
	}

	
	@Override
	public void delPlugin(String id) {
		Query query = this.createQuery("delete DYPlugin where pluginGuid ='"+id+"'",null );
		query.executeUpdate();
	}

	
	
}
