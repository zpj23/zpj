package com.goldenweb.biz.comm.action;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.fxpg.frame.pojo.OnLineUser;
import com.goldenweb.fxpg.frame.service.DynamicFormService;
import com.goldenweb.fxpg.listener.OnLineUserListener;
import com.goldenweb.sys.pojo.Porlet;
import com.goldenweb.sys.pojo.PortalConfig;
import com.goldenweb.sys.pojo.PortalPanel;
import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.service.PorletService;
import com.goldenweb.sys.service.PortalService;
import com.goldenweb.sys.service.PurviewService;
import com.goldenweb.sys.service.UserinfoService;
import com.goldenweb.sys.util.IAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-5-8 上午11:18:49
 */
@Namespace("/")
@Scope("prototype")
@Component("HomeAction")
@ParentPackage("json-default")
public class HomeAction extends IAction {
	@Autowired
	private PorletService porletService;
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private  UserinfoService userinfoService;
	
	@Autowired
	private  PurviewService purviewService;
	
	@Autowired
	private DynamicFormService dynamicFormService;
	
	
	@Action(value = "HomeAction_toHome", results = {
			@Result(name = "success", location = "home/customHome2.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toHome()throws Exception{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		List<Map> listMap = new ArrayList<Map>();
		//throw new StableException(StableCode.mial_nosend);
		List<PortalPanel> list = new ArrayList<PortalPanel>();
		//查询当前用户是否有定制过，没有就显示默认
		SysUserinfo user= (SysUserinfo) request.getSession().getAttribute("iuserinfo");
		List<Porlet> porlets = null;
		List<Porlet> porletsall = porletService.getPorletRole(user.getId().toString());
		porlets = porletService.getInfoByUserid(user.getId().toString());
		if(porlets==null){
			porlets = porletsall; // 默认的
		}
		
		for(Porlet porlet : porlets){
			PortalPanel panel = new PortalPanel();
			panel.setId(porlet.getCode());
			panel.setTitle(porlet.getName());
			panel.setHeight(porlet.getHeight()+"");
			panel.setHref("HomeAction_loadPorletContent?type="+porlet.getCode());
			panel.setToolsStatus(porlet.getIsmore());
			list.add(panel);
			if(panelsCode==null || panelsCode.isEmpty())
				panelsCode = porlet.getCode();
			else
				panelsCode += ","+porlet.getCode();
		}
		
		allPorlets = porletsall;
		
		
		
		panelsJson = gson.toJson(list);
		
		
		//门户配置
//		portalConfig = portalService.getPortalConfig4User(this.getCurrentUser().getId()+"");
		//portalConfig = portalService.getPortalConfig4User("1");
		if(portalConfig == null)
		{
			portalConfig = new PortalConfig();
			portalConfig.setColumnNum(1);
		}
		
		request.setAttribute("display", "none");
		if("edit".equals(request.getParameter("mode"))){
			
			request.setAttribute("display", "block");
		}
			
		
		return SUCCESS;
	}
	
	/**
	 * 首页-待办事项-MORE
	 * @Title todoMore
	 * @return String
	 * @author Lee
	 * @time 2014年9月11日 上午11:39:52
	 */
	@Action(value = "HomeAction_todoMore", results = {
			@Result(name = "success", location = "home/todoMore.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String todoMore() {
		return SUCCESS;
	}
	
	/**
	 * 首页-信息预警-MORE
	 * @Title preMore
	 * @return String
	 * @author Lee
	 * @time 2014年9月11日 上午11:47:46
	 */
	@Action(value = "HomeAction_preMore", results = {
			@Result(name = "success", location = "home/preMore.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String preMore() {
		return SUCCESS;
	}
	
	
	@Action(value="HomeAction_DcdbListJson4MainTodo")
	public void dcdbListJson4MainTodo() throws IOException{
		
		
	}
	
	
	@Action(value="HomeAction_ListJson4OnlineUsers")
	public void listJson4OnlineUsers() throws IOException{

		Collection<OnLineUser> users =  OnLineUserListener.onLineUserList.values();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String retJson = gson.toJson(users);
		response.setCharacterEncoding("utf-8");// 指定为utf-8
		response.getWriter().write(retJson);// 转化为JSOn格式
	}
	
	@Action(value="HomeAction_queryHomeYuJing")
	public void queryHomeYuJing() throws IOException{
		String str = dynamicFormService.queryHomeYuJing(tableName, rowCount, formatterMap);
		this.jsonWrite(str);
	}
	
	@Action(value="HomeAction_savePortalConfig")
	public void savePortalConfig(){
		String configInfo = request.getParameter("configInfo");
		String objectInfo = configInfo;
		if(configInfo.split(":").length==2){
			objectInfo = configInfo.split(":")[0]+","+configInfo.split(":")[1];
		}
		if(configInfo.split(":").length==3){
			objectInfo = configInfo.split(":")[0]+","+configInfo.split(":")[1]+","+configInfo.split(":")[2];
		}

		portalConfig = new PortalConfig();
//		portalConfig.setOid(this.getCurrentUser().getId()+"");
//		portalConfig.setObjectType(this.getCurrentUser().getLoginname());
		portalConfig.setColumnNum(configInfo.split(":").length);
		portalConfig.setObjectInfo(objectInfo);
		portalConfig.setObjectConfigInfo(configInfo);
		portalService.saveConfigInfoWithconfig(portalConfig);
	}
	
	@Action(value="HomeAction_savePorlets", results = {
			@Result(name = "success", type="redirect", location = "HomeAction_toHome"),
			@Result(name = "error", location = "/error.jsp") })
	public String savePorlets() throws Exception{
		
		int columnNum_int = Integer.parseInt(columnNum);
		String configInfo = "";
		if(columnNum_int > 1){
			porlets = porlets.replaceAll(" ", "");
			String[] pa = porlets.split(",");
			int splitNum = Integer.parseInt(columnNum);
			StringBuffer stringBuffer = new StringBuffer();
			for(int i=0;i<pa.length;i++){
				if(i%splitNum==0 && pa.length!=splitNum){
					stringBuffer.append(pa[i].trim()+",");
				}else{
					stringBuffer.append(pa[i].trim()+":");
				}
			}
			// 奇数
			if(pa.length%2!=0){
				stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length()-1);
			}
			configInfo = stringBuffer.toString();
			
		}else{
			configInfo = porlets+":";
		}
		
		portalConfig = new PortalConfig();
//		portalConfig.setOid(this.getCurrentUser().getId()+"");
//		portalConfig.setObjectType(this.getCurrentUser().getLoginname());
		portalConfig.setColumnNum(columnNum_int);
		portalConfig.setObjectInfo(porlets);
		portalConfig.setObjectConfigInfo(configInfo);
		portalService.saveConfigInfo(portalConfig);
		
		
		
		//以下为加载首页
		
		List<PortalPanel> list = new ArrayList<PortalPanel>();
		List<Porlet> porlets = porletService.getAll();
		for(Porlet porlet : porlets){
			PortalPanel panel = new PortalPanel();
			panel.setId(porlet.getCode());
			panel.setTitle(porlet.getName());
			panel.setHeight(porlet.getHeight()+"");
			//panel.setClosable("true");
			//panel.setCollapsible("false");
			panel.setHref("HomeAction_loadPorletContent?type="+porlet.getCode());
			list.add(panel);
			
			if(panelsCode==null || panelsCode.isEmpty())
				panelsCode = porlet.getCode();
			else
				panelsCode += ","+porlet.getCode();
		}
		
		allPorlets = porlets;
		
		//门户配置
//		portalConfig = portalService.getPortalConfig4User(this.getCurrentUser().getId()+"");
		
		//portalConfig = portalService.getPortalConfig4User("1");
		if(portalConfig == null)
		{
			portalConfig = new PortalConfig();
			portalConfig.setColumnNum(2);
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();		
		panelsJson = gson.toJson(list);		
		request.setAttribute("display", "none");
			
		return  SUCCESS;
	}
	
	@Action(value="HomeAction_loadPorletContent")
	public void loadPorletContent() throws IOException, TemplateException{
		String type= request.getParameter("type");
		SysUserinfo user= (SysUserinfo) request.getSession().getAttribute("iuserinfo");
		Porlet porlet = porletService.getByCode(type);
		
		Configuration config = new Configuration();
		//config.setDirectoryForTemplateLoading(new File(request.getRealPath("")+"/template/porlet"));
		
		StringWriter writer = new StringWriter();
		if("p_notice".equals(type)){
			

			Template t  =  new Template(type,new StringReader(porlet.getTemplate()), config, "UTF-8");

			Map root = new HashMap();
		
			t.process(root,writer);
		}
		else if("p_todo".equals(type)){
			Template t  =  new Template(type,new StringReader(porlet.getTemplate()), config, "UTF-8");
			Map root = new HashMap();
			t.process(root,writer);
		}
		else if("p_tj".equals(type)){
			
			//当前人的机构级别
			int orglv = userinfoService.toDeptLv(user.getMainOrgcode()); //返回14是镇
			
			Template t  =  new Template(type,new StringReader(porlet.getTemplate()), config, "UTF-8");
			Map root = new HashMap();
			if(orglv!=0){
			root.put("orgname", user.getMainOrgname());
			}else{
				root.put("orgname", user.getThirdorgname());	
			}
			root.put("orglv", orglv);
			
			t.process(root,writer);
		}
		else if("p_yj".equals(type)){
			Template t  =  new Template(type,new StringReader(porlet.getTemplate()), config, "UTF-8");
			Map root = new HashMap();
			root.put("ctx", request.getContextPath());
			List<Object[]> quickmenu =purviewService.findQuickmenuByRoleid(user.getId().toString());
			root.put("quickmenu", quickmenu);
			t.process(root,writer);
		}
		else if("p_map".equals(type)){
			Template t  =  new Template(type,new StringReader(porlet.getTemplate()), config, "UTF-8");
			Map root = new HashMap();
			t.process(root,writer);
		}
		else if("p_onlineuser".equals(type)){
			Template t  =  new Template(type,new StringReader(porlet.getTemplate()), config, "UTF-8");
			Map root = new HashMap();
			t.process(root,writer);
		}else{
			Template t  =  new Template(type,new StringReader(porlet.getTemplate()), config, "UTF-8");
			Map root = new HashMap();
			t.process(root,writer);
		}
		
		response.setCharacterEncoding("utf-8");// 指定为utf-8
		response.getWriter().write(writer.toString());// 转化为JSOn格式
	}
	
	
	
	/*@Action(value = "HomeAction_test", results = {
			@Result(name = "success", location = "home/test.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String test() {
		return SUCCESS;
	}*/
	
	
	String panelsCode;
	String panelsJson;
	PortalConfig portalConfig;
	private List<Porlet> allPorlets;
	private String columnNum = "2";
	private String porlets;
	private String tableName;
	private int rowCount;
	private String formatterMap;
	// MORE panel title
	private String titleName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String getFormatterMap() {
		return formatterMap;
	}

	public void setFormatterMap(String formatterMap) {
		this.formatterMap = formatterMap;
	}

	public List<Porlet> getAllPorlets() {
		return allPorlets;
	}

	public void setAllPorlets(List<Porlet> allPorlets) {
		this.allPorlets = allPorlets;
	}
	
	public String getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(String columnNum) {
		this.columnNum = columnNum;
	}

	public String getPorlets() {
		return porlets;
	}

	public void setPorlets(String porlets) {
		this.porlets = porlets;
	}

	public PortalConfig getPortalConfig() {
		return portalConfig;
	}

	public void setPortalConfig(PortalConfig portalConfig) {
		this.portalConfig = portalConfig;
	}

	public String getPanelsJson() {
		return panelsJson;
	}

	public void setPanelsJson(String panelsJson) {
		this.panelsJson = panelsJson;
	}

	public String getPanelsCode() {
		return panelsCode;
	}

	public void setPanelsCode(String panelsCode) {
		this.panelsCode = panelsCode;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
