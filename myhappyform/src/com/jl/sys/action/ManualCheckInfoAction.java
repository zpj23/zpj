package com.jl.sys.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import com.goldenweb.fxpg.frame.tools.StringFormat;
import com.goldenweb.sys.pojo.SysUserinfo;
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
		user = (UserInfo)request.getSession().getAttribute("jluserinfo");
		String id=request.getParameter("id");
		if(id!=null&&!id.equalsIgnoreCase("")){
			cinfo=mService.findById(id);
		}else{
			cinfo=new CheckInfo();
			cinfo.setId(UUID.randomUUID().toString());
			cinfo.setDepartmentcode(user.getDepartmentcode());
		}
		return "success";
	}
	
	@Action(value="jlManualCheckInfoAction_doAdd",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void doAdd(){
		user = (UserInfo)request.getSession().getAttribute("jluserinfo");
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
					tmpci.setCreateuserid(user.getId());
					tmpci.setSgxm(cinfo.getSgxm());
					tmpci.setSgqy(cinfo.getSgqy());
					if(user.getIsAdmin().equalsIgnoreCase("1")){
						//管理员  审核状态改成已审核
						tmpci.setShenhe("1");
					}else{
						//普通人 录入的状态是未审核
						tmpci.setShenhe("0");
					}
					mService.saveInfo(tmpci);
				}
				CheckInfo temp=mService.findById(cinfo.getId());
				if(temp!=null){
					//编辑的时候 如果是有分隔符 说明是需要分割这条数据  ，在分割保存完这些数据以后要删除之前未分割的数据
					mService.delInfo(temp.getId());
				}
			}else{
				cinfo.setCreateuserid(user.getId());
				if(user.getIsAdmin().equalsIgnoreCase("1")){
					//管理员  审核状态改成已审核
					cinfo.setShenhe("1");
				}else{
					//普通人 录入的状态是未审核
					cinfo.setShenhe("0");
				}
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
	/**
	 * 管理员审核功能
	 * @Title shenhe
	 * @author zpj
	 * @time 2017-7-1 下午2:14:36
	 */
	@Action(value="jlManualCheckInfoAction_doshenhe",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void shenhe(){
		String id=request.getParameter("id");
		CheckInfo cInfo1=mService.findById(id);
		cInfo1.setShenhe("1");
		mService.saveInfo(cInfo1);
		try {
			this.jsonWrite(1);
		} catch (IOException e) {
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
		String sgxm = request.getParameter("sgxm");//施工项目
		String sgqy=request.getParameter("sgqy");//施工区域
		String workcontent =request.getParameter("workcontent");//工作内容
		String shenhe=request.getParameter("shenhe");//审核状态
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
		param.put("sgxm", sgxm);
		param.put("sgqy", sgqy);
		param.put("workcontent", workcontent);
		param.put("shenhe", shenhe);
		Map map=mService.findList(user,page,rows,param);
		List<UserInfo> list=(List<UserInfo>)map.get("list");
		int countNumber=(Integer)map.get("count");
		request.setAttribute("zgs", (Double)map.get("zgs"));
		if(list!=null&&list.size()>0){
			  StringBuffer str =new StringBuffer();
			  str.append("{\"total\":\"").append(countNumber).append("\",\"rows\":");
//			  JSONArray jsonArray = JSONArray.fromObject(list);
			  String lstr=gson.toJson(list);
			  str.append(lstr);
			  if(user.getIsAdmin().equalsIgnoreCase("1")){
				  str.append(",\"footer\":[{\"id\":\"1\",\"departmentname\":\"\",\"workdate\":\"\",\"workduringtime\":\""+(Double)map.get("zgs")+"\",\"workcontent\":\"合计\",\"overtime\":\"\",\"staffname\":\"\"}]");
			  }
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
	
	
	@Action(value="jlManualCheckInfoAction_exportExcel",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void exportExcel(){
		user = (UserInfo) request.getSession().getAttribute("jluserinfo");
		String datemin=request.getParameter("datemin");//开始时间
		String datemax=request.getParameter("datemax");//结束时间
		String username=request.getParameter("username");//用户名称
		String departmentid=request.getParameter("departmentid");//部门id
		String address = request.getParameter("address");//施工项目及区域
		String workcontent=request.getParameter("workcontent");//工作内容
		Map<String,String> param=new HashMap<String,String>();
		param.put("datemin", datemin);
		param.put("datemax", datemax);
		param.put("username", username);
		param.put("departmentid", departmentid);
		param.put("address", address);
		param.put("workcontent", workcontent);
		mService.exportExcel(param,request,response,user);
		
	}
	
	/**
	 * 根据用户展示一年根据月分的工时记录柱状图（分正常和加班两项）
	 * @Title toShowByType
	 * @return
	 * @author zpj
	 * @time 2017-7-12 下午4:18:44
	 */
	@Action(value="jlManualCheckInfoAction_toShowByType",results={
			@Result(name="success",location="sys/manualcheck/analysis.jsp"),//fashionHome.jsp
			@Result(name="error",location="/login.jsp")
	})
	public String toShowByType(){
		return "success";
	}
	
	@Action(value="jlManualCheckInfoAction_initChart",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void  jlDepartmentInfoAction_initDu(){
		String datemin=request.getParameter("datemin");//开始时间
		String username=request.getParameter("username");//用户名称
		String departmentid=request.getParameter("departmentid");//部门id
		String sgxm = request.getParameter("sgxm");//施工项目
		String sgqy = request.getParameter("sgqy");//施工区域
		String workcontent=request.getParameter("workcontent");//工作内容
		Map<String,String> param=new HashMap<String,String>();
		param.put("datemin", datemin);
		param.put("username", username);
		param.put("departmentid", departmentid);
		param.put("sgxm", sgxm);
		param.put("sgqy", sgqy);
		param.put("workcontent", workcontent);
		List list=mService.findChartByUser(param);
		try {
			this.jsonWrite(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Action(value="jlManualCheckInfoAction_saveInfoByPhone",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void saveInfoByPhone(){
		String sgxm=request.getParameter("sgxm");
		String sgqy=request.getParameter("sgqy");
		String workdate=request.getParameter("workdate");
		String staffname=request.getParameter("staffname");
		String workduringtime=request.getParameter("workduringtime");
		String overtime=request.getParameter("overtime");
		String workcontent=request.getParameter("workcontent");
		String remark=request.getParameter("remark");
		String dempartmentname=request.getParameter("departmentname");
		System.out.println(sgxm);
		Map retMap =new HashMap();
		
		try {
			retMap.put("msg",true);
			jsonWrite(retMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
