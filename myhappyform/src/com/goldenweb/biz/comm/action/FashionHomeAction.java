package com.goldenweb.biz.comm.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.biz.comm.model.ToDo;

import com.goldenweb.fxpg.frame.service.DynamicFormService;
import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.util.IAction;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-27 下午1:43:09
 */
@Namespace("/")
@Scope("prototype")
@Component("FashionHomeAction")
@ParentPackage("json-default")
public class FashionHomeAction extends IAction {
	
	
	@Autowired
	private DynamicFormService dynamicFormService;
	
	@Action(value = "FashionHomeAction_tofashionIndex", results = {
			@Result(name = "success", location = "home/fashionHome.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String tofashionIndex() throws ParseException{
		
		
		
		//待办
		List<ToDo> todoList = new ArrayList<ToDo>();
		Page pageObject = new Page(page, rows);
		
		
		List<ToDo> swList = dynamicFormService.showHomeWaitList("weiwxx", pageObject, this.getCurrentUser());
		todoList.addAll(swList);
		request.setAttribute("todoList", todoList);
		
		return SUCCESS;
	}
}
