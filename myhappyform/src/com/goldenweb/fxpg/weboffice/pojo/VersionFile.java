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
 * @date 2014-3-11 上午11:23:11
 */
@Entity
@Table(name = "WEBOFFICE_VERSION_FILE")
public class VersionFile {
	private Integer FileId;
	private String RecordId;
	private String FileName;
	private String FileType;
	private Integer FileSize;
	private Date FileDate;
	private String FileBody;
	private String FilePath;
	private String UserName;
	private String Descript;
	
	@Id
    @Column(name = "FileId", nullable = false, length=36)
	public Integer getFileId() {
		return FileId;
	}
	public void setFileId(Integer fileId) {
		FileId = fileId;
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
	
	@Column(name = "FileType", length=4)
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
	public String getFileBody() {
		return FileBody;
	}
	public void setFileBody(String fileBody) {
		FileBody = fileBody;
	}
	
	@Column(name = "FilePath", length=128)
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
