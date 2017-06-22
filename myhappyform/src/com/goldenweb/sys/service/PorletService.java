package com.goldenweb.sys.service;

import java.io.Serializable;
import java.util.List;

import com.goldenweb.fxpg.frame.service.GenericService;
import com.goldenweb.sys.pojo.Porlet;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-18 上午9:43:35
 */
public interface PorletService extends GenericService<Porlet, Serializable> {
	Porlet getByCode(String code);

	List<Porlet> getInfoByUserid(String string);

	String findProletStrByRoleid(String roleid);

	void saveSetPorlet(String roleid, String porlets);

	List<Porlet> getPorletRole(String string);
}
