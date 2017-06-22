package com.goldenweb.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.goldenweb.fxpg.comm.BaseDao;
import com.goldenweb.sys.dao.RoleHibernate;
import com.goldenweb.sys.pojo.SysRole;

@Repository
public class RoleHibernateDao extends BaseDao<SysRole> implements RoleHibernate{
 
	
	/**********************************************************************************************/

	/**
	 * 
	 */	
	@Override
	public int saveOrUpdateRole(SysRole sr) {		
		Session session =null;
		try {
			session=sessionFactory.getCurrentSession();
			if(sr.getId()!=null){
				session.update(sr);
				return sr.getId();
			}else{
				Serializable id = session.save(sr);
				return (Integer) id;
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return 0;
	}


	/**
	 * 依据角色id删除角色机构关联关系
	 * @param roleid
	 */	
	@Override
	public void delRoleOrg(int roleid){
		String sql = "delete from sys_roleorganization where roleid="+roleid;
		this.executeUpdateOrDelete(sql);
	}


	/**
	 * 依据角色id删除角色人员关联关系
	 * @param roleid
	 */
	@Override
	public void delRoleUser(int roleid){
		String sql = "delete from sys_userrole where roleid="+roleid;
		this.executeUpdateOrDelete(sql);
	}


	/**
	 * 删除角色
	 * @param id
	 */
	@Override
	public void delRole(String id) {
		try {
			String sql = "delete from sys_role where id in ("+id+")";
			this.executeUpdateOrDelete(sql);	
			//角色菜单关联数据
			sql = "delete from sys_rolefunction where roleid in ("+id+")";
			this.executeUpdateOrDelete(sql);	
			//角色机构关联数据
			sql = "delete from sys_roleorganization where roleid in ("+id+")";
			this.executeUpdateOrDelete(sql);	
			//角色人员关联数据
			sql = "delete from sys_userrole where roleid in ("+id+")";
			this.executeUpdateOrDelete(sql);	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 查询该角色关联的机构
	 * @param id
	 * @return
	 */
	@Override
	public List<Object[]> findRoleOrg(String id) {
		try {
			String sql = " select a.orgid,b.org_Name from Sys_Roleorganization  a  " +
			" inner join Sys_Organization  b on a.orgid=b.id where a.roleid = ?";
			List<Object[]> list = this.findBySql(sql, id);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}


	/**
	 * 查询该角色关联的人员
	 * @param id
	 * @return
	 */
	@Override
	public List<Object[]> findRoleUser(String id) {
		try {
			String sql = " select a.userid,b.username from sys_userrole  a  " +
			" inner join Sys_Userinfo  b on a.userid=b.id where a.roleid = ?";
			List<Object[]> list = this.findBySql(sql, id);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 依据code查询该角色下的人员
	 * @param id
	 * @return
	 */
	@Override
	public List<Object[]> findRoleUserByCode(String code) {
		try {
			String sql = " select a.userid from sys_userrole  a  " +
			" left  join sys_role  b on a.roleid=b.id where b.rolecode = ?";
			List<Object[]> list = this.findBySql(sql, code);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 检查角色代码是否唯一
	 * @param roleid
	 * @param rolecode
	 * @return
	 */
	@Override
	public List<Object[]> checkRoleCode(String roleid, String rolecode) {
		List<Object[]> list=null;
		try {
			if(roleid==null||"".equals(roleid)){
				//新增时
				String sql = " select id from sys_role where rolecode =? ";
				list = this.findBySql(sql, rolecode);
			}else{
				//修改时
				String sql = " select id from sys_role where rolecode =?  " +
				" and id <> ?";
				list = this.findBySql(sql, rolecode,roleid);
			}				
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 获取角色下的人员
	 * @param rolecode
	 * @return json  {userid:xx,username:xx}
	 */
	@Override
	public List<Object[]> findUsersOfRole(String rolecode) {
		try {
			String sql = " select a.userid,c.username from sys_userrole  a  " +
			" left  join sys_role  b on a.roleid=b.id left join sys_userinfo c" +
			" on a.userid=c.id where b.rolecode like '"+rolecode+"%'";
			List<Object[]> list = this.findBySql2(sql);
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 查询所有的流程角色
	 * @return
	 */
	@Override
	public List<Object[]> findProcessRoles() {
		String sql = "select rolecode,rolename from sys_role where is_process='y'";
		return this.findBySql2(sql);
	}
	
	
	/**
	 * 是否存在流程角色：1-存在；2-不存在	
	 * @param rolecode
	 */
	@Override
	public int isExistsGroup(String rolecode) {
		String sql = "select * from act_id_group where id_ ='"+rolecode+"'";
		List<Object[]> list = findBySql2(sql);
		if(list!=null&&list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 是否存在流程用户：1-存在；2-不存在	
	 * @param rolecode
	 */
	@Override
	public int isExistsUser(String userid) {
		String sql = "select * from act_id_user where id_ ='"+userid+"'";
		List<Object[]> list = findBySql2(sql);
		if(list!=null&&list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	
	@Override
	public SysRole getEntity(int id) {		
		return this.get(id);
	}


	@Override
	public List<Object[]> findRolePageList(String selectName, Integer page,
			Integer rows) {
		String sql = " select id,rolename,rolecode,remark from SysRole ";
		if(selectName!=null){
			sql+=" where rolename like '%"+selectName+"%'";
		}
		sql+=" order by createTime desc ";
		return this.findListToPageByHql(sql, null, page, rows);
	}


	@Override
	public int findCountNumber(String selectName) {
		String hql = " select count(id) from SysRole   ";
		if(selectName!=null){
			hql+=" where rolename like '%"+selectName+"%'";
		}	
		return this.findListCount(hql, null);
	}


	
	@Override
	public List<SysRole> findRoleByName(String rolename) {
		String hql = " from SysRole ";
		if(rolename!=null&&!"".equals(rolename)){
			hql+=" where rolename like '%"+rolename+"%' ";
		}
		return this.find(hql, null);
	}


}
