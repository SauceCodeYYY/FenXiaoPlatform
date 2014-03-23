package com.lhq.prj.bms.po;

import java.io.Serializable;

/**
 * Subject.java Create on 2008-9-21 下午03:42:14
 * 
 * 商品类
 * 
 * Copyright (c) 2008 by MTA.
 * 
 * @author 廖瀚卿
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Subject implements Serializable {


	/** 商品id */
	private Integer subjectId;
	private String novid;
	private String brand;
	private String sizeone;
	private String sizetwo;
	private String largeclass;
	private String styles;
	private String color;
	private String object;
	private String subjectName;
	private String tagprice;
	private String discount;
	private String season;
	private String series;
	private String sex;
	private String year;
	private String remarks;
	private String province;
	private String total;
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public String getNovid() {
		return novid;
	}
	public void setNovid(String novid) {
		this.novid = novid;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSizeone() {
		return sizeone;
	}
	public void setSizeone(String sizeone) {
		this.sizeone = sizeone;
	}
	public String getSizetwo() {
		return sizetwo;
	}
	public void setSizetwo(String sizetwo) {
		this.sizetwo = sizetwo;
	}
	public String getLargeclass() {
		return largeclass;
	}
	public void setLargeclass(String largeclass) {
		this.largeclass = largeclass;
	}
	public String getStyles() {
		return styles;
	}
	public void setStyles(String styles) {
		this.styles = styles;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getTagprice() {
		return tagprice;
	}
	public void setTagprice(String tagprice) {
		this.tagprice = tagprice;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
	
	

}
