package com.goldenweb.sys.service;

import java.io.Serializable;

import com.goldenweb.fxpg.frame.service.GenericService;
import com.goldenweb.sys.pojo.PortalConfig;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-5 上午10:10:06
 */
public interface PortalService extends GenericService<PortalConfig, Serializable>{
	PortalConfig getPortalConfig4User(String userid);
	
	void saveConfigInfo(PortalConfig portalConfig);

	void saveConfigInfoWithconfig(PortalConfig portalConfig);
}
