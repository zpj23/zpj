package com.goldenweb.fxpg.frame.action;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.fxpg.frame.dao.ConcreteColumn;
import com.goldenweb.fxpg.frame.factory.GenericFactory;
import com.goldenweb.fxpg.frame.service.DynamicFormService;
import com.goldenweb.fxpg.frame.tools.CallProcedure;
import com.goldenweb.fxpg.frame.tools.ClobAndBlobToString;
import com.goldenweb.fxpg.frame.tools.Constant;
import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.pojo.SysOrganization;
import com.goldenweb.sys.pojo.SysUserinfo;
import com.goldenweb.sys.service.UserinfoService;
import com.goldenweb.sys.util.FileHelper;
import com.goldenweb.sys.util.IAction;

@Namespace("/")
@Scope("prototype")
@Component("genericAction")
@ParentPackage("json-default")
public class GenericAction extends IAction {
	
	@Autowired
	private DynamicFormService dynamicFormService ;
	
	@Autowired
	private GenericFactory genericFactory;
	@Autowired
	private UserinfoService userinfoService; 
	
	private SysOrganization org;
	public SysOrganization getOrg() {
		return org;
	}
	public void setOrg(SysOrganization org) {
		this.org = org;
	}
	
	// SQL
	private String sql_; 
	// 表名称
	private String tableName_;
	// 表主键
	private String tableGuid_;
	// 列绑定数据
	private String formatterColumnStr;
	// 模糊检索
	private String searchColumnStr;
	// 业务模块ID
	private String busGuId_;
	// 组织机构主键
	private String orgGuid_;
	// 组织机构名称
	private String orgName;
	// blob列
	private String blobObjArray_;
	// 日期数组
	private String dateObjArray_;
	// 按钮隐藏显示状态
	private String btnStatus;
	// 动态表数据ID
	private String data_uuid_;
	// 子表SQLJSON
	private String childSql_;
	
	private String dataid;
	//菜单除表名的查询条件
    private String conn;
	
