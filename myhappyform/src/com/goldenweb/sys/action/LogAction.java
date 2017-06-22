package com.goldenweb.sys.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.sys.service.LogService;
import com.goldenweb.sys.util.ArgsUtil;
import com.goldenweb.sys.util.IAction;
import com.google.gson.Gson;

@Namespace("/")
@Scope("prototype")
@Component("logAction")
@ParentPackage("json-default")
public class LogAction extends IAction {
	@Autowired
	private LogService logService;
	private String htmlValue;

	public String getHtmlValue() {
		return htmlValue;
	}

	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}

	/************************************************************************************/


	/**
	 * 日志管理page
	 * @Title toLogList
	 * @return String
	 * @author Lee
	 * @time 2015年3月18日 下午4:14:49
	 */
	@Action(value = "logAction_toLogList", results = {
			@Result(name = "success", location = "sys/log/logListUI.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toLogList() {
		try {
			List<String> logtypeList = logService.findLogTypes();
			request.setAttribute("logtypeList", logtypeList);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * easyui的日志界面datagrid数据
	 * @Title datagrid
	 * @author Lee
	 * @time 2015年3月18日 下午4:13:36
	 */
	@Action(value = "logAction_datagrid", results = { @Result(name = "success", type = "json", params = {
			"root", "htmlValue" }) })
	public void datagrid() {

		try {
			String selectType = request.getParameter("selectType");// 类型
			String selectUser = request.getParameter("selectUser");
			String begintime = request.getParameter("begintime");
			String endtime = request.getParameter("endtime");

			String pageIndex = request.getParameter("page");
			if (pageIndex == null || pageIndex.equals("")) {
				pageIndex = "1"; // 设置为第1页
			}
			// 查询每页记录条数,从参数设定表中获取
			int pageSize = 15;
			String pagesize = request.getParameter("rows");
			if (pagesize != null && !pagesize.equals("")) {
				pageSize = Integer.parseInt(pagesize);
			} else {
				pageSize = Integer.parseInt(ArgsUtil.getPageSize()); // 默认值从系统参数里读取
			}

			Page page = logService.getPageList(selectType, begintime, endtime,
					selectUser, pageSize, Integer.parseInt(pageIndex));
			StringBuilder sb = new StringBuilder();

			Gson gson = new Gson();
			htmlValue = gson.toJson(page.getMapResult());

			response.setCharacterEncoding("utf-8");// 指定为utf-8
			response.getWriter().write(htmlValue);// 转化为JSOn格式
			// return "success";
		} catch (Exception e) {
			e.printStackTrace();
			// return "error";
		}
	}

}
