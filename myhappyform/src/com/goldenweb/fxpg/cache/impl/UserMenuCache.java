package com.goldenweb.fxpg.cache.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.goldenweb.fxpg.cache.ICache;

public class UserMenuCache implements ICache {

	public static Map<Object, Object> menuMap = new LinkedHashMap<Object, Object>();
	
	@Override
	public Object get(String userid) {
		return menuMap.get(userid);
	}

	@Override
	public  void init(String key, Object value) {
		clear();
		menuMap.put(key, value);
		
	}

	@Override
	public void clear() {
		
	}


}
