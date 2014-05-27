package com.lhq.prj.bms.dao;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Feedback;
import com.lhq.prj.bms.po.User;


public interface IFeedbackDao {
	public Object saveFeedback(Feedback feedback);


	public List findByPage(Page page);

	public int findByCount(Page page);

	
	public Integer update(Feedback feedback) throws Exception;

	
	public Integer deleteById(Integer feedbackid);
	
	public  Object deletefeedback(Feedback feedback);



	public List findByExample(User user);

	public Integer updateBalance(Feedback feedback);


	public List findByPageAndUser(Page pageBean);


	public int findByUserCount(Page pageBean);

}
