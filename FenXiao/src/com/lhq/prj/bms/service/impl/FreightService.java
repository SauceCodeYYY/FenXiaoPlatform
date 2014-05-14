package com.lhq.prj.bms.service.impl;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IFreightDao;
import com.lhq.prj.bms.po.Freight;
import com.lhq.prj.bms.service.IFreightService;

public class FreightService implements IFreightService {
	
	private IFreightDao freightDao;
	

	public void setFreightDao(IFreightDao freightDao) {
		this.freightDao = freightDao;
	}

	public boolean deleteFreight(Integer freightid) {
		Integer flag = freightDao.deleteById(freightid);
		if (flag != null) {
			return true;
		}
		return false;
	}

	public List findByExample(Freight freight) {
	
		return freightDao.findByExample(freight);
	}

	public Page findByPage(Page page) {
		page.setRoot(freightDao.findByPage(page));
		page.setTotalProperty(freightDao.findByCount(page));
		return page;
	}

	public Object saveFreight(Freight freight) {
		return freightDao.saveFreight(freight) ;
	}

	public Integer updateBalance(Freight freight) {
		
		return freightDao.updateBalance(freight);
	}

	public boolean updateFreight(Freight freight) throws Exception {
		Integer flag = freightDao.update(freight);
		
		if (flag != null) {
			return true;
		}
		return false;
	}

	public Object deleteFreight1(Freight freight) {
		/*Integer flag = freightDao.deletefreight(freight);
		if (flag != null) {
			return true;
		}
		return false;*/
		
		return freightDao.deletefreight(freight) ;
	}

	
}
