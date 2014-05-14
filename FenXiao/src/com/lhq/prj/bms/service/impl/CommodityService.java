package com.lhq.prj.bms.service.impl;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.ICommodityDao;
import com.lhq.prj.bms.po.Commodity;
import com.lhq.prj.bms.service.ICommodityService;

public class CommodityService implements ICommodityService {

	private ICommodityDao commodityDao;

	public void setCommodityDao(ICommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}

	public boolean deleteCommodity(String commodityId) {
		String[] idsStrings = commodityId.split(",");
		int size = 0;
		for (int i = 0; i < idsStrings.length; i++) {
			size += commodityDao.deleteById(Integer.valueOf(idsStrings[i]));
		}

		if (size == idsStrings.length) {
			return true;
		} else {
			return false;
		}
	}

	public List findAll(Commodity commodity) {
		return commodityDao.findAll(commodity);
	}

	public Object saveCommodity(Commodity commodity) {
		return commodityDao.saveCommodity(commodity);
	}

	public boolean updateCommodity(Commodity commodity) throws Exception {
		Integer flag = commodityDao.update(commodity);
		if (null != flag) {
			return true;
		}
		return false;
	}

	public Page findByPage(Page page) {
		page.setRoot(commodityDao.findByPage(page));
		page.setTotalProperty(commodityDao.findByCount(page));
		return page;
	}

	public Page findByNovid(Page page) {
		page.setRoot(commodityDao.findByNovid(page));
		// page.setTotalProperty(commodityDao.findByCount(page));
		return page;
	}

	public Commodity findExact(Commodity commodity) {
		return commodityDao.findByExact(commodity);
	}

	public Page findByIds(Page page) {
		page.setRoot(commodityDao.findByIds(page));
		page.setTotalProperty(commodityDao.findByIdsCount(page));
		return page;
	}

	public boolean updateState(Commodity commodity) throws Exception {
		Integer flag = commodityDao.updateState(commodity);
		if (null != flag) {
			return true;
		}
		return false;
	}

