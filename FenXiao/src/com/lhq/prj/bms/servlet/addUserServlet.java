package com.lhq.prj.bms.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhq.prj.bms.core.Page;
import com.lhq.prj.bms.daojdbc.Dao;
import com.lhq.prj.bms.md5.MyMD5Util;
import com.lhq.prj.bms.po.User;
import com.lhq.prj.bms.service.IUserService;
import com.lhq.prj.bms.service.impl.UserService;

public class addUserServlet extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String userName = new String(request.getParameter("userName").getBytes(
				"ISO8859_1"), "utf-8");
		String password = new String(request.getParameter("password").getBytes(
				"ISO8859_1"), "utf-8");

		String email = new String(request.getParameter("email").getBytes(
				"ISO8859_1"), "utf-8");
		String phone = new String(request.getParameter("phone").getBytes(
				"ISO8859_1"), "utf-8");
		String payPwd = new
		 String(request.getParameter("payPwd").getBytes("ISO8859_1"),"utf-8");
		System.out.println("===addUserServlet===" + userName + "  " + password);

		String encryptedPwd = null, encPayPwd = null;       
	    
		try {
			encryptedPwd = MyMD5Util.getEncryptedPwd(password);
			encPayPwd = MyMD5Util.getEncryptedPwd(payPwd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {     
            e.printStackTrace();     
        }         
	                 
		User user = new User();
		user.setUserName(userName);
		user.setPassword(encryptedPwd);
		user.setPayPwd(encPayPwd);
		user.setEmail(email);
		user.setPhone(phone);
		user.setRole("normal");

		Dao dao = new Dao();

		dao.addUser(user);

		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}