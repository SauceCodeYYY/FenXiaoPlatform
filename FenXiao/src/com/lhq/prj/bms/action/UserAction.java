package com.lhq.prj.bms.action;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.md5.MyMD5Util;
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

	private Long userId;

	private String userName;

	private String password;

	private boolean manager;

	private String tip;

	private Float total;

	private String oldPwd;

	private String oldPayPwd;
	
	private static Map usersl = new HashMap();

	public String logout() {
		getSession().removeAttribute("user");
		success = true;
		return SUCCESS;
	}

	public static boolean loginValid(String userNames, String passwords)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String pwdInDb = (String) usersl.get(userNames);
		System.out.println("===UserAction.loginValid===" + pwdInDb);
		if (null != pwdInDb) { // 该用户存在
			return MyMD5Util.validPassword(passwords, pwdInDb);
		} else {
			System.out.println("不存在该用户！！！");
			return false;
		}
	}

	public String login() {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		// ////////////md5
		System.out.println("===UserAction.login===" + getRequest().getParameter("userName") + " " + getRequest().getParameter("password"));
		User _user = userService.login(user);
		System.out.println("===UserAction.login===" + _user);
		if (_user != null) {
			try {
				if (loginValid(_user.getUserName(), _user.getPassword())) {
					System.out.println("欢迎登陆！！！");
					if ("admin".equals(_user.getRole())) {
						this.setTip("admin");// 管理员
					} else {
						this.setTip("user");// 普通用户
					}

					getSession().setAttribute("user", _user);
					success = true;
				} else {
					System.out.println("口令错误，请重新输入！！！");
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else {
			this.setTip("用户名或者密码错误!");
			return INPUT;
		}
		return SUCCESS;
	}

	public String checkPayPwd() {
		User currUser = (User) getSession().getAttribute("user");
		if (null == currUser) {
			return ERROR;
		}
		if (user.getPayPwd().equals(currUser.getPayPwd())) {
			success = true;
		} else {
			success = false;
		}
		return SUCCESS;
	}

	public String checkBalance() {
		User currUser = (User) getSession().getAttribute("user");
		if (null == currUser) {
			return ERROR;
		}
		if (currUser.getBalance() >= total) {
			success = true;
		} else {
			success = false;
		}
		return SUCCESS;
	}

	/**
	 * 注册
	 * 
	 * @return
	 */

	public String register() {
		System.out.println("begin action");
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		userId = (Long) userService.saveUser(user);
		if (userId != null) {
			success = true;

		} else {
			return ERROR;
		}
		return SUCCESS;
	}

	public String test1() {
		System.out.println("123123132");
		return SUCCESS;
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	public String saveUser() {
		userId = (Long) userService.saveUser(user);
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
		for (String c : conditions) {
			try {
				utf8Condition
						.add(new String(c.getBytes("iso-8859-1"), "utf-8"));
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
		if (currUser == null) {
			return ERROR;
		}
		if (user != null) {
			// System.out.println(user.getPassword());
			// System.out.println(oldPwd);
			if (user.getPassword() != null && !user.getPassword().isEmpty()) {
				if (MyMD5Util.validPassword(oldPwd, currUser.getPassword())) {
					user.setPassword(MyMD5Util.getEncryptedPwd(user.getPassword()));
					success = userService.updateUser(user);
				} else {
					success = false;
					setTip("登录密码错误!");
				}
			} else if (user.getPayPwd() != null && !user.getPayPwd().isEmpty()) {
				if (MyMD5Util.validPassword(oldPayPwd, currUser.getPayPwd())) {
					user.setPayPwd(MyMD5Util.getEncryptedPwd(user.getPayPwd()));
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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
