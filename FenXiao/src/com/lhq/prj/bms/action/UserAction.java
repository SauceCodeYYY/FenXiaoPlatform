package com.lhq.prj.bms.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.User;
import com.lhq.prj.bms.service.IUserService;

/**
 * UserAction.java Create on 2008-9-19 上午01:38:39
 * 
 * 用户处理
 * 
 * Copyright (c) 2008 by MTA. Download by http://www.codefans.net
 * 
 * @author
 * @version 1.0
 */
@SuppressWarnings("serial")
public class UserAction extends BaseAction {

	public static final String SUCCESS_MANAGER = "success_manager";

	private IUserService userService;

	private User user;

	private boolean success;

	private Page page;

	private Integer pageS;

	private Integer userId;

	private String userName;

	private String password;

	private boolean manager;

	private String tip;

	public String logout() {
		getSession().removeAttribute("user");
		success = true;
		return SUCCESS;
	}

	public String login() {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		User _user = userService.login(user);
		if (_user != null) {
			if ("admin".equals(_user.getRole())) {
				this.setTip("admin");// 管理员
			} else {
				this.setTip("user");// 普通用户
			}
			getSession().setAttribute("user", _user);
			return SUCCESS;
		} else {
			this.setTip("用户名或者密码错误!");
			return INPUT;
		}
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	public String saveUser() {
		userId = (Integer) userService.saveUser(user);
		if (userId != null) {
			success = true;
		}
		return SUCCESS;
	}

	/**
	 * 查找用户信息
	 * 
	 * @return
	 */
	public String findAllUser() {
		System.out.println("===");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		List<String> utf8Condition = new ArrayList<String>();
		for (String c: conditions){
			try {
				utf8Condition.add(new String(c.getBytes("iso-8859-1"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		page = new Page();
		page.setConditions(utf8Condition);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(++start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		page = userService.findByPage(page);
		return SUCCESS;
	}

	public String findByExample() {
		page = new Page();
		page.setRoot(userService.findByExample(user));
		return SUCCESS;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	public String deleteUser() {
		success = userService.deleteUser(Long.parseLong(userId.toString()));
		return SUCCESS;
	}

	/**
	 * 修改用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateUser() throws Exception {
		if (user.getUserId() != null) {
			success = userService.updateUser(user);
		}
		return SUCCESS;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Integer getPageS() {
		return pageS;
	}

	public void setPage(Integer pageS) {
		this.pageS = pageS;
	}

}
