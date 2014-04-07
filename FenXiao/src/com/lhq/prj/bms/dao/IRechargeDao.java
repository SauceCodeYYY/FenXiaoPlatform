package com.lhq.prj.bms.dao;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Recharge;
import com.lhq.prj.bms.po.User;

public interface IRechargeDao {

	/**
	 * 保存一个新的充值记录
	 * 
	 * @param recharge
	 * @return
	 */
	public Object saveRecharge(Recharge recharge);

	/**
	 * 查找所有充值记录
	 * 
	 * @return
	 */

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            条件
	 * @return
	 */
	public List findByPage(Page page);

	/**
	 * 页查找的总记录
	 * 
	 * @param page
	 *            条件
	 * @return
	 */
	public int findByCount(Page page);

	/**
	 * 根据示例查找充值信息
	 * 
	 * @param recharge
	 * @return
	 */
	public List findByExample(Recharge recharge);

	/**
	 * 修改充值信息
	 * 
	 * @param recharge
	 * @return
	 * @throws Exception
	 */
	public Integer update(Recharge recharge) throws Exception;

	/**
	 * 根据id删除充值记录
	 * 
	 * @param rechargeId
	 * @return
	 */
	public Integer deleteById(Integer rechargeId);

	/**
	 * 根据提交时间查找充值记录
	 * 
	 * @param page
	 * @return
	 */
	public List findByTime(Page page);

	/**
	 * 根据提交时间查找充值记录
	 * 
	 * @param page
	 * @return
	 */
	public Integer findByTimeCount(Page page);

	public List findByTimeAndUser(Page page);

	public Integer findByTimeAndUserCount(Page page);

	public List findByPageAndUser(Page page);

	public Integer findByUserCount(Page page);
}
