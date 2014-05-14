package com.lhq.prj.bms.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IFreightDao;
import com.lhq.prj.bms.po.Freight;

public class FreightDao extends SqlMapClientDaoSupport implements IFreightDao{

	public Integer deleteById(Integer freightid) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("Freight.deleteById", freightid);
	}
	
	public int findByCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject("Freight.findByCount", page);
	}

	public List findByExample(Freight freight) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Freight.findByExample",freight);
	}

	public List findByPage(Page page) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Freight.findByPage", page);
	}

	public Object saveFreight(Freight freight) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().insert("Freight.save", freight);
	}

	public Integer update(Freight freight) throws Exception {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("Freight.update", freight);
	}

	public Integer updateBalance(Freight freight) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("Freight.updateBalance",freight);
	}

	public Object deletefreight(Freight freight) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("Freight.deletefreight", freight);
	}

	
}
