package com.lhq.prj.bms.po;

import java.util.Date;

public class CartItem {
	private Long cartItemId;
	private Long userId;
	private String novid;
	private String channel;
	private String size;
	private Integer amount;
	private Date time;
	private Integer subjectId;
	private Float tagprice;
	private Float discount;
	
	public String getNovid() {
		return novid;
	}

	public void setNovid(String novid) {
		this.novid = novid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Float getTagprice() {
		return tagprice;
	}

	public void setTagprice(Float tagprice) {
		this.tagprice = tagprice;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

}
