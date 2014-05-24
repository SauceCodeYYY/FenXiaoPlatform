package com.lhq.prj.bms.dao;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Discount;

public interface IDiscountDao {
	public Object saveDiscount(Discount discount);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Discount discount) throws Exception;

	public Integer deleteById(Page pageBean);

	public Object findByExample(Discount discount);
	
	public List findPageByExample(Discount discount);
}
