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

	private Page pageBean;

	private Integer page;

	private Integer userId;

	private String userName;

	private String password;

	private boolean manager;

	private String tip;

	private Float total;
	
	private String oldPwd;
	
	private String oldPayPwd;
	
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

	public String checkPayPwd(){
		User currUser = (User)getSession().getAttribute("user");
		if (null == currUser){
			return ERROR;
		}
		if (user.getPayPwd().equals(currUser.getPayPwd())){
			success = true;
		} else {
			success = false;
		}
		return SUCCESS;
	}
	
	public String checkBalance(){
		User currUser = (User)getSession().getAttribute("user");
		if (null == currUser){
			return ERROR;
		}
		if (currUser.getBalance() >= total){
			success = true;
		} else {
			success = false;
		}
		return SUCCESS;
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
		pageBean = new Page();
		pageBean.setConditions(utf8Condition);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		pageBean = userService.findByPage(pageBean);
		return SUCCESS;
	}

	public String findByExample() {
		pageBean = new Page();
		pageBean.setRoot(userService.findByExample(user));
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
		User currUser = (User) getSession().getAttribute("user");
		if (currUser == null){
			return ERROR;
		}
		if (user != null) {
			if(user.getPassword() != null) {
				if (oldPwd.equals(currUser.getPassword())){
					success = userService.updateUser(user);
				} else {
					success = false;
					setTip("登录密码错误!");
				}
			} else if (user.getPayPwd() != null){
				if (currUser.getPayPwd().equals(oldPayPwd)){
					success = userService.updateUser(user);
				} else {
					success = false;
					setTip("支付密码错误!");
				}
			} else {
				success = userService.updateUser(user);
			}
		}
		return SUCCESS;
	}

	public Page getPageBean() {
		return pageBean;
	}

	public void setPageBean(Page page) {
		this.pageBean = page;
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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer pageS) {
		this.page = pageS;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getOldPayPwd() {
		return oldPayPwd;
	}

	public void setOldPayPwd(String oldPayPwd) {
		this.oldPayPwd = oldPayPwd;
	}

}
