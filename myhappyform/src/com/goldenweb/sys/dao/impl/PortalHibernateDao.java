package com.goldenweb.sys.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.sys.dao.PortalHibernate;
import com.goldenweb.sys.pojo.PortalConfig;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-5 上午10:08:33
 */
@Repository
public class PortalHibernateDao extends GenericDaoHibernate<PortalConfig, Serializable> implements PortalHibernate {
	public PortalHibernateDao() {
		super(PortalConfig.class);
	}

	@Override
	public void updatePortalConfigInfo(String objectInfo,String configInfo,Integer columnNum, String objectId,
			String objectType) {
		String hqlUpdate =" update sys_Portal_Config set objectConfigInfo='"+configInfo+"' ,  columnNum="+columnNum+" where oid = '"+objectId+"' and objectType = '"+objectType+"'";
		//String hqlUpdate =" update sys_Portal_Config set objectInfo='"+objectInfo+"' , objectConfigInfo = '"+configInfo+"', columnNum="+columnNum+" where oid = '"+objectId+"' and objectType = '"+objectType+"'";
		this.getSession().createSQLQuery(hqlUpdate).executeUpdate();
	}
	
	@Override
	public void updatePortalConfigInfoWithconfig(String objectInfo,String configInfo,Integer columnNum, String objectId,
			String objectType) {
		String hqlUpdate =" update sys_Portal_Config set objectInfo='"+objectInfo+"' , objectConfigInfo = '"+configInfo+"', columnNum="+columnNum+" where oid = '"+objectId+"' and objectType = '"+objectType+"'";
		this.getSession().createSQLQuery(hqlUpdate).executeUpdate();
	}

	
	@Override
	public void deleteByRoleid(String roleid) {
		String sql = "delete from sys_Portal_Config where exists "
				+ " (select userid from sys_userrole where roleid="+roleid+" and userid=oid)";
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
}
