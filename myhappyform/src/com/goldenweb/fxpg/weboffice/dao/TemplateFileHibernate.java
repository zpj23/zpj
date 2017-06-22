package com.goldenweb.fxpg.weboffice.dao;

import java.io.Serializable;

import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.fxpg.weboffice.pojo.TemplateFile;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 下午4:31:53
 */
public interface TemplateFileHibernate extends GenericDao<TemplateFile, Serializable> {
	void updateTemplateFileByRecordId(TemplateFile tf);
}
