package com.goldenweb.fxpg.weboffice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.fxpg.weboffice.dao.DocumentHibernate;
import com.goldenweb.fxpg.weboffice.pojo.Document;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-11 下午1:54:26
 */
@Repository
public class DocumentHibernateDao extends GenericDaoHibernate<Document, Serializable> implements
		DocumentHibernate {

	public DocumentHibernateDao(){
		super(Document.class);
	}
	
	@Override
	public List<Document> getDocuments(Document queryDocument) {
		List<String> conditions = new ArrayList<String>();
		
		String hql = " from Document where 1=1 ";
		
		
		return this.createQuery(hql,conditions.toArray()).list();
	}

	@Override
	public Document getByRecordId(String recordId) {
		String hql = " from Document where RecordId = ?";
		
		return (Document) this.createQuery(hql, new Object[]{recordId}).uniqueResult();
	}

}
