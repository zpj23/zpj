package com.goldenweb.sys.service.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.annotations.RemoteMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.goldenweb.sys.dao.MenuConfigHibernate;
import com.goldenweb.sys.dao.OrganizationHibernate;
import com.goldenweb.sys.dao.ResourceItemHibernate;
import com.goldenweb.sys.dao.UserinfoHibernate;
import com.goldenweb.sys.pojo.SysFunction;
import com.goldenweb.sys.pojo.SysOrganization;
import com.goldenweb.sys.pojo.SysResourceItem;
import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.service.UserinfoService;
import com.goldenweb.sys.util.DateHelper;
import com.goldenweb.sys.util.LogUtil;
import com.goldenweb.fxpg.comm.BaseService;
import com.goldenweb.fxpg.frame.tools.MD5;

/**
 * 用户管理类
 * 
 */
@Service
@Component("userinfoService")
public class UserinfoServiceImpl extends BaseService<SysUserinfo> implements UserinfoService{
	@Autowired
	private UserinfoHibernate userinfoDao;
	@Autowired
	private OrganizationHibernate organizationDao;	
	@Autowired
	private MenuConfigHibernate menuConfigDao;
	@Autowired
	private ResourceItemHibernate itemDao;
	
	public UserinfoServiceImpl() {
		super();
	}	
	
	/********************************************************************************/

	@Override
	public List<?> used(String userid,String url) {
		/*String hql="select ur.userinfo.userid,re.name,re.url from Userrole ur,Role r,Rolerights rr,Rights ri,Rightsresources rs,Resources re "+
			"where ur.role.id=r.id and r.id=rr.role.id and rr.rights.id=ri.id and ri.id=rs.rights.id and rs.resources.id=re.id and "+
			"ur.userinfo.userid=? and re.url=?";
		List<?> userResource = userinfoDao.findByHql(hql,userid,url);
		return userResource;*/
		return null;
	}


