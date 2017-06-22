package com.goldenweb.fxpg.weboffice.dao;

import java.io.Serializable;
import java.util.List;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.fxpg.weboffice.pojo.Document;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-11 下午1:52:37
 */
public interface DocumentHibernate extends GenericDao<Document, Serializable> {
	
	public List<Document> getDocuments(Document queryDocument);
	
	public Document getByRecordId(String recordId);
	
}
