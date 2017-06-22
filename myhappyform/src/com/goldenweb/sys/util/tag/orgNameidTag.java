package com.goldenweb.sys.util.tag;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.goldenweb.fxpg.cache.impl.OrginfoCache;


@SuppressWarnings("serial")
public class orgNameidTag extends TagSupport {
	//ids
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
        	String str = OrginfoCache.findOrgnamesByIds(code);
        	
        	this.pageContext.getOut().write(str);
        	            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
}
