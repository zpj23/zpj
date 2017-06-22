package com.goldenweb.sys.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.service.FocusService;
import com.goldenweb.sys.util.IAction;
import com.opensymphony.xwork2.ActionContext;

@Namespace("/")
@Scope("prototype")
@Controller
@ParentPackage("json-default")
public class UserFocusAction extends IAction {
	private Map<String,String> params = new HashMap<String,String>();
	public String startDate;
	public String endDate;
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Autowired
	private FocusService  focusService;
	
	@Action(value="focusAction_toList",results={
			@Result(name="success",location="sys/user/userFocusList.jsp")
	})
	public String toList(){		
			return "success";
	}
	
	@Action(value="focusAction_toJson",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void toJson() throws IOException{	
		 SysUserinfo user = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
		String pageIndex = request.getParameter("page");
		if (pageIndex == null || pageIndex.equals("")) {
			page = 1; 
		}
		jsonData = focusService.toJsonList(page,rows,user,startDate,endDate);
		jsonWrite(jsonData);
		 
	}
	
	@Action(value="focusAction_makeFocus",results={
			@Result(name="success",type="json")
	})
	public String  makeFocus(){
		String eventid=request.getParameter("eventid");
		String type=request.getParameter("type");
		SysUserinfo user = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
		Integer userid=user.getId();
		Map map=new  HashMap();
		try{
				focusService.addFocus(eventid,type,userid);
				map.put("result",true);
/*				List list=focusService.findEventInfo(user);
				user.setFocusEventList(list);
				request.getSession().setAttribute("iuserinfo", user);*/
				 }
				catch(Exception e){
				map.put("result",false);
		        }
		ActionContext.getContext().getValueStack().push(map);
		return "success";
	}
	
	@Action(value="focusAction_cancleFocus",results={
			@Result(name="success",type="json")
	})
	public String  cancleFocus(){
		String eventid=request.getParameter("eventid");
		String type=request.getParameter("type");
		SysUserinfo user = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
		Integer userid=user.getId();
		Map map=new  HashMap();
		try{
				focusService.cancleFocus(eventid,type,userid);
/*				List list=focusService.findEventInfo(user);
				user.setFocusEventList(list);
				request.getSession().setAttribute("iuserinfo", user);*/
				map.put("result",true);
				 }
				catch(Exception e){
				map.put("result",false);
		        }
		ActionContext.getContext().getValueStack().push(map);
		return "success";
	}
}
