package com.goldenweb.sys.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.pojo.Porlet;
import com.goldenweb.sys.service.PorletService;
import com.goldenweb.sys.util.IAction;
import com.google.gson.Gson;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-18 上午9:46:57
 */
@Namespace("/")
@Scope("prototype")
@Component("PorletAction")
@ParentPackage("json-default")
public class PorletAction extends IAction {
	@Autowired
	PorletService porletService;
	//PorletAction_porletListJson
	
	@Action(value="PorletAction_porletListJson")
	public void porletListJson() throws IOException{
		Page myPage = new Page<Porlet>();
		myPage.setPageSize(rows);
		myPage.setNowPage(page);
		
		Page result = porletService.getByPage(myPage, null);
		List<Porlet> list = result.getResults();
		
		Gson gson = new Gson();
		String json = gson.toJson(list);
		
		response.setCharacterEncoding("utf-8");// 指定为utf-8
		response.getWriter().write(json);// 转化为JSOn格式
	}
	
	@Action(value="PorletAction_save")
	public void save(){
		porletService.save(porlet);
	}
	
	@Action(value="PorletAction_toDeal", results = {
			@Result(name = "list", location = "sys/porlet/list.jsp"),
			@Result(name = "edit", location = "sys/porlet/edit.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toDeal() throws IOException{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		
		if("list".equals(type)){
			return "list";
		}
		else if("add".equals(type)){
			return "edit";
		}
		else if("update".equals(type)){
			porlet = porletService.get(Integer.parseInt(id));
			
			return "edit";
		}
		else if("del".equals(type)){
			porletService.remove(Integer.parseInt(id));
			return "list";
		}
		return ERROR;
	}
	
	
	Porlet porlet;

	public Porlet getPorlet() {
		return porlet;
	}

	public void setPorlet(Porlet porlet) {
		this.porlet = porlet;
	}
}
