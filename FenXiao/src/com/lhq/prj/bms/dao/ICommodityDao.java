package com.lhq.prj.bms.dao;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Commodity;

/**
 * ICommodityDao.java Create on 2008-9-18 下午08:16:37
 * 
 * 商品持久层接口
 * 
 * Copyright (c) 2008 by MTA.
 * 
 * @author 廖瀚卿
 * @version 1.0
 */
public interface ICommodityDao {

	/**
	 * 保存一个商品到数据库
	 * 
	 * @param commodity
	 * @return
	 */
	public Object saveCommodity(Commodity commodity);

	/**
	 * 查找所有商品
	 * 
	 * @return
	 */
	
	public List findAll(Commodity commodity);

	/**
	 * 修改商品信息
	 * 
	 * @param commodity
	 * @return
	 * @throws Exception
	 */
	public Integer update(Commodity commodity) throws Exception;
	
	/**
	 * 同一渠道商品批量下线
	 * @param commodity
	 * @return
	 * @throws Exception
	 */
	public Integer updateState(Commodity commodity) throws Exception;
	
	/**
	 * 根据id删除商品
	 * 
	 * @param commodityId
	 * @return
	 */
	public Integer deleteById(Long commodityId);
	
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
	 * 通过货号查找
	 * @param page
	 * @return
	 */
	public List findByNovid(Page page);

	/**
	 * 通过货号+渠道+尺寸查找单个商品
	 * @param commodity
	 * @return
	 */
	public Commodity findByExact(Commodity commodity);
	
	/**
	 * 通过商品编号列表查找
	 * @param page
	 * @return
	 */
	public List findByIds(Page page);
	
	public int findByIdsCount(Page page);
	
	public Integer updateAmount(Commodity commodity);
	
	public Object findById(Commodity commodity);
}
