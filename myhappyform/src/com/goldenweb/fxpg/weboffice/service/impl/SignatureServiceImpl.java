package com.goldenweb.fxpg.weboffice.service.impl;

import java.io.InputStream;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.comm.BaseService.MethodLog2;
import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.fxpg.weboffice.dao.SignatureHibernate;
import com.goldenweb.fxpg.weboffice.pojo.Signature;
import com.goldenweb.fxpg.weboffice.service.SignatureService;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-14 下午6:11:37
 */
@Service
@Component("signatureService")
public class SignatureServiceImpl extends GenericManagerImpl<Signature, Serializable> implements
		SignatureService {

	private SignatureHibernate signatureHibernate;
	  
    public SignatureServiceImpl(){}
	
	@Autowired    
    public SignatureServiceImpl(SignatureHibernate signatureHibernate){ 
		super(signatureHibernate);
        this.signatureHibernate = signatureHibernate;         
	}

	@Override
	@MethodLog2(remark="编辑签章",type="编辑")
	public void saveSignature(final Signature signature,final InputStream inputStream) {
		signatureHibernate.saveSignature(signature,inputStream);
	}

}
