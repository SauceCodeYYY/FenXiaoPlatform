package com.lhq.prj.bms.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.lhq.prj.bms.core.BaseAction;
import com.lhq.prj.bms.core.MyUtils;
import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Address;
import com.lhq.prj.bms.po.User;
import com.lhq.prj.bms.service.IAddressService;

public class AddressAction extends BaseAction {

	private static final long serialVersionUID = -3820945768524195151L;

	public static final String SUCCESS_MANAGER = "success_manager";

	private IAddressService addressService;

	private Address address;

	private boolean success;

	private Page pageBean;

	private Integer page;

	private String pageS;

	private Integer addressId;

	public String saveAddress() {
		addressId = (Integer) addressService.saveAddress(address);
		success = addressId != null;
		return SUCCESS;
	}

	public String findAllAddress() {
		System.out.println("AddressAction.findAllAddress");
		String strCondition = getRequest().getParameter("conditions");
		List<String> conditions = new ArrayList<String>();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		List<String> utf8Condition = new ArrayList<String>();
		for (String c: conditions){
			try {
				utf8Condition.add(new String(c.getBytes("iso-8859-1"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		pageBean = new Page();
		User loginUser = (User) getSession().getAttribute("user");
		if (null == loginUser){
			return ERROR;
		}
		pageBean.setUserId(loginUser.getUserId());
		pageBean.setConditions(utf8Condition);
		int start = Integer.valueOf(getRequest().getParameter("start"));
		int limit = Integer.valueOf(getRequest().getParameter("limit"));
		pageBean.setStart(++start);
		pageBean.setLimit(limit = limit == 0 ? 20 : limit);
		pageBean = addressService.findByPage(pageBean);
		return SUCCESS;
	}

	public String findByExample() {
		pageBean = new Page();
		pageBean.setRoot(addressService.findByExample(address));
		return SUCCESS;
	}

	public String deleteAddress() {
		success = addressService.deleteAddress(address.getAddressId());
		return SUCCESS;
	}

	public String updateAddress() throws Exception {
		success = addressService.updateAddress(address);
		return SUCCESS;
	}

	public IAddressService getAddressService() {
		return addressService;
	}

	public void setAddressService(IAddressService addressService) {
		this.addressService = addressService;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public void setPage(String pageS) {
		this.pageS = pageS;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	
}
