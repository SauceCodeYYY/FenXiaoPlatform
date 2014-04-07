package com.lhq.prj.bms.service;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Recharge;
import com.lhq.prj.bms.po.Subject;
import com.lhq.prj.bms.po.User;

public interface IRechargeService {
	/**
	 * 添加充值
	 * 
	 * @param recharge
	 * @return
	 */
	Object saveRecharge(Recharge recharge);

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            分页对象
	 * @return
	 */
	Page findByPage(Page page);

	/**
	 * 修改充值信息
	 * 
	 * @param recharge
	 * @return
	 * @throws Exception
	 */
	boolean updateRecharge(Recharge recharge) throws Exception;

	/**
	 * 删除充值
	 * 
	 * @param rechargeId
	 * @return
	 */
	boolean deleteRecharge(Integer rechargeId);

	/**
	 * 根据实例查找充值记录
	 * 
	 * @param recharge
	 * @return
	 */
	List findByExample(Recharge recharge);
	
	/**
	 * 根据提交时间查找充值记录
	 * 
	 * @param page
	 * @return
	 */
	Page findByTime(Page page);
	
	Page findByTimeAndUser(Page page);
	
	Page findByPageAndUser(Page page);
}
