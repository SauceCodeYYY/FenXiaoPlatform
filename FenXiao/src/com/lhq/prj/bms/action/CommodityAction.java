package com.lhq.prj.bms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Commodity;
import com.lhq.prj.bms.service.ICommodityService;

@SuppressWarnings("serial")
public class CommodityAction extends BaseAction {

	private ICommodityService commodityService;

	private Commodity commodity;

	private Long commodityId;

	private boolean success;

	private Integer page;

	private Page pageBean;

	private File excelFile; // 上传的文件

	private String excelFileFileName; // 保存原始文件名
	
	private String conditions;

	public String saveCommodity() {
		commodityId = (Long) commodityService.saveCommodity(commodity);
		if (commodityId != null) {
			success = true;
		}
		return SUCCESS;
	}

	public String findExact() {
		pageBean = new Page();
		try {
			commodity.setChannel(new String(commodity.getChannel().getBytes("iso-8859-1"), "utf-8"));
			commodity.setState("online");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Commodity commodity = commodityService.findExact(this.commodity);
		List list = new ArrayList();
		if (commodity != null) {
			list.add(commodity);
		}
		pageBean.setRoot(list);
		pageBean.setTotalProperty(list.size());
		return SUCCESS;
	}

	public String findByIds(){
		ArrayList conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(this.conditions, " ,"));
		pageBean = new Page();
		pageBean.setConditions(conditions);
		pageBean = commodityService.findByIds(pageBean);
		return SUCCESS;
	}
	
	public String findCommodity() {
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(this.conditions, " ,"));
		pageBean = new Page();
		pageBean.setConditions(conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		pageBean = commodityService.findByPage(pageBean);
		return SUCCESS;
	}

	public String findCommodityByNovid() {
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(this.conditions, " ,"));
		pageBean = new Page();
		pageBean.setConditions(conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		pageBean = commodityService.findByNovid(pageBean);
		return SUCCESS;
	}

	public String findAllCommodity() {
		pageBean = new Page();
		pageBean.setRoot(commodityService.findAll(commodity));
		return SUCCESS;
	}

	public String deleteCommodity() {
		String strCommodityId = getRequest().getParameter("ids");
		if (strCommodityId != null && !"".equals(strCommodityId)) {
			success = commodityService.deleteCommodity(strCommodityId);
		}
		return SUCCESS;
	}

	public String updateCommodity() throws Exception {
		if (commodity != null) {
			success = commodityService.updateCommodity(commodity);
		}
		return SUCCESS;
	}

	public Workbook createWorkBook(InputStream is) throws IOException {
		if (excelFileFileName.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(is);
		}
		if (excelFileFileName.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}

	public String upload() {
		try {
			Workbook book = createWorkBook(new FileInputStream(excelFile));
			Sheet sheet = book.getSheetAt(0);
			// set old commodity to offline
			String channel = sheet.getRow(1).getCell(12).getStringCellValue();
			Commodity comm = new Commodity();
			comm.setChannel(channel);
			try {
				commodityService.updateState(comm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
				Row ros = sheet.getRow(i);
				ros.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(9).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(10).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(12).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(13).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(14).setCellType(HSSFCell.CELL_TYPE_STRING);
				ros.getCell(15).setCellType(HSSFCell.CELL_TYPE_STRING);

				// CardStock card = new CardStock();
				Commodity commodity = new Commodity();
				Commodity bean = new Commodity();
				bean.setNovid(ros.getCell(0).getStringCellValue());
				bean.setNewNovid(ros.getCell(1).getStringCellValue());
				bean.setBrand(ros.getCell(2).getStringCellValue());
				bean.setSubjectName(ros.getCell(3).getStringCellValue());
				bean.setLargeclass(ros.getCell(4).getStringCellValue());
				bean.setSeason(ros.getCell(5).getStringCellValue());
				bean.setSex(ros.getCell(6).getStringCellValue());
				bean.setSeries(ros.getCell(7).getStringCellValue());
				bean.setTagprice(ros.getCell(8).getStringCellValue());
				bean.setColor(ros.getCell(9).getStringCellValue());
				bean.setYear(ros.getCell(10).getStringCellValue());
				bean.setMonthl(ros.getCell(11).getStringCellValue());
				bean.setChannel(ros.getCell(12).getStringCellValue());
				bean.setSizeone(ros.getCell(13).getStringCellValue());
				bean.setNumbers(ros.getCell(14).getStringCellValue());
				bean.setDiscount(ros.getCell(15).getStringCellValue());
				bean.setState("online");

				commodityId = (Long) commodityService.saveCommodity(bean);
				if (commodityId != null) {
					success = true;
				}

				/*
				 * card.setCardNumber(ros.getCell(0).getStringCellValue());
				 * card.setState(ros.getCell(1).getStringCellValue());
				 */
				// System.out.println(new Gson().toJson(bean));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Page getPageBean() {
		return pageBean;
	}

	public void setPageBean(Page pageBean) {
		this.pageBean = pageBean;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public Long getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}

	public void setCommodityService(ICommodityService commodityService) {
		this.commodityService = commodityService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public String getExcelFileFileName() {
		return excelFileFileName;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	public ICommodityService getCommodityService() {
		return commodityService;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

}
