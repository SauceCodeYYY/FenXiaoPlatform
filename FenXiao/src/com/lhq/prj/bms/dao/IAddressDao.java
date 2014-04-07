package com.lhq.prj.bms.dao;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Address;

public interface IAddressDao {
	public Object saveAddress(Address address);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Address address) throws Exception;

	public Integer deleteById(Long userId);

	public List findByExample(Address address);
}
