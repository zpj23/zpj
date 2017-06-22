package com.goldenweb.sys.util.tag;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**  
 * @Description: TODO(文件上传)
 * @author ljn  
 * @date 2014-2-25 下午2:31:41
 */
public class CopyOfFileUploadTag extends TagSupport {
	private String id;
	private String moduleType;
	private String moduleID;
	private String mode;
	
	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();  
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script type=\"text/javascript\">FileUpload.init('"+mode+"','"+moduleType+"','"+moduleID+"','"+id+"','"+request.getContextPath()+"');</script>");
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
        	/*buffer.append("<link href=\""+request.getContextPath()+"/js/uploadify/uploadify.css\" rel=\"stylesheet\" type=\"text/css\" />")
        			.append("<script src=\""+request.getContextPath()+"/js/uploadify/jquery.uploadify.min.js\" type=\"text/javascript\"></script>")
        					.append("<script src=\""+request.getContextPath()+"/js/uploadify/swfobject.js\" type=\"text/javascript\"></script>")
        						.append("<script src=\""+request.getContextPath()+"/js/comm/fileUpload.js\" type=\"text/javascript\"></script>");*/
        	buffer.append("<link href=\""+request.getContextPath()+"/js/uploadify/uploadify.css\" rel=\"stylesheet\" type=\"text/css\" />")
			.append("<script src=\""+request.getContextPath()+"/js/uploadify/jquery.uploadify.min.js?ver="+UUID.randomUUID().toString()+"\" type=\"text/javascript\"></script>")
					.append("<script src=\""+request.getContextPath()+"/js/uploadify/swfobject.js\" type=\"text/javascript\"></script>")
						.append("<script src=\""+request.getContextPath()+"/js/comm/fileUpload.js\" type=\"text/javascript\"></script>");
        	
        	buffer.append("<input type=\"file\" name=\""+id+"\" id=\""+id+"\" />");
        	//buffer.append("<input type=\"hidden\" id=\""+id+"_ModuleType\" name=\""+id+"_ModuleType\" />");
        	//buffer.append("<input type=\"hidden\" id=\""+id+"_ModuleID\" name=\""+id+"_ModuleID\" />");
        	//buffer.append("<input type=\"hidden\" id=\""+id+"_IsView\" name=\""+id+"_IsView\"   />");
        	buffer.append("<div id=\"FileList_"+id+"\" ></div>");
        	
        	this.pageContext.getOut().write(buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }
    
    public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getModuleID() {
		return moduleID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
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
