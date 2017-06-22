package com.goldenweb.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.frame.tools.UUIDGenerator;
import com.goldenweb.sys.dao.RemindHibernate;
import com.goldenweb.sys.pojo.SysRemind;
import com.goldenweb.sys.service.RemindService;
import com.goldenweb.sys.util.DateHelper;

@Service
@Component("remindService")
public class RemindServiceImpl implements RemindService{

	@Autowired
	private RemindHibernate remindDao;

	
	/**
	 * 提醒数据json
	 */
	@Override
	public String findRemindJson(String userid,String num) {
		List<SysRemind> list = remindDao.findRemindList(userid); 
		if(list!=null&&list.size()>0){
			  StringBuffer str =new StringBuffer();
			  str.append("{\"total\":\"").append(list.size()).append("\",\"rows\":[");
			  int countnum;
			  if("0".equals(num)){
				  countnum = list.size();
			  }else{
				  countnum = Integer.parseInt(num);
			  }
			  for(int i=0;i<countnum;i++){
				  if(list.get(i)!=null){
				  str.append("{");
				  str.append("\"id\":\"").append(list.get(i).getId()).append("\",");
				  str.append("\"title\":\"").append(list.get(i).getRemindTitle()).append("\",");
				  str.append("\"url\":\"").append(list.get(i).getRemindUrl()).append("\",");
				  str.append("\"remindtime\":\"").append(DateHelper.getDateString(list.get(i).getRemindTime(), "yyyy-MM-dd")).append("\",");
				  str.append("\"username\":\"").append(list.get(i).getStartUsername()).append("\"");
				  str.append("},");	
				  }
			  }
			  str.delete(str.toString().length()-1, str.toString().length());
			  str.append("]}");
			  return str.toString();
			}
			return "[]";
	}
	
	@Override
	public List<SysRemind> findRemindList(String userid) {
		List<SysRemind> list = remindDao.findRemindList(userid); 		
		return list;
	}


	/**
	 * 查询提醒条数
	 */
	@Override
	public int findRemindNumber(String userid) {		
		return remindDao.findRemindNumber(userid);
	}


	@Override
	public void saveRemind(String userid) {		
		remindDao.delRemindWithMail();
		//查询未读邮件()
		List<Object[]> list = remindDao.findMailNoreadList(null);
		if(list!=null&&list.size()>0){
			SysRemind remind = null;
			for(int i=0;i<list.size();i++){
				remind = new SysRemind();
				remind.setId(UUIDGenerator.generate32UUID());
				remind.setRemindTitle(list.get(i)[1].toString());
				remind.setRemindUrl("mailAction_viewMailIndex?mailid="+list.get(i)[0].toString());
				remind.setRemindType("mail");
				remind.setRemindUserid(list.get(i)[3].toString());
				remind.setStartUsername(list.get(i)[4].toString());
				remind.setRemindTime(DateHelper.getStringDate(list.get(i)[2].toString(), "yyyy-MM-dd HH:mm:ss"));
				remind.setRemindDataid(list.get(i)[0].toString());
				remindDao.saveRemind(remind);
			}
		}
	}


	@Override
	public void delRemind(String remindid) {
		remindDao.delRemind(remindid);		
	}

	
	@Override
	public List<SysRemind> findRemindAllList(String userid) {
		 List<Object[]> list = remindDao.findRemindAllList(userid);
		 
		 if(list!=null&&list.size()>0){
			 List<SysRemind> remindlist = new ArrayList<SysRemind>();
			 
			 String str="";
			 SysRemind remind = null;
			 for(int i=0;i<list.size();i++){
				 remind = new SysRemind();
				 remind.setRemindTitle(list.get(i)[1].toString());
				 remind.setRemindType("mail");
				 remind.setRemindDataid(list.get(i)[0].toString());
				 remind.setRemindUrl("mailAction_viewMailIndex?mailid="+list.get(i)[0].toString());
				 remind.setStartUsername(String.valueOf(list.get(i)[2]));
				 remind.setRemindUserid(userid);
				 remind.setRemindTime((Date)list.get(i)[3]);
				 				 
				 Double num =  Double.valueOf(list.get(i)[4].toString());
				 if(num<60){
					 str = num+"分钟前";
				 }else if(num <60*24){
					 str = (int)Math.ceil((num/60))+"小时前";
				 }else{
					 str = (int)Math.ceil((num/60/24))+"天前";
				 }
				 remind.setShowtime(str);
				 remindlist.add(remind);
			 }
			 return remindlist;
		 }else{
			 return null;
		 }
		 
		 
	}
	
	
	
}
