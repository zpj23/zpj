package com.goldenweb.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.comm.BaseService.MethodLog2;
import com.goldenweb.sys.dao.FunctionHibernate;
import com.goldenweb.sys.dao.FunctionOrgHibernate;
import com.goldenweb.sys.dao.FunctionRoleHibernate;
import com.goldenweb.sys.dao.FunctionUserHibernate;
import com.goldenweb.sys.dao.MenuConfigHibernate;
import com.goldenweb.sys.dao.QuickmenuHibernate;
import com.goldenweb.sys.pojo.SysFunction;
import com.goldenweb.sys.pojo.SysFunctionorganization;
import com.goldenweb.sys.pojo.SysFunctionuser;
import com.goldenweb.sys.pojo.SysMenuConfig;
import com.goldenweb.sys.pojo.SysQuickmenu;
import com.goldenweb.sys.pojo.SysRolefunction;
import com.goldenweb.sys.service.PurviewService;

@Service
@Component("purviewService")
public class PurviewServiceImpl implements PurviewService{

	@Autowired
	private FunctionHibernate functionDao;
	@Autowired
	private FunctionUserHibernate functionUserDao;
	@Autowired
	private FunctionOrgHibernate functionOrgDao;
	@Autowired
	private FunctionRoleHibernate functionRoleDao;
	@Autowired
	private MenuConfigHibernate menuConfigDao;
	@Autowired
	private QuickmenuHibernate  quickmenuDao;	
	/*********************************************************************************************************/

	@Override
	public String findOperateByFunid(String funid){
		StringBuffer str = new StringBuffer("");
		try{
			//查询功能list
			List<Object[]> list = functionDao.findFunByParentid(funid);

			SysFunction sf = functionDao.getEntity(Integer.parseInt(funid));
			str.append("[{id:\"").append(sf.getId()).append("\", pId:\"")
			.append(sf.getParentFunid()).append("\", name:\"")
			.append("菜单").append("\" ,").append("click:\"findFunctionid('" + sf.getId() + "')\" ")
			.append(", open : true },");

			if (list != null && list.size() > 0) {
				//str.append("[");
				// 次节点下的隐藏
				String sign = "";
				String ischeck = "";
				for (int i = 0; i < list.size(); i++) {	
					sign = "click:\"findFunctionid('" + list.get(i)[0] + "')\" ";				

					str.append("{id:\"").append(list.get(i)[0]).append("\", pId:\"")
					.append(list.get(i)[2]).append("\", name:\"")
					.append(list.get(i)[1]).append("\" ,").append(sign)
					.append(", open : true },");
				}
			} 
			String s = str.toString();
			if (!s.equals("")) {
				s = s.substring(0, s.length() - 1) + "]";
			}   			
			return s;
		}catch (Exception e) {
			return "[]";
		}
	}


	/**
	 * 依据功能id查询出机构
	 * @param funids
	 * @return
	 */
	@Override
	public List<Object[]> findSomeOrg(String funid) {
		List<Object[]> list = functionOrgDao.findSomeOrg(funid);
		return list;
	}


