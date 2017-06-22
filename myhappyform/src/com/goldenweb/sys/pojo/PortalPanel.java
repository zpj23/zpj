package com.goldenweb.sys.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-3 下午5:20:09
 */
@Entity
@Table(name = "SYS_PORTAL_PANEL")
public class PortalPanel implements java.io.Serializable {
	String id;
    String title;
    String height;
    String width;
    String collapsible;
    String closable;
    String href;
    String content;
    // 自定义工具状态
    private String toolsStatus;
    
    @Id
    @Column(name = "id", nullable = false, length = 50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "height", length = 20)
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	
	@Column(name = "width", length = 20)
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	
	@Column(name = "collapsible", length = 10)
	public String getCollapsible() {
		return collapsible;
	}
	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}
	
	@Column(name = "closable", length = 10)
	public String getClosable() {
		return closable;
	}
	public void setClosable(String closable) {
		this.closable = closable;
	}
	
	@Column(name = "content", length = 500)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "href", length = 200)
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	@Transient
	public String getToolsStatus() {
		return toolsStatus;
	}
	public void setToolsStatus(String toolsStatus) {
		this.toolsStatus = toolsStatus;
	}
	
}
