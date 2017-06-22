package com.goldenweb.fxpg.frame.pojo;

import java.util.List;

public class HighChartsData {

	// 列名称
	private List<String> categories;
	
	// 统计数值
	private String y;
	
	// 统计颜色
	private String color;

	public HighChartsData() {
		super();
	}

	public HighChartsData(List<String> categories, String y, String color) {
		super();
		this.categories = categories;
		this.y = y;
		this.color = color;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
