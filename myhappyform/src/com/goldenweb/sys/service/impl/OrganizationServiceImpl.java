package com.goldenweb.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.fxpg.cache.impl.OrginfoCache;
import com.goldenweb.fxpg.comm.BaseService;
import com.goldenweb.fxpg.frame.service.impl.GenericManagerImpl;
import com.goldenweb.sys.dao.OrganizationHibernate;
import com.goldenweb.sys.dao.UserinfoHibernate;
import com.goldenweb.sys.pojo.SysOrganization;
import com.goldenweb.sys.service.OrganizationService;
import com.goldenweb.sys.util.tag.ResourceCodeUtil;

@Service
@Component("organizationService")
public class OrganizationServiceImpl extends BaseService<SysOrganization> implements OrganizationService{
	@Autowired
	private OrganizationHibernate organizationDao;
	@Autowired
	private UserinfoHibernate userinfoHibernate;
	
	public OrganizationServiceImpl(){
		super();
	}
	
	/********************************************************************************************************/

	/**
	 * 构建组织机构的tree
	 */
	/*public String findOrgJson() {
		try{
			//查询现有的组织机构
			List<SysOrganization> list = organizationDao.findAll();
			StringBuffer str = new StringBuffer("");
			if (list != null && list.size() > 0) {
				str.append("[");
				// 次节点下的隐藏
				String sign = "";
				String ischeck = "";
				String imgurl = "images/tree/file.gif";
				for (int i = 0; i < list.size(); i++) {
					sign = "click:\"getContextid('" + list.get(i).getId() + "')\" ";
					if(list.get(i).getOrgType()==2){//部门
						imgurl="";
					}else{
						imgurl = "images/tree/file.gif";
					}
					str.append("{id:\"").append(list.get(i).getId()).append("\", pId:\"")
					.append(list.get(i).getParentOrgid()).append("\", name:\"")
					.append(list.get(i).getOrgName()).append("\" ,").append(sign)
					.append(",icon:\""+imgurl+"\"").append(", open : true },");
				}
			} else {
				return "0";
			}
			String s = str.toString();
			if (!s.equals("")) {
				s = s.substring(0, s.length() - 1) + "]";
			}   
			return s;
		}catch (Exception e) {
			return "[]";
		}
	}*/


