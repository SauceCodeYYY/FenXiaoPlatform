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
	 * @param subject
	 * @return
	 */
	Object saveCommodity(Commodity subject);
	
	
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
	List findAll(Commodity subject);

	/**
	 * 修改商品信息
	 * 
	 * @param subject
	 * @return
	 * @throws Exception
	 */
	boolean updateCommodity(Commodity subject) throws Exception;

	/**
	 * 删除商品
	 * 
	 * @param subjectId
	 * @return
	 */
	boolean deleteCommodity(String subjectId);
	
	/**
	 * 上传
	 * 
	 */
/*
	public String importExl(HttpServletRequest request,Commodity bean) throws IOException, FileUploadException;
	 
	public void import2Exl(InputStream in) throws BiffException, IOException;
	 */
	 

}
