package com.goldenweb.sys.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.fxpg.cache.impl.UserMenuCache;
import com.goldenweb.sys.pojo.SysFunction;
import com.goldenweb.sys.pojo.SysRemind;
import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.service.PurviewService;
import com.goldenweb.sys.service.RemindService;
import com.goldenweb.sys.service.UserinfoService;
import com.goldenweb.sys.util.FileHelper;
import com.goldenweb.sys.util.IAction;

@Namespace("/")
@Scope("prototype")
@Component("loginAction")
@ParentPackage("json-default")
public class LoginAction extends IAction{

	public SysUserinfo user;

	public void setUser(SysUserinfo user) {
		this.user = user;
	}
	public SysUserinfo getUser() {
		return user;
	}
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private RemindService remindService;
	@Autowired
	private PurviewService purviewService;

	
	/**
	 * @Description:统计信息
	 * @Fields statisticalService 
	 */


	private String htmlValue;
	public String getHtmlValue() {
		return htmlValue;
	}
	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}	
	/*******************************************************************************************/

	/**
	 * 注销用户
	 * @return
	 */
	@Action(value="loginAction_cancellation",results={
			@Result(name="success",location="/login.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String cancellation(){
		try {
			//session.clear();
			request.getSession().removeAttribute("iuserinfo"); 
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	
	/**
	 * 登陆验证
	 */
	@Action(value="loginAction_checkLogin",results={
			@Result(name="success",location="home/index.jsp"),//fashionHome.jsp
			@Result(name="error",location="/login.jsp")
	})
	public String checkLogin(){
		try {
			user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");
			String loginname,pwd;
			//首次登陆
			 loginname = request.getParameter("username");
			 pwd = request.getParameter("pwd");
			 
			 if(loginname==null&&user==null){
				 //服务器重启后页面非正常情况访问
				 return "error";
			 }
			if(loginname==null){
				loginname = user.getLoginname();
				pwd =user.getPwd();
			}
			
			SysUserinfo user=new SysUserinfo();
 			user.setLoginname(loginname);
			user.setPwd(pwd);
			user = userinfoService.findLogin(user);		     
			if(user==null){			
				//不存在该用户，或密码有误
				request.setAttribute("msg", "用户名或密码错误");
				request.setAttribute("loginerror","1");
				request.setAttribute("loginname", loginname);
				return "error";
			}else{			
				//人员与角色的关系
				List<Object[]> userRoleList = userinfoService.findRolesByUserid(user.getId());
				user.setUserRoleList(userRoleList);
				//同级部门
				List<Object[]> deptList = userinfoService.findSameLvDeptNoSelf(user.getId(),user.getDeptcode());
				user.setSameDeptList(userinfoService.findSameLvDept(user.getId()));
				user.setSameDeptListNoSelf(deptList);
				// 下级机构 
				List<Object[]> childOrgList = userinfoService.findChindOrg(user.getId());
				user.setChildOrgList(childOrgList);
				//下级机构的同级部门
				List<Object[]> childDeptList = userinfoService.findChildLvDept(user.getId());
				user.setChildDeptList(childDeptList);
				 //角色
		        String rolecodes="";
		        if(userRoleList!=null&&userRoleList.size()>0){
		        	for(int i=0;i<userRoleList.size();i++){
		        		rolecodes+=userRoleList.get(i)[2]+",";
		        	}
		        }		        
		        user.setRoles(rolecodes);
		        user.setCurrentLoginIp(request.getRemoteAddr());

				//人员id,姓名,是否管理员,所在组织机构在user中有实际意义
				request.getSession().setAttribute("iuserinfo",user);
				
				//菜单显示,以权限配置表中的为主，配置中无信息在进行关联查询
				List<SysFunction> userFunctionList = purviewService.findUserMenuList(user.getId().toString());
				if(userFunctionList==null){
					userFunctionList = userinfoService.findFunctionByUserid(user.getId());
				}					
				request.setAttribute("userFunctionList", userFunctionList);
				
				request.getSession().setAttribute("iusermenu",userFunctionList);
				//按钮的权限
				Map<String, List<SysFunction>> buttonmap = purviewService.findUserButtonMap(userFunctionList);
				request.getSession().setAttribute("buttonpurview",buttonmap);
				
				/*Map<String, List<SysFunction>> mmp = (Map<String, List<SysFunction>>) request.getSession().getAttribute("buttonpurview");
				List<SysFunction> lt = mmp.get("3");
				for(int i=0;i<lt.size();i++){
					System.out.println("ccc:"+lt.get(i).getOperateType());
				}*/

				new UserMenuCache().init(user.getId().toString(),userFunctionList);
												
				//记录登陆日志	
//				LogUtil.logInfo("登陆", user.getUsername()+"于"+DateHelper.getToday("yyyy-MM-dd HH:mm:ss")+"成功登陆系统", user.getUsername(), user.getId());	
				request.setAttribute("iuser", user);
				
				// 根据当前用户机构ID、机构编码自动计算考核扣分信息入库 
				//statisticalService.executeAppraiseZonghpm(this.getCurrentUser());
				// 将综合排名的数据插入临时表
				//statisticalService.saveTempAppraiseZonghpm(this.getCurrentUser());
				
			}
 			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	/**
	 * 首页面提醒显示列表
	 * @return
	 */
	@Action(value="loginAction_findRemindHtml",results={
			@Result(name="success",location="sys/login/remind.jsp")
	})
	public String findRemindHtml(){
		user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");
				
		List<SysRemind> remindList = remindService.findRemindAllList(user.getId().toString());
		request.setAttribute("remindList", remindList);
		if(remindList!=null){
			request.setAttribute("listlength", remindList.size());
		}else{
			request.setAttribute("listlength", 0);
		}
		
		return "success";
	}
	
	@Action(value="loginAction_findRemindInfo",results={
			@Result(name="success",type="json", params={"root","jsonData"})
	})
	public void findRemindInfo() throws IOException{
		user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");
		String num = request.getParameter("remindnum");
		jsonData = remindService.findRemindJson(user.getId().toString(),num);		
		jsonWrite(jsonData);
	}
	
	
	@Action(value="loginAction_findRemindNumber",results={
			@Result(name="success",location="/ajax.jsp")
	})
	public String findRemindNumber(){
		user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");
		int num = remindService.findRemindNumber(user.getId().toString());
		
		htmlValue = String.valueOf(num);
		return "success";
	}
	
	
	/**
	 * 新增提醒
	 * @return
	 */
	@Action(value="loginAction_addRemindData",results={
			@Result(name="success",location="/ajax.jsp")
	})
	public String addRemindData(){
		user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");		
        //查询个数
		int num = remindService.findRemindNumber(user.getId().toString());		
		htmlValue = String.valueOf(num);
		return "success";
	}
	
		
	@Action(value="loginAction_uploadie",results={
			@Result(type="json", params={"root","jsonData"})
       })
	public void  uploadie() throws Exception {		
		String path = ServletActionContext.getServletContext().getRealPath("/upload") + 
				"/ie/IE10-Windows6.1-x86-zh-cn.exe";
		FileHelper.downloadFile(path, "IE10-Windows6.1-x86-zh-cn.exe", response);
		jsonData=path;		
	}
	
	@Action(value="loginAction_uploadexcel",results={
			@Result(type="json", params={"root","jsonData"})
       })
	public void  uploadexcel() throws Exception {		
		String path = ServletActionContext.getServletContext().getRealPath("/upload") + 
				"/pglc/评估信息报送模板.xls";
		FileHelper.downloadFile(path, "评估信息报送模板.xls", response);
		jsonData=path;		
	}
	
	@Action(value="loginAction_downloadPrintActive",results={
			@Result(type="json", params={"root","jsonData"})
       })
	public void  downloadPrintActive() throws Exception {		
		String path = ServletActionContext.getServletContext().getRealPath("/upload") + 
				"\\printActiveX\\printActiveX.bat";
		FileHelper.downloadFile(path, "printActiveX.bat", response);
		jsonData=path;		
	}
	
	
	public boolean used(String userid,String url) {
		List<?> list=null;
		try {
			list = userinfoService.used(userid, url);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(list!=null&&!list.isEmpty()){
			return true;
		}
		return false;
	}
	
	@Action(value="yingjcz",results={
			@Result(name="success",location="/yingjcz.jsp")
	})
	public String yingjcz(){
		return "success";
	}

}
