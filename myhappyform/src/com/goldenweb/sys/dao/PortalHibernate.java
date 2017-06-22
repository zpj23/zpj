package com.goldenweb.sys.dao;

import java.io.Serializable;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.sys.pojo.PortalConfig;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-5 上午10:06:42
 */
public interface PortalHibernate extends GenericDao<PortalConfig, Serializable>{
	
		void updatePortalConfigInfo(String objectInfo,String configInfo,Integer columnNum,String objectId,String objectType);

		void updatePortalConfigInfoWithconfig(String objectInfo,
				String objectConfigInfo, Integer columnNum, String oid,
				String objectType);

		void deleteByRoleid(String roleid);
		
}
