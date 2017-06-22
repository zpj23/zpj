package com.goldenweb.sys.dao;

import java.util.List;

import com.goldenweb.sys.pojo.SysRemind;

public interface RemindHibernate {

	List<SysRemind> findRemindList(String userid);

	int findRemindNumber(String userid);

	void delRemind(String remindid);

	List<Object[]> findMailNoreadList(String userid);

	void saveRemind(SysRemind remind);

	void delRemindWithMail();

	List<Object[]> findRemindAllList(String userid);
	
	void delRemindWithDataid(String dataid,String type);

}
