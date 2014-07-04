package com.lhq.prj.bms.daojdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.lhq.prj.bms.getConnection.GetConnection;
import com.lhq.prj.bms.po.User;

public class Dao {
	private Connection conn;
	private PreparedStatement pstat;
	String sql = "";

	/**
	 * 用户注册
	 */
	public void addUser(User user) {
		conn = GetConnection.getConnection();
		sql = "INSERT INTO t_user (userName, email, phone, role, password, payPwd) VALUES (?, ?, ?, ?, ?, ?)SELECT @@IDENTITY AS ID";
		try {

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUserName());
			pstat.setString(2, user.getEmail());
			pstat.setString(3, user.getPhone());
			pstat.setString(4, user.getRole());
			pstat.setString(5, user.getPassword());
			pstat.setString(6, user.getPayPwd());

			pstat.executeUpdate();
			pstat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public User login(User user) {
		conn = GetConnection.getConnection();
		sql = "SELECT * FROM T_USER WHERE USERNAME = ?";
		User u = null;
		try {
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUserName());
			ResultSet rs = pstat.executeQuery();
			while (rs.next()){
				u = new User();
				u.setUserId((long) rs.getInt(1));
				u.setUserName(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setPhone(rs.getString(4));
				u.setRole(rs.getString(5));
				u.setPassword(rs.getString(6));
				u.setPoints(rs.getInt(7));
				u.setWwId(rs.getString(8));
				u.setBalance(rs.getFloat(9));
				u.setPayPwd(rs.getString(10));
			}
			pstat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
}