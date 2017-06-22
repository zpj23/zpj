package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysFocus;

public interface FocusEventDao {

	public List<SysFocus> findEventByUserid(Integer id);
	
	public int findCountNumberByid(Integer id, String startDate, String endDate);

	public void addFocus(SysFocus info);

	public void cancleFocus(String eventInfo, Integer userid, String type);

	//public Page<SysFocus> findJsonList(Page<SysFocus> newPage, Integer id);
	

	//List<Object[]> findAlljson(Integer id, Integer page, Integer rows);

	List<Object[]> findAlljson(Integer id, Integer page, Integer rows,
			String startDate, String endDate);

}
