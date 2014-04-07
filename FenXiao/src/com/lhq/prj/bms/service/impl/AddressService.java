package com.lhq.prj.bms.service.impl;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IAddressDao;
import com.lhq.prj.bms.po.Address;
import com.lhq.prj.bms.service.IAddressService;

public class AddressService implements IAddressService {
	private IAddressDao addressDao;

	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public boolean deleteAddress(Long addressId) {
		return null != addressDao.deleteById(addressId);
	}

	public Page findByPage(Page page) {
		page.setRoot(addressDao.findByPage(page));
		page.setTotalProperty(addressDao.findByCount(page));
		return page;
	}

	public Object saveAddress(Address address) {
		return addressDao.saveAddress(address);
	}

	public boolean updateAddress(Address address) throws Exception {
		return null != addressDao.update(address);
	}

	public List findByExample(Address address) {
		return addressDao.findByExample(address);
	}

}
