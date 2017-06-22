package com.goldenweb.fxpg.weboffice.service;

import java.io.InputStream;
import java.io.Serializable;
import com.goldenweb.fxpg.frame.service.GenericService;
import com.goldenweb.fxpg.weboffice.pojo.Signature;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 下午6:09:51
 */
public interface SignatureService extends GenericService<Signature, Serializable> {
	
	public void saveSignature(final Signature signature,final InputStream inputStream);
	
}
