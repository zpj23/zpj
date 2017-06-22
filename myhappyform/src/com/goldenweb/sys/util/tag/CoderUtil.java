package com.goldenweb.sys.util.tag;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.goldenweb.fxpg.frame.tools.SpringContext;
import com.goldenweb.sys.service.impl.ResourceServiceImpl;



public class CoderUtil {

    public static Map<String, Map<String, String>> CODER = new LinkedHashMap<String, Map<String, String>>();
    public static Map<String, Map<String, String>> CODERLOOK = new LinkedHashMap<String, Map<String, String>>();
    public static Map<String, String> cc = new LinkedHashMap<String, String>();
    public static Map<String, Map<String, String>> idvalue = new LinkedHashMap<String, Map<String, String>>();
    
    public static void initCode(HttpServletRequest request) {
    		CODER.clear();
    		idvalue.clear();
    		
             //查询所有的code数据 
            //ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
            ResourceServiceImpl resourceService = (ResourceServiceImpl) SpringContext.getContext().getBean("resourceService");  //getMessageSource();//(ResourceService) ctx.getBean("resourceService");
            //SysResourceItem condition = new SysResourceItem();
            //SysUserinfo user = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
            //查询2级type和全部item
            List<Object[]> itemDataList = resourceService.findItemAll();         
             //初始化CODER 
            Map<String,String> tempLinkedHashMap;
            if(itemDataList != null) {
                for(int i = 0; i < itemDataList.size(); i++) {
                    tempLinkedHashMap = CODER.get(String.valueOf(itemDataList.get(i)[2]));
                    if(tempLinkedHashMap == null) {
                        tempLinkedHashMap = new LinkedHashMap<String,String>();
                        CODER.put(String.valueOf(itemDataList.get(i)[2]), tempLinkedHashMap);
                    }
                    tempLinkedHashMap.put(String.valueOf(itemDataList.get(i)[0]), String.valueOf(itemDataList.get(i)[1]));
                    cc.put(String.valueOf(itemDataList.get(i)[0]), String.valueOf(itemDataList.get(i)[1]));
                }
            }
            
            
            List<Object[]> itemDataListLook = itemDataList;
            Map<String,String> tempLinkedHashMapLook;
            if(itemDataListLook != null) {
                for(int i = 0; i < itemDataListLook.size(); i++) {
                    tempLinkedHashMapLook = CODERLOOK.get(String.valueOf(itemDataListLook.get(i)[2]));
                    if(tempLinkedHashMapLook == null) {
                        tempLinkedHashMapLook = new LinkedHashMap<String,String>();
                        CODERLOOK.put(String.valueOf(itemDataListLook.get(i)[2]), tempLinkedHashMapLook);
                    }
                    tempLinkedHashMapLook.put(String.valueOf(itemDataListLook.get(i)[0]), String.valueOf(itemDataListLook.get(i)[1]));
                }
            }
            
            
            List<Object[]> idvalueList = itemDataList;
            Map<String,String> idvalueHashMap;
            if(idvalueList != null) {
                for(int i = 0; i < idvalueList.size(); i++) {
                	if(idvalueList.get(i)[4]!=null){                	
                	idvalueHashMap = idvalue.get(String.valueOf(idvalueList.get(i)[4]));
                    if(idvalueHashMap == null) {
                    	idvalueHashMap = new LinkedHashMap<String,String>();
                        idvalue.put(String.valueOf(idvalueList.get(i)[4]), idvalueHashMap);
                    }
                    idvalueHashMap.put(String.valueOf(idvalueList.get(i)[3]), String.valueOf(idvalueList.get(i)[1]));
                  }
                }                	
            }
    }

    /**
     * 根据指定类型查找该类型下列表
     * @param type
     * @return
     */
    public static Map<String,String> getCodeMapByType(String type) {       
        return ResourceCodeUtil.getCodeMapByType(type);
    }

    /**
     * 根据指定类型查找该类型下列表(id-value)
     * @param type
     * @return
     */
    public static Map<String,String> getCodeMapByType2(String id) {
        Map<String,String> result = idvalue.get(id);
        if(result == null) {
            result = new LinkedHashMap<String, String>();
        }
        return result;
    }
    
    /**
     * 根据指定类型与编号，查找中文文字
     * @param type
     * @param no
     * @return
     */
    public static String getCodeName(String type,String no) {       
        return ResourceCodeUtil.getCodeName(type, no);
    }
    
    public static String getCodeName2(String key) {
    	 return ResourceCodeUtil.getDictNameByCode(key);
    }  
    
    public static String getDictNameByCode(String code) {
        return ResourceCodeUtil.getDictNameByCode(code);
    } 
}
