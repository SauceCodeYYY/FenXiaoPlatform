package com.lhq.prj.bms.dao;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Freight;

public interface IFreightDao {
	public Object saveFreight(Freight freight);


	public List findByPage(Page page);

	public int findByCount(Page page);

	
	public Integer update(Freight freight) throws Exception;

	
	public Integer deleteById(Integer freightid);
	
	public  Object deletefreight(Freight freight);



	public List findByExample(Freight freight);

	public Integer updateBalance(Freight freight);
}
