package com.lhq.prj.bms.po;

import java.io.Serializable;

/**
 * User.java Create on 2008-9-18 ����09:32:48
 * 
 * �û���
 * 
 * Copyright (c) 2008 by MTA. Download by http://www.codefans.net
 * 
 * @author �����
 * @version 1.0
 */
@SuppressWarnings("serial")
public class User implements Serializable {

	public User() {
	}

	/** �û�id */
	private Long userId;

	/** �û��� */
	private String userName;

	/** ���� */
	private String email;

	/** �ƶ��绰 */
	private String phone;

	/** ��ɫ */
	private String role;

	/** ���� */
	private String password;

	/** ���� */
	private Integer points;

	/** ����ID */
	private String wwId;

	/** ��� */
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
