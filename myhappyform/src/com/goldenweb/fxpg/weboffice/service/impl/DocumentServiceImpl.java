package com.goldenweb.fxpg.weboffice.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.fxpg.weboffice.dao.DocumentHibernate;
import com.goldenweb.fxpg.weboffice.pojo.Document;
import com.goldenweb.fxpg.weboffice.service.DocumentService;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-11 下午2:21:13
 */
@Service
@Component("documentService")
public class DocumentServiceImpl extends GenericManagerImpl<Document, Serializable> implements
		DocumentService {
	
	private DocumentHibernate documentDao;
	  
    public DocumentServiceImpl(){}
	
	@Autowired    
    public DocumentServiceImpl(DocumentHibernate documentDao){ 
		super(documentDao);
        this.documentDao = documentDao;         
    }

	@Override
	public Document getByRecordId(String recordId) {
		return documentDao.getByRecordId(recordId);
	}     
	
	
}
