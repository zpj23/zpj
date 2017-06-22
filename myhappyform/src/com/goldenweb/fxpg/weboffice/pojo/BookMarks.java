package com.goldenweb.fxpg.weboffice.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-11 上午11:34:32
 */
@Entity
@Table(name = "WEBOFFICE_BOOKMARKS")
public class BookMarks {
	private Integer BookMarkId;
	private String BookMarkName;
	private String BookMarkDesc;
	private String BookMarkText;
	
	@Id
    @Column(name = "BookMarkId", nullable = false, length=36) 
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SQ_BOOKMARKS")   
//	@SequenceGenerator(name="SQ_BOOKMARKS", allocationSize=1, initialValue=1,sequenceName="SQ_BOOKMARKS")  
	public Integer getBookMarkId() {
		return BookMarkId;
	}
	public void setBookMarkId(Integer bookMarkId) {
		BookMarkId = bookMarkId;
	}
	
	@Column(name = "BookMarkName", length=64)
	public String getBookMarkName() {
		return BookMarkName;
	}
	public void setBookMarkName(String bookMarkName) {
		BookMarkName = bookMarkName;
	}
	
	@Column(name = "BookMarkDesc", length=128)
	public String getBookMarkDesc() {
		return BookMarkDesc;
	}
	public void setBookMarkDesc(String bookMarkDesc) {
		BookMarkDesc = bookMarkDesc;
	}
	
	@Column(name = "BookMarkText", length=200)
	public String getBookMarkText() {
		return BookMarkText;
	}
	public void setBookMarkText(String bookMarkText) {
		BookMarkText = bookMarkText;
	}
	
}
