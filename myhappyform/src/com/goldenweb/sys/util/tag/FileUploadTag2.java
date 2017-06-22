package com.goldenweb.sys.util.tag;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**  
 * @Description: TODO(文件上传)
 * @author ljn  
 * @date 2014-2-25 下午2:31:41
 */
public class FileUploadTag2 extends TagSupport {
	private String id;
	private String moduletype;
	private String moduleid;
	private String mode;
	
	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();  
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script type=\"text/javascript\">baidufile.init('"+mode+"','"+moduleid+"','"+id+"');</script>");
		try {
			this.pageContext.getOut().write(buffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return EVAL_PAGE;
    }

    @Override
	public int doStartTag() throws JspException {
    	HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();  
        try {
        	StringBuffer buffer = new StringBuffer();
        	
        	buffer.append("<link href=\"ueditor1_4_3-utf8-jsp/themes/default/css/ueditor.css\" rel=\"stylesheet\" type=\"text/css\" />")
			.append("<script src=\"ueditor1_4_3-utf8-jsp/ueditor.config.js\" charset=\"utf-8\" type=\"text/javascript\"></script>")
			.append("<script src=\"ueditor1_4_3-utf8-jsp/ueditor.all.js\" charset=\"utf-8\" type=\"text/javascript\"></script>")
			.append("<script src=\"ueditor1_4_3-utf8-jsp/lang/zh-cn/zh-cn.js\" charset=\"utf-8\" type=\"text/javascript\"></script>")
			.append("<script src=\"ueditor1_4_3-utf8-jsp/lang/en/en.js\"  charset=\"utf-8\"  type=\"text/javascript\"></script>")
			.append("<script src=\"js/comm/file.js\"    type=\"text/javascript\"></script>");
        	
        	buffer.append("<div id=\""+id+"\" style=\"height:0px;width:32px;\"></div>");
        	buffer.append("<input type=\"hidden\" id=\"fileid\" name=\"fileid\" value=\""+moduleid+"\" />");
        	buffer.append("<div id=\"FileList_"+id+"\" ></div>");
        	
        	this.pageContext.getOut().write(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
    
    

	public String getModuletype() {
		return moduletype;
	}

	public void setModuletype(String moduletype) {
		this.moduletype = moduletype;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
    
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
}
