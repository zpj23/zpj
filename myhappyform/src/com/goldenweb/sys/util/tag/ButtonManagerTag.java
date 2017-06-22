package com.goldenweb.sys.util.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.goldenweb.sys.pojo.SysFunction;

// <mf:buttonManager url="28" onClickName="toAdd()" value="新增"></mf:buttonManager>
public class ButtonManagerTag extends TagSupport {
	
	 /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /* 按钮的Url */
    private String url;
    /* 按钮名称 */
    private String value;
    /* 如果按钮的Click调用的不是默认的doSubmit事件，那么写入调用的方法名 */
    private String onClickName;
    /* 按钮的类型 */
    private String type;
    /* 按钮的长度 */
    private String width;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOnClickName() {
        return onClickName;
    }

    public void setOnClickName(String onClickName) {
        this.onClickName = onClickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    /* 按钮开始标记 */
    @Override
	public int doStartTag() throws JspException {
        /* 获得Session */
        HttpSession session = this.pageContext.getSession();
        /* 从session取得当前登录对象 */
        List<SysFunction> functionList = (List<SysFunction>) session.getAttribute("iusermenu");
        if (functionList != null && functionList.size() > 0) {
            /* 判断该URL是否属于该用户权限 */
            for (SysFunction tree : functionList) {
                String urlTree = tree.getId().toString();
                if (urlTree.equals(url)) {
                    /* 输出字符串 */
                    StringBuffer outButton = new StringBuffer();
                    
                	outButton.append("<input type=\"button\" class=\"main_button\" onclick=\"" + onClickName + "\"");
                    if (width != null && !width.equals("")){                    	
                    outButton.append(" style=\"width:" + width + "px;\"");
                    }
                    outButton.append(" value=\"" + value + "\"/>");
                    
                    try {
                        this.pageContext.getOut().write(outButton.toString());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return EVAL_BODY_INCLUDE;
    }

    /* 按钮结束 */
    @Override
	public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
