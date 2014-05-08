package com.lhq.prj.bms.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IOrderDao;
import com.lhq.prj.bms.po.Order;

public class OrderDao extends SqlMapClientDaoSupport implements IOrderDao {

	public Object saveOrder(Order order) {
		return getSqlMapClientTemplate().insert("Order.save", order);
	}

	public List findByPage(Page page) {
		return getSqlMapClientTemplate().queryForList("Order.findByPage", page);
	}

	public int findByCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Order.findByCount", page);
	}

	public Integer update(Order order) throws Exception {
		return getSqlMapClientTemplate().update("Order.update", order);
	}

	public List findByTime(Page page) {
		return getSqlMapClientTemplate().queryForList("Order.findByTime", page);
	}

	public Integer findByTimeCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Order.findByTimeCount", page);
	}

	public List findByTimeAndUser(Page page) {
		return getSqlMapClientTemplate().queryForList(
				"Order.findByUserAndTime", page);
	}

	public Integer findByTimeAndUserCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Order.findByUserAndTimeCount", page);
	}

	public List findByUser(Page page) {
		return getSqlMapClientTemplate().queryForList(
				"Order.findByUser", page);
	}

	public Integer findByUserCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Order.findByUserCount", page);
	}

}
