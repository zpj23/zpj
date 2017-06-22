package com.goldenweb.sys.dao;

import java.io.Serializable;
import java.util.List;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.sys.pojo.Porlet;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-18 上午9:39:11
 */
public interface PorletHibernate extends GenericDao<Porlet, Serializable> {

	String getConfiginfoByUserid(String userid);

	List<Porlet> getPorletRole(String userid);

}
