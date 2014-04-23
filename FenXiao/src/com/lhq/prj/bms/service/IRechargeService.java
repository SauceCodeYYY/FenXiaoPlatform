package com.lhq.prj.bms.service;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Recharge;
import com.lhq.prj.bms.po.Commodity;
import com.lhq.prj.bms.po.User;

public interface IRechargeService {
	/**
	 * ��ӳ�ֵ
	 * 
	 * @param recharge
	 * @return
	 */
	Object saveRecharge(Recharge recharge);

	/**
	 * ��ҳ����
	 * 
	 * @param page
	 *            ��ҳ����
	 * @return
	 */
	Page findByPage(Page page);

	/**
	 * �޸ĳ�ֵ��Ϣ
	 * 
	 * @param recharge
	 * @return
	 * @throws Exception
	 */
	boolean updateRecharge(Recharge recharge) throws Exception;

	/**
	 * ɾ���ֵ
	 * 
	 * @param rechargeId
	 * @return
	 */
	boolean deleteRecharge(Integer rechargeId);

	/**
	 * ���ʵ����ҳ�ֵ��¼
	 * 
	 * @param recharge
	 * @return
	 */
	List findByExample(Recharge recharge);
	
	/**
	 * ����ύʱ����ҳ�ֵ��¼
	 * 
	 * @param page
	 * @return
	 */
	Page findByTime(Page page);
	
	Page findByTimeAndUser(Page page);
	
	Page findByPageAndUser(Page page);
}
