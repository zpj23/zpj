package com.goldenweb.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.FunctionHibernate;
import com.goldenweb.sys.pojo.SysFunction;

@Repository
public class FunctionHibernateDao extends BaseDao<SysFunction> implements FunctionHibernate{
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}	
	/*********************************************************************************************/
	
	/**
	 * 保存菜单并返回主键
	 */
	@Override
	public int saveFunction(SysFunction fun) {
		Session session =null;
		try {
			 session=sessionFactory.getCurrentSession();
			 Serializable id = session.save(fun);
			 return (Integer) id;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return 0;
	}
	
	
	/**
	 * 查询功能list
	 * @return
	 */
	@Override
	public List<Object[]> findFunction(){
		String hql = "select a.id,a.title,a.parentFunid from SysFunction as a where" +
		" operateType is null or operateType like '%,%' order by a.funOrder ";
		try {
			List<Object[]> list = this.findByHql2(hql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	 
	@Transactional
	public List<SysFunction> findFunctionTest(){
		String hql = "from SysFunction as a where" +
		" operateType is null or operateType like '%,%' order by a.funOrder ";
		try {
			List<SysFunction> list = this.sessionFactory.getCurrentSession().createQuery(hql).list(); 
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 依据角色id查询功能角色关系
	 * @return
	 */
	@Override
	public List<Object[]> findFunRole(String roleid){
		String sql = " select id,functionid from sys_rolefunction where roleid = ?";		
		try {
			List<Object[]> list = this.findBySql(sql, roleid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 依据父节点id查询子节点功能
	 * @return
	 */
	@Override
	public List<Object[]> findChildFun(int parentid,String type){			
		try {
			String sql = "select t.id,t.title from Sys_Function  t where t.parent_Funid=? and t.operate_Type=?";
			List<Object[]> list = this.findBySql(sql, parentid,type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Override
	public String delFunction(int id){
		try {
			/*String hql = " from SysFunction where parentFunid=?";
			List<Object[]> list = this.findByHql(hql, id);
			if(list!=null&&list.size()>0){
				//存在下级，不可删除
				return "2";
			}else{
				//执行删除				
				this.deleteData(id);
				return "1";
			}*/
			//执行删除				
			this.deleteData(id);
			//删除下面的按钮，有就删除
			this.deleteButton(id);
			
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	
	
	/**
	 * 删除角色功能关系
	 * @param roleid
	 */
	@Override
	public void delRoleFun(String roleid){
		String sql ="delete from sys_rolefunction where roleid = "+roleid;
		this.executeUpdateOrDelete(sql);
	}
	
	
	/**
	 * 查询功能list
	 * @return
	 */
	@Override
	public List<Object[]> findFunByParentid(String funid){
		List<Object[]> list = null;
		try{
		String hql = "select a.id,a.title,a.parentFunid,a.url,a.picture from SysFunction as a where isMenu=1 and parentFunid = ?";		
		list = this.findByHql(hql,Integer.valueOf(funid));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}	
	
	
	@Override
	public SysFunction getEntity(int id) {
		return get(id);
	}
	
	
	public void deleteData(int id){
		String sql ="delete from sys_function where id = "+id;
		executeUpdateOrDelete(sql);
	}
	
	@Override
	public List<Object[]> findMenuByPid(String pid,String userid) {
		try{
			/*String sql = "  select  a.id,a.title,a.url,a.parent_funid,a.fun_order from sys_function a inner join sys_functionuser b " +
			" on a.id=b.functionid and b.userid ="+userid+" where a.parent_funid="+pid+"  union  " +
			" select  a.id,a.title,a.url,a.parent_funid,a.fun_order from sys_function a  left join sys_functionorganization b  on a.id=b.functionid "+
			" left join sys_userinfo c on c.main_orgid=b.orgid where c.id = "+userid+" and a.parent_funid="+pid+
			" union  select distinct a.id,a.title,a.url,a.parent_funid,a.fun_order from sys_function a  left join sys_rolefunction b on a.id=b.functionid "+
			" left join sys_role c on c.id=b.roleid  left join sys_userrole d on d.roleid=c.id  where d.userid="+userid+" start with a.id = "+
			pid+" connect by a.parent_funid = prior a.id order by fun_order";*/
			String sql = "  select distinct a.id,a.title,a.url,a.parent_funid,a.fun_order,a.picture from sys_function a  left join sys_rolefunction b on a.id=b.functionid "+
			" left join sys_role c on c.id=b.roleid  left join sys_userrole d on d.roleid=c.id  where d.userid="+userid+" start with a.id = "+
			pid+" connect by a.parent_funid = prior a.id order by fun_order";
			List<Object[]> list = this.findBySql2(sql);
			return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	
	@Override
	public List<SysFunction> findFunctionByPid(int pid) {
		String hql ="from SysFunction where  isMenu=1 and parentFunid=?   order by funOrder";
		return this.find(hql, pid);
	}
	
	
	@Override
	public void saveOrUpdateFunction(SysFunction menu) {
		this.saveOrUpdate(menu);		
	}
	
	
	/**
	 * 设置菜单
	 */
	@Override
	public List<Object[]> findMenuJson(String roleid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id,a.title,a.parent_funid,(case when b.id is null then 0 else 1 end) ,")
		.append(" f_getbuttoninfo(a.id,a.operate_type,").append(roleid).append(")  from  sys_function a ")
		.append(" left join sys_rolefunction b  on  b.functionid = a.id and roleid = ").append(roleid)
		.append(" where a.is_menu=1 order by a.fun_order ");
		return this.findBySql2(sql.toString());
	}
	
	@Override
	public List<Object[]> configData() {
		String sql = " select distinct a.userid,b.id,b.title,b.url,b.is_menu,b.parent_funid,b.operate_type,b.fun_order,b.picture,b.is_popup from sys_function b "+
		" left join sys_rolefunction c on c.functionid=b.id "+
		" left join sys_userrole a on a.roleid = c.roleid "+
		"  where a.userid is not null order by a.userid,b.parent_funid,b.fun_order ";
		return this.findBySql2(sql);
	}
	
	private void deleteButton(int id) {
		String sql ="delete from sys_function where parent_funid = "+id;
		executeUpdateOrDelete(sql);
	}
	
	
	
	@Override
	public List<Object[]> findRoleMenuJson(String roleid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id,a.title,a.parent_funid,(case when c.id is null then 0 else 1 end),c.picpath from   ")
		.append(" sys_function a right join sys_rolefunction b  on  b.functionid = a.id and b.roleid = ")
		.append(roleid).append("  left join SYS_Quickmenu c on c.menuid = b.functionid and c.roleid = ")
		.append(roleid).append("  where a.is_menu=1 order by a.fun_order ");
		return this.findBySql2(sql.toString());
	}
	
	
}
