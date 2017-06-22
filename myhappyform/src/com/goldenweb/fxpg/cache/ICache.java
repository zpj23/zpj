package com.goldenweb.fxpg.cache;

public interface ICache {
	Object get(String key);
	
	void init(String key,Object value);
	
	void clear();
}
