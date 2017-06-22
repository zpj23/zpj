package com.goldenweb.sys.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class FreeMarkerUtil {
	
	
	 private Configuration cfg = new Configuration();   

	public void printFile(String templateName,
			Map<String, Object> root, HttpServletResponse response) {
			cfg.setDefaultEncoding("UTF-8");  
			cfg.setClassForTemplateLoading(this.getClass(), "/com/goldenweb/sys/printTemplate");
			Template t = null ;
			try {
				t = cfg.getTemplate(templateName);
				response.setContentType("text/html; charset=" + t.getEncoding());  
				Writer out = response.getWriter(); 
				t.process(root, out);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			} 
			
	}

}
