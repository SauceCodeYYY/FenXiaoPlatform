package com.lhq.prj.bms.service.impl;

import java.util.List;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IFeedbackDao;
import com.lhq.prj.bms.po.Feedback;
import com.lhq.prj.bms.po.User;
import com.lhq.prj.bms.service.IFeedbackService;


public class FeedbackService  implements IFeedbackService{
	
	private IFeedbackDao feedbackDao;
	
	


	public void setFeedbackDao(IFeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	public boolean deleteFeedback(Integer feedbackid) {
		Integer flag = feedbackDao.deleteById(feedbackid);
		if (flag != null) {
			return true;
		}
		return false;
	}

	public Object deleteFeedback1(Feedback feedback) {
		
		return null;
	}

	public List findByExample(User user) {
		
		return feedbackDao.findByExample(user);
	}

	public Page findByPage(Page page) {
		page.setRoot(feedbackDao.findByPage(page));
		page.setTotalProperty(feedbackDao.findByCount(page));
		return page;
	}

	public Object saveFeedback(Feedback feedback) {
		
		return feedbackDao.saveFeedback(feedback) ;
	}

	public Integer updateBalance(Feedback feedback) {
		
		return feedbackDao.updateBalance(feedback);
	}

	public boolean updateFeedback(Feedback feedback) throws Exception {
		
		Integer flag= feedbackDao.update(feedback);
		if (flag != null) {
			return true;
		}
		return false;
	}

	public Page findByPageAndUser(Page pageBean) {
		pageBean.setRoot(feedbackDao.findByPageAndUser(pageBean));
		pageBean.setTotalProperty(feedbackDao.findByUserCount(pageBean));
		return pageBean;
	}

}
