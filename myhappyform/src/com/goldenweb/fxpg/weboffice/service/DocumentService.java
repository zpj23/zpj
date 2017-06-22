package com.goldenweb.fxpg.weboffice.service;

import java.io.Serializable;

import com.goldenweb.fxpg.frame.service.GenericService;
import com.goldenweb.fxpg.weboffice.pojo.Document;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-11 下午2:17:02
 */
public interface DocumentService extends GenericService<Document,Serializable> {
	Document getByRecordId(String recordId);

}
