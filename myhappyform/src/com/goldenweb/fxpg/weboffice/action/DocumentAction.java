package com.goldenweb.fxpg.weboffice.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.goldenweb.sys.pojo.SysLog;
import com.goldenweb.sys.util.IAction;
import com.goldenweb.fxpg.frame.tools.Page;
import com.goldenweb.fxpg.weboffice.pojo.BookMarks;
import com.goldenweb.fxpg.weboffice.pojo.Document;
import com.goldenweb.fxpg.weboffice.pojo.Signature;
import com.goldenweb.fxpg.weboffice.pojo.TemplateFile;
import com.goldenweb.fxpg.weboffice.service.BookMarkService;
import com.goldenweb.fxpg.weboffice.service.DocumentService;
import com.goldenweb.fxpg.weboffice.service.SignatureService;
import com.goldenweb.fxpg.weboffice.service.TemplateFileService;
import com.google.gson.Gson;

/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-3-11 下午1:44:37
 */
@Namespace("/")
@Scope("prototype")
@Component("WebOfficeAction")
@ParentPackage("json-default")
public class DocumentAction extends IAction {
	
	@Autowired
	private DocumentService documentService;	
	@Autowired
	private BookMarkService bookMarkService;
	@Autowired
	private TemplateFileService templateFileService;
	@Autowired
	private SignatureService signatureService;
	
