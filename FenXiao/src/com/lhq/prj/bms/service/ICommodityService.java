package com.lhq.prj.bms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.fileupload.FileUploadException;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Commodity;

/**
 * ICommodityService.java Create on 2008-9-21 下午03:57:53
 * 
 * 商品管理业务层
 * 
 * Copyright (c) 2008 by MTA.
 * 
 * @author 廖瀚卿
 * @version 1.0
 */
public interface ICommodityService {
	/**
	 * 添加商品
	 * 
	 * @param commodity
	 * @return
	 */
	Object saveCommodity(Commodity commodity);
	
	
	/**
	 * 分页查找
	 * @param page 分页对象
	 * @return
	 */
	Page findByPage(Page page);

	/**
	 * 查找所有商品
	 * 
	 * @return
	 */
	List findAll(Commodity commodity);

	/**
	 * 修改商品信息
	 * 
	 * @param commodity
	 * @return
	 * @throws Exception
	 */
	boolean updateCommodity(Commodity commodity) throws Exception;

	/**
	 * 删除商品
	 * 
	 * @param commodityId
	 * @return
	 */
	boolean deleteCommodity(String commodityId);
	
	/**
	 * 分页+货号查找
	 * @param page 分页对象
	 * @return
	 */
	Page findByNovid(Page page);

	/**
	 * 通过货号+渠道+尺码查找单个商品
	 * @return
	 */
	Commodity findExact(Commodity commodity);
	
	/**
	 * 通过商品编号列表查找
	 * @param page
	 * @return
	 */
	Page findByIds(Page page);
	
	/**
	 * 商品批量下线
	 * @param commodity
	 * @return
	 * @throws Exception
	 */
	boolean updateState(Commodity commodity) throws Exception;
}
