package com.goldenweb.sys.util.tag;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


@SuppressWarnings("serial")
public class Laber2Tag extends TagSupport {
    private String key;

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
	public int doStartTag() throws JspException {
        try {
        	if(key.indexOf(",")>-1){
        		String str ="";
        		String no_ [] = key.split(",");
        		for(int i=0;i<no_.length;i++){
        			if(no_[i]!=""){
        				String name = ResourceCodeUtil.getDictNameByCode(no_[i]);
        				if(name!=null&&!"".equals(name)){
        					str+=name+",";
        				}        				
        			}
        		}
        		if(!"".equals(str)){
        			str= str.substring(0,str.length()-1);
        		}
        		this.pageContext.getOut().write(str);
        	}else{
            this.pageContext.getOut().write(ResourceCodeUtil.getDictNameByCode(key));
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
}
