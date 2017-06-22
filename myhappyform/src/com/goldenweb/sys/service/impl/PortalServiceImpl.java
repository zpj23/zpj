package com.goldenweb.sys.service.impl;

import java.io.Serializable;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.comm.BaseService.MethodLog2;
import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.sys.dao.PortalHibernate;
import com.goldenweb.sys.pojo.PortalConfig;
import com.goldenweb.sys.service.PortalService;


/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-5 上午10:10:30
 */
@Service
@Component("PortalService")
public class PortalServiceImpl extends GenericManagerImpl<PortalConfig, Serializable> implements PortalService{
	@Autowired
	PortalHibernate portalHibernate;
	
	@Override
	public PortalConfig getPortalConfig4User(String userid) {
		String hql = " from PortalConfig where oid = ?";
		Query query = portalHibernate.createQuery(hql, new Object[]{userid});
		if(query.list().size()>0){
			PortalConfig portalConfig = (PortalConfig)query.uniqueResult();
			return portalConfig;
		}else{
			return null;
		}
	}

	/**
	 * 修改时 configinfo字段不做变动
	 * @param portalConfig
	 */
	@Override
	@MethodLog2(remark="编辑首页设置",type="编辑")
	public void saveConfigInfo(PortalConfig portalConfig) {
		String hql = " from PortalConfig where oid = ? and objectType = ?";
		Query query = portalHibernate.createQuery(hql, new Object[]{portalConfig.getOid(),portalConfig.getObjectType()});
		if(query.list().size()>0){
			PortalConfig config = (PortalConfig)query.uniqueResult();
			if(checkConfig(portalConfig.getObjectInfo(),config.getObjectInfo())){
				//设置的显示区有数量变动
				portalHibernate.updatePortalConfigInfoWithconfig(portalConfig.getObjectInfo(),portalConfig.getObjectConfigInfo(),portalConfig.getColumnNum(),portalConfig.getOid(),portalConfig.getObjectType());
			}else{
				//显示区的位置有变化
			 portalHibernate.updatePortalConfigInfo(portalConfig.getObjectInfo(),portalConfig.getObjectConfigInfo(),portalConfig.getColumnNum(),portalConfig.getOid(),portalConfig.getObjectType());
			}
		}else{
			portalHibernate.save(portalConfig);
		}
	}
	
	/**
	 * 检查首页显示区域是否有变化
	 * @param objectInfo
	 * @param objectInfoold //原数据
	 * @return
	 */
	public boolean checkConfig(String objectInfo, String objectInfoold) {
		if(objectInfoold==null||objectInfoold.length()!=objectInfo.length()){
			return true;
		}
		String arr [] = objectInfo.split(",");
		for(int i=0;i<arr.length;i++){
			if(objectInfoold.indexOf(arr[i])<=-1){
				return true;
			}
		}		
		return false;
	}

	/**
	 * 修改时 configinfo字段将做变动
	 * @param portalConfig
	 */
	@Override
	@MethodLog2(remark="编辑首页设置",type="编辑")
	public void saveConfigInfoWithconfig(PortalConfig portalConfig) {
		String hql = " from PortalConfig where oid = ? and objectType = ?";
		Query query = portalHibernate.createQuery(hql, new Object[]{portalConfig.getOid(),portalConfig.getObjectType()});
		if(query.list().size()>0){
			portalHibernate.updatePortalConfigInfoWithconfig(portalConfig.getObjectInfo(),portalConfig.getObjectConfigInfo(),portalConfig.getColumnNum(),portalConfig.getOid(),portalConfig.getObjectType());
			
		}else{
			portalHibernate.save(portalConfig);
		}
	}
	
}
