package com.goldenweb.fxpg.weboffice.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-10 下午4:47:30
 */
@Entity
@Table(name = "WEBOFFICE_TEMPLATE_BOOKMARKS")
public class TemplateBookMarks implements java.io.Serializable {
	private Integer BookMarkId;
	private String RecordId;
	private String BookMarkName;
	
	@Id
    @Column(name = "BookMarkId", nullable = false, length=36)
	public Integer getBookMarkId() {
		return BookMarkId;
	}
	public void setBookMarkId(Integer bookMarkId) {
		BookMarkId = bookMarkId;
	}
	
	@Column(name = "RecordId", length=50)
	public String getRecordId() {
		return RecordId;
	}
	public void setRecordId(String recordId) {
		RecordId = recordId;
	}
	
	@Column(name = "BookMarkName", length=120)
	public String getBookMarkName() {
		return BookMarkName;
	}
	public void setBookMarkName(String bookMarkName) {
		BookMarkName = bookMarkName;
	}
	
	
}
