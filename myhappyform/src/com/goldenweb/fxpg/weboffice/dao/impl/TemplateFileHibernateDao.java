package com.goldenweb.fxpg.weboffice.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.fxpg.weboffice.dao.TemplateFileHibernate;
import com.goldenweb.fxpg.weboffice.pojo.TemplateFile;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 下午4:32:50
 */
@Repository
public class TemplateFileHibernateDao extends GenericDaoHibernate<TemplateFile, Serializable> implements
		TemplateFileHibernate {
	
	public TemplateFileHibernateDao(){
		super(TemplateFile.class);
	}

	@Override
	public void updateTemplateFileByRecordId(TemplateFile tf) {
		String hql = "update TemplateFile Set FileName = '"+ tf.getFileName() +"',Descript = '"+ tf.getDescript() +"' Where RecordID='"+ tf.getRecordId() +"'";
		this.createQuery(hql).executeUpdate();
	}
}
