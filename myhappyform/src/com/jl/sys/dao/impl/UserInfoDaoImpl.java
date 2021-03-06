package com.jl.sys.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.goldenweb.fxpg.frame.tools.MD5;
import com.jl.common.BaseDao;
import com.jl.sys.dao.UserInfoDao;
import com.jl.sys.pojo.UserInfo;

@Repository
public class UserInfoDaoImpl extends BaseDao<UserInfo> implements UserInfoDao{
	
	@Override
	public List<Object[]> findLogin(String loginname,String pwd,boolean flag){		
		try {
				if(flag){
					String sql =" select a.id,a.username from jl_user_info a where a.loginname=? and  a.password = ? and a.isdel=0 ";
					return this.findBySql(sql, loginname,pwd);
				}else{
					pwd = MD5.md5s(loginname+"{"+pwd+"}");//加密 规则      用户名{密码}
					String sql =" select a.id,a.username from jl_user_info a where a.loginname=? and  a.password = ? and a.isdel=0 ";
					return this.findBySql(sql, loginname,pwd);
				}
			} catch (Exception e) {
				return null;
			}
	}
	@Override
	public UserInfo findById(int id){
		return this.get(id);
	}
	
	public List<UserInfo> findUserByUserName(String username){
		StringBuffer sql = new StringBuffer(100).append(" from UserInfo where username='").append(username+"'");
		List<UserInfo> list=this.findObjectByHql(sql.toString());
		return list;
	}
	public List<UserInfo> findUserByOpenId(String openId){
		StringBuffer sql = new StringBuffer(100).append(" from UserInfo where openid='").append(openId+"'");
		List<UserInfo> list=this.findObjectByHql(sql.toString());
		return list;
	}
	@Override
	public List findList(UserInfo user,int page,int rows,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from jl_user_info a where 1=1  ");
		if(null!=param.get("datemin")&&!"".equalsIgnoreCase(param.get("datemin").toString())){
			sql.append(" and createtime >= ").append("'"+param.get("datemin")+"'");
		}
		if(null!=param.get("datemax")&&!"".equalsIgnoreCase(param.get("datemax").toString())){
			sql.append(" and createtime <= ").append("'"+param.get("datemax")+"'");
		}
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and ( username like ").append("'%"+param.get("username")+"%' or loginname like'%"+param.get("username")+"%' or address like'%"+param.get("username")+"%' or remark like'%"+param.get("username")+"%' ) ");
		}
		if(null!=param.get("departmentid")&&!"".equalsIgnoreCase(param.get("departmentid").toString())){
			sql.append(" and departmentcode = ").append("'"+param.get("departmentid")+"'");
		}
		sql.append(" order by createtime desc");
		List list=this.findMapObjBySql(sql.toString(), null, page, rows);
		return list;
	}
	
	@Override
	public int findCount(UserInfo user,Map<String,String> param){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(id) from UserInfo where 1=1  ");
		if(null!=param.get("datemin")&&!"".equalsIgnoreCase(param.get("datemin").toString())){
			sql.append(" and createtime >= ").append("'"+param.get("datemin")+"'");
		}
		if(null!=param.get("datemax")&&!"".equalsIgnoreCase(param.get("datemax").toString())){
			sql.append(" and createtime <= ").append("'"+param.get("datemax")+"'");
		}
		if(null!=param.get("username")&&!"".equalsIgnoreCase(param.get("username").toString())){
			sql.append(" and ( username like ").append("'%"+param.get("username")+"%' or loginname like'%"+param.get("username")+"%' or address like'%"+param.get("username")+"%' or remark like'%"+param.get("username")+"%' ) ");
		}
		if(null!=param.get("departmentid")&&!"".equalsIgnoreCase(param.get("departmentid").toString())){
			sql.append(" and departmentcode = ").append("'"+param.get("departmentid")+"'");
		}
		int count=this.findListCount(sql.toString(), null);
		return count;
		
	}
	
	@Override
	public int saveUser(UserInfo user){
		try{
			int id=user.getId();
			UserInfo ui=this.findById(id);
			Session session =null;
			if(ui!=null){
				user.setPassword(ui.getPassword());
				this.merge(user, String.valueOf(id));
				return user.getId();
			}else{
				//新增
				String pwd =user.getLoginname()+ "{"+user.getPassword()+"}";  //加密规则 ：  用户名{密码}
				pwd = MD5.md5s(pwd); // 密码加密
				user.setPassword(pwd);
//				this.save(user);
				session=sessionFactory.getCurrentSession();
				Serializable id1 = session.save(user);
				return (Integer) id1;
			}
			
		}catch (Exception e) {
			return 0;
		}
	}
	public void saveUserPw(UserInfo user){
		UserInfo ui=this.findById(user.getId());
		String pwd =ui.getLoginname()+ "{"+user.getPassword()+"}";  //加密规则 ：  用户名{密码}
		pwd = MD5.md5s(pwd); // 密码加密
		ui.setPassword(pwd);
		this.merge(ui, String.valueOf(user.getId()));
	}
	@Override
	public void delUser(int id){
		UserInfo ui=this.findById(id);
		this.delete(ui);
	}
	
	public List<Map> findUserByDepCode(String code){
		StringBuffer sql = new StringBuffer();
		sql.append(" select id,username from jl_user_info  where 1=1  ");
		if(null!=code&&!"".equalsIgnoreCase(code)){
			sql.append(" and departmentcode = ").append("'"+code+"'");
		}
		List<Map> list=this.findMapObjBySql(sql.toString(), null, 1, 100);
		return list;
	}
	
	public void importData(String sql){
		this.executeSql(sql);
	}
	
	public List<Map> findUserCountByDep(){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as total,departmentname as name  from jl_user_info GROUP BY departmentname ");
		List<Map> list=this.findMapObjBySql(sql.toString(), null, 1, 100);
		return list;
	}
	
	public void updateOpenId(String id,String openId){
		this.executeUpdateOrDelete("update jl_user_info set openid='"+openId+"' where id='"+id+"'");
	}
}
