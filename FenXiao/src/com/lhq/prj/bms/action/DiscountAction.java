package com.lhq.prj.bms.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Discount;
import com.lhq.prj.bms.service.IDiscountService;

public class DiscountAction extends BaseAction {
	private static final long serialVersionUID = -7319155344307243635L;

	private IDiscountService discountService;
	private Discount discount;
	private boolean success;
	private Page pageBean;
	private Integer page;
	private Long discountId;
	private String tip;
	private List list;
	
	public String saveDiscount() {
		Discount temp = (Discount)discountService.findByExample(discount);
		if (temp != null){
			this.setTip("折扣记录已存在!");
			return SUCCESS;
		}
		discountId = (Long) discountService.saveDiscount(discount);
		if (discountId != null) {
			success = true;
		}
		return SUCCESS;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String findAllDiscountByUser() {
		System.out.println("DiscountAction.findAllDiscount");
		String strCondition = getRequest().getParameter("conditions");
		String searchType = getRequest().getParameter("searchType");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		List<String> utf8Conditions = new ArrayList<String>();
		for (String condition : conditions) {
			try {
				utf8Conditions.add(new String(condition.getBytes("ISO-8859-1"),
						"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		pageBean = new Page();
		pageBean.setConditions(utf8Conditions);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		
		if ("userId".equals(searchType)){
			pageBean.setUserId(Long.parseLong(getRequest().getParameter("userId")));
		}
		pageBean = discountService.findByPage(pageBean);
		return SUCCESS;
	}

	public String deleteDiscount() {
		String ids = getRequest().getParameter("ids");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(ids, ","));
		if (ids != null) {
			pageBean = new Page();
			pageBean.setConditions(conditions);
			System.out.println(conditions);
			success = discountService.deleteDiscount(pageBean);
		}
		return SUCCESS;
	}

	public String updateDiscount() throws Exception {
		if (discount.getDiscountId() != null) {
			success = discountService.updateDiscount(discount);
		}
		return SUCCESS;
	}
	
	public String findDiscountByExample() throws Exception {
		if (discount != null) {
			pageBean = discountService.findPageByExample(discount);
			System.out.println(pageBean.getRoot().size());
			success = true;
		}
		return SUCCESS;
	}
	
	public IDiscountService getDiscountService() {
		return discountService;
	}

	public void setDiscountService(IDiscountService discountService) {
		this.discountService = discountService;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
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

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

}
