package com.lhq.prj.bms.service.impl;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IRechargeDao;
import com.lhq.prj.bms.po.Recharge;
import com.lhq.prj.bms.service.IRechargeService;

public class RechargeService implements IRechargeService {
	private IRechargeDao rechargeDao;

	public void setRechargeDao(IRechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}

	public Object saveRecharge(Recharge recharge) {
		return rechargeDao.saveRecharge(recharge);
	}

	public Page findByPage(Page page) {
		page.setRoot(rechargeDao.findByPage(page));
		page.setTotalProperty(rechargeDao.findByCount(page));
		return page;
	}

	public boolean updateRecharge(Recharge recharge) throws Exception {
		return rechargeDao.update(recharge) != null;
	}

	public boolean deleteRecharge(Integer rechargeId) {
		return rechargeDao.deleteById(rechargeId) != null;
	}

	public List findByExample(Recharge recharge) {
		return rechargeDao.findByExample(recharge);
	}

	public Page findByTime(Page page) {
		page.setRoot(rechargeDao.findByTime(page));
		page.setTotalProperty(rechargeDao.findByTimeCount(page));
		return page;
	}

	public Page findByTimeAndUser(Page page) {
		page.setRoot(rechargeDao.findByTimeAndUser(page));
		page.setTotalProperty(rechargeDao.findByTimeAndUserCount(page));
		return page;
	}

	public Page findByPageAndUser(Page page) {
		page.setRoot(rechargeDao.findByPageAndUser(page));
		page.setTotalProperty(rechargeDao.findByUserCount(page));
		return page;	
	}
}
