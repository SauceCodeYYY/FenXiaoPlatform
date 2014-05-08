package com.lhq.prj.bms.service;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Recharge;

public interface IRechargeService {
	Object saveRecharge(Recharge recharge);

	Page findByPage(Page page);

	boolean updateRecharge(Recharge recharge) throws Exception;

	boolean deleteRecharge(Integer rechargeId);

	List findByExample(Recharge recharge);

	Page findByTime(Page page);

	Page findByTimeAndUser(Page page);

	Page findByPageAndUser(Page page);
}
