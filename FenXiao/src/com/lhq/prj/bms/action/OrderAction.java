package com.lhq.prj.bms.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Order;
import com.lhq.prj.bms.po.User;
import com.lhq.prj.bms.service.ICartItemService;
import com.lhq.prj.bms.service.IOrderService;
import com.lhq.prj.bms.service.IUserService;

public class OrderAction extends BaseAction {
	private static final long serialVersionUID = -6152090171062953623L;

	private IOrderService orderService;

	private IUserService userService;

	private ICartItemService cartItemService;
	
	private Order order;

	private boolean success;

	private Page pageBean;

	private Integer page;

	private Long orderId;

	private String cartItemIds;
	
	public String saveOrder() {
		User currUser = (User) getSession().getAttribute("user");
		if (null == currUser) {
			return ERROR;
		}
		order.setUserId(currUser.getUserId());
		orderId = (Long) orderService.saveOrder(order);
		if (orderId != null) {
			success = true;
			currUser.setBalance(-order.getTotal());
			userService.updateBalance(currUser);
			cartItemService.deleteCartItem(cartItemIds);
		}
		return SUCCESS;
	}

	public String findAllOrder() {
		System.out.println("OrderAction.findAllOrder");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		pageBean = new Page();
		pageBean.setConditions(conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		if (getRequest().getParameter("from") != null
				&& getRequest().getParameter("to") != null) {
			conditions = new ArrayList<String>();
			// utf8Conditions.add("");
			conditions.add(getRequest().getParameter("from"));
			conditions.add(getRequest().getParameter("to"));
			pageBean.setConditions(conditions);
			pageBean = orderService.findByTime(pageBean);
		} else {
			System.out.println(conditions);
			pageBean = orderService.findByPage(pageBean);
		}
		return SUCCESS;
	}

	public String findAllOrderByUser() {
		System.out.println("OrderAction.findAllOrderByUser");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		// utf8Conditions.add(getRequest().getParameter("userId"));
		pageBean = new Page();
		User loginUser = (User) getSession().getAttribute("user");
		if (null == loginUser) {
			return ERROR;
		}
		pageBean.setUserId(loginUser.getUserId());
		pageBean.setConditions(conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		if (getRequest().getParameter("from") != null
				&& getRequest().getParameter("to") != null) {
			conditions = new ArrayList<String>();
			// utf8Conditions.add(getRequest().getParameter("userId"));
			conditions.add(getRequest().getParameter("from"));
			conditions.add(getRequest().getParameter("to"));
			pageBean.setConditions(conditions);
			pageBean = orderService.findByTimeAndUser(pageBean);
		} else {
			pageBean = orderService.findByUser(pageBean);
		}
		return SUCCESS;
	}

	// public String findByExample() {
	// pageBean = new Page();
	// pageBean.setRoot(orderService.findByExample(order));
	// return SUCCESS;
	// }
	//
	// public String deleteOrder() {
	// String strOrderId = getRequest().getParameter("orderId");
	// if (strOrderId != null && !"".equals(strOrderId)) {
	// success = orderService.deleteOrder(Integer
	// .parseInt(strOrderId));
	// }
	// return SUCCESS;
	// }

	public String updateOrder() throws Exception {
		if (order != null) {
			order.setCloseTime(new Date());
			success = orderService.updateOrder(order);
			if (success) {
				if (order.getState().equals("已关闭")){
					User u = new User();
					u.setUserId(order.getUserId());
					u.setBalance(order.getTotal());
					success = userService.updateBalance(u);
				}
			}
		}
		return SUCCESS;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public void setPageBean(Page pageBean) {
		this.pageBean = pageBean;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ICartItemService getCartItemService() {
		return cartItemService;
	}

	public void setCartItemService(ICartItemService cartItemService) {
		this.cartItemService = cartItemService;
	}

	public String getCartItemIds() {
		return cartItemIds;
	}

	public void setCartItemIds(String cartItemIds) {
		this.cartItemIds = cartItemIds;
	}
}