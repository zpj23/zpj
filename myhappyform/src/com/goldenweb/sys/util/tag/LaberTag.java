package com.goldenweb.sys.util.tag;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


@SuppressWarnings("serial")
public class LaberTag extends TagSupport {
    private String type;
    private String no;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @Override
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
	public int doStartTag() throws JspException {
        try {
        	if(no.indexOf(",")>-1){
        		String str ="";
        		String no_ [] = no.split(",");
        		for(int i=0;i<no_.length;i++){
        			if(!"".equals(no_[i].trim())){
        				str+=ResourceCodeUtil.getCodeName(type, no_[i])+",";
        			}
        		}
        		if(!"".equals(str)){
        			str= str.substring(0,str.length()-1);
        		}
        		this.pageContext.getOut().write(str);
        	}else{
        		this.pageContext.getOut().write(ResourceCodeUtil.getCodeName(type, no));
        	}            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
}
