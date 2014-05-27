package com.lhq.prj.bms.po;

import java.io.Serializable;

public class Feedback implements Serializable{
	public Feedback()
	{
		
	}
	
	private Integer feedbackId;
	private String userid;
	private String dingdanhao;//订单号
	private String danhao;//单号
	private String yunfei;//运费
	private String zhekou;//折扣
	private String jinou;//金额
	private String sku;//SKU 货号
	private String sizeone;	//尺码1
	private String sizetwo;	//尺码2
	private String numberl	;	//数量
	private String commodity;	//商品名称
	private String price;//上市价格
	private String methods;	//发货方式
	private String address;	//发货地址
	private String userName;//姓名
	private String 	phone;//电话
	private String 	zipcode;//邮编
	private String	channels;	//发货渠道
	private String	leaf;//叶子编号
	private String	remarks	;//备注
	
	

	public Integer getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getDingdanhao() {
		return dingdanhao;
	}
	public void setDingdanhao(String dingdanhao) {
		this.dingdanhao = dingdanhao;
	}
	public String getDanhao() {
		return danhao;
	}
	public void setDanhao(String danhao) {
		this.danhao = danhao;
	}
	
	public String getZhekou() {
		return zhekou;
	}
	public void setZhekou(String zhekou) {
		this.zhekou = zhekou;
	}
	
	public String getJinou() {
		return jinou;
	}
	public void setJinou(String jinou) {
		this.jinou = jinou;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
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
	
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	
	public String getMethods() {
		return methods;
	}
	public void setMethods(String methods) {
		this.methods = methods;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getChannels() {
		return channels;
	}
	public void setChannels(String channels) {
		this.channels = channels;
	}
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getYunfei() {
		return yunfei;
	}
	public void setYunfei(String yunfei) {
		this.yunfei = yunfei;
	}
	public String getNumberl() {
		return numberl;
	}
	public void setNumberl(String numberl) {
		this.numberl = numberl;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
