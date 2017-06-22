package com.goldenweb.sys.service;

import java.util.List;

import com.goldenweb.sys.pojo.SysRemind;

public interface RemindService {

	String findRemindJson(String userid,String num);
	
	int  findRemindNumber(String userid);

	void saveRemind(String userid);

	void delRemind(String remindid);

	List<SysRemind> findRemindList(String string);

	List<SysRemind> findRemindAllList(String string);

}
