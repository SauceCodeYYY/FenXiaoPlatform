package com.lhq.prj.bms.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.impl.UserDao;
import com.lhq.prj.bms.po.Recharge;
import com.lhq.prj.bms.po.User;
import com.lhq.prj.bms.service.IRechargeService;
import com.lhq.prj.bms.service.IUserService;

public class RechargeAction extends BaseAction {
	private static final long serialVersionUID = 5105023530432988011L;
	public static final String SUCCESS_MANAGER = "success_manager";

	private IRechargeService rechargeService;

	private IUserService userService;

	private Recharge recharge;

	private boolean success;

	private Page pageBean;

	private Integer page;

	private Integer rechargeId;

	public String saveRecharge() {
		rechargeId = (Integer) rechargeService.saveRecharge(recharge);
		if (rechargeId != null) {
			success = true;
		}
		return SUCCESS;
	}

	public String findAllRecharge() {
		System.out.println("RechargeAction.findAllRecharge");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		List<String> utf8Conditions = new ArrayList<String>();
		// utf8Conditions.add("");
		for (String condition : conditions) {
			try {
				utf8Conditions.add(new String(condition.getBytes("ISO-8859-1"),
						"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		pageBean = new Page();
		pageBean.setConditions(utf8Conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		if (getRequest().getParameter("from") != null
				&& getRequest().getParameter("to") != null) {
			utf8Conditions = new ArrayList<String>();
			// utf8Conditions.add("");
			utf8Conditions.add(getRequest().getParameter("from"));
			utf8Conditions.add(getRequest().getParameter("to"));
			pageBean.setConditions(utf8Conditions);
			pageBean = rechargeService.findByTime(pageBean);
		} else {
			pageBean = rechargeService.findByPage(pageBean);
		}
		return SUCCESS;
	}

	public String findAllRechargeByUser() {
		System.out.println("RechargeAction.findAllRechargeByUser");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		List<String> utf8Conditions = new ArrayList<String>();
		// utf8Conditions.add(getRequest().getParameter("userId"));
		for (String condition : conditions) {
			try {
				utf8Conditions.add(new String(condition.getBytes("ISO-8859-1"),
						"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		pageBean = new Page();
		User loginUser = (User) getSession().getAttribute("user");
		if (null == loginUser) {
			return ERROR;
		}
		pageBean.setUserId(loginUser.getUserId());
		pageBean.setConditions(utf8Conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		if (getRequest().getParameter("from") != null
				&& getRequest().getParameter("to") != null) {
			utf8Conditions = new ArrayList<String>();
			// utf8Conditions.add(getRequest().getParameter("userId"));
			utf8Conditions.add(getRequest().getParameter("from"));
			utf8Conditions.add(getRequest().getParameter("to"));
			pageBean.setConditions(utf8Conditions);
			pageBean = rechargeService.findByTimeAndUser(pageBean);
		} else {
			pageBean = rechargeService.findByPageAndUser(pageBean);
		}
		return SUCCESS;
	}

	public String findByExample() {
		pageBean = new Page();
		pageBean.setRoot(rechargeService.findByExample(recharge));
		return SUCCESS;
	}

	public String deleteRecharge() {
		String strRechargeId = getRequest().getParameter("rechargeId");
		if (strRechargeId != null && !"".equals(strRechargeId)) {
			success = rechargeService.deleteRecharge(Integer
					.parseInt(strRechargeId));
		}
		return SUCCESS;
	}

	public String updateRecharge() throws Exception {
		if (recharge.getRechargeId() != null) {
			success = rechargeService.updateRecharge(recharge);
			if (success && "已通过".equals(recharge.getState())) {
				Recharge r = (Recharge) rechargeService.findByExample(recharge)
						.get(0);
				User u = new User();
				u.setUserId(r.getUserId());
				u.setBalance(r.getAmount());
				success = userService.updateBalance(u);
			}
		}
		return SUCCESS;
	}

	public IRechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(IRechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public Recharge getRecharge() {
		return recharge;
	}

	public void setRecharge(Recharge recharge) {
		this.recharge = recharge;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Page getPageBean() {
		return pageBean;
	}

	public void setPageBean(Page page) {
		this.pageBean = page;
	}

	public Integer getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(Integer rechargeId) {
		this.rechargeId = rechargeId;
	}

	public void setPage(Integer pageI) {
		this.page = pageI;
	}

	public Integer getPage() {
		return page;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
