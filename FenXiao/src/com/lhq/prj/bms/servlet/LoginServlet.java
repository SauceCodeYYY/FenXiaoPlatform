package com.lhq.prj.bms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhq.prj.bms.daojdbc.Dao;
import com.lhq.prj.bms.md5.MyMD5Util;
import com.lhq.prj.bms.po.User;

public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		Dao dao = new Dao();
		String userName = new String(request.getParameter("userName").getBytes("ISO8859-1"), "UTF-8");
		String password = new String(request.getParameter("password").getBytes("ISO8859-1"), "UTF-8");
		System.out.println("===LoginServlet.doPost===" + userName + " " + password);
		User temp = new User();
		temp.setUserName(userName);
		temp.setPassword(password);
		
		User userInDb = dao.login(temp);
		if (userInDb != null) {
			try {
				if (loginValid(temp, userInDb)) {
					System.out.println("欢迎登陆！！！");
					if ("admin".equals(userInDb.getRole())) {
						request.getSession().setAttribute("tip", "admin");// 管理员
					} else {
						request.getSession().setAttribute("tip", "user");;// 普通用户
					}
					request.getSession().setAttribute("user", userInDb);
					request.getRequestDispatcher("main.jsp").forward(request, response);
				} else {
					System.out.println("口令错误，请重新输入！！！");
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			request.getSession().setAttribute("tip", "用户名或者密码错误!");// 管理员
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	public boolean loginValid(User temp, User userInDb)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println("===UserAction.loginValid===" + userInDb.getPassword());
		if (null != userInDb.getPassword()) { // 该用户存在
			return MyMD5Util.validPassword(temp.getPassword(), userInDb.getPassword());
		} else {
			System.out.println("不存在该用户！！！");
			return false;
		}
	}
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
