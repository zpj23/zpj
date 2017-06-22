package com.goldenweb.sys.util.tag;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.goldenweb.fxpg.frame.tools.SpringContext;
import com.goldenweb.sys.pojo.SysResourceItem;
import com.goldenweb.sys.service.impl.ResourceServiceImpl;



public class ResourceCodeUtil {

    public static Map<String, List<SysResourceItem>> CODER = new LinkedHashMap<String, List<SysResourceItem>>();
    public static Map<String, List<SysResourceItem>> CODERLOOK = new LinkedHashMap<String,List<SysResourceItem>>();
    public static Map<String, String> cc = new LinkedHashMap<String, String>();
    
    public static void initCode() {
    		CODER.clear();
    		
             //查询所有的code数据 
            ResourceServiceImpl resourceService =  (ResourceServiceImpl) SpringContext.getContext().getBean("resourceService"); 
            //查询2级type和全部item
            List<Object[]> itemDataList = resourceService.findItemAll();         
             //初始化CODER 
            List<SysResourceItem> list;
            SysResourceItem item =null;
            if(itemDataList != null) {
                for(int i = 0; i < itemDataList.size(); i++) {
                	if(itemDataList.get(i)[0]!=null&&!"".equals(itemDataList.get(i)[0])){
                	list = CODER.get(String.valueOf(itemDataList.get(i)[2]));
                    if(list == null) {
                    	list = new  ArrayList<SysResourceItem>();
                        CODER.put(String.valueOf(itemDataList.get(i)[2]), list);
                    }
                   
                    item = new SysResourceItem();
                    item.setId(Integer.parseInt(itemDataList.get(i)[3].toString()));
                    item.setItemName(itemDataList.get(i)[1].toString());
                    if(itemDataList.get(i)[0]!=null){
                    item.setItemCode(itemDataList.get(i)[0].toString());
                    }
                    if(itemDataList.get(i)[2]!=null){
                    item.setParentItemname(itemDataList.get(i)[2].toString());
                    }
                    if(itemDataList.get(i)[4]!=null){
                        item.setParentItemid(Integer.parseInt(itemDataList.get(i)[4].toString()));
                        }
                    if(itemDataList.get(i)[7]!=null){
                        item.setTypeid(Integer.parseInt(itemDataList.get(i)[7].toString()));
                        }
                    if(itemDataList.get(i)[5]!=null){
                    item.setItemOrder(Integer.parseInt(itemDataList.get(i)[5].toString()));
                    }
                    if(itemDataList.get(i)[8]!=null){
                        item.setIsable(Integer.parseInt(itemDataList.get(i)[8].toString()));
                        }
                    if("data".equals(itemDataList.get(i)[6])){
                    	list.add(item);
                    }
                    
                    CODER.put(String.valueOf(itemDataList.get(i)[2]), list);
                    cc.put(String.valueOf(itemDataList.get(i)[0]), String.valueOf(itemDataList.get(i)[1]));
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
        List<SysResourceItem> itemmap = CODER.get(type);
        Map<String,String> result = new LinkedHashMap<String, String>();
        
        
        if(itemmap == null) {
            result = new LinkedHashMap<String, String>();
        }else{
        	for(int i=0;i<itemmap.size();i++){
        		if(itemmap.get(i).getIsable()==1){
        		result.put(itemmap.get(i).getItemCode() ,itemmap.get(i).getItemName());
        		}
        	}
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
        String result = null;
        List<SysResourceItem> temp = CODER.get(type);
        if(temp != null) {
        	for(int i=0;i<temp.size();i++){
        		if(no.equals(temp.get(i).getItemCode())){
        			result = temp.get(i).getItemName();
        		}
        		
        	}
        }
        if(result == null) {
            result = "";
        }
        return result;
    }
    
    
    public static List<SysResourceItem> getItemByType(String type) {
    	List<SysResourceItem> itemmap = CODER.get(type);       
        return itemmap;
    }
    
    
    /**
     * 更新缓存
     * @param item
     */
    public static void changeList(SysResourceItem item,String key) {
    	List<SysResourceItem> itemList = CODER.get(key);
    	
    	List<SysResourceItem> backList = new ArrayList<SysResourceItem>();
    	if(itemList!=null){
	    	for(int i=0;i<itemList.size();i++){	    		
	    		if(item.getId()!=null&&item.getId().toString().equals(itemList.get(i).getId().toString())){	    			
	    			backList.add(item);
	    		}else{
	    			backList.add(itemList.get(i));
	    		}
	    	}
	    	if("".equals(item.getItemCode())){
	    	backList.add(item);
	    	}
    	}else{
    		backList.add(item);
    	}
    	CODER.put(key, backList);
    	cc.put(item.getItemCode(), item.getItemName());
    }
    
    
    /**
     * 情况删除数据的缓存
     * @param item
     */
    public static void removeList(SysResourceItem item,String key) {
    	List<SysResourceItem> itemList = CODER.get(key);
    	
    	List<SysResourceItem> backList = new ArrayList<SysResourceItem>();
    	for(int i=0;i<itemList.size();i++){
    		if(item.getId().toString().equals(itemList.get(i).getId().toString())){
    			
    		}else{
    			backList.add(itemList.get(i));
    		}
    	}
    	CODER.put(key, backList);
    	cc.remove(item.getItemCode());
    }
    
    public static String getDictNameByCode(String code) {
        String result = null;
        result = cc.get(code);
        
        if(result == null) {
            result = "";
        }
        return result;
    } 
    
    
    public static String getDictNameByCodeMany(String code) {
        String result = "";
        String arr [] = code.split(",");
        for(int i=0;i<arr.length;i++){
        	if(cc.get(arr[i])!=null){
        	  result += cc.get(arr[i])+",";
        	}
        }
        
        if(!"".equals(result)){
        	result = result.substring(0,result.length()-1);
        }
        return result;
    } 
    
    
   
}
