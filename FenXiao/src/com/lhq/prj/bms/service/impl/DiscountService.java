package com.lhq.prj.bms.service.impl;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IDiscountDao;
import com.lhq.prj.bms.po.Discount;
import com.lhq.prj.bms.service.IDiscountService;

public class DiscountService implements IDiscountService {
	private IDiscountDao discountDao;
	
	public Object saveDiscount(Discount discount) {
		return discountDao.saveDiscount(discount);
	}

	public Page findByPage(Page page) {
		page.setRoot(discountDao.findByPage(page));
		page.setTotalProperty(discountDao.findByCount(page));
		return page;
	}

	public boolean updateDiscount(Discount discount) throws Exception {
		return discountDao.update(discount) != null;

	}

	public boolean deleteDiscount(Page pageBean) {
		return discountDao.deleteById(pageBean) != null;
	}

	public IDiscountDao getDiscountDao() {
		return discountDao;
	}

	public void setDiscountDao(IDiscountDao discountDao) {
		this.discountDao = discountDao;
	}

	public Object findByExample(Discount discount) {
		return discountDao.findByExample(discount);
	}
	
	public Page findPageByExample(Discount discount) {
		Page pageBean = new Page();
		pageBean.setRoot(discountDao.findPageByExample(discount));
		return pageBean;
	}
}
