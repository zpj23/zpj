package com.goldenweb.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.dao.impl.GenericDaoHibernate;
import com.goldenweb.sys.dao.PorletHibernate;
import com.goldenweb.sys.pojo.Porlet;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-18 上午9:41:42
 */
@Repository
public class PorletHibernateDao extends GenericDaoHibernate<Porlet, Serializable>
		implements PorletHibernate {
	public PorletHibernateDao() {
		super(Porlet.class);
	}



	@Override
	public String getConfiginfoByUserid(String userid) {
		String sql = "select objectconfiginfo,id from SYS_PORTAL_CONFIG  where oid = "+userid;
		List<Object[]> list = this.queryListBySql(sql);
		if(list!=null&&list.size()>0){
			return (String) list.get(0)[0];
		}
		return "";
	}



	
	@Override
	public List<Porlet> getPorletRole(String userid) {
		String hql  = "select p from Porlet p where id in ( select distinct a.id from Porlet a,PorletRole b where a.id = b.portalid  and  "
				+" exists (select roleid from SysUserrole where userid = "+userid+" and roleid =b.roleid)) ";
		
		//select  a.* from SYS_PORLET a ,sys_portal_role b  where  a.id = b.portalid and exists (select roleid from sys_userrole where userid = 10 and roleid =b.roleid)
		return find(hql, null);
	}
}
