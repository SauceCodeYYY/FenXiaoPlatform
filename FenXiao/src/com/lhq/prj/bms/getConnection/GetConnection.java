package com.lhq.prj.bms.getConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
*
* ��JDBC�ķ��������ݿ������
*
*/
public class GetConnection {
//ͨ��̬����ע����������
public static Connection getConnection()
{
	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=FenXiaoPlatform";
	Connection con = null;
	try {
		Class.forName(driver);
		try {
			con = DriverManager.getConnection(url,"sa","sa");
			} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	catch (ClassNotFoundException e)
	{
		e.printStackTrace();
	}
	System.out.println("�ѻ����ݿ������");
	return con;
}
/*public static void main(String args[]){
getConnection();
}*/
}