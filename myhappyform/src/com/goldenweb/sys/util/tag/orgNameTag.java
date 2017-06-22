package com.goldenweb.sys.util.tag;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.goldenweb.fxpg.cache.impl.OrginfoCache;


@SuppressWarnings("serial")
public class orgNameTag extends TagSupport {
    private String code;
   
    public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
	public int doStartTag() throws JspException {
        try {
        	if(code.indexOf(",")>-1){
        		String str ="";
        		String no_ [] = code.split(",");
        		for(int i=0;i<no_.length;i++){
        			if(!"".equals(no_[i].trim())){
        				String name = OrginfoCache.findOrgNameByCode(no_[i]);
        				if(name!=null&&!"".equals(name)){
        					str+=name+",";
        				}        				
        			}
        		}
        		this.pageContext.getOut().write(str.substring(0,str.length()-1));
        	}else{
        		this.pageContext.getOut().write(OrginfoCache.findOrgNameByCode(this.code));
        	}
        	            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
}
