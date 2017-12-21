/******************************************************************

 *    Package:     com.jl.sys.action
 *
 *    Filename:    ManualCheckPhoneAction.java
 *
 *    Description: TODO(用一句话描述该文件做什么)
 *
 *    Copyright:   Copyright (c) 2001-2014
 *
 *    Company:     Digital Telemedia Co.,Ltd
 *
 *    @author:     zpj
 *
 *    @version:    1.0.0
 *
 *    Create at:   2017-11-22 下午6:14:29
 *
 *    Revision:
 *
 *    2017-11-22 下午6:14:29
 *        - first revision
 *
 *****************************************************************/
package com.jl.sys.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.util.IAction;
import com.jl.sys.pojo.CheckInfo;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.ManualInfoService;
import com.jl.sys.service.UserInfoService;
import com.jl.util.DateHelper;


/**
 * @ClassName ManualCheckPhoneAction
 * @Description TODO(手机的mui后台action)
 * @author zpj
 * @Date 2017-11-22 下午6:14:29
 * @version 1.0.0
 */
@Namespace("/")
@Scope("prototype")
@Component("jlManualCheckPhoneAction")
@ParentPackage("json-default")
public class ManualCheckPhoneAction extends IAction {
	
	@Autowired
	private UserInfoService jlUserInfoService;
	
	@Autowired
	private ManualInfoService mService;
	
	private CheckInfo cinfo;
	
	private UserInfo user;
	
	
	public CheckInfo getCinfo() {
		return cinfo;
	}
	public void setCinfo(CheckInfo cinfo) {
		this.cinfo = cinfo;
	}
	
