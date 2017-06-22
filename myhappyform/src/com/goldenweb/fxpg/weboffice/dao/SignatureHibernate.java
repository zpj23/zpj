package com.goldenweb.fxpg.weboffice.dao;

import java.io.InputStream;
import java.io.Serializable;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.fxpg.weboffice.pojo.Signature;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 下午6:13:33
 */
public interface SignatureHibernate extends GenericDao<Signature, Serializable> {

	
	public void saveSignature(final Signature signature,final InputStream inputStream);
	

}
