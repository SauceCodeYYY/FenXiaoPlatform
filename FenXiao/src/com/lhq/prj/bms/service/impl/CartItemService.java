/*
 * @(#)CartItemService.java 2008-10-11
 *
 * Copyright LHQ. All rights reserved.
 */

package com.lhq.prj.bms.service.impl;

import com.lhq.prj.bms.core.Page;

import com.lhq.prj.bms.dao.ICartItemDao;

import com.lhq.prj.bms.po.CartItem;
import com.lhq.prj.bms.service.ICartItemService;

public class CartItemService implements ICartItemService {

	private ICartItemDao cartItemDao;

	public void setCartItemDao(ICartItemDao cartItemDao) {
		this.cartItemDao = cartItemDao;
	}

	public boolean deleteCartItem(Integer cartItemId) {
		Integer flag = cartItemDao.deleteById(cartItemId);
		if (flag != null) {
			return true;
		}
		return false;
	}

	public Page findByPage(Page page) {
		page.setRoot(cartItemDao.findByPage(page));
		page.setTotalProperty(cartItemDao.findByCount(page));
		return page;
	}

	public Object saveCartItem(CartItem cartItem) throws Exception {
		/*
		 * if(flag != null){ Book book = new Book(cartItem.getSubjectId());
		 * book.setLogId((Integer)flag); book.setState(0); bookDao.update(book);
		 * }
		 */
		return cartItemDao.saveCartItem(cartItem);
	}

	public boolean updateCartItem(CartItem cartItem) throws Exception {
		Integer flag = cartItemDao.update(cartItem);
		if (null != flag) {
			return true;
		}
		return false;
	}

	// public List findByall(User user) {
	// // TODO Auto-generated method stub
	// return cartItemDao.findByall(user);
	// }

	public boolean deleteCartItem(String ids) {
		String[] idsStrings = ids.split(",");
		int size = 0;
		for (int i = 0; i < idsStrings.length; i++) {
			size += cartItemDao.deleteById(Integer.valueOf(idsStrings[i]));
		}

		if (size == idsStrings.length) {
			return true;
		} else {
			return false;
		}
	}

}
