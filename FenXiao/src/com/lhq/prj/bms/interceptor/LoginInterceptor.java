package com.lhq.prj.bms.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.lhq.prj.bms.po.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = -5292321870385568327L;
	private String tip;
	
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 获取session对象(经过struts2包装过)
		Map session = ActionContext.getContext().getSession();
		// 获取session作用域内是否有值
		User user = (User) session.get("user");
		if (user != null) {// 合法访问
			return invocation.invoke();
		} else {// user为空说明未经过登陆,保存错误提示信息,跳到登陆页面
			ServletActionContext.getResponse().sendError(408);
			// ActionContext.getContext().put("tip", "请先登陆再进行操作!");
			return Action.LOGIN;
		}
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
}
