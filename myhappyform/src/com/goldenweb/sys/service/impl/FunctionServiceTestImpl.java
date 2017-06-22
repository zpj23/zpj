package com.goldenweb.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.frame.service.MethodLog;
import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.sys.dao.FunctionHibernateTest;
import com.goldenweb.sys.pojo.SysFunction;
import com.goldenweb.sys.service.FunctionServiceTest; 

@Service("functionservicetest")
public class FunctionServiceTestImpl extends GenericManagerImpl<SysFunction, Serializable> implements FunctionServiceTest{

	@Autowired
	private FunctionHibernateTest functionHibernate; 
	
	@Override
	@MethodLog(remark="查询所有菜单信息")  
	public List<SysFunction> findFunctionTest() {
		return functionHibernate.findFunctionTest();
	}
}