	public String getConn() {
		return conn;
	}
	public void setConn(String conn) {
		this.conn = conn;
	}
	public void setSql_(String sql_) {
		this.sql_ = sql_;
	}
	public String getTableName_() {
		return tableName_;
	}
	public void setTableName_(String tableName_) {
		this.tableName_ = tableName_;
	}
	public String getTableGuid_() {
		return tableGuid_;
	}
	public void setTableGuid_(String tableGuid_) {
		this.tableGuid_ = tableGuid_;
	}
	public String getFormatterColumnStr() {
		return formatterColumnStr;
	}
	public void setFormatterColumnStr(String formatterColumnStr) {
		this.formatterColumnStr = formatterColumnStr;
	}
	public String getSearchColumnStr() {
		return searchColumnStr;
	}
	public void setSearchColumnStr(String searchColumnStr) {
		this.searchColumnStr = searchColumnStr;
	}
	public String getBusGuId_() {
		return busGuId_;
	}
	public void setBusGuId_(String busGuId_) {
		this.busGuId_ = busGuId_;
	}
	public String getOrgGuid_() {
		return orgGuid_;
	}
	public void setOrgGuid_(String orgGuid_) {
		this.orgGuid_ = orgGuid_;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getBlobObjArray_() {
		return blobObjArray_;
	}
	public void setBlobObjArray_(String blobObjArray_) {
		this.blobObjArray_ = blobObjArray_;
	}
	public String getDateObjArray_() {
		return dateObjArray_;
	}
	public void setDateObjArray_(String dateObjArray_) {
		this.dateObjArray_ = dateObjArray_;
	}
	public String getBtnStatus() {
		return btnStatus;
	}
	public void setBtnStatus(String btnStatus) {
		this.btnStatus = btnStatus;
	}	
	public String getDataid() {
		return dataid;
	}
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}	
	public String getData_uuid_() {
		return data_uuid_;
	}
	public void setData_uuid_(String data_uuid_) {
		this.data_uuid_ = data_uuid_;
	}
	public String getChildSql_() {
		return childSql_;
	}
	public void setChildSql_(String childSql_) {
		this.childSql_ = childSql_;
	}
	
	
	
	
	/**
	 * 选择地图页面
	 * @return
	 * @throws IOException
	 */
	@Action(value="genericAction_map",results={
			@Result(name="success",location="dialogs/chooseMapPoint.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String genericAction_map() throws IOException{
		return SUCCESS;
	}
	
	
	/**
	 * 选择地图页面(子页面适用)
	 * @return
	 * @throws IOException
	 */
	@Action(value="genericAction_mapchild",results={
			@Result(name="success",location="dialogs/chooseMapPointChild.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String genericAction_mapchild() throws IOException{
		return SUCCESS;
	}
	
	/**
	 * 动态表单解析
	 * @Title toList
	 * @return String  
	 * @author Lee
	 * @throws IOException 
	 * @time 2014-3-10 上午08:56:06
	 */
	@Action(value="genericAction_form",results={
			@Result(name="success",location="form/formList.jsp",params={"root","jsonData"}),
			@Result(name="error",location="/error.jsp")
	})
	public String genericAction_form() throws IOException{
		ConcreteColumn concreteColumn = genericFactory.showConcreteColumn();
		jsonData = concreteColumn.query_form(tableGuid_,tableName_, Constant.TOFULLFORMAT);
		request.setAttribute("orgGuid_", orgGuid_);
		request.setAttribute("dataGuid", dataid);
		return "success";
	}
	
	@Action(value="genericAction_formview",results={
			@Result(name="success",location="form/formView.jsp",params={"root","jsonData"}),
			@Result(name="error",location="/error.jsp")
	})
	public String genericAction_formview() throws IOException{
		ConcreteColumn concreteColumn = genericFactory.showConcreteColumn();
		jsonData = concreteColumn.query_form(tableGuid_,tableName_, Constant.TOFULLFORMAT);
		request.setAttribute("orgGuid_", orgGuid_);
		request.setAttribute("dataGuid", dataid);
		return "success";
	}
	
	
	@Action(value="genericAction_formprint",results={
			@Result(name="success",location="form/formListprint.jsp",params={"root","jsonData"}),
			@Result(name="error",location="/error.jsp")
	})
	public String genericAction_formprint() throws IOException{
		ConcreteColumn concreteColumn = genericFactory.showConcreteColumn();
		jsonData = concreteColumn.query_form(tableGuid_,"", Constant.TOFULLFORMAT);
		return "success";
	}
	
	/**
	 * @Description TODO(组织机构数据展示)
	 * @Title showOrg
	 * @return String  
	 * @author Lee
	 * @throws Exception 
	 * @time 2014-2-22 上午09:17:49
	 */
	@Action(value="genericAction_showOrg",
			results={
			@Result(name="success",location="form/show_org.jsp"),
			@Result(name="error",location="/error.jsp")
	})
	public String showOrg() throws Exception{
		orgName = showLoginObj().getMainOrgname();
		orgGuid_ = showLoginObj().getMainOrgid().toString();
		//
		Integer levelnum = userinfoService.toDeptLv(showLoginObj().getMainOrgcode());
		if(levelnum>=14){
			request.setAttribute("nochilddata",false);
		}else{
			request.setAttribute("nochilddata",true);
		}
		return SUCCESS;
	}

	/**
	 * 动态表数据入库
	 * @Title saveorUpdateFormObj
	 * @author Lee
	 * @throws Exception 
	 * @time 2014-3-21 下午03:45:15
	 */
	@Action("saveorUpdateFormObj")
	public void saveorUpdateFormObj() throws Exception {
		boolean bool;
		if("weiwxx".equals(tableName_)){//涉稳信息的子表涉稳人员有点特殊，和主表不是采用org_guid关联的，采用link_guid
			bool = dynamicFormService.createSQLLinkid(sql_,showLoginObj(),blobObjArray_,dateObjArray_,childSql_);
		}else{
		    bool = dynamicFormService.createSQL(sql_,showLoginObj(),blobObjArray_,dateObjArray_,childSql_);
		}
		// 统计
		List params = new ArrayList();
		params.add(tableName_);
		CallProcedure.callProcedure("{call P_BUSINESS_COUNT(?)}", params);
		ServletActionContext.getResponse().getWriter().print(bool); 
	}
	
	/**
	 * 查询表单信息
	 * @Title queryForm
	 * @throws IOException  
	 * @author Lee
	 * @throws SQLException 
	 * @time 2014-3-21 下午05:20:30
	 */
	@Action("queryForm")
	public void queryForm() throws IOException, SQLException {
		Map map = dynamicFormService.queryForm(tableName_, tableGuid_, blobObjArray_);
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(map);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(jsonArray.toString()); 
	}
	
	
	/**
	 * 流程关联项是否选中
	 * @throws IOException
	 * @throws SQLException
	 */
	@Action("linkchecked")
	public void linkchecked() throws IOException, SQLException {
		String curuserid = getCurrentUser().getId().toString();
		Map map = dynamicFormService.queryForm(tableName_, tableGuid_, blobObjArray_);
		List<Object[]> list = dynamicFormService.findLinkinfo(tableName_,tableGuid_,curuserid,"guanzhu");
		Boolean bool = false;
		if(list!=null){
			bool = true;
		}
		
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(bool); 
	}
	
	
	/**
	 * 查询子表表单信息
	 * @Title queryChildForm
	 * @throws IOException
	 * @throws SQLException  
	 * @author Lee
	 * @time 2014-4-21 下午03:11:26
	 */
	@Action("queryChildForm")
	public void queryChildForm() throws IOException, SQLException {
		String tableStr = dynamicFormService.queryChildForm(tableName_, tableGuid_);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(tableStr); 
	}
	
	/**
	 * @Description TODO(一级菜单JSON数据)
	 * @Title showJsonOrg
	 * @return String
	 * @author Lee
	 * @throws IOException 
	 * @time 2014-2-22 上午09:17:23
	 */
	@Action(value="orgAction_showJsonOrg",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void showJsonOrg() throws IOException{
		this.jsonData = dynamicFormService.getChildNode(String.valueOf(showLoginObj().getMainOrgid()), tableName_);
		this.jsonWrite(jsonData);
	}
	
	/**
	 * @Description TODO(子菜单JSON数据)
	 * @Title showJsonOrg
	 * @return String
	 * @author Lee
	 * @throws IOException 
	 * @time 2014-2-22 上午09:17:23
	 */
	@Action("orgAction_showJsonChildNode")
	public void showJsonChildNode() throws IOException{
		String jsonData = dynamicFormService.getChildNode(this.org.getNodeId(), tableName_);
		this.jsonWrite(jsonData);
	}
	
	@Action(value="orgAction_showChildNode",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void showChildNode() throws IOException{
		String id = request.getParameter("id");
		this.jsonData = dynamicFormService.findDeptJson(id);
		this.jsonWrite(jsonData);
	}
	
	/**
	 * 动态表单列、数据JSON
	 * @Title queryFromTable
	 * @throws IOException  
	 * @author Lee
	 * @time 2014-3-24 下午01:27:50
	 */
	@Action(value="genericAction_queryFromColumn",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void queryFromColumn() throws IOException{
		Page pageObject = new Page(page, rows);
		this.jsonData = dynamicFormService.queryFromColumn(tableName_, pageObject);
		this.jsonWrite(jsonData);
	}
	
	/**
	 * 动态表数据JSON
	 * @Title queryFromData
	 * @author Lee
	 * @throws Exception 
	 * @time 2014-3-24 下午01:27:50
	 */
	@Action(value="genericAction_queryFromData",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void queryFromData() throws Exception{
		Page pageObject = new Page(page, rows);
		this.jsonData = dynamicFormService.queryFromData(tableName_,
				pageObject, formatterColumnStr, searchColumnStr,
				showLoginObj(), orgGuid_, blobObjArray_);
		this.jsonWrite(jsonData);

	}
	
	
	/**
	 * 市显示涉稳信息JSON
	 * @throws IOException
	 * @throws SQLException
	 */
	@Action(value="genericAction_queryShiWeiw",
			results={
			@Result(type="json", params={"root","jsonData"})})
	public void queryShiWeiw() throws IOException, SQLException{
		Page pageObject = new Page(page, rows);
		this.jsonData = dynamicFormService.queryFromData(tableName_,
				pageObject, searchColumnStr, formatterColumnStr, blobObjArray_);
		this.jsonWrite(jsonData);
	}
	 
	/**
	 * 删除动态表数据
	 * @Title delFormTable
	 * @author Lee
	 * @throws Exception 
	 * @time 2014-3-25 下午01:07:46
	 */
	@Action("genericAction_delFormTable")
	public void delFormTable() throws Exception {
		boolean bool = dynamicFormService.delFormTable(tableName_, tableGuid_);
		// 统计
		List params = new ArrayList();
		params.add(tableName_);
		CallProcedure.callProcedure("{call P_BUSINESS_COUNT(?)}", params);
		ServletActionContext.getResponse().getWriter().print(bool); 
	}
	
	/**
	 * 根据业务主键查询表名
	 * @Title queryColumnById
	 * @throws IOException  
	 * @author Lee
	 * @time 2014-4-9 下午01:52:31
	 */
	@Action("genericAction_queryColumnById")
	public void queryColumnById() throws IOException {
		String tableName = dynamicFormService.queryColumnById(busGuId_);
		ServletActionContext.getResponse().getWriter().print(tableName); 
	}
	
	private SysUserinfo showLoginObj(){
		SysUserinfo user = (SysUserinfo)request.getSession().getAttribute("iuserinfo");
		return user;
	}
	
	
	
	
	@Action(value="genericAction_importWord",results={
			@Result(type="json", params={"root","jsonData"})
       })
	public void importWord() throws Exception {
		String tablename = request.getParameter("tablename");
		String guid = request.getParameter("guid");
		String filename = dynamicFormService.findFilename(tablename,guid);
		String path=dynamicFormService.importWord(tablename,guid);
		
		response.setContentType("application/msword");
		FileHelper.downloadFile(path, (filename+".doc"), response);
		jsonData=path;
		
		}
	
	
	@Action(value="genericAction_importWord2",results={
			@Result(name="success",location="form/yulan.jsp"),
			@Result(name="error",location="/error.jsp")
       })
	public String importWord2() throws Exception {
		String tablename = request.getParameter("tablename");
		String guid = request.getParameter("guid");
		
		List<Object[]> list = dynamicFormService.findFileList(tablename,guid);
		String str="";
		if(list.get(0)[1]!=null){
			 str = ClobAndBlobToString.BlobToString((Blob)list.get(0)[1]);
		}		
		request.setAttribute("str", str);
		return "success";
		
		}
	
	
}
