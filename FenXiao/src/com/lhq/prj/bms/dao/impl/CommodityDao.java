package com.lhq.prj.bms.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.ICommodityDao;
import com.lhq.prj.bms.po.Commodity;

public class CommodityDao extends SqlMapClientDaoSupport implements ICommodityDao {

	public Integer deleteById(Integer commodityId) {
		return getSqlMapClientTemplate().delete("Commodity.deleteById", commodityId);
	}

	public List findAll(Commodity commodity) {
		return getSqlMapClientTemplate().queryForList("Commodity.findAll",commodity);
	}

	public Object saveCommodity(Commodity commodity) {
		return getSqlMapClientTemplate().insert("Commodity.save", commodity);
	}

	public Integer update(Commodity commodity) throws Exception {
		return getSqlMapClientTemplate().update("Commodity.update", commodity);
	}
	
	public int findByCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Commodity.findByCount", page);
	}

	public List findByPage(Page page) {
		return getSqlMapClientTemplate().queryForList("Commodity.findByPage", page);
	}

}
