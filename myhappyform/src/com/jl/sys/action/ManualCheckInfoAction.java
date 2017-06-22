package com.jl.sys.action;

import java.io.IOException;
import java.util.Date;
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
import com.jl.sys.pojo.CheckInfo;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.ManualInfoService;
import com.jl.util.DateHelper;

/**
 * @Description:微信考勤导入
 * @ClassName: ManualCheckInfoAction
 * @author zpj 
 * @date 2017-6-17 下午1:49:20
 *
 */
@Namespace("/")
@Scope("prototype")
@Component("jlManualCheckInfoAction")
@ParentPackage("json-default")
public class ManualCheckInfoAction extends IAction{

	private CheckInfo cinfo;
	
	private UserInfo user;
	
	
	public CheckInfo getCinfo() {
		return cinfo;
	}
	public void setCinfo(CheckInfo cinfo) {
		this.cinfo = cinfo;
	}

	@Autowired
	private ManualInfoService mService;
	
	@Action(value="jlManualCheckInfoAction_toList",results={
			@Result(name="success",location="sys/manualcheck/list.jsp"),
			@Result(name="error",location="/login.jsp")
	})
	public String toList(){
		return "success";
	}
	@Action(value="jlManualCheckInfoAction_toAdd",results={
			@Result(name="success",location="sys/manualcheck/add.jsp"),
			@Result(name="error",location="/login.jsp")
	})
	public String toAdd(){
		String id=request.getParameter("id");
		if(id!=null&&!id.equalsIgnoreCase("")){
			cinfo=mService.findById(id);
		}else{
			cinfo=new CheckInfo();
			cinfo.setId(UUID.randomUUID().toString());
		}
		return "success";
	}
	
	@Action(value="jlManualCheckInfoAction_doAdd",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void doAdd(){
		try {
			String staffnames=cinfo.getStaffname();
			cinfo.setAdddate(new Date());
			//判断是否是多人同时输入的
			if(staffnames.contains("，")){
				String[] nameArr=staffnames.split("，");
				for(int i=0;i<nameArr.length;i++){
					CheckInfo tmpci=new CheckInfo();
					tmpci.setId(UUID.randomUUID().toString());
					tmpci.setStaffname(nameArr[i]);
					tmpci.setWorkdate(cinfo.getWorkdate());
					tmpci.setWorkduringtime(cinfo.getWorkduringtime());
					tmpci.setDepartmentname(cinfo.getDepartmentname());
					tmpci.setDepartmentcode(cinfo.getDepartmentcode());
					tmpci.setWorkcontent(cinfo.getWorkcontent());
					tmpci.setAdddate(cinfo.getAdddate());
					tmpci.setAddress(cinfo.getAddress());
					tmpci.setOvertime(cinfo.getOvertime());	
					tmpci.setRemark(cinfo.getRemark());
					mService.saveInfo(tmpci);
				}
			}else{
				mService.saveInfo(cinfo);
			}
			
			JSONObject job=new JSONObject();
			job.put("status","y");
			job.put("statusText", "保存成功");
			job.put("readyState", "true");
			job.put("responseText", "true");
			this.jsonWrite(job);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Action(value="jlManualCheckInfoAction_del",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void del(){
		String id=request.getParameter("id");
		if(null!=id&&!id.equalsIgnoreCase("")){
			mService.delInfo(id);
			try {
				this.jsonWrite(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Action(value="jlManualCheckInfoAction_toiframe",results={
			@Result(name="success",location="sys/manualcheck/list_iframe.jsp"),
			@Result(name="error",location="/login.jsp")
	})
	public String toiframe(){
		return "success";
	}
	
	@Action(value="jlManualCheckInfoAction_getListJson",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void getUserListJson(){
		user = (UserInfo)request.getSession().getAttribute("jluserinfo");
		String datemin=request.getParameter("datemin");//开始时间
		String datemax=request.getParameter("datemax");//结束时间
		String username=request.getParameter("username");//用户名称
		String departmentid=request.getParameter("departmentid");//部门id
		String address = request.getParameter("address");
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
		param.put("departmentid", departmentid);
		param.put("address", address);
		Map map=mService.findList(user,page,rows,param);
		List<UserInfo> list=(List<UserInfo>)map.get("list");
		int countNumber=(Integer)map.get("count");
		if(list!=null&&list.size()>0){
			  StringBuffer str =new StringBuffer();
			  str.append("{\"total\":\"").append(countNumber).append("\",\"rows\":");
//			  JSONArray jsonArray = JSONArray.fromObject(list);
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
	
}
