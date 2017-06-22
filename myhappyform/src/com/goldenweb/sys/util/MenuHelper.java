package com.goldenweb.sys.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.goldenweb.sys.pojo.SysFunction;

public class MenuHelper {
	
	 public static Map<String, Map<Object, SysFunction>> imenuMap = new LinkedHashMap<String, Map<Object, SysFunction>>();
	 public static Map<Object, SysFunction> menuMap = new LinkedHashMap<Object, SysFunction>();
	 public static void initMenu(int userid,List<Object[]> menuList) {		
		 if(imenuMap.get(userid)!=null){
			 imenuMap.get(userid).clear();
		 }		 
		 String url="";
		 SysFunction sf =null;
		 for(int i=0;i<menuList.size();i++){
			 if(menuList.get(i)[2]==null){
				 url = "";
			 }else{
			     url = menuList.get(i)[2].toString();
			     url=url.substring(0,url.indexOf("_"));
			 }
			 sf =new SysFunction();
			 sf.setId(Integer.parseInt(menuList.get(i)[0].toString()));
			 sf.setUrlPurview(url);
			 if(menuList.get(i)[2]!=null){
			 sf.setUrl(menuList.get(i)[2].toString());
			 }
			 sf.setParentFunid(Integer.parseInt(menuList.get(i)[3].toString()));
			 sf.setTitle(menuList.get(i)[1].toString());
			 menuMap.put(i,sf);
			}	
		 imenuMap.put(String.valueOf(userid), menuMap);
	 }
	 
	 public static Map<Object, SysFunction> getMenuUrlMap(String userid){
		 return imenuMap.get(userid);
	 }

}
