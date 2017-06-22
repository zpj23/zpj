package com.goldenweb.fxpg.weboffice.service;

import java.io.Serializable;

import com.goldenweb.fxpg.frame.service.GenericService;
import com.goldenweb.fxpg.weboffice.pojo.TemplateFile;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 下午4:26:58
 */
public interface TemplateFileService extends GenericService<TemplateFile, Serializable> {
	void updateTemplateFileByRecordId(TemplateFile tf);
}
