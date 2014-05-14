package com.lhq.prj.bms.service;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Order;

public interface IOrderService {
	Object saveOrder(Order order);

	boolean updateOrder(Order order) throws Exception;

	// boolean deleteOrder(Integer orderId);

	// List findByExample(Order order);

	Page findByPage(Page page);

	Page findByTime(Page page);

	Page findByTimeAndUser(Page page);

	Page findByUser(Page page);
	
	Page findByExactTime(Page page);
}
