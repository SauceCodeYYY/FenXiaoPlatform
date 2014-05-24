package com.lhq.prj.bms.po;

import java.io.Serializable;

public class Commodity implements Serializable {
	private static final long serialVersionUID = -1793380622582674164L;

	// 商品编号
	private Long subjectId;
	// 货号
	private String novid;
	// 品牌
	private String brand;
	// 尺码1
	private String sizeone;
	// 尺码2
	private String sizetwo;
	// 大类
	private String largeclass;
	// 款型
	private String styles;
	// 颜色
	private String color;
	// 项目
	private String object;
	// 货号名称
	private String subjectName;
	// 吊牌价格
	private String tagprice;
	// 折扣
	private String discount;
	// 季节
	private String season;
	// 系列
	private String series;
	// 季节
	private String sex;
	// 年份
	private String year;
	// 备注
	private String remarks;
	// 总计
	private String total;
	// 渠道
	private String channel;
	// 新货号
	private String newNovid;
	// 月份
	private String monthl;
	// 库存
	private Integer numbers;

	private String state;

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNewNovid() {
		return newNovid;
	}

	public void setNewNovid(String newNovid) {
		this.newNovid = newNovid;
	}

	public String getMonthl() {
		return monthl;
	}

	public void setMonthl(String monthl) {
		this.monthl = monthl;
	}

	public Integer getNumbers() {
		return numbers;
	}

	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
