package com.goldenweb.sys.util.tag;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.goldenweb.fxpg.cache.impl.OrginfoCache;

@SuppressWarnings("serial")
public class SelectTag extends TagSupport {

    private String type;
    private String no;
    private String id;
    private String name;
    private String style;
    private String onchange;
    private String cssclass;
    private String readonly;
    private String headerKey;
    private String headerValue;
    private String disabled;
    private String required;
    private String validType;
    private String isarea;

    
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getHeaderKey() {
        return headerKey;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

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

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public String getCssclass() {
        return cssclass;
    }

    public void setCssclass(String cssclass) {
        this.cssclass = cssclass;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}
	public String getIsarea() {
		return isarea;
	}
	public void setIsarea(String isarea) {
		this.isarea = isarea;
	}

	@Override
	public int doStartTag() throws JspException {
        if (null == this.no) {
            this.no = "";
        }
        if(null == this.required){
        	this.required = "false";
        }
        try {
            /* 插入取得当前SELECTBOX的值 */
            StringBuffer buffer = new StringBuffer();
            StringBuffer option = new StringBuffer();
            if(this.headerValue != null && !this.headerValue.equals("")) {
                option.append("<option value='");
                if(this.headerValue != null && !this.headerValue.equals("")) {
                    option.append(this.headerKey);
                }
                option.append("'>" + this.headerValue + "</option>");
            }
            /* 插入option */
            Map<String,String> codeMap ;
            String typecode = this.type;
            if (null != this.isarea && !"".equals(this.isarea)) {
            	if("".equals(typecode)){
            		typecode =null;
            	}
            	codeMap = OrginfoCache.getCodeMapByType(typecode);
            }else{
            	codeMap = ResourceCodeUtil.getCodeMapByType(typecode);
            }
            
            
            
            Set<Entry<String,String>> set = codeMap.entrySet();
            Iterator<Entry<String,String>> iterator = set.iterator();
            Entry<String,String> entry;
            while(iterator.hasNext()) {
                entry = iterator.next();
                option.append("<option value='" + entry.getKey() + "'");
                if(this.no.equals(entry.getKey())) {
                    option.append(" selected");
                }
                option.append(">" + entry.getValue() + "</option>");
            }
            
            /* 插入名称和ID */
            buffer.append("<select name='" + this.name + "'  ");
            buffer.append(" id='" + this.id + "'");

            /* 插入样式 */
            if (null != this.style && !"".equals(this.style)) {
                buffer.append(" style='" + this.style + "'");
            }
            if (null != this.readonly && !"".equals(this.readonly)) {
                buffer.append(" readonly='" + this.readonly + "'");
            }
            if (null != this.disabled && !"".equals(this.disabled)) {
                buffer.append(" disabled='" + this.disabled + "'");
            }
            if (null != this.onchange && !"".equals(onchange)) {
                buffer.append(" onchange=\"" + this.onchange + "\"");
            }
            if (null != this.cssclass && !"".equals(this.cssclass)) {
                buffer.append(" class='" + this.cssclass + "'");
            }
            if (null != this.required && !"".equals(this.required)) {
                buffer.append(" required='" + this.required + "'");
            }
            if (null != this.validType && !"".equals(this.validType)) {
                buffer.append(" validType=\"" + this.validType + "\"");
            }
            
            
            buffer.append(">");
            buffer.append(option);
            buffer.append("</select>");
            /* 插入JSP画面 */
            this.pageContext.getOut().write(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
}
