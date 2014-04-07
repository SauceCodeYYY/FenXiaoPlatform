package com.lhq.prj.bms.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IRechargeDao;
import com.lhq.prj.bms.po.Recharge;

public class RechargeDao extends SqlMapClientDaoSupport implements IRechargeDao {

	public Object saveRecharge(Recharge recharge) {
		return getSqlMapClientTemplate().insert("Recharge.save", recharge);
	}

	public List findByPage(Page page) {
		return getSqlMapClientTemplate().queryForList("Recharge.findByPage",
				page);
	}

	public int findByCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Recharge.findByCount", page);
	}

	public List findByExample(Recharge recharge) {
		return getSqlMapClientTemplate().queryForList("Recharge.findByExample",
				recharge);
	}

	public Integer update(Recharge recharge) throws Exception {
		return getSqlMapClientTemplate().update("Recharge.update", recharge);
	}

	public Integer deleteById(Integer rechargeId) {
		return getSqlMapClientTemplate().delete("Recharge.deleteById",
				rechargeId);
	}

	public List findByTime(Page page) {
		return getSqlMapClientTemplate().queryForList("Recharge.findByTime",
				page);
	}

	public Integer findByTimeCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Recharge.findByTimeCount", page);
	}

	public List findByTimeAndUser(Page page) {
		return getSqlMapClientTemplate().queryForList(
				"Recharge.findByTimeAndUser", page);
	}

	public Integer findByTimeAndUserCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Recharge.findByTimeAndUserCount", page);
	}

	public List findByPageAndUser(Page page) {
		return getSqlMapClientTemplate().queryForList(
				"Recharge.findByPageAndUser", page);
	}

	public Integer findByUserCount(Page page) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Recharge.findByUserCount", page);
	}

}
