package com.lhq.prj.bms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Freight;

import com.lhq.prj.bms.service.IFreightService;

@SuppressWarnings("serial")
public class FreightAction extends BaseAction {

	public static final String SUCCESS_MANAGER = "success_manager";

	private IFreightService freightService;

	private Freight freight;

	private boolean success;

	private Page pageBean;

	private Integer page;

	private String pageS;

	private Integer freightid;

	private File excelFile; // 上传的文件
	private String excelFileFileName; // 保存原始文件名

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	public String saveFreight() {
		freightid = (Integer) freightService.saveFreight(freight);

		if (freightid != null) {
			success = true;
		}
		return SUCCESS;
	}

	/**
	 * 查找用户信息
	 * 
	 * @return
	 */
	public String findAllFreight() {
		System.out.println("===");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		List<String> utf8Condition = new ArrayList<String>();
		for (String c : conditions) {
			try {
				utf8Condition
						.add(new String(c.getBytes("iso-8859-1"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		pageBean = new Page();
		pageBean.setConditions(utf8Condition);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		pageBean = freightService.findByPage(pageBean);
		return SUCCESS;
	}

	public String findByExample() {
		pageBean = new Page();
		pageBean.setRoot(freightService.findByExample(freight));
		return SUCCESS;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	public String deleteFreight() {
		success = freightService.deleteFreight(freightid);
		return SUCCESS;
	}

	/**
	 * 修改用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateFreight() throws Exception {

		if (freight != null) {
			// success = userService.updateUser(user);
			success = freightService.updateFreight(freight);
		}
		return SUCCESS;
	}

	// 判断文件类型
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
			ArrayList<Freight> freList = null;
			if (excelFile != null) {

				Workbook book = createWorkBook(new FileInputStream(excelFile));
				Sheet sheet = book.getSheetAt(0);
				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					Row ros = sheet.getRow(i);
					ros.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
					ros.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
					ros.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
					ros.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
					ros.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);

					Freight bean = new Freight();

					bean.setPriovice(ros.getCell(0).getStringCellValue());
					bean.setFirstfreight(Integer.valueOf(ros.getCell(1)
							.getStringCellValue()));
					bean.setLastfreight(Integer.valueOf(ros.getCell(2)
							.getStringCellValue()));
					bean.setFreightcompany(ros.getCell(3).getStringCellValue());
					bean.setChannel(ros.getCell(4).getStringCellValue());

					freightid = (Integer) freightService.saveFreight(bean);

					if (freightid != null) {
						success = true;
					}

					/*
					 * freList=new ArrayList<Freight>(); freList.add(bean);
					 */
					// System.out.println("liyang.............."+freList);
				}

			}

			/*
			 * ArrayList<Freight> freArrayList=(ArrayList<Freight>)
			 * freightService.findByExample(freight);
			 * System.out.println("liyang........."+freList.size());
			 * if(!freArrayList.isEmpty()&& freArrayList!=null) {
			 * 
			 * for(int i1=0;i1<=freArrayList.size()-1;i1++) {
			 * 
			 * Freight bean= freList.get(i1);
			 * 
			 * //
			 * System.out.println("liyang"+freArrayList.get(i1).getFreightcompany
			 * ()); if(freArrayList.get(i1).getFreightcompany().equals(bean.
			 * getFreightcompany())&&
			 * freArrayList.get(i1).getChannel().equals(bean.getChannel()) ) {
			 * bean.setFreightid( freArrayList.get(i1).getFreightid());
			 * if(bean.getFreightid()!=null) {
			 * 
			 * try { success =freightService.updateFreight(bean); } catch
			 * (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace();
			 * 
			 * } } }else{ freightid=(Integer) freightService.saveFreight(bean);
			 * if (freightid != null) { success = true; }
			 * 
			 * }
			 * 
			 * }
			 * 
			 * }else { for(int i=0;i<freList.size();i++) { freightid=(Integer)
			 * freightService.saveFreight( freList.get(i)); if (freightid !=
			 * null) { success = true; }
			 * 
			 * }
			 * 
			 * 
			 * }
			 */

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;

	}

	// /////////////////////////导出
	public InputStream getDownloadFile() throws Exception {
		return this.getInputStream();
	}

	public InputStream getInputStream() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("用户列表");

		// 设置表格样式
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10); // 字体高度
		font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		// font.setItalic( true ); // 是否使用斜体
		// font.setStrikeout(true); // 是否使用划线

		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
		cellStyle.setWrapText(false);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("省份");

		cell = row.createCell(1);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("首重");

		cell = row.createCell(2);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("续重");

		cell = row.createCell(3);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("快递公司");

		cell = row.createCell(4);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("渠道名称");

		// 如果是数据库的数据的话，用一个for循环就可以输出全部了
		row = sheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellValue("河北省");

		cell = row.createCell(1);
		cell.setCellValue(11);

		cell = row.createCell(2);
		cell.setCellValue(14);

		cell = row.createCell(3);
		cell.setCellValue("申通");

		cell = row.createCell(4);
		cell.setCellValue("江西A渠道");

		// String fileName=RandomStringUtils.randomAlphanumeric(10);
		String fileName = "Users";
		fileName = new StringBuffer(fileName).append(".xls").toString();
		File file = new File(fileName);
		try {
			OutputStream os = new FileOutputStream(file);
			wb.write(os);
			os.close();
			InputStream is = new FileInputStream(file);
			return is;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	private Integer Integer(String stringCellValue) {
		// TODO Auto-generated method stub
		return null;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Page getPageBean() {
		return pageBean;
	}

	public void setPageBean(Page pageBean) {
		this.pageBean = pageBean;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getPageS() {
		return pageS;
	}

	public void setPageS(String pageS) {
		this.pageS = pageS;
	}

	public static String getSuccessManager() {
		return SUCCESS_MANAGER;
	}

	public IFreightService getFreightService() {
		return freightService;
	}

	public void setFreightService(IFreightService freightService) {
		this.freightService = freightService;
	}

	public Freight getFreight() {
		return freight;
	}

	public void setFreight(Freight freight) {
		this.freight = freight;
	}

	public Integer getFreightid() {
		return freightid;
	}

	public void setFreightid(Integer freightid) {
		this.freightid = freightid;
	}

}
