package com.goldenweb.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.QuickmenuHibernate;
import com.goldenweb.sys.pojo.SysQuickmenu;

@Repository
public class QuickmenuHibernateDao extends  BaseDao<SysQuickmenu> 
  implements QuickmenuHibernate{

	
	@Override
	public void delLinkByRoleid(String roleid) {
		String sql = " delete from SYS_Quickmenu where roleid ='"+roleid+"'";
		this.executeUpdateOrDelete(sql);
	}

	
	@Override
	public List<Object[]> findQuickmenuByRoleid(String userid) {
		String sql = " select b.id,b.title,a.picpath,b.url  from  SYS_Quickmenu a  "
				+ " left join sys_function b  on a.menuid = b.id where a.roleid in "
				+ " (select roleid from sys_userrole  where userid = '"+userid+"') ";
		List<Object[]> list = this.findBySql2(sql);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

}
