/*
 * @(#)ICartItemService.java 2008-10-11
 *
 * Copyright LHQ. All rights reserved.
 */

package com.lhq.prj.bms.service;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.CartItem;

/**
 * Create on 2008-10-11 下午07:08:18 Download by http://www.codefans.net
 * 图书借出还书记录业务层接口
 * 
 * @author 廖瀚卿
 * @version
 */
public interface ICartItemService {
	/**
	 * 添加记录
	 * 
	 * @param cartItem
	 * @return
	 * @throws Exception
	 */
	Object saveCartItem(CartItem cartItem) throws Exception;

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            分页对象
	 * @return
	 */
	Page findByPage(Page page);

	/**
	 * 修改记录信息
	 * 
	 * @param cartItem
	 * @return
	 * @throws Exception
	 */
	boolean updateCartItem(CartItem cartItem) throws Exception;

	/**
	 * 删除记录
	 * 
	 * @param cartItemId
	 * @return
	 */
	boolean deleteCartItem(Integer cartItemId);

	/**
	 * 查找所有商品
	 * 
	 * @return
	 */
	// List findByall(User user);

	/**
	 * 删除记录
	 * 
	 * @param subjectId
	 * @return
	 */
	boolean deleteCartItem(String LogId);

}
