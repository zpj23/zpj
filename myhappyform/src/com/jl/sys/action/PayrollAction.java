package com.jl.sys.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.util.IAction;
@Namespace("/")
@Scope("prototype")
@Component("jlPayrollAction")
@ParentPackage("json-default")
public class PayrollAction extends IAction {
	@Action(value="jlPayrollAction_toList",results={
			@Result(name="success",location="sys/payroll/list.jsp"),
			@Result(name="error",location="/login.jsp")
	})
	public String toList(){
		return "success";
	}
	
	@Action(value="jlPayrollAction_toiframe",results={
			@Result(name="success",location="sys/payroll/list_iframe.jsp"),
			@Result(name="error",location="/login.jsp")
	})
	public String toiframe(){
		return "success";
	}
	
}
