package com.lhq.prj.bms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Commodity;
import com.lhq.prj.bms.po.Order;
import com.lhq.prj.bms.po.User;
import com.lhq.prj.bms.service.ICartItemService;
import com.lhq.prj.bms.service.ICommodityService;
import com.lhq.prj.bms.service.IOrderService;
import com.lhq.prj.bms.service.IUserService;

public class OrderAction extends BaseAction {
	private static final long serialVersionUID = -6152090171062953623L;

	private IOrderService orderService;

	private IUserService userService;

	private ICommodityService commodityService;

	private ICartItemService cartItemService;

	private Order order;

	private boolean success;

	private Page pageBean;

	private Integer page;

	private Long orderId;

	private String cartItemIds;

	private String export_date;
	
	private InputStream inputStream;
	
	private String excelFile;

	private String tip;
	
	public String saveOrder() {
		User currUser = (User) getSession().getAttribute("user");
		if (null == currUser) {
			return ERROR;
		}
		order.setUserId(currUser.getUserId());
		
		for (String item: order.getOrderItem().split(",")){
			String[] per = item.split("_");
			String subjectId = per[0];
			Integer amount = Integer.parseInt(per[1]);
			Commodity commodity = new Commodity();
			commodity.setSubjectId(Long.parseLong(subjectId));
			commodity = (Commodity) commodityService.findById(commodity);
			if (amount > commodity.getNumbers()){
				success= false;
				setTip("货号" + commodity.getNovid() + "， 尺码" + commodity.getSizeone() + "库存不够，请修改购买数量！");
				return SUCCESS;
			}
		}
		
		orderId = (Long) orderService.saveOrder(order);
		if (orderId != null) {
			success = true;
			currUser.setBalance(-order.getTotal());
			userService.updateBalance(currUser);
			cartItemService.deleteCartItem(cartItemIds);
			String orderItems = order.getOrderItem();
			String[] items = orderItems.split(",");
			for (String item: items){
				String[] per = item.split("_");
				String subjectId = per[0];
				Integer amount = Integer.parseInt(per[1]);
				Commodity commodity = new Commodity();
				commodity.setSubjectId(Long.parseLong(subjectId));
				commodity.setNumbers(-amount);
				commodityService.updateAmount(commodity);
			}
		}
		return SUCCESS;
	}