	/**
	 * 保存组织机构
	 * @param orgids
	 */
	@Override
	public void saveOrgs(String orgids,String funids) {
		try {
			String orgid [] = orgids.split(",");
			String funid [] = funids.split(",");
			SysFunctionorganization fo;
			for(int i=0;i<funid.length;i++){
				//将原有的关联数据删除
				functionOrgDao.delFunOrg(funid[i]);
				//保存现有的关联数据
				for(int j=0;j<orgid.length;j++){
					if(!"".equals(orgid[j])){
						fo=new SysFunctionorganization();
						fo.setFunctionid(Integer.parseInt(funid[i]));
						fo.setOrgid(Integer.parseInt(orgid[j]));
						functionOrgDao.save(fo);
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	/**
	 * 保存与人员关联关系
	 * @param orgids
	 */
	@Override
	@MethodLog2(remark="编辑菜单人员关联信息",type="编辑")
	public void saveUsers(String userids, String funids) {
		try {
			String userid [] = userids.split(",");
			String funid [] = funids.split(",");
			SysFunctionuser fu;
			for(int i=0;i<funid.length;i++){
				//将原有的关联数据删除
				functionUserDao.delFunUser(funid[i]);
				//保存现有的关联数据
				for(int j=0;j<userid.length;j++){
					if(!"".equals(userid[j])){
						fu=new SysFunctionuser();
						fu.setFunctionid(Integer.parseInt(funid[i]));
						fu.setUserid(Integer.parseInt(userid[j]));
						functionUserDao.save(fu);
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 查询与功能id相关联的人员
	 * @param funid
	 * @return
	 */
	@Override
	public List<Object[]> findSomeUser(String funid) {		
		List<Object[]> list = functionUserDao.findSomeUser(funid);
		return list;
	}

	
	/**
	 * 查询与功能id相关联的角色
	 * @param funid
	 * @return
	 */
	@Override
	public List<Object[]> findSomeRole(String funid) {		
		List<Object[]> list = functionRoleDao.findSomeRole(funid);
		return list;
	}

	
	/**
	 * 保存与角色关联关系
	 * @param orgids
	 */
	@Override
	@MethodLog2(remark="编辑菜单角色关联信息",type="编辑")
	public void saveRoles(String roleids, String funids) {
		try {
			String roleid [] = roleids.split(",");
			String funid [] = funids.split(",");
			SysRolefunction rf;
			for(int i=0;i<funid.length;i++){
				//将原有的关联数据删除
				functionRoleDao.delFunRole(funid[i]);
				//保存现有的关联数据
				for(int j=0;j<roleid.length;j++){
					if(!"".equals(roleid[j])){
						rf=new SysRolefunction();
						rf.setFunctionid(Integer.parseInt(funid[i]));
						rf.setRoleid(Integer.parseInt(roleid[j]));
						functionRoleDao.save(rf);
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 保存与3者关联关系
	 * @param orgids
	 */
	@Override
	public void saveAll(String roleids, String orgids, String userids,
			String funids) {
		saveRoles(roleids, funids);
		saveOrgs(orgids, funids);
		saveUsers(userids, funids);
	}


	
	@Override
	public String findMenuJson(String roleid) {
		List<Object[]> list = functionDao.findMenuJson(roleid);
		if(list!=null&&list.size()>0){
			  StringBuffer str =new StringBuffer();
			  str.append("{\"total\":\"").append(list.size()).append("\",\"rows\":[");
			  for(int i=0;i<list.size();i++){
				  str.append("{");
				  str.append("\"id\":").append(list.get(i)[0]).append(",");
				  str.append("\"title\":\"").append(list.get(i)[1]).append("\",");
				  str.append("\"functionid\":").append(list.get(i)[0]).append(",");
				  str.append("\"childbutton\":\"").append(list.get(i)[4]==null?"":list.get(i)[4]).append("\",");
				  str.append("\"ischeck\":\"").append(list.get(i)[3]).append("\"");
				  if(!"1".equals(list.get(i)[2].toString())){
				  str.append(",\"_parentId\":").append(list.get(i)[2]).append("");
				  }
				  str.append("},");			  
			  }
			  str.delete(str.toString().length()-1, str.toString().length());
			  str.append("],\"footer\":[]}");
			  return str.toString();
			}
			return "[]";
	}


	
	@Override
	public void saveSetData(String roleid, String menuid) {
		//删除原来数据
		functionRoleDao.delLinkByRoleid(roleid);
		
		//新增关联数据
		String arr [] = menuid.split(",");
		SysRolefunction rf = null;
		for(int i=0;i<arr.length;i++){
			if(arr[i]!=""){
				rf =new SysRolefunction();
				rf.setRoleid(Integer.parseInt(roleid));
				rf.setFunctionid(Integer.parseInt(arr[i]));
				functionRoleDao.save(rf);			
			}
		}		
	}

	
	//权限配置信息
	@Override
	public void saveConfigData() {
		List<Object[]> list = functionDao.configData();
		String userid="";
		SysMenuConfig config = null;
		StringBuffer str = new StringBuffer();
		for(int i=0;i<list.size();i++){
			
			
			if(!userid.equals(list.get(i)[0].toString())||i==(list.size()-1)){
				if(!"".equals(userid)&&!"".equals(str.toString())){
					config = new SysMenuConfig();
					config.setUserid(userid);
					
					if(i==(list.size()-1)){
						//最后一天数据记录
						str.append("{\"id\":\"").append(list.get(i)[1]).append("\",");
						str.append("\"title\":\"").append(list.get(i)[2]).append("\",");
						str.append("\"url\":\"").append(list.get(i)[3]).append("\",");
						str.append("\"isMenu\":\"").append(list.get(i)[4]).append("\",");
						str.append("\"parentFunid\":\"").append(list.get(i)[5]).append("\",");
						str.append("\"picture\":\"").append(list.get(i)[8]).append("\",");
						str.append("\"isPopup\":\"").append(list.get(i)[9]).append("\",");
						str.append("\"operateType\":\"").append(list.get(i)[6]).append("\"},");
					}
					
					str.delete(str.toString().length()-1, str.toString().length());
					config.setMenuinfo("["+str.toString()+"]");
					menuConfigDao.saveMenuConfig(config);
				}
				str = new StringBuffer();
			}
			userid = list.get(i)[0].toString();
			
			if(userid.equals(list.get(i)[0].toString())){
				str.append("{\"id\":\"").append(list.get(i)[1]).append("\",");
				str.append("\"title\":\"").append(list.get(i)[2]).append("\",");
				str.append("\"url\":\"").append(list.get(i)[3]).append("\",");
				str.append("\"isMenu\":\"").append(list.get(i)[4]).append("\",");
				str.append("\"parentFunid\":\"").append(list.get(i)[5]).append("\",");
				str.append("\"picture\":\"").append(list.get(i)[8]).append("\",");
				str.append("\"isPopup\":\"").append(list.get(i)[9]).append("\",");
				str.append("\"operateType\":\"").append(list.get(i)[6]).append("\"},");
			}
			
		}
	}
	
	//查询某人的权限配置信息
	@Override
	public String findOneMenuConfig(String userid){
		SysMenuConfig smc = menuConfigDao.findOneMenuConfig(userid);
		String str="";
		if(smc!=null){
			str = smc.getMenuinfo();
		}
		return str;
	}

	//查询某人的权限配置信息返回菜单按钮的实体类集合
	@Override
	public List<SysFunction> findUserMenuList(String userid){
		String strJson = findOneMenuConfig(userid);
		if("".equals(strJson)){
			return null;
		}
		List<SysFunction> obj =  JSONArray.toList(JSONArray.fromObject(strJson), new SysFunction(), new JsonConfig());
		return obj;
	}

	//删除某人的配置信息
	@Override
	public void delOneMenuConfig(String userid){
		menuConfigDao.del(userid);
	}


	
	@Override
	public Map<String, List<SysFunction>> findUserButtonMap(List<SysFunction> userFunctionList) {
				
	    Map<String, List<SysFunction>> map = new LinkedHashMap<String, List<SysFunction>>();
		if(userFunctionList!=null){
			 List<SysFunction> list;
			for(int i=0;i<userFunctionList.size();i++){
				list = map.get(userFunctionList.get(i).getParentFunid().toString());
				 if(list == null) {
	                	list = new ArrayList<SysFunction>();
	                	map.put(userFunctionList.get(i).getParentFunid().toString(), list);                   
	                }
                list.add(userFunctionList.get(i));
                map.put(userFunctionList.get(i).getParentFunid().toString(), list);
			}
			return map;
		}
		return null;
	}


	
	@Override
	public String findButtonByMenuid(Map<String, List<SysFunction>> map,String menuid) {
		String str="";
		if(map!=null){//对于未设置权限信息的不做处理
		List<SysFunction> lt = map.get(menuid);
			if(lt!=null){
				for(int i=0;i<lt.size();i++){			
					str += lt.get(i).getOperateType()+",";
				}
				if(!"".equals(str)){
					str=","+str;
				}
			}
		}
		return str;
	}


	
	@Override
	public String findRoleMenuJson(String roleid) {
		List<Object[]> list = functionDao.findRoleMenuJson(roleid);
		if(list!=null&&list.size()>0){
			  StringBuffer str =new StringBuffer();
			  str.append("{\"total\":\"").append(list.size()).append("\",\"rows\":[");
			  for(int i=0;i<list.size();i++){
				  str.append("{");
				  str.append("\"id\":").append(list.get(i)[0]).append(",");
				  str.append("\"title\":\"").append(list.get(i)[1]).append("\",");
				  str.append("\"functionid\":").append(list.get(i)[0]).append(",");
				  str.append("\"picture\":\"").append(list.get(i)[4]==null?"":list.get(i)[4]).append("\",");
				  str.append("\"number\":").append(i).append(",");
				  str.append("\"ischeck\":\"").append(list.get(i)[3]).append("\"");
				  if(!"1".equals(list.get(i)[2].toString())){
				  str.append(",\"_parentId\":").append(list.get(i)[2]).append("");
				  }
				  str.append("},");			  
			  }
			  str.delete(str.toString().length()-1, str.toString().length());
			  str.append("],\"footer\":[]}");
			  return str.toString();
			}
			return "[]";
	}


	/**
	 * 保存设置的快键菜单
	 */
	@Override
	public void saveQuickmenu(String roleid, String menuid,String picpath) {
		//删除原来数据
		quickmenuDao.delLinkByRoleid(roleid);				
		//新增关联数据
		String arr [] = menuid.split(",");
		String arr2 [] = picpath.split(",",(arr.length+1));
		SysQuickmenu qm = null;
		for(int i=0;i<arr.length;i++){
			if(arr[i]!=""){
				qm =new SysQuickmenu(Integer.parseInt(arr[i]),Integer.parseInt(roleid),arr2[i]);
				quickmenuDao.save(qm);
			}
		}
	}
	
	
	@Override
	public List<Object[]> findQuickmenuByRoleid(String userid){
		   return quickmenuDao.findQuickmenuByRoleid(userid);
	}


}
