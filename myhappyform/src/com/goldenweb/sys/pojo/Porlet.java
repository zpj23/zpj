package com.goldenweb.sys.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-6-18 上午9:18:50
 */
@Entity
@Table(name = "SYS_PORLET")
public class Porlet {
	private Integer id;
	private String code;
	private String name;
	private Integer height;
	private String dataType; //service、query
	private String dataUrl;
	private String template;
	private String ismore; //是否需要显示更多,1:是，0:否
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id", nullable = false)  
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "code",length=50)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "name",length=100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "height")
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	@Column(name = "dataType",length=50)
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column(name = "dataUrl",length=200)
	public String getDataUrl() {
		return dataUrl;
	}
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	
	
	
	@Column(name = "ismore",length=50)
	public String getIsmore() {
		return ismore;
	}
	public void setIsmore(String ismore) {
		this.ismore = ismore;
	}
	
	
	/*@Lob 
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name="template", columnDefinition="CLOB", nullable=true,length=8000)*/
	@Column(name = "template",length=8000)
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
