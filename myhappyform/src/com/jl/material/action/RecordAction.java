package com.jl.material.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.util.IAction;
import com.jl.material.pojo.RecordInfo;
import com.jl.material.service.RecordService;
import com.jl.sys.pojo.UserInfo;

@Namespace("/")
@Scope("prototype")
@Component("jlRecordInfoAction")
@ParentPackage("json-default")
public class RecordAction extends IAction{
	
	public UserInfo user;
	
	public RecordInfo recordInfo;
	
	
	public RecordInfo getRecordInfo() {
		return recordInfo;
	}


	public void setRecordInfo(RecordInfo recordInfo) {
		this.recordInfo = recordInfo;
	}

	@Autowired
	public RecordService recordService;
	
	@Action(value="jlRecordInfoAction_toListRecord",results={
			@Result(name="success",location="material/receiveRecord/list.jsp"),
			@Result(name="error",location="/login.jsp")
	})
	public String toListRecord(){
		return "success";
	}
	
	
	@Action(value="jlRecordInfoAction_toiframe",results={
			@Result(name="success",location="material/receiveRecord/list_iframe.jsp"),
			@Result(name="error",location="/login.jsp")
	})
	public String toiframe(){
		return "success";
	}
	
	/**
	 * datagrid列表
	 * @Title getSupplierListJson
	 * @author zpj
	 * @time 2016-4-8 下午2:54:25
	 */
	@Action(value="jlRecordInfoAction_getRecordsListJson",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void getSupplierListJson(){
		user = (UserInfo)request.getSession().getAttribute("jluserinfo");
		String datemin=request.getParameter("datemin");//开始时间
		String datemax=request.getParameter("datemax");//结束时间
		String username=request.getParameter("username");//领用人名称
		String tpage=request.getParameter("page");
		String trows=request.getParameter("rows");
		if(null!=tpage&&!"".equalsIgnoreCase(tpage)){
			page=Integer.parseInt(tpage);
		}
		if(null!=trows&&!"".equalsIgnoreCase(trows)){
			rows=Integer.parseInt(trows);
		}
		Map<String,String> param=new HashMap<String,String>();
		param.put("datemin", datemin);
		param.put("datemax", datemax);
		param.put("username", username);
		
		Map map=recordService.findList(user,page,rows,param);
		List list=(List)map.get("list");
		int countNumber=(Integer)map.get("count");
		if(list!=null&&list.size()>0){
			  StringBuffer str =new StringBuffer();
			  str.append("{\"total\":\"").append(countNumber).append("\",\"rows\":");
			  String lstr=gson.toJson(list);
			  str.append(lstr);
			  str.append("}");
			  jsonData= str.toString();
		}else{
			jsonData="[]";
		}
		try {
			this.jsonWrite(jsonData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Action(value="jlRecordAction_toAddRecord",results={
			@Result(name="success",location="material/receiveRecord/add.jsp"),
			@Result(name="error",location="/login.jsp")
	})
	public String toAddRecord(){
		String id=request.getParameter("id");
		recordInfo=null;
		if(null!=id&&!"".equalsIgnoreCase(id)){
			recordInfo=recordService.findById(id);
		}
		if(recordInfo==null){
			recordInfo=new RecordInfo();
			recordInfo.setId(UUID.randomUUID().toString());
		}
		return "success";
	}
	
	@Action(value="jlRecordAction_saveRecord",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void saveRecord(){
		int m=recordService.saveRecord(recordInfo);
		try {
			JSONObject job=new JSONObject();
			if(m==1){
				job.put("status","y");
				job.put("statusText", "保存成功");
				job.put("readyState", "true");
				job.put("responseText", "true");
			}else{
				job.put("status","n");
				job.put("statusText", "保存出错");
				job.put("readyState", "false");
				job.put("responseText", "false");
			}
			this.jsonWrite(job);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action(value="jlRecordAction_delRecord",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void delRecord(){
		String id=request.getParameter("id");
		int m=0;
		if(null!=id&&!id.equalsIgnoreCase("")){
			m=recordService.delRecord(id);
		}
		try {
			this.jsonWrite(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
