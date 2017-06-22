package com.goldenweb.fxpg.frame.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.goldenweb.fxpg.frame.dao.GenericDao;
import com.goldenweb.fxpg.frame.service.GenericService;
import com.goldenweb.fxpg.frame.tools.Constant;
import com.goldenweb.fxpg.frame.tools.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GenericManagerImpl<T, PK extends Serializable> implements GenericService<T, PK> {
	
    protected final Log log = LogFactory.getLog(getClass());

    protected GenericDao<T, PK> dao;
    
	public GenericManagerImpl() {
    }

    public GenericManagerImpl(GenericDao<T, PK> genericDao) {
        this.dao = genericDao;
    }

    @Override
	public List<T> getAll() {
        return dao.getAll();
    }

    @Override
	public T get(PK id) {
        return dao.get(id);
    }

    @Override
	public boolean exists(PK id) {
        return dao.exists(id);
    }

    @Override
	public T save(T object) {
        return dao.save(object);
    }
    
    @Override
	public void remove(PK id) {
        dao.remove(id);
    }
    
	@Override
	public void remove(PK[] ids) {
		for(PK pk : ids){
			dao.remove(pk);
		}
	}

	@Override
	public T updateRow(Map<String, Object> map, String id, String idFiled) {
		return dao.updateRow(map,id,idFiled);
	}
 
	@Override
	public boolean isExist(Map<String, String> map) {
		return dao.isExist(map);
	}
	
	@Override
	public Page getByPage(Page queryPage, Map values) {
		return dao.hqlQueryPage("", queryPage, values);
	}
	
	public Page getByPage2(Page queryPage, Map values,String ordercon) {
		return dao.hqlQueryPage2("", queryPage, values,ordercon);
	}
	
	/**
	 * @Description TODO(List转json)
	 * @Title gsonStr
	 * @param list
	 * @return String  
	 * @author Lee
	 * @time 2014-2-24 下午04:43:27
	 */
	public static String gsonStr(List list){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", list.size());
		Gson gson = new GsonBuilder()
		.setDateFormat(Constant.TOFULLFORMAT)
		.create();
		String strGson = gson.toJson(map);
		return strGson;
	}
	
	public void jsonWrite(String json_) throws IOException{
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getResponse().getWriter().write(json_);
	}
}
