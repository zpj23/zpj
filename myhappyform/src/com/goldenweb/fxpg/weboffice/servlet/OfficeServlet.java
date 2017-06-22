package com.goldenweb.fxpg.weboffice.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goldenweb.fxpg.weboffice.service.OfficeService;
import com.goldenweb.fxpg.weboffice.service.impl.OfficeServiceImpl;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-4-10 上午11:27:58
 */
public class OfficeServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
	        throws ServletException, IOException{ 
		OfficeService os = new OfficeServiceImpl();
		os.ExecuteRun(req, resp);
	}
		  
}