	/*public String findDeptJsonBak() {
		try{	
			
			//查询现有的组织机构
			List<SysOrganization> list = organizationDao.findOrgLvOne();
			StringBuffer str = new StringBuffer("");
			if (list != null && list.size() > 0) {
				str.append("[");
				// 次节点下的隐藏
				String sign = "";
				String ischeck = "";
				for (int i = 0; i < list.size(); i++) {	
					sign = "click:\"getOneDeptInfo('" + list.get(i).getId() + "')\",icon:\"images/tree/file.gif\"";				

					str.append("{id:\"").append(list.get(i).getId()).append("\", pId:\"")
					.append(list.get(i).getParentOrgid()).append("\", name:\"")
					.append(list.get(i).getOrgName()).append("\" ,").append(sign).append(", open : true },");
				}
			} else {
				return "0";
			}
			String s = str.toString();
			if (!s.equals("")) {
				s = s.substring(0, s.length() - 1) + "]";
			}   
			return s;
		}catch (Exception e) {
			return "[]";
		}
	}*/
	
	
	@Override
	public String findDeptJson(String id) {
		try{
			//查询2级数据id下的item
			Map<String,String> map =OrginfoCache.getCodeMapByType(id);

			StringBuffer str = new StringBuffer("");
			if (map != null && map.size() > 0) {
				str.append("[");
				// 次节点下的隐藏
				String sign = "";
				String ischeck = "";
				
				SysOrganization orginfo = organizationDao.getEntity(Integer.parseInt(id));
				sign = "click:\"getOneDeptInfo('" + orginfo.getId()+"','"+orginfo.getOrgName() + "','"+orginfo.getOrgCode() + "')\" ";
				str.append("{id:\"").append(orginfo.getId()).append("\", pId:\"")
				.append("").append("\", name:\"")
				.append(orginfo.getOrgName()).append("\" ,").append(sign)
				.append(", open : true,isParent:true},");
				
				for (Map.Entry entry : map.entrySet()){	
					sign = "click:\"getOneDeptInfo('" + (String)entry.getKey()+"','"+entry.getValue() + "','"+OrginfoCache.findOrgcodeById((String)entry.getKey()) + "')\" ";
					str.append("{id:\"").append(entry.getKey()).append("\", pId:\"")
					.append(id).append("\", name:\"")
					.append(entry.getValue()).append("\" ,").append(sign)
					.append(", open : false ");
					if(OrginfoCache.getCodeMapByType((String)entry.getKey()).size()>0){
						//存在下级
						str.append(",isParent:true");
					}
					str.append("},");
				}
			} else {
				return "0";
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
	 * 用于组织机构多选
	 * @return
	 */
	@Override
	public String findDeptsJson() {
		try{
			//查询现有的组织机构
			List<SysOrganization> list = organizationDao.findAll();
			StringBuffer str = new StringBuffer("");
			if (list != null && list.size() > 0) {
				str.append("[");
				// 次节点下的隐藏
				String sign = "";
				String ischeck = "";
				for (int i = 0; i < list.size(); i++) {
					sign = "click:\"getOneDeptInfo('" + list.get(i).getId() + "','"+list.get(i).getOrgName()+"')\",icon:\"images/tree/file.gif\"";				

					str.append("{id:\"").append(list.get(i).getId()).append("\", pId:\"")
					.append(list.get(i).getParentOrgid()).append("\", name:\"")
					.append(list.get(i).getOrgName()).append("\" ,").append(sign).append(", open : true },");
				}
			} else {
				return "0";
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
	 * 查询所有机构下的人员，人员未关联机构的不显示
	 * @return
	 */
	@Override
	public List<Object[]> findUsers() {		
		List<Object[]> list = organizationDao.findUsers();
		return list;				
	}


	/**
	 * 查询所有流程人员
	 * @return
	 */
	@Override
	public List<Object[]> findProcessUsers() {		
		List<Object[]> list = organizationDao.findProcessUsers();
		return list;
	}

	/**
	 * 单个组织机构信息
	 * @param id
	 * @return
	 */
	@Override
	public SysOrganization showOneOrg(int id){
		try {
			SysOrganization oneorg =  organizationDao.getEntity(id);
			return oneorg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 保存/编辑组织机构
	 * @param list
	 * @return
	 */
	@Override
	public Object saveOrg(List<SysOrganization> list){
		try{
			SysOrganization orginfo = new SysOrganization();
			orginfo = list.get(0);		

			if("".equals(orginfo.getOrgnamesPath())){
				orginfo.setOrgnamesPath("组织机构");
			}		
			if(!orginfo.getOrgnamesPath().contains(String.valueOf(orginfo.getOrgName())+"-")){
				orginfo.setOrgnamesPath(orginfo.getOrgName()+"-"+orginfo.getOrgnamesPath());
			}		
			if("".equals(orginfo.getOrgidsPath())){
				orginfo.setOrgidsPath("64");
			}
			if(orginfo.getOrgType()==2){//机构
				if("".equals(orginfo.getDeptnamesPath())){
					orginfo.setDeptnamesPath("组织机构");
				}		
				if(!orginfo.getDeptnamesPath().contains(String.valueOf(orginfo.getOrgName())+"-")){
					orginfo.setDeptnamesPath(orginfo.getOrgName()+"-"+orginfo.getDeptnamesPath());
				}		
				if("".equals(orginfo.getDeptidsPath())){
					orginfo.setDeptidsPath("64");
				}
			}

			Object orgid = orginfo.getId();
			String status="";//操作状态
			if(orginfo.getId()==0){
				orgid = organizationDao.saveOrg(orginfo);			
				//新增
				organizationDao.saveGroup(orgid.toString(),orginfo.getOrgName());

				status="add";
			}else{
				if(!orginfo.getOrgidsPath().contains(String.valueOf(orginfo.getId())+"-")){
					orginfo.setOrgidsPath(orginfo.getId()+"-"+orginfo.getOrgidsPath());
				}
				if(orginfo.getOrgType()==2&&!orginfo.getDeptidsPath().contains(String.valueOf(orginfo.getId())+"-")){
					orginfo.setDeptidsPath(orginfo.getId()+"-"+orginfo.getDeptidsPath());
				}
				organizationDao.update(orginfo);
				status="update";
			}
			return orgid+"_"+status;
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}



	/**
	 * 删除组织机构
	 * @param id
	 * @return
	 */
	@Override
	public String delOrg(int id){
		try {
			List list = organizationDao.findOrgByParentorgid(String.valueOf(id) );

			List list2 = organizationDao.findOrgByParentdeptid(String.valueOf(id) );
			if((list!=null&&list.size()>0)||(list2!=null&&list2.size()>0)){
				//存在下级
				return "2";
			}else{
				organizationDao.delGroup(id);
				
				organizationDao.deleteEntity(id);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * 获取部门下的人员
	 * @param orgid
	 * @return
	 */
	@Override
	public String showUserOfOrg(int orgid){
		try {
			List<Object[]> list = organizationDao.findUserByOrgid(orgid);

			StringBuffer str = new StringBuffer("[");
			for(int i=0;i<list.size();i++){
				str.append("{id:").append(list.get(i)[0]).append(",name:'").append(list.get(i)[1]).append("' },");
			}
			String s = str.toString();
			if (!s.equals("[")) {
				s = s.substring(0, s.length() - 1) + "]";
			}else{
				s=s+"]";
			}
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getANode(){
		List ObjList = organizationDao.getANode();
		return GenericManagerImpl.gsonStr(ObjList);
		
	}

	@Override
	public String getChildNode(String nodeId) {
		List ObjList = organizationDao.getChildNode(nodeId);
		return GenericManagerImpl.gsonStr(ObjList);
	}

	@Override
	public String showOrgJson(String id) {
		if(id==null){
			//id="first";
			id= "1947308";//"1947308";//南通市
		}		
		List<SysOrganization> list = OrginfoCache.getChildOrgList(id);
		if(list!=null){		
		StringBuffer str = new StringBuffer();	
		str.append("[");
		for(int i=0;i<list.size();i++){				
			String state = "open";
			List<SysOrganization> list2 = OrginfoCache.getChildOrgList(list.get(i).getId().toString());
			if(list2!=null&&list2.size()>0){
				state = "closed";
			}
			str.append("{\"id\":\"").append(list.get(i).getId()).append("\",\"orgName\":\"").append(list.get(i).getOrgName()==null?"":list.get(i).getOrgName());	
			if(list.get(i).getParentOrgid()==null){
				str.append("\",\"parentOrgid\":\"").append("-1"); 
			}else{
				str.append("\",\"parentOrgid\":\"").append(list.get(i).getParentOrgid()==null?"":list.get(i).getParentOrgid());
			}
			str.append("\",\"orgType\":\"").append(list.get(i).getOrgType()==null?"":list.get(i).getOrgType())
			.append("\",\"orgCode\":\"").append(list.get(i).getOrgCode()==null?"":list.get(i).getOrgCode())
			.append("\",\"parentDeptid\":\"").append(list.get(i).getParentDeptid()==null?"":list.get(i).getParentDeptid())
			.append("\",\"parentDeptname\":\"").append(list.get(i).getParentDeptname()==null?"":list.get(i).getParentDeptname())
			.append("\",\"state\":\""+state+"\" },");		
		}
		if(!"[".equals(str.toString())){
			str.delete(str.toString().length()-1, str.toString().length());
		}
			str.append("]");
		return str.toString();
		}else{
			return "[]";
		}
	}

	
	
	@Override
	public List<Object[]> checkOrgCode(String id, String orgCode) {
		return organizationDao.checkOrgCode(id,orgCode);
	}

	@Override
	@MethodLog2(remark="编辑组织机构信息",type="编辑")
	public SysOrganization saveOrg(SysOrganization org) {
		//保存数据库
		if(org.getParentDeptid()!=null&&!"".equals(org.getParentDeptid())){
			SysOrganization sysorg = organizationDao.getEntity(Integer.parseInt(org.getParentDeptid()));
			org.setParentDeptname(sysorg.getOrgName());
		}
		organizationDao.saveOrUpdateResource(org);
		
		//保存缓存
		OrginfoCache.changeList(org);
		
		return org;
	}

	@Override
	public List<Object[]> checkChildOrgData(String id) {
		return organizationDao.checkChildOrgData(id);
	}

	@Override
	@MethodLog2(remark="删除组织机构信息",type="删除")
	public void deleleOrg(String id) {
		//更新缓存
		SysOrganization org = organizationDao.getEntity(Integer.parseInt(id));		
		OrginfoCache.removeList(org);
		//删除数据
		organizationDao.deleleOrg(id);
		
	}
	
	
	@Override
	public String findDeptJsonEasyui(String id,String selectid) {
		try{
			//查询2级数据id下的item
			Map<String,String> map =OrginfoCache.getCodeMapByType(id);

			StringBuffer str = new StringBuffer("");
			if (map != null && map.size() > 0) {
				str.append("[");
				
				// 次节点下的隐藏
				String state = "open";
				for (Map.Entry entry : map.entrySet()){	
					
					str.append("{\"id\":\"").append(entry.getKey()).append("\", \"text\":\"")
					.append(entry.getValue()).append("\" ");
					if(OrginfoCache.getCodeMapByType((String)entry.getKey()).size()>0){
						//存在下级
						state="closed";
					}
					if(id!=null&&(","+selectid+",").indexOf(entry.getKey().toString())>-1){
						str.append(", \"checked\" : true ");
					}
					str.append(", \"state\" : \"").append(state).append("\" },");
					
				}
			} else {
				return "0";
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
	
	
	
	
	@Override
	public String findOrgCodeJsonEasyui(String code,String selectstr) {
		try{
			//code --> id
			//String orgid = organizationDao.findOrgidByCode(code);
			
			//查询2级数据id下的item
			Map<String,String> map =new HashMap<String, String>(); //OrginfoCache.getCodeMapByType2(orgid);
			map.put(code, OrginfoCache.findOrgNameByCode(code)); //自身

			StringBuffer str = new StringBuffer("");
			if (map != null && map.size() > 0) {
				str.append("[");
				// 次节点下的隐藏
				String state = "open";
				for (Map.Entry entry : map.entrySet()){						
					str.append("{\"id\":\"").append(entry.getKey()).append("\", \"text\":\"")
					.append(entry.getValue()).append("\" ");
					if(selectstr!=null&&!"".equals(selectstr)&&selectstr.indexOf(entry.getKey().toString())>-1){
						str.append(", \"checked\" : \"true\" ");
					}
					if(OrginfoCache.getCodeMapByType(OrginfoCache.findOrgidByCode((String)entry.getKey())).size()>0){
						//存在下级
						str.append(", \"state\" : \"").append(state).append("\" ");						
						Map<String,String>  map2 = OrginfoCache.getCodeMapByType2(OrginfoCache.findOrgidByCode((String)entry.getKey()));
						str.append(",\"children\":").append(aaa(map2,selectstr));
					}					
					str.append(" },");					
				}
			} else {
				return "0";
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
	
	
	
	public String aaa(Map<String,String> map,String selectstr){
		StringBuffer str = new StringBuffer("");
		if (map != null && map.size() > 0) {
			str.append("[");
			// 次节点下的隐藏
			String state = "closed";
			for (Map.Entry entry : map.entrySet()){						
				str.append("{\"id\":\"").append(entry.getKey()).append("\", \"text\":\"")
				.append(entry.getValue()).append("\" ");
				if(selectstr!=null&&!"".equals(selectstr)&&selectstr.indexOf(entry.getKey().toString())>-1){						
					str.append(", \"checked\" : \"true\" ");
				}
				if(OrginfoCache.getCodeMapByType(OrginfoCache.findOrgidByCode((String)entry.getKey())).size()>0){
					str.append(", \"state\" : \"").append(state).append("\" ");					
					//存在下级
					Map<String,String>  map2 = OrginfoCache.getCodeMapByType2(OrginfoCache.findOrgidByCode((String)entry.getKey()));
					str.append(",\"children\":").append(aaa(map2,selectstr));
				}
				str.append(" },");					
			}
		} else {
			return "0";
		}
		String s = str.toString();
		if (!s.equals("")) {
			s = s.substring(0, s.length() - 1) + "]";
		}
		return s;
	}
	
	
	

	@Override
	public List<SysOrganization> findDeptJsonEasyui2(String id) {
		return OrginfoCache.getChildOrgList(id);
	}
	
	
	/**
	 * 获取查询的人员（选择人员页面）
	 * @param orgid
	 * @return
	 */
	@Override
	public String showUserOfSearchname(String searchname){
		try {
			List<Object[]> list = organizationDao.findUserBySearchname(searchname);

			StringBuffer str = new StringBuffer("[");
			for(int i=0;i<list.size();i++){
				str.append("{id:").append(list.get(i)[0]).append(",name:'").append(list.get(i)[1]).append("' },");
			}
			String s = str.toString();
			if (!s.equals("[")) {
				s = s.substring(0, s.length() - 1) + "]";
			}else{
				s=s+"]";
			}
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return "[]";
		}
	}
	
	/**
	 * 依据机构id查询同级部门
	 */
	@Override
	public List<Object[]> findDept(String orgid) {
		
		return null;
	}

	@Override
	public String findDeptsByOrgidJson(List<Object []> list, String selectstr) {
		if(list!=null&&list.size()>0){
			StringBuffer str = new StringBuffer("[");
			for(int i=0;i<list.size();i++){
				str.append("{\"id\":\"").append(list.get(i)[2]).append("\", \"text\":\"")
				.append(list.get(i)[1]).append("\" ");				
				str.append("},");
			}
			 str.delete(str.toString().length()-1, str.toString().length());
			 str.append("]");
			return str.toString();
		}else{
			return "[]";
		}
	}

	

	public static void main(String[] args) {
		String str = "MC320685400000000, MC320685103000000";
		if(str.indexOf("MC320685103000000")>-1){
			System.out.println("1111111111");
		}else{
			System.out.println("2222222222");
		}
	}

	@Override
	public String getOrgDeptName(String orgCode, String deptCode) {		
		return OrginfoCache.findOrgNameByCode(orgCode)+ResourceCodeUtil.getDictNameByCode(deptCode);
	}

	
	@Override
	public String searchoorg(String selectword) {
		List<Object[]> list = organizationDao.searchoorg(selectword);
		StringBuffer str = new StringBuffer("[");
		for(int i=0;i<list.size();i++){
			str.append("{\"id\":\"").append(list.get(i)[0]).append("\"},");
		}
		if(!"[".equals(str.toString())){
			str.delete(str.toString().length()-1, str.toString().length());
		}
		 str.append("]");
		return str.toString();		
	}

	
}
