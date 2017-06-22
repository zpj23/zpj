package com.goldenweb.fxpg.weboffice.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.comm.BaseService.MethodLog2;
import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.fxpg.weboffice.dao.TemplateFileHibernate;
import com.goldenweb.fxpg.weboffice.pojo.TemplateFile;
import com.goldenweb.fxpg.weboffice.service.TemplateFileService;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 下午4:30:06
 */
@Service
@Component("templateFileService")
public class TemplateFileServiceImpl extends GenericManagerImpl<TemplateFile, Serializable>
		implements TemplateFileService {
	private TemplateFileHibernate templateFileHibernate;
	  
    public TemplateFileServiceImpl(){}
	
	@Autowired    
    public TemplateFileServiceImpl(TemplateFileHibernate templateFileHibernate){ 
		super(templateFileHibernate);
        this.templateFileHibernate = templateFileHibernate;         
    }

	@Override
	@MethodLog2(remark="编辑weboffice模板",type="编辑")
	public void updateTemplateFileByRecordId(TemplateFile tf) {
		templateFileHibernate.updateTemplateFileByRecordId(tf);
	}   
}
