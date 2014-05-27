package com.lhq.prj.bms.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.dao.IFeedbackDao;
import com.lhq.prj.bms.po.Feedback;
import com.lhq.prj.bms.po.User;

public class FeedbackDao extends SqlMapClientDaoSupport implements IFeedbackDao {

	public Integer deleteById(Integer feedbackid) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("Feedback.deleteById",
				feedbackid);
	}

	public Object deletefeedback(Feedback feedback) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("Feedback.deletefreight",
				feedback);
	}

	public int findByCount(Page page) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Feedback.findByCount", page);
	}

	public List findByExample(User user) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Feedback.findByExample",
				user);
	}

	public List findByPage(Page page) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Feedback.findByPage",
				page);
	}

	public Object saveFeedback(Feedback feedback) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().insert("Feedback.save", feedback);
	}

	public Integer update(Feedback feedback) throws Exception {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("Feedback.update", feedback);
	}

	public Integer updateBalance(Feedback feedback) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("Feedback.updateBalance",
				feedback);
	}

	public List findByPageAndUser(Page pageBean) {
		return getSqlMapClientTemplate().queryForList(
				"Feedback.findByPageAndUser", pageBean);
	}

	public int findByUserCount(Page pageBean) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"Feedback.findByUserCount", pageBean);
	}

}
