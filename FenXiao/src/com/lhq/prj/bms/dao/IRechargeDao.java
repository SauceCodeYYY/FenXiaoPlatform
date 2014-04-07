package com.lhq.prj.bms.dao;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Recharge;
import com.lhq.prj.bms.po.User;

public interface IRechargeDao {

	/**
	 * ����һ���µĳ�ֵ��¼
	 * 
	 * @param recharge
	 * @return
	 */
	public Object saveRecharge(Recharge recharge);

	/**
	 * �������г�ֵ��¼
	 * 
	 * @return
	 */

	/**
	 * ��ҳ����
	 * 
	 * @param page
	 *            ����
	 * @return
	 */
	public List findByPage(Page page);

	/**
	 * ҳ���ҵ��ܼ�¼
	 * 
	 * @param page
	 *            ����
	 * @return
	 */
	public int findByCount(Page page);

	/**
	 * ����ʾ�����ҳ�ֵ��Ϣ
	 * 
	 * @param recharge
	 * @return
	 */
	public List findByExample(Recharge recharge);

	/**
	 * �޸ĳ�ֵ��Ϣ
	 * 
	 * @param recharge
	 * @return
	 * @throws Exception
	 */
	public Integer update(Recharge recharge) throws Exception;

	/**
	 * ����idɾ����ֵ��¼
	 * 
	 * @param rechargeId
	 * @return
	 */
	public Integer deleteById(Integer rechargeId);

	/**
	 * �����ύʱ����ҳ�ֵ��¼
	 * 
	 * @param page
	 * @return
	 */
	public List findByTime(Page page);

	/**
	 * �����ύʱ����ҳ�ֵ��¼
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
