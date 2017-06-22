package com.goldenweb.biz.comm.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.util.IAction;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-7-1 下午2:47:51
 */
@Namespace("/")
@Scope("prototype")
@Component("DialogAction")
@ParentPackage("json-default")
public class DialogAction extends IAction {
	
	
	private  String selectname;
	
	public String getSelectname() {
		return selectname;
	}
	public void setSelectname(String selectname) {
		this.selectname = selectname;
	}

	@Action(value = "DialogAction_toCommonSqlDialog", results = { 
			@Result(name = "success", location = "dialogs/chooseCommonSqlDialog.jsp"),
			@Result(name = "SWRYinfo", location = "dialogs/chooseSWRYinfo.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toCommonSqlDialog(){
//		if("chooseSWRY".equals(type)){
//			String sql = "select STABLE_SWRY_GUID as id, xingming, xingbie,SHENFENZHENGHAO, HUJIDIZHI, YIDONGDIANHUA from STABLE_SWRY";
//			SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql);
//
//			colNames = rowset.getMetaData().getColumnNames();
//		}
//		else{
//			
//		}
		
		//swryService.saveSWRY(this.getCurrentUser().getId()+"",this.getCurrentUser().getMainOrgid()+"", "test1","AA0201", "320602198505096666","ddd","123");
		if("chooseSWRYinfo".equals(type)){
			return "SWRYinfo";
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查询所有涉稳人员信息
	 * @Title jsonData
	 * @author Lee
	 * @time 2014年8月29日 上午10:03:41
	 */
	@Action(value = "DialogAction_jsonData")
	public void getAllSwry() {
		Page page = new Page(this.page, this.rows);
		
	}
	
	private String type;
	private String[] colNames;
	private String json;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getColNames() {
		return colNames;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
