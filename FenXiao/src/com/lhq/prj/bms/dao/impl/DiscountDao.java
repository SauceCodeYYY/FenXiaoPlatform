package com.lhq.prj.bms.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IDiscountDao;
import com.lhq.prj.bms.po.Discount;

public class DiscountDao extends SqlMapClientDaoSupport implements IDiscountDao {

	public Object saveDiscount(Discount discount) {
		return getSqlMapClientTemplate().insert("Discount.save", discount);
	}

	public List findByPage(Page page) {
		return getSqlMapClientTemplate().queryForList("Discount.findByPage",
				page);
	}

	public int findByCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Discount.findByCount", page);
	}

	public Integer update(Discount discount) throws Exception {
		return getSqlMapClientTemplate().update("Discount.update", discount);
	}

	public Integer deleteById(Page pageBean) {
		return getSqlMapClientTemplate().delete("Discount.deleteByIds",
				pageBean);
	}

	public Object findByExample(Discount discount) {
		return getSqlMapClientTemplate().queryForObject("Discount.findByExample",
				discount);
	}
	
	public List findPageByExample(Discount discount) {
		return getSqlMapClientTemplate().queryForList("Discount.findPageByExample",
				discount);
	}
}