	public String findAllOrder() {
		System.out.println("OrderAction.findAllOrder");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		pageBean = new Page();
		pageBean.setConditions(conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		if (getRequest().getParameter("from") != null
				&& getRequest().getParameter("to") != null) {
			conditions = new ArrayList<String>();
			// utf8Conditions.add("");
			conditions.add(getRequest().getParameter("from"));
			conditions.add(getRequest().getParameter("to"));
			pageBean.setConditions(conditions);
			pageBean = orderService.findByTime(pageBean);
		} else {
			System.out.println(conditions);
			pageBean = orderService.findByPage(pageBean);
		}
		return SUCCESS;
	}

	public String findAllOrderByUser() {
		System.out.println("OrderAction.findAllOrderByUser");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		// utf8Conditions.add(getRequest().getParameter("userId"));
		pageBean = new Page();
		User loginUser = (User) getSession().getAttribute("user");
		if (null == loginUser) {
			return ERROR;
		}
		pageBean.setUserId(loginUser.getUserId());
		pageBean.setConditions(conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		if (getRequest().getParameter("from") != null
				&& getRequest().getParameter("to") != null) {
			conditions = new ArrayList<String>();
			// utf8Conditions.add(getRequest().getParameter("userId"));
			conditions.add(getRequest().getParameter("from"));
			conditions.add(getRequest().getParameter("to"));
			pageBean.setConditions(conditions);
			pageBean = orderService.findByTimeAndUser(pageBean);
		} else {
			pageBean = orderService.findByUser(pageBean);
		}
		return SUCCESS;
	}

	// public String findByExample() {
	// pageBean = new Page();
	// pageBean.setRoot(orderService.findByExample(order));
	// return SUCCESS;
	// }
	//
	// public String deleteOrder() {
	// String strOrderId = getRequest().getParameter("orderId");
	// if (strOrderId != null && !"".equals(strOrderId)) {
	// success = orderService.deleteOrder(Integer
	// .parseInt(strOrderId));
	// }
	// return SUCCESS;
	// }

	public String updateOrder() throws Exception {
		if (order != null) {
			order.setCloseTime(new Date());
			success = orderService.updateOrder(order);
			if (success) {
				if (order.getState().equals("已关闭")) {
					User u = new User();
					u.setUserId(order.getUserId());
					u.setBalance(order.getTotal());
					success = userService.updateBalance(u);
					
					String orderItems = order.getOrderItem();
					String[] items = orderItems.split(",");
					for (String item: items){
						String[] per = item.split("_");
						String subjectId = per[0];
						Integer amount = Integer.parseInt(per[1]);
						Commodity commodity = new Commodity();
						commodity.setSubjectId(Long.parseLong(subjectId));
						commodity.setNumbers(amount);
						commodityService.updateAmount(commodity);
					}
				}
			}
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		System.out.println(inputStream);
		return SUCCESS;
	}
    
	public InputStream getInputStream() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("订单导出");
		// 设置表格样式
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10); // 字体高度
		font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
		cellStyle.setWrapText(false);
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("单号");

		cell = row.createCell(1);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("邮费");

		cell = row.createCell(2);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("备注");

		cell = row.createCell(3);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("品牌");

		cell = row.createCell(4);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("货号");
		
		cell = row.createCell(5);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("渠道");
		
		cell = row.createCell(6);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("尺码");

		cell = row.createCell(7);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("上市价格");

		cell = row.createCell(8);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("数量");

		cell = row.createCell(9);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("发货方式");
		
		cell = row.createCell(10);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("姓名");
		
		cell = row.createCell(11);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("电话号码");

		cell = row.createCell(12);
		cell.setCellStyle(cellStyle); // 设置单元格样式
		cell.setCellValue("发货地址");

		int rowNum = 1;
		pageBean = new Page();
		List conditions = new ArrayList();
		conditions.add(export_date);
		pageBean.setConditions(conditions);
		pageBean = orderService.findByExactTime(pageBean);
		for (Object o : pageBean.getRoot()) {
			Order order = (Order) o;
			String itemStr = order.getOrderItem();
			String[] itemArr = itemStr.split(",");
			HashMap<String, String> amountObj = new HashMap<String, String>();
			String itemIds = "";
			for (String item : itemArr) {
				String[] single = item.split("_");
				String itemId = single[0];
				String itemAmount = single[1];
				itemIds += itemId + ",";
				amountObj.put(itemId, itemAmount);
			}
			itemIds = itemIds.substring(0, itemIds.length() - 1);
			Page tempPage = new Page();
			List conditions2 = new ArrayList();
			MyUtils.addToCollection(conditions2, MyUtils.split(itemIds, ","));
			tempPage.setConditions(conditions2);
			tempPage = commodityService.findByIds(tempPage);

			for (Object oo: tempPage.getRoot()){
				Commodity commodity = (Commodity) oo;
				// 如果是数据库的数据的话，用一个for循环就可以输出全部了
				row = sheet.createRow(rowNum);

				cell = row.createCell(0);
				cell.setCellValue(order.getOrderId());

				cell = row.createCell(1);
				cell.setCellValue("");

				cell = row.createCell(2);
				cell.setCellValue(order.getNote());

				cell = row.createCell(3);
				cell.setCellValue(commodity.getBrand());

				cell = row.createCell(4);
				cell.setCellValue(commodity.getNovid());

				cell = row.createCell(5);
				cell.setCellValue(commodity.getChannel());
				
				cell = row.createCell(6);
				cell.setCellValue(commodity.getSizeone());

				cell = row.createCell(7);
				cell.setCellValue(commodity.getTagprice());

				cell = row.createCell(8);
				cell.setCellValue(amountObj.get(commodity.getSubjectId().toString()));

				cell = row.createCell(9);
				cell.setCellValue(order.getDeliveryMethod());

				cell = row.createCell(10);
				cell.setCellValue(order.getReceiver());
				
				cell = row.createCell(11);
				cell.setCellValue(order.getCell());

				cell = row.createCell(12);
				cell.setCellValue(order.getAddress());

				rowNum++;
			}
		}

		// String fileName=RandomStringUtils.randomAlphanumeric(10);
		String basePath = getServletContext().getRealPath("/");
		String fileName = "orders-" + new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		setExcelFile(fileName);
		// System.out.println(fileName);
		fileName = new StringBuffer( basePath + fileName).append(".xls").toString();
		File file = new File(fileName);
		try {
			OutputStream os = new FileOutputStream(file);
			wb.write(os);
			os.flush();
			os.close();
			inputStream = new FileInputStream(file);
			System.out.println("--2--" + inputStream + "--" + excelFile);
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ICartItemService getCartItemService() {
		return cartItemService;
	}

	public void setCartItemService(ICartItemService cartItemService) {
		this.cartItemService = cartItemService;
	}

	public String getCartItemIds() {
		return cartItemIds;
	}

	public void setCartItemIds(String cartItemIds) {
		this.cartItemIds = cartItemIds;
	}

	public String getExport_date() {
		return export_date;
	}

	public void setExport_date(String export_date) {
		this.export_date = export_date;
	}

	public ICommodityService getCommodityService() {
		return commodityService;
	}

	public void setCommodityService(ICommodityService commodityService) {
		this.commodityService = commodityService;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(String excelFile) {
		this.excelFile = excelFile;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
}