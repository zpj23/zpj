package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.RemindHibernate;
import com.goldenweb.sys.pojo.SysRemind;

@Repository
public class RemindHibernateDao extends BaseDao<SysRemind> implements RemindHibernate{

	
	@Override
	public List<SysRemind> findRemindList(String userid) {
		String hql = " from SysRemind where remindUserid=?";
		return this.find(hql, userid);
	}

	
	@Override
	public int findRemindNumber(String userid) {		
		String sql = "select a.id  from oa_mailinfo a " +
		" left join  sys_userinfo b on a.create_userid=b.id   left join  oa_mailuser c on a.id=c.mailid " +
		" where  c.mail_status =0 and  c.userid = '"+userid+"'  "; 
		List<Object[]> list = this.findBySql2(sql);
		return list.size();
	}

	
	@Override
	public void delRemind(String remindid) {
		String hql = " delete from SysRemind where id = ? ";
		 this.updateordelete(hql, remindid);
	}


	
	@Override
	public List<Object[]> findMailNoreadList(String userid) {
		String sql ="select  b.id,b.title,to_char(b.create_time,'yyyy-MM-dd HH24:mi:ss'),a.userid,c.username from  oa_mailuser a left join  oa_mailinfo b "+
		" on a.mailid=b.id left join sys_userinfo c on c.id=b.create_userid  where  a.mail_status = 0  ";
		return this.findBySql2(sql);
	}


	
	@Override
	public void saveRemind(SysRemind remind) {
		save(remind);		
	}


	
	@Override
	public void delRemindWithMail() {
		String hql = " delete from SysRemind where remindType = 'mail' ";
		this.updateordelete(hql, null);
	}


	//  to_char(a.create_time,'yyyy-mm-dd HH24:mi:ss')
	@Override
	public List<Object[]> findRemindAllList(String userid) {
		String sql = "select a.id,a.title,b.username,a.create_time,ceil((sysdate-a.create_time)*24*60)  from oa_mailinfo a " +
				" left join  sys_userinfo b on a.create_userid=b.id   left join  oa_mailuser c on a.id=c.mailid " +
				" where  c.mail_status =0 and  c.userid = ?  order by a.create_time desc"; 
		return this.findBySql(sql, userid);
	}
	
	
	
	@Override
	public void delRemindWithDataid(String dataid,String type) {
		String hql = " delete from SysRemind where remindType = ? and remindDataid=? ";
		this.updateordelete(hql, type,dataid);
	}



}
