package com.lhq.prj.bms.service;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Freight;

public interface IFreightService {
	
	
	Object saveFreight(Freight freight);


	
	Page findByPage(Page page);

	
	boolean updateFreight(Freight freight) throws Exception;

	
	boolean deleteFreight(Integer freightid);
	
	Object  deleteFreight1(Freight freight);

	List findByExample(Freight freight);
	
	Integer updateBalance(Freight freight);

}
