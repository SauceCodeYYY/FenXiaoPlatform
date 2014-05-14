package com.lhq.prj.bms.service.impl;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IOrderDao;
import com.lhq.prj.bms.po.Order;
import com.lhq.prj.bms.service.IOrderService;

public class OrderService implements IOrderService {
	private IOrderDao orderDao;

	public Object saveOrder(Order order) {
		return orderDao.saveOrder(order);

	}

	public boolean updateOrder(Order order) throws Exception {
		return orderDao.update(order) != null;
	}

	public Page findByPage(Page page) {
		page.setRoot(orderDao.findByPage(page));
		page.setTotalProperty(orderDao.findByCount(page));
		return page;
	}

	public Page findByTime(Page page) {
		page.setRoot(orderDao.findByTime(page));
		page.setTotalProperty(orderDao.findByTimeCount(page));
		return page;
	}

	public Page findByTimeAndUser(Page page) {
		page.setRoot(orderDao.findByTimeAndUser(page));
		page.setTotalProperty(orderDao.findByTimeAndUserCount(page));
		return page;
	}

	public Page findByUser(Page page) {
		page.setRoot(orderDao.findByUser(page));
		page.setTotalProperty(orderDao.findByUserCount(page));
		return page;
	}

	public IOrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(IOrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public Page findByExactTime(Page page) {
		page.setRoot(orderDao.findByExactSubmitTime(page));
		return page;
	}
}
