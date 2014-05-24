package com.lhq.prj.bms.service;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Discount;

public interface IDiscountService {
	Object saveDiscount(Discount discount);

	Page findByPage(Page page);

	boolean updateDiscount(Discount discount) throws Exception;

	boolean deleteDiscount(Page pageBean);
	
	Object findByExample(Discount discount);

	Page findPageByExample(Discount discount);
}
