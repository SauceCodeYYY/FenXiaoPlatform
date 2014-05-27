package com.lhq.prj.bms.service;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.po.Feedback;
import com.lhq.prj.bms.po.User;

public interface IFeedbackService {
	
	Object saveFeedback(Feedback feedback);

	Page findByPage(Page page);
	
	boolean updateFeedback(Feedback feedback) throws Exception;

	
	boolean deleteFeedback(Integer feedbackid);
	
	Object  deleteFeedback1(Feedback feedback);

	List findByExample(User user);
	
	Integer updateBalance(Feedback feedback);

	Page findByPageAndUser(Page pageBean);

}