	/***************************************  Document Action begin ******************************************************/
	@Action(value = "WebOfficeAction_saveDocument", results = {
			@Result(name = "success", location = "weboffice/documentList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String saveDocument(){
		documentService.save(doc);
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	
	@SuppressWarnings("static-access")
	@Action(value ="WebOfficeAction_toDocumentEdit", results = {
			@Result(name = "success", location = "weboffice/documentEdit.jsp") })
	public String toDocumentEdit() {
		doc.setRecordId("123456");
		doc.setFileType(".doc");
		
		request.setAttribute("doc", doc);
		request.setAttribute("editType", "1,1");
		return this.SUCCESS; 
	}
	
	@Action(value = "WebOfficeAction_toDocumentList", results = {
			@Result(name = "success", location = "weboffice/documentList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toDocumentList() {
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	
	@Action(value="WebOfficeAction_DocumentListJson")
	public void DocumentListJson() throws IOException{	
		Page queryPage = new Page<SysLog>();
		queryPage.setPageSize(rows);
		queryPage.setNowPage(page);
		
		Page page = documentService.getByPage(queryPage, null);
		
		Gson gson = new Gson();
		//htmlValue = ;

		response.setCharacterEncoding("utf-8");// 指定为utf-8
		response.getWriter().write(gson.toJson(page.getMapResult()));// 转化为JSOn格式
		//documentService.save(object)
	}
	/***************************************  Document Action end ******************************************************/
	
	/***************************************  BookMark Action begin ******************************************************/
	@Action(value = "WebOfficeAction_saveBookMark", results = {
			@Result(name = "success", location = "weboffice/bookmark/bookMarkList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String saveBookMark(){
		bookMarkService.save(bookMark);
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	
	@Action(value = "WebOfficeAction_toDelBookMark", results = {
			@Result(name = "success", location = "weboffice/bookmark/bookMarkList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String delBookMark(){
		String ids = request.getParameter("ids");
		for(String id : ids.split(",")){
			bookMarkService.remove(Integer.parseInt(id));
		}
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}

	@Action(value="WebOfficeAction_toBookMarkEdit", results = {
			@Result(name = "success", location = "weboffice/bookmark/bookMarkEdit.jsp") })
	public String toBookMarkEdit(){
		if(bookMark !=null && bookMark.getBookMarkId()>0){
			bookMark = bookMarkService.get(bookMark.getBookMarkId());
		}
		return com.opensymphony.xwork2.Action.SUCCESS;
	}
	
	@Action(value = "WebOfficeAction_toBookMarkList", results = {
			@Result(name = "success", location = "weboffice/bookmark/bookMarkList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toBookMarkList(){
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	
	@Action(value="WebOfficeAction_BookMarkListJson")
	public void BookMarkListJson() throws IOException{
		Page queryPage = new Page<SysLog>();
		queryPage.setPageSize(rows);
		queryPage.setNowPage(page);
		Page page = bookMarkService.getByPage(queryPage, null);
		Gson gson = new Gson();
		response.setCharacterEncoding("utf-8");// 指定为utf-8
		response.getWriter().write(gson.toJson(page.getMapResult()));// 转化为JSOn格式
	}
	/***************************************  BookMark Action end ******************************************************/
	
	/***************************************  Template Action begin ******************************************************/
	@Action(value = "WebOfficeAction_toTemplateList", results = {
			@Result(name = "success", location = "weboffice/template/templateList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toTemplateList(){
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	
	@Action(value = "WebOfficeAction_toTemplateEdit", results = {
			@Result(name = "success", location = "weboffice/template/templateEdit.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toTemplateEdit(){
		String mRecordID=request.getParameter("RecordID");
		String mFileType="."+request.getParameter("FileType");
		
		if("".equals(mRecordID) || mRecordID==null){
			java.util.Date dt=new java.util.Date();
	        long lg=dt.getTime();
	        Long ld=new Long(lg);
	        //初始化值
	        mRecordID=ld.toString();
		}
		String mFileName="公文模版"+mFileType;
		String mDescript="发文公文模版";
		
		templateFile = new TemplateFile();
		templateFile.setRecordId(mRecordID);
		templateFile.setFileType(mFileType);
		templateFile.setFileName(mFileName);
		templateFile.setDescript(mDescript);
		
		
		request.setAttribute("templateFile", templateFile);
		request.setAttribute("editType", "1,1");
		request.setAttribute("userName", "Administrator");
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	
	@Action(value = "WebOfficeAction_saveTemplate", results = {
			@Result(name = "success", location = "weboffice/template/templateList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String saveTemplate(){
		templateFileService.save(templateFile);
		return SUCCESS;
	}
	
	@Action(value = "WebOfficeAction_updateTemplateFile", results = {
			@Result(name = "success", location = "weboffice/template/templateList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String updateTemplateFile(){
		templateFileService.updateTemplateFileByRecordId(templateFile);
		return SUCCESS;
	}
	
	@Action(value="WebOfficeAction_TemplateFileListJson")
	public void TemplateFileListJson() throws IOException{
		Page queryPage = new Page<SysLog>();
		queryPage.setPageSize(rows);
		queryPage.setNowPage(page);
		Page page = templateFileService.getByPage(queryPage, null);
		Gson gson = new Gson();
		response.setCharacterEncoding("utf-8");// 指定为utf-8
		response.getWriter().write(gson.toJson(page.getMapResult()));// 转化为JSOn格式
	}
	/***************************************  Template Action end ******************************************************/
	
	
	/***************************************  Signature Action begin ******************************************************/
	@Action(value = "WebOfficeAction_toSignatureList", results = {
			@Result(name = "success", location = "weboffice/signature/signatureList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toSignatureList(){
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	
	@Action(value = "WebOfficeAction_toSignatureEdit", results = {
			@Result(name = "success", location = "weboffice/signature/signatureEdit.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String toSignatureEdit(){
		if(signature!=null && signature.getSignatureId()>0){
			signature = signatureService.get(signature.getSignatureId());
		}
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	
	@Action(value = "WebOfficeAction_saveSignature", results = {
			@Result(name = "success", location = "weboffice/signature/signatureList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String saveSignature() throws SQLException{
		byte[] buffer = null;  
        try {    
            FileInputStream fis = new FileInputStream(signatureFile);  
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
        
        signature.setMarkType(signatureFileFileName.substring(signatureFileFileName.lastIndexOf(".")));
        signature.setMarkBody(buffer);
        signatureService.saveSignature(signature, null);
		

		return SUCCESS;
	}
	
	
	@Action(value="WebOfficeAction_SignatureListJson")
	public void SignatureListJson() throws IOException{
		Page queryPage = new Page<SysLog>();
		queryPage.setPageSize(rows);
		queryPage.setNowPage(page);
		Page page = signatureService.getByPage(queryPage, null);
		Gson gson = new Gson();
		response.setCharacterEncoding("utf-8");// 指定为utf-8
		response.getWriter().write(gson.toJson(page.getMapResult()));// 转化为JSOn格式
	}
	
	@Action(value = "WebOfficeAction_toDelSignature", results = {
			@Result(name = "success", location = "weboffice/signature/signatureList.jsp"),
			@Result(name = "error", location = "/error.jsp") })
	public String delSignature(){
		String ids = request.getParameter("ids");
		for(String id : ids.split(",")){
			signatureService.remove(Integer.parseInt(id));
		}
		return com.opensymphony.xwork2.Action.SUCCESS; 
	}
	/***************************************  Signature Action end ******************************************************/
	
	private String htmlValue;
	private Document doc;
	private BookMarks bookMark;
	private TemplateFile templateFile;
	private Signature signature;
	private File signatureFile;
	private String signatureFileFileName;
	
	public String getSignatureFileFileName() {
		return signatureFileFileName;
	}

	public void setSignatureFileFileName(String signatureFileFileName) {
		this.signatureFileFileName = signatureFileFileName;
	}

	public File getSignatureFile() {
		return signatureFile;
	}

	public void setSignatureFile(File signatureFile) {
		this.signatureFile = signatureFile;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public TemplateFile getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(TemplateFile templateFile) {
		this.templateFile = templateFile;
	}

	public BookMarks getBookMark() {
		return bookMark;
	}

	public void setBookMark(BookMarks bookMark) {
		this.bookMark = bookMark;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public String getHtmlValue() {
		return htmlValue;
	}
	
	public void setHtmlValue(String htmlValue) {
		this.htmlValue = htmlValue;
	}	
}
