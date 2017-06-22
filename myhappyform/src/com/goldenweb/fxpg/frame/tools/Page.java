package com.goldenweb.fxpg.frame.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: Page
 * @Description: TODO(分页)
 * @author Lee
 * @date 2014-2-10 下午1:12:20
 *
 * @param <T> 
 */
public class Page<T> {

	private int totalResult; // 查询结果总数
	
	private int totalPage; // 查询结果总页数
	
	private List<T> results; // 当前查询返回结果集
	
	private int nowPage; // 当前第几页
	
	private int pageSize; // 当前加载数据条数
	
	public Page(){}
	
	public Page(int nowPage, int pageSize) {
		super();
		this.nowPage = nowPage;
		this.pageSize = pageSize;
	}
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	} 
	
	/**
	 * 当前第几页
	 * @Title getNowPage
	 * @return int  
	 * @author Lee
	 * @time 2014-3-24 下午01:15:04
	 */
	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
  
	public int getTotalResult() {
		return totalResult;
	}
  
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
  
	/**
	 * 查询结果总数
	 * @Title getResults
	 * @return List<T>  
	 * @author Lee
	 * @time 2014-3-24 下午01:15:18
	 */
	public List<T> getResults() {
		return results;
	}
  
	public void setResults(List<T> results) {
		this.results = results;
	} 
	
	//计算总页数
	public void calcuatePage() {
		if (totalResult > 0) {
			totalPage = totalResult % pageSize == 0 ? totalResult / pageSize : (totalResult / pageSize + 1);
		}
	}
	
	/**
	 * @Title getMapResult
	 * @Description 返回Map类型的页面查询结果 total查询的总数 rows查询的当前列表
	 * @return Map
	 * @author Lee
	 * @time 2014-2-10 下午1:17:08
	 */
	public Map getMapResult() {
		Map map = new HashMap();
		map.put("total", this.getTotalResult());
		map.put("rows", this.getResults());
		return map;
	}
	
	/**
	 * hibernate分页查询开始数
	 * @Title firstResult
	 * @return int  
	 * @author Lee
	 * @time 2014-5-9 下午02:32:14
	 */
	public int firstResult(){
		return (this.nowPage-1)*pageSize;
	}
}
