package com.lhq.prj.bms.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IAddressDao;
import com.lhq.prj.bms.po.Address;

public class AddressDao extends SqlMapClientDaoSupport implements IAddressDao {
	public Integer deleteById(Long userId) {
		return getSqlMapClientTemplate().delete("Address.deleteById", userId);
	}

	public int findByCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Address.findByCount", page);
	}

	public List findByPage(Page page) {
		return getSqlMapClientTemplate().queryForList("Address.findByPage",
				page);
	}

	public Object saveAddress(Address address) {
		return getSqlMapClientTemplate().insert("Address.save", address);
	}

	public Integer update(Address address) throws Exception {
		return getSqlMapClientTemplate().update("Address.update", address);
	}

	public List findByExample(Address address) {
		return getSqlMapClientTemplate().queryForList("Address.findByExample",
				address);
	}

}
