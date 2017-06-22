package com.goldenweb.sys.service;

import java.util.List;
import java.util.Map;

import com.goldenweb.sys.pojo.SysFocus;
import com.goldenweb.sys.pojo.SysUserinfo;

public interface FocusService {

	String toJson(Integer page, Integer rows, Map<String, String> params,
			SysUserinfo user);

	List<SysFocus> findEventInfo(SysUserinfo user);

	void addFocus(String eventid, String type, Integer userid);

	void cancleFocus(String eventid, String type, Integer userid);

	//String toJsonList(Integer page, Integer rows, SysUserinfo user);

	String toJsonList(Integer page, Integer rows, SysUserinfo user,
			String startDate, String endDate);

	//Map findJsonList(Page<SysFocus> newPage, SysUserinfo user);

}
