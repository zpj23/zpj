package com.goldenweb.fxpg.cache.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.goldenweb.fxpg.frame.tools.SpringContext;
import com.goldenweb.sys.pojo.SysOrganization;
import com.goldenweb.sys.service.impl.ResourceServiceImpl;

public class OrginfoCache {

	
	public static Map<String, List<SysOrganization>> orgCache = new LinkedHashMap<String, List<SysOrganization>>();
	public static Map<String, String> orgLookCache = new LinkedHashMap<String, String>();
	public static Map<String, String> orgLookCache2 = new LinkedHashMap<String, String>();
	public static Map<String, String> orgLookCache3 = new LinkedHashMap<String, String>();
	
	public static void init(){
		
		ResourceServiceImpl resourceService =  (ResourceServiceImpl) SpringContext.getContext().getBean("resourceService");
		//查询全部组织机构信息
        List<SysOrganization> itemDataList = resourceService.findOrgAll();
		 //初始化CODER 
        List<SysOrganization> list;
        if(itemDataList != null) {
            for(int i = 0; i < itemDataList.size(); i++) {
            	if(itemDataList.get(i).getParentOrgid()!=null){
            		list = orgCache.get(String.valueOf(itemDataList.get(i).getParentOrgid()));
            	}else{
            		list = orgCache.get("first");
            	}               
                if(list == null) {
                	list = new ArrayList<SysOrganization>();
                	orgCache.put(itemDataList.get(i).getParentOrgid(), list);                   
                }
                list.add(itemDataList.get(i));
                if(itemDataList.get(i).getParentOrgid()==null){
                	orgCache.put("first", list);	
                }else{
                 orgCache.put(itemDataList.get(i).getParentOrgid(), list);    
                }
                orgLookCache.put(itemDataList.get(i).getOrgCode(), itemDataList.get(i).getOrgName());
                orgLookCache2.put(itemDataList.get(i).getOrgCode(), itemDataList.get(i).getId().toString());
                orgLookCache3.put(itemDataList.get(i).getId().toString(), itemDataList.get(i).getOrgCode());
            }
            
        }
        
	}
	
	/**
     * 根据指定类型查找该类型下列表
     * @param type
     * @return
     */
    public static Map<String,String> getCodeMapByType(String type) {        
        List<SysOrganization> itemmap = orgCache.get(type);
        Map<String,String> result = new LinkedHashMap<String, String>();   
        if(itemmap == null) {
            result = new LinkedHashMap<String, String>();
        }else{
        	for(int i=0;i<itemmap.size();i++){
        		result.put(itemmap.get(i).getId().toString() ,itemmap.get(i).getOrgName());
        	}
        }
        return result;
    }
    
    
    public static Map<String,String> getCodeMapByType2(String type) {        
        List<SysOrganization> itemmap = orgCache.get(type);
        Map<String,String> result = new LinkedHashMap<String, String>();   
        if(itemmap == null) {
            result = new LinkedHashMap<String, String>();
        }else{
        	for(int i=0;i<itemmap.size();i++){
        		result.put(itemmap.get(i).getOrgCode() ,itemmap.get(i).getOrgName());
        	}
        }
        return result;
    }
    
    
    public static List<SysOrganization> getChildOrgList(String type) {
    	List<SysOrganization> itemmap = orgCache.get(type);       
        return itemmap;
    }
    
    
    public static String findOrgNameByCode(String code){
    	if(code==null||"".equals(code)){
    		return "";
    	}else{
    	return orgLookCache.get(code);
    	}
    }
    
    public static String findOrgidByCode(String code){
    	if(code==null||"".equals(code)){
    		return "";
    	}else{
    	return orgLookCache2.get(code);
    	}
    }
    
    public static String findOrgcodeById(String id){
    	if(id==null||"".equals(id)){
    		return "";
    	}else{
    	return orgLookCache3.get(id);
    	}
    }
    
    
    public static String findOrgnamesByIds(String ids){
    	String arr [] = ids.split(",");
    	int len = arr.length;
    	String str ="";String temp ="";
    	for(int i=0;i<len;i++){
    		temp = orgLookCache.get(orgLookCache3.get(arr[i]));
    		if(temp!=null&&!"".equals(temp)&&!"null".equals(temp)){
    		 str+=temp;
    		}
    	}
    	return str;
    }
    
    
    
    
    public static String findAllOrgidsByCode(String code){
    	if(code==null||"".equals(code)){
    		return "";
    	}else{
    		String a="",str="";
    		str = orgLookCache2.get(code.substring(0,6)+"00000000000");//市
    		a = a+ str+",";
    		if(!orgLookCache2.get(code.substring(0,8)+"000000000").equals(str)){
    			str=orgLookCache2.get(code.substring(0,8)+"000000000"); //县
    		    a = a+ str+",";
    		}
    		if(!orgLookCache2.get(code.substring(0,11)+"000000").equals(str)){
    			str=orgLookCache2.get(code.substring(0,11)+"000000");  //镇
    		    a = a+ str+",";
    		}
    		a = a.substring(0,a.length()-1);
    	    return a;
    	}
    }
        
    
    /**
     * 更新缓存
     * @param item
     */
    public static void changeList(SysOrganization org) {
    	List<SysOrganization> orgList = orgCache.get(org.getParentOrgid().toString());
    	
    	List<SysOrganization> backList = new ArrayList<SysOrganization>();
    	if(orgList!=null){
    		int num=0;
	    	for(int i=0;i<orgList.size();i++){	
	    		if(orgList.get(i).getId().equals(org.getId())){
	    			num++;
	    		}
	    		if(org.getId()!=null&&org.getId().toString().equals(orgList.get(i).getId().toString())){	    			
	    			backList.add(org);
	    		}else{
	    			backList.add(orgList.get(i));
	    		}
	    	}
	    	if(num==0){
	    	backList.add(org);
	    	}
    	}else{
    		backList.add(org);
    	}
    	orgCache.put(org.getParentOrgid().toString(), backList);
    	orgLookCache.put(org.getOrgCode(), org.getOrgName());
    	orgLookCache2.put(org.getOrgCode(), org.getId().toString());
    	orgLookCache3.put(org.getId().toString(),org.getOrgCode());
    }
    
    
    /**
     * 情况删除数据的缓存
     * @param item
     */
    public static void removeList(SysOrganization org) {
    	if(org.getParentOrgid()!=null){
    	List<SysOrganization> orgList = orgCache.get(org.getParentOrgid().toString());
    	
    	List<SysOrganization> backList = new ArrayList<SysOrganization>();
    	for(int i=0;i<orgList.size();i++){
    		if(org.getId().toString().equals(orgList.get(i).getId().toString())){
    			
    		}else{
    			backList.add(orgList.get(i));
    		}
    	}
    	orgCache.put(org.getParentOrgid().toString(), backList);
    	orgLookCache.put(org.getOrgCode(), org.getOrgName());
    	orgLookCache2.put(org.getOrgCode(), org.getId().toString());
    	orgLookCache3.put(org.getId().toString(),org.getOrgCode());
    	}
    }
}
