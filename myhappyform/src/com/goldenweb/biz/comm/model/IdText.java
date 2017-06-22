package com.goldenweb.biz.comm.model;
/**  
 * @Description: TODO(用一句话描述该文件做什么)
 * @author ljn  
 * @date 2014-5-13 上午10:45:55
 */
public class IdText {
	private String id;
	private String text;
	
	public IdText(String id,String text){
		this.id = id;
		this.text = text;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