	/**
	 * 查询该用户是否存在
	 */
	@Override
	public SysUserinfo findLogin(SysUserinfo user) {		
		try {
			List<Object[]> list = userinfoDao.findLogin(user.getLoginname().trim(),user.getPwd());
			if(list!=null&&list.size()>0){
				SysUserinfo sui = new SysUserinfo();
				sui.setId(Integer.parseInt(list.get(0)[0].toString()));	//用户id			
				if(list.get(0)[1]!=null){//用户名
					sui.setUsername(list.get(0)[1].toString());		
				}
				sui.setIsAdmin(Integer.parseInt(list.get(0)[2].toString()));
				if(list.get(0)[3]!=null){//机构id
					sui.setMainOrgid(Integer.parseInt(list.get(0)[3].toString()));	
				}
				if(list.get(0)[4]!=null){//机构名称
					sui.setMainOrgname(list.get(0)[4].toString());
				}
				if(list.get(0)[5]!=null){//登陆名
					sui.setLoginname(list.get(0)[5].toString());
				}
				if(list.get(0)[6]!=null){//头像路径
					sui.setImg(list.get(0)[6].toString());
				}
				if(list.get(0)[7]!=null){//部门编码
					sui.setDeptcode(list.get(0)[7].toString());
				}
				if(list.get(0)[8]!=null){//部门名称
					sui.setDeptname(list.get(0)[8].toString());
				}
				if(list.get(0)[9]!=null){//机构编码
					sui.setMainOrgcode(list.get(0)[9].toString());
				}
				if(list.get(0)[10]!=null){//电话
					sui.setPhone(list.get(0)[10].toString());
				}
				if(list.get(0)[11]!=null){//邮件
					sui.setEmail(list.get(0)[11].toString());
				}
				if(list.get(0)[12]!=null){//地址
					sui.setAddress(list.get(0)[12].toString());
				}
				if(list.get(0)[13]!=null){//备注
					sui.setRemark(list.get(0)[13].toString());
				}
				if(list.get(0)[14]!=null){//是否流程用户
					sui.setIsProcess(list.get(0)[14].toString());
				}
				if(list.get(0)[15]!=null){//排序
					sui.setNum(Integer.parseInt(list.get(0)[15].toString()));
				}
				if(list.get(0)[16]!=null){//第3方机构id
					sui.setThirdorgid(list.get(0)[16].toString());
				}
				if(list.get(0)[17]!=null){//第3方机构名称
					sui.setThirdorgname(list.get(0)[17].toString());
				}				
				sui.setPwd(user.getPwd());
				return sui;
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 人员列表
	 * @param request
	 * @param selectName
	 * @param selectWeb
	 * @param page
	 * @param pageSize
	 */
	@Override
	public void findUserinfoList(HttpServletRequest request, String selectName,
			String page, int pageSize) {

		StringBuffer hql = new StringBuffer();
		hql.append("select id,loginname,username,isWebuser, "
				+ " isDel,mainOrgname,phone,email from SysUserinfo  where isDel = 0 ");
		if (null != selectName && !"".equals(selectName.trim())) {
			hql.append(" and (loginname like '%" + selectName + "%' or ")
			.append(" username like '%" + selectName.trim() + "%') ");
		}		
		hql.append(" order by num ");

		// 传递分页数据
		userinfoDao.setPageData(request, hql.toString(), null, page, pageSize);
	}


	/**
	 * 新增用户
	 * @param userinfo
	 */
	@Override
	@MethodLog2(remark="编辑用户信息",type="编辑")
	public void saveUser(SysUserinfo userinfo) {
		try {
			userinfo.setIsDel(0);
			
			Integer id;
			if(userinfo.getId()==null){
				//新增
				String pwd =  userinfo.getLoginname()+"{"+userinfo.getPwd()+"}";  //加密规则 ：  用户名{密码}
				pwd = MD5.md5s(pwd); // 密码加密
				userinfo.setPwd(pwd);
				id = userinfoDao.saveUserinfo(userinfo);
			}else{
				//编辑
				userinfoDao.update(userinfo);
				id = userinfo.getId();
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	  
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface MyAnnotation4Method { 
	        public String msg1(); 

	        public String msg2(); 
	} 

	
	
	/**
	 * 依据id查询出单个用户实体类
	 * @param id
	 * @return
	 */
	@Override
	public SysUserinfo findOneUserInfo(int id) {
		try {
			return userinfoDao.getEntity(id);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 删除
	 * @param id
	 */
	@Override
	@MethodLog2(remark="删除用户信息",type="删除")
	public void delUserInfo(String id) {
		
		//删除用户表数据
		userinfoDao.delUserInfo(id);
		//删除用户角色关联数据
		userinfoDao.delUserRoleLinkData(id);
		//删除用户在权限配置表中的信息
		menuConfigDao.del(id);
		
	}
	

	/**
	 * 通过人员id查询其角色
	 * @param id
	 * @return
	 */
	@Override
	public List<Object[]> findRolesByUserid(Integer userid) {		
		List<Object[]> list = userinfoDao.findRolesByUserid(userid);
		return list; 
	}


	/**
	 * 查询人员可见的功能菜单list
	 * @param id
	 * @return
	 */
	@Override
	public List<SysFunction> findFunctionByUserid(Integer userid) {		
		List<Object[]> list = userinfoDao.findFunctionByUserid(userid);
		if(list!=null&&list.size()>0){		
		SysFunction sf =null;String url="";
		List<SysFunction> lt = new ArrayList<SysFunction>();
		for(int i=0;i<list.size();i++){
			sf = new SysFunction();
			sf.setId(Integer.parseInt(list.get(i)[0].toString()));
			sf.setTitle(list.get(i)[1].toString());
			if(list.get(i)[2]!=null){
			sf.setUrl(list.get(i)[2].toString());
			}
			sf.setParentFunid(Integer.parseInt(list.get(i)[3].toString()));
			if(list.get(i)[2]==null){
				 url = "";
			 }else{
			     url = list.get(i)[2].toString();
			     if(url.indexOf("_")>-1){
			     url=url.substring(0,url.indexOf("_"));
			     }
			 }
			sf.setUrlPurview(url);
			sf.setPicture(list.get(i)[5]==null?"":list.get(i)[5].toString());
			sf.setIsMenu(Integer.parseInt(list.get(i)[6].toString()));
			lt.add(sf);
		}
		return lt; 
		}else{
			return null;
		}
	}


	/**
	 * 依据角色code查询人员
	 * @param string
	 * @return
	 */
	@Override
	public List<Object[]> findUserByRolecode(String code) {		
		List<Object[]> list = userinfoDao.findUserByRolecode(code);
		return list;
	}


	/**
	 * 更改当前登录人密码
	 * @param id
	 * @param pwd1
	 */
	@Override
	@MethodLog2(remark="编辑用户密码",type="编辑")
	public void updatePwd(Integer id, String pwd) {
		userinfoDao.updatePwd(id,pwd);
	}


	/**
	 *  权限内可见的人员ids  (str)
	 * @param userid
	 * @return
	 */
	@Override
	public String findSeeString(Integer userid){
		try {
			List list = findSeeList(userid);
			String str="(";
			if(list!=null){
				for(int i=0;i<list.size();i++){
					str=str+list.get(i)+",";
				}
				str=str.substring(0,str.length()-1);
				str=str+")";
			}	
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *  权限内可见的人员ids (list)
	 * @param userid
	 * @return
	 */
	@Override
	public List findSeeList(Integer userid){
		List useridList = new ArrayList();
		try {			
			List<Object[]> rolecodeList = userinfoDao.findRoleByUserid(userid);
			if(rolecodeList!=null&&rolecodeList.size()>0){
				//只针对市场，民生，技术，实施，企划5个部门				
				for(int i=0;i<rolecodeList.size();i++){
					if("SCBFZR".equals(rolecodeList.get(i)[0])){
						//市场部负责人，可对市场部用户可见。同级不可见
						List<Object[]> list = userinfoDao.findUserRoleByRolecode("SCBYH");
						if(list!=null&&list.size()>0){
							for(int j=0;j<list.size();j++){
								useridList.add(list.get(j)[0]);
							}
						}
					}
					if("MSBFZR".equals(rolecodeList.get(i)[0])){
						//民生部负责人，可对民生部用户可见。同级不可见
						List<Object[]> list = userinfoDao.findUserRoleByRolecode("MSBYH");
						if(list!=null&&list.size()>0){
							for(int j=0;j<list.size();j++){
								useridList.add(list.get(j)[0]);
							}
						}
					}
					if("JSBFZR".equals(rolecodeList.get(i)[0])){
						//技术部负责人，可对技术部用户可见。同级不可见
						List<Object[]> list = userinfoDao.findUserRoleByRolecode("JSBYH");
						if(list!=null&&list.size()>0){
							for(int j=0;j<list.size();j++){
								useridList.add(list.get(j)[0]);
							}
						}
					}
					if("SSBFZR".equals(rolecodeList.get(i)[0])){
						//实施部负责人，可对实施部用户可见。同级不可见
						List<Object[]> list = userinfoDao.findUserRoleByRolecode("SSBYH");
						if(list!=null&&list.size()>0){
							for(int j=0;j<list.size();j++){
								useridList.add(list.get(j)[0]);
							}
						}
					}
					if("QHBFZR".equals(rolecodeList.get(i)[0])){
						//企划部负责人，可对企划部用户可见。同级不可见
						List<Object[]> list = userinfoDao.findUserRoleByRolecode("QHBYH");
						if(list!=null&&list.size()>0){
							for(int j=0;j<list.size();j++){
								useridList.add(list.get(j)[0]);
							}
						}
					}
				}			

			}
			useridList.add(userid);
			return useridList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 编辑上传头像
	 * @param id
	 * @param imgpath
	 */
	@Override
	public void updateImg(Integer id, String imgpath) {
		userinfoDao.updateImg(id,imgpath);
	}


	/**
	 * 验证登陆
	 * @param username
	 * @param pwd
	 * @param request
	 * @return
	 */
	@Override
	@RemoteMethod
	public String checkLogin(String username,String pwd,HttpServletRequest request){
		SysUserinfo iuserinfo = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
		if(iuserinfo!=null){
			username=iuserinfo.getLoginname();
			pwd=iuserinfo.getPwd();
		}

		SysUserinfo user=new SysUserinfo();
		user.setLoginname(username);
		user.setPwd(pwd);
		user = findLogin(user);		     
		if(user==null){			
			return  "0_"+username;
		}else{		
			//人员与角色的关系
			List<Object[]> userRoleList = findRolesByUserid(user.getId());
			user.setUserRoleList(userRoleList);

			//人员id,姓名,是否管理员,所在组织机构在user中有实际意义
			request.getSession().setAttribute("iuserinfo",user);

			StringBuffer str = new StringBuffer("{");
			str.append("\"status\":\"1\",\"username\":\"").append(user.getUsername()).append("\",")
			.append("\"img\":\"").append(user.getImg()).append("\",")
			.append("\"loginname\":\"").append(user.getLoginname()).append("\",");

			//角色
			String rolecodes="";
			if(userRoleList!=null&&userRoleList.size()>0){
				for(int i=0;i<userRoleList.size();i++){
					rolecodes+=userRoleList.get(i)[2]+",";
				}
			}
			str.append("\"rolecodes\":\"").append(rolecodes).append("\",");

			str.append("\"deptname\":\"").append(user.getMainOrgname()).append("\"}");
			if(iuserinfo==null){
				//记录登陆日志	
				LogUtil.logInfo("登陆", user.getUsername()+"于"+DateHelper.getToday("yyyy-MM-dd HH:mm:ss")+"成功登陆系统", user.getUsername(), user.getId());
			}
			return  str.toString();
		}
	}


	@Override
	@RemoteMethod
	public String checkSeesionExists(HttpServletRequest request){
		SysUserinfo iuserinfo = (SysUserinfo) request.getSession().getAttribute("iuserinfo");
		if(iuserinfo==null){
			return "0";
		}else{
			return "1";
		}
	}
	
	/**
	 * 通过主键获取实体类信息
	 */
	@Override
	public SysUserinfo get(Integer id) {		
		try {
			return userinfoDao.getEntity(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String findUserinfoJson(String selectName,String useroforg,String userofdept,int page,int rows) {
		
		//将部门id转换成code
		if(userofdept!=null&&!"".equals(userofdept)){
			SysResourceItem item = itemDao.getEntity(Integer.parseInt(userofdept));
			if(item!=null){
				userofdept = item.getItemCode();
			}
			if("2465131".equals(userofdept)){// 数据类型部门数据的id
				userofdept = null;
			}
		}
		
		List<Object[]> list = userinfoDao.findUserinfoList(selectName,useroforg,userofdept,page,rows);
		int countNumber  = userinfoDao.findCountNumber(selectName,useroforg,userofdept);
		if(list!=null&&list.size()>0){
		  StringBuffer str =new StringBuffer();
		  str.append("{\"total\":\"").append(countNumber).append("\",\"rows\":[");
		  for(int i=0;i<list.size();i++){
			  str.append("{");
			  str.append("\"id\":\"").append(list.get(i)[0]).append("\",");
			  str.append("\"username\":\"").append(list.get(i)[1]==null?"":list.get(i)[1]).append("\",");
			  str.append("\"loginname\":\"").append(list.get(i)[2]==null?"":list.get(i)[2]).append("\",");
			  str.append("\"email\":\"").append(list.get(i)[3]==null?"":list.get(i)[3]).append("\",");
			  str.append("\"phone\":\"").append(list.get(i)[4]==null?"":list.get(i)[4]).append("\",");
			  str.append("\"mainOrgname\":\"").append(list.get(i)[5]==null?"":list.get(i)[5]).append("\",");
			  str.append("\"deptname\":\"").append(list.get(i)[6]==null?"":list.get(i)[6]).append("\",");
			  List<Object[]> rolelist = userinfoDao.findRolesByUserid(Integer.valueOf(list.get(i)[0].toString()));
			  String roles="";
			  if(rolelist!=null){
				  for(int s=0;s<rolelist.size();s++){
					  roles += rolelist.get(s)[1]+",";
				  }
				  if(!"".equals(roles)){
					  roles = roles.substring(0,roles.length()-1);
				  }
			  }
			  str.append("\"roles\":\"").append(roles).append("\",");
			  str.append("\"isDel\":\"").append(list.get(i)[7]).append("\"");
			  str.append("},");			  
		  }
		  str.delete(str.toString().length()-1, str.toString().length());
		  str.append("]}");
		  return str.toString();
		}
		return "[]";
	}

	@Override
	public List<Object[]> checkSameUser(String userid, String loginname) {		
		return userinfoDao.checkSameUser(userid,loginname);
	}

	
	/**
	 * 获取当前用户的同级部门(所有)
	 */
	@Override
	public List<Object[]> findSameLvDept(Integer userid) {		
		List<Object[]> list = userinfoDao.findOrgCodeByUserid(userid);
		if(list.get(0)[0]!=null){
			String orgcode = list.get(0)[0].toString();
			// orgcode 转换成部门的级别
			Integer deptLv = this.toDeptLv(orgcode);
			List<Object[]> deptList = userinfoDao.findSameLvDept(deptLv);
			return deptList;
		}else{
			return null;
		}		
	}
	
	
	/**
	 * 获取当前用户的同级部门(不包括本用户所在部门)
	 */
	@Override
	public List<Object[]> findSameLvDeptNoSelf(Integer userid,String deptcodeself) {		
		List<Object[]> list = userinfoDao.findOrgCodeByUserid(userid);
		if(list.get(0)[0]!=null){
			String orgcode = list.get(0)[0].toString();
			// orgcode 转换成部门的级别
			Integer deptLv = this.toDeptLv(orgcode);
			List<Object[]> deptList = userinfoDao.findSameLvDeptNoSelf(deptLv,deptcodeself);
			return deptList;
		}else{
			return null;
		}		
	}
	
	
	@Override
	public List<Object[]> findDeptByOrgid(Integer orgid) {		
		SysOrganization org = organizationDao.getEntity(orgid);
		if(org!=null){
			String orgcode =org.getOrgCode();
			// orgcode 转换成部门的级别
			Integer deptLv = this.toDeptLv(orgcode);
			List<Object[]> deptList = userinfoDao.findSameLvDept(deptLv);
			return deptList;
		}else{
			return null;
		}		
	}
	
	@Override
	public List<Object[]> findDeptByOrgcode(String orgcode) {	
			// orgcode 转换成部门的级别
			Integer deptLv = this.toDeptLv(orgcode);
			List<Object[]> deptList = userinfoDao.findSameLvDept(deptLv);
			return deptList;				
	}

	@Override
	public Integer toDeptLv(String orgcode) {
		if(orgcode!=null){
			//机构code组成方式 MC2+2+2+3+3+3共17位
			//从后向前依次截取
			String a = "";
			//网格
			a =orgcode.substring(14,orgcode.length());
			if(!"000".equals(a)){
				return 18;
			}
			//村
			a =orgcode.substring(11,14);
			if(!"000".equals(a)){
				return 16;
			}
			//镇
			a =orgcode.substring(8,11);
			if(!"000".equals(a)){
				return 14;
			}
			//区
			a =orgcode.substring(6,8);
			if(!"00".equals(a)){
				return 12;
			}
			//市
			a =orgcode.substring(4,6);
			if(!"00".equals(a)){
				return 10;
			}
			//省
			a =orgcode.substring(2,4);
			if(!"00".equals(a)){
				return 8;
			}
		}
		return 0;
	}
	
	
	private Integer toChildDeptLv(String orgcode) {
		//机构code组成方式 MC2+2+2+3+3+3共17位
		//从后向前依次截取
		String a = "";
		//网格
		a =orgcode.substring(14,orgcode.length());
		if(!"000".equals(a)){
			return 20;
		}
		//村
		a =orgcode.substring(11,14);
		if(!"000".equals(a)){
			return 18;
		}
		//镇
		a =orgcode.substring(8,11);
		if(!"000".equals(a)){
			return 16;
		}
		//区
		a =orgcode.substring(6,8);
		if(!"00".equals(a)){
			return 14;
		}
		//市
		a =orgcode.substring(4,6);
		if(!"00".equals(a)){
			return 12;
		}
		//省
		a =orgcode.substring(2,4);
		if(!"00".equals(a)){
			return 10;
		}
		return null;
	}

	/**
	 * 获取当前用户的下级机构
	 */
	@Override
	public List<Object[]> findChindOrg(Integer userid) {
		List<Object[]> list = userinfoDao.findOrgCodeByUserid(userid);
		if(list.get(0)[0]!=null){
			String orgcode = list.get(0)[0].toString();
			// orgcode 转换成部门的级别
			String connstr = this.toSelectConn(orgcode);
			List<Object[]> orgList = userinfoDao.findChindOrg(connstr);
			return orgList;
		}else{
			return null;
		}
		
		
	}
	
	
	
    private String toSelectConn(String orgcode) {
    	//从后向前依次截取
		String a = "";
		//网格无下级不做考虑
		//村
		a =orgcode.substring(11,14);
		if(!"000".equals(a)){
			return orgcode.substring(0,14)+"___";
		}
		//镇
		a =orgcode.substring(8,11);
		if(!"000".equals(a)){
			return orgcode.substring(0,11)+"___"+"000";
		}
		//区
		a =orgcode.substring(6,8);
		if(!"00".equals(a)){
			return orgcode.substring(0,8)+"___"+"000000";
		}
		//市
		a =orgcode.substring(4,6);
		if(!"00".equals(a)){
			return orgcode.substring(0,6)+"__"+"000000000";
		}
		//省
		a =orgcode.substring(2,4);
		if(!"00".equals(a)){
			return orgcode.substring(0,4)+"__"+"00000000000";
		}
		return null;
	}

	

	@Override
	public String findDeptDataJson(String orgid) {
		List<Object[]> list = findDeptByOrgid(Integer.parseInt(orgid));
		if(list!=null&&list.size()>0){
			StringBuffer str = new StringBuffer("[");
			for(int i=0;i<list.size();i++){
				str.append("{code:\"").append(list.get(i)[2]).append("\", name:\"")
				.append(list.get(i)[1]).append("\" },");	
			}
			 str.delete(str.toString().length()-1, str.toString().length());
			 str.append("]");
			return str.toString();
		}else{
			return "[]";
		}
	}

	/**
	 * 改变用户的状态
	 */
	@Override
	public String updateUserStatus(String statusflag, String userid) {
		userinfoDao.updateUserStatus(statusflag,userid);
		if("1".equals(statusflag)){
			return "0";
		}else{
			return "1";
		}
	}

	@Override
	public String findUserRole(String userid) {
		List<Object[]> list = userinfoDao.findUserRole(userid);
		if(list!=null&&list.size()>0){
			StringBuffer str = new StringBuffer("");
			str.append("{\"total\":\"").append(10).append("\",\"rows\":[");
			for(int i=0;i<list.size();i++){
				str.append("{\"rolename\":\"").append(list.get(i)[0]).append("\", \"rolename\":\"")
				.append(list.get(i)[1]==null?"":list.get(i)[1]).append("\" },");	
			}
			 str.delete(str.toString().length()-1, str.toString().length());
			 str.append("]}");
			return str.toString();
		}else{
			return "[]";
		}
	}

	/**
	 * 下级机构的同级部门
	 */
	@Override
	public List<Object[]> findChildLvDept(Integer userid) {
		List<Object[]> list = userinfoDao.findOrgCodeByUserid(userid);
		if(list.get(0)[0]!=null){
			String orgcode = list.get(0)[0].toString();
			// orgcode 转换成部门的级别
			Integer deptLv = this.toChildDeptLv(orgcode);
			List<Object[]> deptList = userinfoDao.findSameLvDept(deptLv);
			return deptList;
		}else{
			return null;
		}	
	}

	
	@Override
	public List<SysUserinfo> findUsersWithCondition(String orgcode,
			String deptcode, String rolecode) {
		return userinfoDao.findUsersWithCondition(orgcode,deptcode,rolecode);
	}
	
	

	@Override
	public String findUserIdsWithCondition(String orgcode, String deptcode,
			String rolecode) {
		String retStr = "";
		List<SysUserinfo> users = this.findUsersWithCondition(orgcode, deptcode, rolecode);
		for(SysUserinfo user : users){
			retStr += user.getId()+",";
		}
		if(retStr.endsWith(","))
			retStr = retStr.substring(0,retStr.length()-1);
		return retStr;
	}

	
	@Override
	public List<Object[]> findThirdorglist() {		
		return userinfoDao.findThirdorglist();
	}

	
	@Override
	public String findThirdorgjson() {
		List<Object[]> list = findThirdorglist();
		StringBuffer str = new StringBuffer("[");
		for(int i=0;i<list.size();i++){
			str.append("{\"id\":\"").append(list.get(i)[0]).append("\",\"text\":\"")
			.append(list.get(i)[1]).append("\"},");
		}
		if(!"[".equals(str.toString())){
		 str.delete(str.toString().length()-1, str.toString().length());
		}
		str.append("]");
		return str.toString();
	}

	
	@Override
	public String findUserIdsWithCondition(String thirdorgid,String rolecode) {
		String retStr = "";
		List<SysUserinfo> users = this.findUsersWithCondition(thirdorgid, rolecode);
		for(SysUserinfo user : users){
			retStr += user.getId()+",";
		}
		if(retStr.endsWith(","))
			retStr = retStr.substring(0,retStr.length()-1);
		return retStr;
	}

	@Override
	public List<SysUserinfo> findUsersWithCondition(String thirdorgid, String rolecode) {
		return userinfoDao.findUsersWithCondition(thirdorgid,rolecode);
	}
	
	@Override
	public String findThirdOrgnameById(String thirdorgid) {
		List<Object[]> list = userinfoDao.fingThirdOrgnameById(thirdorgid);
		if(list!=null&&list.size()>0){
			return list.get(0)[0].toString();
		}
		return "";
	}
	
	
	public String findUserIdsForHnlplbBak(String orgcode, String deptcode,
			String rolecode) {
		String retStr = "";
		List<SysUserinfo> users = userinfoDao.findUserIdsForHnlplb(orgcode, deptcode, rolecode);
		for(SysUserinfo user : users){
			retStr += user.getId()+",";
		}
		if(retStr.endsWith(","))
			retStr = retStr.substring(0,retStr.length()-1);
		return retStr;
	}
	
	@Override
	public String findUserIdsForHnlplb(String orgcode, String deptcode,
			String rolecode) {
		String retStr = "";
		List<Object[]> users = userinfoDao.findUserIdsForHnlplb2(orgcode, deptcode, rolecode);
		/*for(SysUserinfo user : users){
			retStr += user.getId()+",";
		}*/
		for(int i=0;i<users.size();i++){
			retStr += users.get(i)[0] +",";
		}
		if(retStr.endsWith(","))
			retStr = retStr.substring(0,retStr.length()-1);
		return retStr;
	}
	
	/**
	 * 查询实施主体下的人员
	 */
	@Override
	public String findUserIdsForSs(String orgcode,String deptcode){
		String retStr = "";
		List<SysUserinfo> users = userinfoDao.findUserIdsForSs(orgcode, deptcode);
		for(SysUserinfo user : users){
			retStr += user.getId()+",";
		}
		if(retStr.endsWith(","))
			retStr = retStr.substring(0,retStr.length()-1);
		return retStr;
	}
	
	/**
	 * 查询第三方机构下的人员
	 */
	@Override
	public String findUserIdsForThird(String thirdorgid){
		String retStr = "";
		List<SysUserinfo> users = userinfoDao.findUserIdsForThird(thirdorgid);
		for(SysUserinfo user : users){
			retStr += user.getId()+",";
		}
		if(retStr.endsWith(","))
			retStr = retStr.substring(0,retStr.length()-1);
		return retStr;
	}

	
	@Override
	public String findSameDeptJsonByOrgid(String orgid) {
		List<Object[]> list = findDeptByOrgid(Integer.valueOf(orgid));
		SysOrganization org = organizationDao.getEntity(Integer.valueOf(orgid));
		StringBuffer str = new StringBuffer();
		if(list!=null&&list.size()>0){
			str.append("{\"orgname\":\"").append(org.getOrgName()).append("\",\"orgcode\":\"")
			.append(org.getOrgCode()).append("\",\"deptinfo\":[");
			for(int i=0;i<list.size();i++){
				if(i==(list.size()-1)){
					str.append("{\"name\":\"").append(list.get(i)[1])
					.append("\",\"code\":\"").append(list.get(i)[2]).append("\"}");
				}else{
					str.append("{\"name\":\"").append(list.get(i)[1])
					.append("\",\"code\":\"").append(list.get(i)[2]).append("\"},");
				}
			}			
			str.append("]}");
		}else{
			str.append("[]");
		}		
		return str.toString();
	}

	
	/**
	 * 查询责任主体下的人员
	 */
	@Override
	public String findUserIdsForAssessment(String orgcode, String deptcode) {
		String retStr = "";
		List<SysUserinfo> users = userinfoDao.findUserIdsForZr(orgcode, deptcode);
		for(SysUserinfo user : users){
			retStr += user.getId()+",";
		}
		if(retStr.endsWith(","))
			retStr = retStr.substring(0,retStr.length()-1);
		return retStr;
	}
	
	
	@Override
	public  Integer  toDeptLvById(String orgid) {		
		SysOrganization org = organizationDao.getEntity(Integer.parseInt(orgid));
		String orgcode = org.getOrgCode();
		if(orgcode!=null){
			//机构code组成方式 MC2+2+2+3+3+3共17位
			//从后向前依次截取
			String a = "";
			//网格
			a =orgcode.substring(14,orgcode.length());
			if(!"000".equals(a)){
				return 18;
			}
			//村
			a =orgcode.substring(11,14);
			if(!"000".equals(a)){
				return 16;
			}
			//镇
			a =orgcode.substring(8,11);
			if(!"000".equals(a)){
				return 14;
			}
			//区
			a =orgcode.substring(6,8);
			if(!"00".equals(a)){
				return 12;
			}
			//市
			a =orgcode.substring(4,6);
			if(!"00".equals(a)){
				return 10;
			}
			//省
			a =orgcode.substring(2,4);
			if(!"00".equals(a)){
				return 8;
			}
		}
		return 0;
	}
	
	
	@Override
	public String selectConn(String orgid) {
		SysOrganization org = organizationDao.getEntity(Integer.parseInt(orgid));
		String orgcode = org.getOrgCode();
    	//从后向前依次截取
		String a = "";
		//网格无下级不做考虑
		//村
		a =orgcode.substring(11,14);
		if(!"000".equals(a)){
			return orgcode.substring(0,14);
		}
		//镇
		a =orgcode.substring(8,11);
		if(!"000".equals(a)){
			return orgcode.substring(0,11);
		}
		//区
		a =orgcode.substring(6,8);
		if(!"00".equals(a)){
			return orgcode.substring(0,8);
		}
		//市
		a =orgcode.substring(4,6);
		if(!"00".equals(a)){
			return orgcode.substring(0,6);
		}
		//省
		a =orgcode.substring(2,4);
		if(!"00".equals(a)){
			return orgcode.substring(0,4);
		}
		return null;
	}

}
