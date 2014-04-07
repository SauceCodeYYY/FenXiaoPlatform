package com.lhq.prj.bms.po;

import java.io.Serializable;

/**
 * User.java Create on 2008-9-18 下午09:32:48
 * 
 * 用户类
 * 
 * Copyright (c) 2008 by MTA. Download by http://www.codefans.net
 * 
 * @author 廖瀚卿
 * @version 1.0
 */
@SuppressWarnings("serial")
public class User implements Serializable {

	public User() {
	}

	/** 用户id */
	private Long userId;

	/** 用户名 */
	private String userName;

	/** 邮箱 */
	private String email;

	/** 移动电话 */
	private String phone;

	/** 角色 */
	private String role;

	/** 密码 */
	private String password;

	/** 积分 */
	private Integer points;

	/** 旺旺ID */
	private String wwId;

	/** 余额 */
	private Float balance;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getWwId() {
		return wwId;
	}

	public void setWwId(String wwId) {
		this.wwId = wwId;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

}
