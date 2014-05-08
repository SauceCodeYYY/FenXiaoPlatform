/*
 * @(#)CartItemDao.java 2008-10-11
 *
 * Copyright LHQ. All rights reserved.
 */

package com.lhq.prj.bms.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.ICartItemDao;
import com.lhq.prj.bms.po.CartItem;
import com.lhq.prj.bms.po.User;

public class CartItemDao extends SqlMapClientDaoSupport implements ICartItemDao {

	public Integer deleteById(Integer cartItemId) {
		return getSqlMapClientTemplate().delete("CartItem.deleteById", cartItemId);
	}

	public int findByCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject("CartItem.findByCount", page);
	}

	public List findByPage(Page page) {
		return getSqlMapClientTemplate().queryForList("CartItem.findByPage", page);
	}

	public Object saveCartItem(CartItem cartItem) {
		return getSqlMapClientTemplate().insert("CartItem.save", cartItem);
	}

	public Integer update(CartItem cartItem) throws Exception {
		return getSqlMapClientTemplate().update("CartItem.update", cartItem);
	}
//
//	public List findByall(User user) {
//		// TODO Auto-generated method stub
//		return getSqlMapClientTemplate().queryForList("CartItem.findAll",user);
//	}

}