	@Action(value="jlManualCheckPhoneAction_findListInfoByPhone",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void findListInfoByPhone(){
		user = getCurrentUser(request);
		String staffname=request.getParameter("staffname");
		String datemin=request.getParameter("datemin");//开始时间
		String datemax=request.getParameter("datemax");//结束时间
		Map<String,String> param=new HashMap<String,String>();
		param.put("datemin", datemin);
		param.put("datemax", datemax);
		
		Map map=mService.findList(user,1,30,param);
		List<UserInfo> list=(List<UserInfo>)map.get("list");
		try {
			this.jsonWrite(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Action(value="jlManualCheckPhoneAction_findInfoByIdByPhone",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void findInfoByIdByPhone(){
		user = getCurrentUser(request);
		
		String id=request.getParameter("id");//主键id
		CheckInfo c=mService.findById(id);
		Map map =new HashMap();
		if(c!=null){
			map.put("msg", true);
			map.put("data", c);
		}
		try {
			this.jsonWrite(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Action(value="jlManualCheckPhoneAction_delInfoByIdByPhone",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void delInfoByIdByPhone(){
		user = getCurrentUser(request);
		String id = request.getParameter("delId");
		if(null!=id&&!id.equalsIgnoreCase("")){
			try {
				mService.delInfo(id);
				Map map =new HashMap();
				map.put("msg", true);
				this.jsonWrite(map);
			} catch (IOException e) {
				e.printStackTrace();
				Map map =new HashMap();
				map.put("msg", false);
				try {
					this.jsonWrite(map);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	
	@Action(value="jlManualCheckPhoneAction_saveInfoByPhone",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void saveInfoByPhone(){
		user =getCurrentUser(request);
		String id=request.getParameter("id");
		if(id!=null&&!id.equalsIgnoreCase("")){
			
		}else{
			id=UUID.randomUUID().toString();
		}
		String sgxm=request.getParameter("sgxm");
		String sgqy=request.getParameter("sgqy");
		String workdate=request.getParameter("workdate");
		String staffnames=request.getParameter("staffname");
		String workduringtime=request.getParameter("workduringtime");
		String overtime=request.getParameter("overtime");
		String workcontent=request.getParameter("workcontent");
		String remark=request.getParameter("remark");
		String departmentname=request.getParameter("departmentname");
		String departmentcode=request.getParameter("departmentcode");
		
		try {
			boolean flagcn=staffnames.contains("，");
			boolean flagen=staffnames.contains(",");
			//判断是否是多人同时输入的
			if(flagcn||flagen){
				String[] nameArr=null;
				if(flagcn){
					nameArr=staffnames.split("，");
				}
				if(flagen){
					nameArr=staffnames.split(",");
				}
				for(int i=0;i<nameArr.length;i++){
					CheckInfo tmpci=new CheckInfo();
					tmpci.setId(UUID.randomUUID().toString());
					tmpci.setStaffname(nameArr[i]);
					tmpci.setWorkdate(DateHelper.getDateFromString(workdate, "yyyy-MM-dd"));
					tmpci.setWorkduringtime(Double.valueOf(workduringtime));
					tmpci.setDepartmentname(departmentname);
					tmpci.setDepartmentcode(departmentcode);
					tmpci.setWorkcontent(workcontent);
					tmpci.setAdddate(new Date());
//					tmpci.setAddress(cinfo.getAddress());
					tmpci.setOvertime(Double.valueOf(overtime));	
					tmpci.setRemark(remark);
					tmpci.setCreateuserid(user.getId());
					tmpci.setSgxm(sgxm);
					tmpci.setSgqy(sgqy);
					if(user.getIsAdmin().equalsIgnoreCase("1")){
						//管理员  审核状态改成已审核
						tmpci.setShenhe("1");
					}else{
						//普通人 录入的状态是未审核
						tmpci.setShenhe("0");
					}
					mService.saveInfo(tmpci);
				}
				CheckInfo temp=mService.findById(id);
				if(temp!=null){
					//编辑的时候 如果是有分隔符 说明是需要分割这条数据  ，在分割保存完这些数据以后要删除之前未分割的数据
					mService.delInfo(temp.getId());
				}
			}else{
				CheckInfo tmpci=new CheckInfo();
				tmpci.setId(id);
				tmpci.setStaffname(staffnames);
				tmpci.setWorkdate(DateHelper.getDateFromString(workdate, "yyyy-MM-dd"));
				tmpci.setWorkduringtime(Double.valueOf(workduringtime));
				tmpci.setDepartmentname(departmentname);
				tmpci.setDepartmentcode(departmentcode);
				tmpci.setWorkcontent(workcontent);
				tmpci.setAdddate(new Date());
				tmpci.setOvertime(Double.valueOf(overtime));	
				tmpci.setRemark(remark);
				tmpci.setCreateuserid(user.getId());
				tmpci.setSgxm(sgxm);
				tmpci.setSgqy(sgqy);
				if(user.getIsAdmin().equalsIgnoreCase("1")){
					//管理员  审核状态改成已审核
					tmpci.setShenhe("1");
				}else{
					//普通人 录入的状态是未审核
					tmpci.setShenhe("0");
				}
				mService.saveInfo(tmpci);
			}
			
			Map job=new HashMap();
			job.put("msg",true);
			this.jsonWrite(job);
		} catch (Exception e) {
			Map job=new HashMap();
			try {
				job.put("msg",false);
				this.jsonWrite(job);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 获取手机当前登录对象信息
	 * @Title getCurrentUser
	 * @param request
	 * @return
	 * @author zpj
	 * @time 2017-9-18 下午3:33:38
	 */
	public UserInfo getCurrentUser(HttpServletRequest request){
		UserInfo user = (UserInfo)request.getSession().getAttribute("jluserinfo");
		if(user==null){
			String id= request.getParameter("loginId");
			user=jlUserInfoService.findById(Integer.parseInt(id));
			String isAdmin=request.getParameter("isAdmin");
			user.setIsAdmin(isAdmin);
			request.getSession().setAttribute("jluserinfo",user);
		}
		return user;
	}
}
