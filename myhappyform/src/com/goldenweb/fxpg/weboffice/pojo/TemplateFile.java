package com.goldenweb.fxpg.weboffice.pojo;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-10 下午4:51:05
 */
@Entity
@Table(name = "WEBOFFICE_TEMPLATE_FILE")
public class TemplateFile implements java.io.Serializable {
	private Integer TemplateId;
	private String RecordId;
	private String FileName;
	private String FileType;
	private Integer FileSize;
	private Date FileDate;
	private byte[] FileBody;
	private String FilePath;
	private String UserName;
	private String Descript;
	
	@Id
    @Column(name = "TemplateId", nullable = false, length=36) 
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SQ_TEMPLATEFILE")   
//	@SequenceGenerator(name="SQ_TEMPLATEFILE", allocationSize=1, initialValue=1,sequenceName="SQ_TEMPLATEFILE") 
	public Integer getTemplateId() {
		return TemplateId;
	}
	public void setTemplateId(Integer templateId) {
		TemplateId = templateId;
	}
	
	@Column(name = "RecordId", length=16)
	public String getRecordId() {
		return RecordId;
	}
	public void setRecordId(String recordId) {
		RecordId = recordId;
	}
	
	@Column(name = "FileName", length=254)
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	
	@Column(name = "FileType", length=50)
	public String getFileType() {
		return FileType;
	}
	public void setFileType(String fileType) {
		FileType = fileType;
	}
	
	@Column(name = "FileSize")
	public Integer getFileSize() {
		return FileSize;
	}
	public void setFileSize(Integer fileSize) {
		FileSize = fileSize;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FileDate", length=7)
	public Date getFileDate() {
		return FileDate;
	}
	public void setFileDate(Date fileDate) {
		FileDate = fileDate;
	}
	
	@Lob  
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "FileBody", columnDefinition = "BLOB") 
	public byte[] getFileBody() {
		return FileBody;
	}
	public void setFileBody(byte[] fileBody) {
		FileBody = fileBody;
	}
	
	@Column(name = "FilePath", length=255)
	public String getFilePath() {
		return FilePath;
	}
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
	
	@Column(name = "UserName", length=64)
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	@Column(name = "Descript", length=255)
	public String getDescript() {
		return Descript;
	}
	public void setDescript(String descript) {
		Descript = descript;
	}
}
