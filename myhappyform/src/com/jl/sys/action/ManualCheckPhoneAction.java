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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		Map map=mService.findList(user,1,500,param);
		List<UserInfo> list=(List<UserInfo>)map.get("list");
		try {
			this.jsonWrite(map);
		} catch (IOException e) {
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
