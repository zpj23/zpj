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
public class SelectLinkTag extends TagSupport {

    private String type;
    private String no;
    private String name;
    private String style;
    private String cssclass;
    private String readonly;
    private String headerKey;
    private String headerValue;
    private String disabled;
    private String isarea;

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
    public String getIsarea() {
		return isarea;
	}
	public void setIsarea(String isarea) {
		this.isarea = isarea;
	}

	@Override
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
	public int doStartTag() throws JspException {    	
        if (null == this.no) {
            this.no = "";
        }
        String no_ [] = no.split(",");
        try {
        	      	        	
            /* 插入取得当前SELECTBOX的值 */
            StringBuffer buffer = new StringBuffer();
            /*需调用的js*/
            buffer.append(" <script type=\"text/javascript\"> ");
            buffer.append("  function showChildrenSelect(paramObj){");
            buffer.append("  $(paramObj).nextAll().remove(); ");
            buffer.append("  var selectObj_ = $('<select/>',{style:$(paramObj)[0].style ,name: $(paramObj)[0].name, id: $(paramObj)[0].name, onchange:\"showChildrenSelect(this)\"});");
            buffer.append("  var option_=\"\";");
            buffer.append("  $.ajax({");
            buffer.append("  type: \"post\",");
            buffer.append("  url: \"processAction_showChildrenSelect\",");
            buffer.append("  async :false,");
            buffer.append("  dataType:\"json\",");
            buffer.append("  data: {mapCodeType:$(paramObj).find(\"option:selected\").val()},");
            buffer.append("  success: function(data){   ");
            buffer.append("  $.each(data,function(k,v){");
            buffer.append("  option_ += $(\"<option/>\",{text: v, value: k})[0].outerHTML;");
            buffer.append("  });");
            buffer.append("  }");
            buffer.append("  });");
            buffer.append("  if(option_!=\"\"){");
            buffer.append("  $(paramObj).parent().append(selectObj_.append(option_))");//.prepend($(\"<option/>\",{text: \"------请选择------\"}))[0].outerHTML
            buffer.append("  }");
            buffer.append("  }");
            buffer.append("  </script>");
            
           String typecode = this.type;             
           for(int i=0;i<no_.length;i++){
        	   
            StringBuffer option = new StringBuffer();
            if(this.headerValue != null && !this.headerValue.equals("")) {
                option.append("<option value='");
                if(this.headerValue != null && !this.headerValue.equals("")) {
                    option.append(this.headerKey);
                }
                option.append("'>" + this.headerValue + "</option>");
            }
            /* 插入option */
            Map<String,String> codeMap;
            if (null != this.isarea && !"".equals(this.isarea)) {
            	if("".equals(typecode)){
            		typecode =null;
            	}
            	codeMap = OrginfoCache.getCodeMapByType(typecode);
            }else{
            	codeMap = ResourceCodeUtil.getCodeMapByType(typecode);
            } 
            
            if(codeMap.size()>0){
            Set<Entry<String,String>> set = codeMap.entrySet();
            Iterator<Entry<String,String>> iterator = set.iterator();
            Entry<String,String> entry;
            while(iterator.hasNext()) {
                entry = iterator.next();
                option.append("<option value='"+entry.getKey()+"'");
                if(no_[i].equals(entry.getKey())) {
                    option.append(" selected");
                }
                option.append(">"+entry.getValue()+"</option>");
            }
            
            
            /* 插入名称和ID */
            buffer.append("<select name='" + this.name + "'  ");
            buffer.append(" id='" + this.name + "'");

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
           
            buffer.append(" onchange=\"showChildrenSelect(this)\"");
           
            if (null != this.cssclass && !"".equals(this.cssclass)) {
                buffer.append(" class='" + this.cssclass + "'");
            }
            buffer.append(">");
            buffer.append(option);
            buffer.append("</select>");
            
            typecode = no_[i];
            }
          }
            
            /* 插入JSP画面 */
            this.pageContext.getOut().write(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
}