	/*
	 * public String importExl(HttpServletRequest request,Commodity bean) throws
	 * IOException, FileUploadException{ InputStream in = null; String
	 * importRemark;
	 *//**
	 * 锟角否覆盖碉拷锟诫：1锟斤拷锟斤拷锟角达拷锟诫；2锟斤拷锟斤拷锟斤拷锟角碉拷锟斤拷
	 */
	/*
	 * int type;
	 *//**
	 * 锟斤拷录锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷锟斤拷
	 */
	/*
	 * String fileType; try { DiskFileItemFactory factory = new
	 * DiskFileItemFactory(); ServletFileUpload upload = new
	 * ServletFileUpload(factory); upload.setFileSizeMax((long) Math.pow(1024,
	 * 2) * 500);
	 * 
	 * List<FileItem> items = upload.parseRequest(request); Iterator iter =
	 * items.iterator(); while (iter.hasNext()) { FileItem item = (FileItem)
	 * iter.next(); if (item.getFieldName().equals("importRemark")) {
	 * importRemark = item.getString(); // importRemark =
	 * UnicodeUtil.decodeUnicode(importRemark); } if
	 * (item.getFieldName().equals("importType")) { type =
	 * Integer.parseInt(item.getString()); } // 锟斤拷锟斤拷潜?锟斤拷
	 * 锟斤拷锟斤拷锟角凤拷锟侥硷拷锟较达拷元锟斤拷 if (!item.isFormField()) { String fileName =
	 * item.getName(); fileType = fileName.substring(fileName.lastIndexOf(".") +
	 * 1).toLowerCase(); in = item.getInputStream(); this.import2Exl(in); } }
	 * 
	 * 
	 * } catch (BiffException e) { e.printStackTrace();
	 * 
	 * return "{errMsg:'锟斤拷锟斤拷锟叫达拷锟斤拷锟�}"; } return "{success:true}";
	 * 
	 * 
	 * }
	 */
	/*
	 * public void import2Exl(InputStream in) throws BiffException, IOException{
	 * Sheet sheet; Workbook book = null;
	 * 
	 * Cell
	 * cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9,cell10,cell11,cell12
	 * , cell13,cell14,cell15,cell16;
	 * 
	 * book = Workbook.getWorkbook(in);
	 * 
	 * 
	 * // 锟斤拷玫锟揭伙拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷(ecxel锟斤拷sheet锟侥憋拷糯锟�锟斤拷始,0,1,2,3,....)
	 * 
	 * sheet = book.getSheet(0); for(int i=2;i<sheet.getRows();i++) {
	 * 
	 * // 锟斤拷取每一锟叫的碉拷元锟斤拷 sheet.getRows(); cell1 = sheet.getCell(0, i);//
	 * 锟斤拷锟叫ｏ拷锟叫ｏ拷
	 * 
	 * cell2 = sheet.getCell(1, i);
	 * 
	 * cell3 = sheet.getCell(2, i);
	 * 
	 * cell4 = sheet.getCell(3, i); cell5 = sheet.getCell(4, i); cell6 =
	 * sheet.getCell(5, i); cell7 = sheet.getCell(6, i); cell8 =
	 * sheet.getCell(7, i); cell9 = sheet.getCell(8, i); cell10 =
	 * sheet.getCell(9, i); cell11 = sheet.getCell(10, i); cell12 =
	 * sheet.getCell(11, i); cell13 = sheet.getCell(12, i); cell14 =
	 * sheet.getCell(13, i); cell15 = sheet.getCell(14, i); cell16 =
	 * sheet.getCell(15, i);
	 * 
	 * if ("".equals(cell1.getContents()) == true) // 锟斤拷锟斤拷取锟斤拷锟斤拷锟轿拷锟�break;
	 * 
	 * String str1 = cell1.getContents().trim(); String str2 =
	 * cell2.getContents().trim(); String str3 = cell3.getContents().trim();
	 * String str4 = cell4.getContents().trim(); String str5 =
	 * cell5.getContents().trim(); String str6 = cell6.getContents().trim();
	 * String str7 = cell7.getContents().trim(); String str8 =
	 * cell8.getContents().trim(); String str9 = cell9.getContents().trim();
	 * String str10 = cell10.getContents().trim(); String str11 =
	 * cell11.getContents().trim();
	 * 
	 * String str12 = cell12.getContents().trim(); String str13 =
	 * cell13.getContents().trim(); String str14 = cell14.getContents().trim();
	 * String str15 = cell15.getContents().trim(); String str16 =
	 * cell16.getContents().trim();
	 * 
	 * UserBean userBean = new UserBean(); LoginElement login
	 * =AuthorityContext.getLoginElement();
	 * userBean.setId(PrimaryKeyUtil.getSeq()); userBean.setDepartment_id(str1);
	 * userBean.setFullname(str2); userBean.setTelephone(str3);
	 * userBean.setAlias(str4); userBean.setEmail(str5);
	 * userBean.setAccount(str6); userBean.setPassword(str7);
	 * userBean.setOpr_state(str8); userBean.setJzbm1(str9);
	 * userBean.setJzbm2(str10); userBean.setJzbm3(str11);
	 * 
	 * System.out.println("userBean : "+userBean); userMapper.addUser(userBean);
	 * 
	 * Commodity bean=new Commodity(); bean.setNovid(str1);
	 * bean.setNewNovid(str2); bean.setBrand(str3); bean.setCommodityName(str4);
	 * bean.setLargeclass(str5); bean.setSeason(str6); bean.setSex(str7);
	 * bean.setSeries(str8); bean.setTagprice(str9); bean.setColor(str10);
	 * bean.setYear(str11); bean.setMonthl(str12); bean.setChannel(str13);
	 * bean.setSizeone(str14); bean.setNumbers(str15); bean.setDiscount(str16);
	 * commodityDao.saveCommodity(bean); } }
	 */

}
