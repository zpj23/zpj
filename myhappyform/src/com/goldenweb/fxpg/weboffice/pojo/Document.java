package com.goldenweb.fxpg.weboffice.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-10 上午11:28:54
 */
@Entity
@Table(name = "WEBOFFICE_DOCUMENT")
public class Document  implements java.io.Serializable {
	private Integer DocumentId;
	private String RecordId;
	private String Template;
	private String Subject;
	private String Author;
	private Date FileDate;
	private String  FileType;
	private String HtmlPath;
	private String Status;
	
	@Id
    @Column(name = "DocumentId", nullable = false, length=36) 
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SQ_DOCUMENT")   
//	@SequenceGenerator(name="SQ_DOCUMENT", allocationSize=1, initialValue=1,sequenceName="SQ_DOCUMENT")   
	public Integer getDocumentId() {
		return DocumentId;
	}
	public void setDocumentId(Integer documentId) {
		DocumentId = documentId;
	}
	
	@Column(name = "RecordId", length = 36)
	public String getRecordId() {
		return RecordId;
	}
	public void setRecordId(String recordId) {
		RecordId = recordId;
	}
	
	@Column(name = "Template", length = 36)
	public String getTemplate() {
		return Template;
	}
	public void setTemplate(String template) {
		Template = template;
	}
	
	@Column(name = "Subject", length = 255)
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	
	@Column(name = "Author", length = 64)
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FileDate", length = 7)
	public Date getFileDate() {
		return FileDate;
	}
	public void setFileDate(Date fileDate) {
		FileDate = fileDate;
	}
	
	@Column(name = "FileType", length = 50)
	public String getFileType() {
		return FileType;
	}
	public void setFileType(String fileType) {
		FileType = fileType;
	}
	
	@Column(name = "HtmlPath", length = 128)
	public String getHtmlPath() {
		return HtmlPath;
	}
	public void setHtmlPath(String htmlPath) {
		HtmlPath = htmlPath;
	}
	
	@Column(name = "Status", length = 4)
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
}
