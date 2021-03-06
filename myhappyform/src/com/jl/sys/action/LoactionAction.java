package com.jl.sys.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.util.IAction;
import com.jl.sys.pojo.LocationInfo;
import com.jl.sys.pojo.UserInfo;
import com.jl.sys.service.LocationService;
import com.jl.sys.service.UserInfoService;

@Namespace("/")
@Scope("prototype")
@Component("jlLocationAction")
@ParentPackage("json-default")
public class LoactionAction extends IAction{
	@Autowired
	private LocationService locationService;
	@Autowired
	private UserInfoService jlUserInfoService;
	
	private UserInfo user;
	
	Logger logger=Logger.getLogger(LoactionAction.class);
	
	
	/**
	 * 保存微信打卡记录
	 * @Title jlLocationAction_saveLocationInfoByPhone
	 * @author zpj
	 * @throws IOException 
	 * @time 2017-9-29 下午1:38:17
	 */
	@Action(value="jlLocationAction_saveLocationInfoByPhone",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void saveLocationInfoByPhone() throws IOException{
		user = getCurrentUser(request);
		String loginId=request.getParameter("loginId");
		String longAndLat=request.getParameter("longAndLat");
		String checkAddress=request.getParameter("checkAddress");
		String checkTime=request.getParameter("checkTime");
		Map retMap=new HashMap<>();
		try {
			LocationInfo locationInfo=new LocationInfo();
			locationInfo.setUserid(Integer.parseInt(loginId));
			locationInfo.setUpdatetime(new Date());
			locationInfo.setZuobiao(longAndLat);
			locationInfo.setAddress(checkAddress);
			locationInfo.setLtime(checkTime);
			locationInfo.setUsername(user.getUsername());
			locationService.updateLocation(locationInfo);
			retMap.put("flag", true);
			jsonWrite(retMap);
		} catch (IOException e) {
			e.printStackTrace();
			retMap.put("flag", false);
			jsonWrite(retMap);
		}
	}
	
	@Action(value="jlLocationAction_findListInfoByPhone",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void findListInfoByPhone(){
		user = getCurrentUser(request);
		String username=request.getParameter("username");
		String year=request.getParameter("datemin");//年份
		String month=request.getParameter("yuefen");//月份
		String tianshu=request.getParameter("tianshu");//日期
		String cpage=request.getParameter("cpage");
		String pagerow=request.getParameter("pagerow");//分页行数
		Map<String,String> param=new HashMap<String,String>();
		int pr=Integer.parseInt(pagerow);
		StringBuilder dat=new StringBuilder(50);
		if(StringUtils.isNotEmpty(year)){
			dat.append(year);
			if(StringUtils.isNotEmpty(month)){
				dat.append("-").append(month);
				if(StringUtils.isNotEmpty(tianshu)){
					dat.append("-").append(tianshu);
				}
			}
			
		}
		param.put("date", dat.toString());
		param.put("username", username);
		page=Integer.parseInt(cpage);
		Map map=locationService.findList(user,page,pr,param);
		int tot=(Integer)map.get("count");
		double totalPage=Math.ceil((float)tot/pr);
		map.put("totalpage",totalPage );
		try {
			this.jsonWrite(map);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 更新位置信息
	 * @Title jlLocationAction_updateLocationByPhone
	 * @author zpj
	 * @time 2017-9-29 下午1:38:17
	 */
//	@Action(value="jlLocationAction_updateLocationByPhone",
//			results={
//			@Result(type="json", params={"root","jsonData"})})
//	public void jlLocationAction_updateLocationByPhone(){
//		String id=request.getParameter("id");
//		String longitude=request.getParameter("longitude");
//		String latitude=request.getParameter("latitude");
//		String address=request.getParameter("address");
//		
//		
//		LocationInfo locationInfo=new LocationInfo();
//		locationInfo.setUserid(Integer.parseInt(id));
//		locationInfo.setUpdatetime(new Date());
//		locationInfo.setZuobiao(longitude+","+latitude);
//		locationInfo.setAddress(address);
//		locationService.updateLocation(locationInfo);
//		try {
//			jsonWrite("success");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	
	
	
	@Action(value="jlLocationAction_showByName",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void showByName(){
		String datemin=request.getParameter("datemin");
		String datemax=request.getParameter("datemax");
		String username=request.getParameter("username");
		Map param=new HashMap();
		param.put("datemin", datemin);
		param.put("datemax", datemax);
		param.put("username", username);
		List list=locationService.findJson(param);
		try {
			jsonWrite(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Action(value="jlLocationAction_show",results={
			@Result(name="success",location="sys/manualcheck/showInTencentMap.jsp"),//fashionHome.jsp
			@Result(name="error",location="/login.jsp")
	})
	public String showInMap(){
		return "success";
	}
	
	@Action(value="jlLocationAction_showBaidu",results={
			@Result(name="success",location="sys/manualcheck/showInBaiduMap.jsp"),//fashionHome.jsp
			@Result(name="error",location="/login.jsp")
	})
	public String showInBaiduMap(){
		return "success";
	}
	
	public UserInfo getCurrentUser(HttpServletRequest request){
		UserInfo user = (UserInfo)request.getSession().getAttribute("jluserinfo");
//		Enumeration enumeration =request.getSession().getAttributeNames();//获取session中所有的键值对
//		while(enumeration.hasMoreElements()){
//            String AddFileName=enumeration.nextElement().toString();//获取session中的键值
//            UserInfo value=(UserInfo)request.getSession().getAttribute(AddFileName);//根据键值取出session中的值
//            System.out.println(AddFileName);
//            System.out.println(value.getLoginname());
//            //String FileName= (String)session.getAttribute("AddFileName");
//        }
		if(user==null){
			String id= request.getParameter("loginId");
			String isAdmin=request.getParameter("isAdmin");
			user=jlUserInfoService.findById(Integer.parseInt(id));
			user.setIsAdmin(isAdmin);
			request.getSession().setAttribute("jluserinfo",user);
		}
		return user;
	}
	
}
