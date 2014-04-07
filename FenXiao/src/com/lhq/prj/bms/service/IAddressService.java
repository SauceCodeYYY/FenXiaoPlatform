package com.lhq.prj.bms.service;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Address;

public interface IAddressService {
	Object saveAddress(Address address);

	Page findByPage(Page page);

	boolean updateAddress(Address address) throws Exception;

	boolean deleteAddress(Long addressId);

	List findByExample(Address address);

}
