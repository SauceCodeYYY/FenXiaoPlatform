package com.lhq.prj.bms.daojdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.lhq.prj.bms.getConnection.GetConnection;
import com.lhq.prj.bms.po.User;


public class Dao
{
	private Connection conn;
	private PreparedStatement pstat;
	String sql="";

/**
* 用户注册
*/
public void addUser(User user)
{
	conn = GetConnection.getConnection();
	sql = "INSERT INTO t_user (userName, email, phone, role, password) VALUES (?, ?, ?, ?, ?)SELECT @@IDENTITY AS ID";
try
{
	
	pstat = conn.prepareStatement(sql);
	pstat.setString(1,user.getUserName());
	pstat.setString(2,user.getEmail());
	pstat.setString(3,user.getPhone());
	pstat.setString(4,user.getRole());
	pstat.setString(5,user.getPassword());
	//pstat.setString(6,user.getPayPwd());
	
	pstat.executeUpdate();
	pstat.close();
	conn.close();
}catch(SQLException e)
{
	e.printStackTrace();
}

}
}