package com.goldenweb.fxpg.frame.dao;

import java.util.List;

import com.goldenweb.fxpg.frame.pojo.DYPlugin;


public interface DynamicPlugin<T> {

	public List findAllPlugin();
	
	public List findPagePlugin(int page,int rows);

	public Long findPluginNumber();

	public void savePlugin(DYPlugin plugin);

	public List<Object[]> findPageStyle(Integer page, Integer rows);

	public void delPlugin(String string);

}
