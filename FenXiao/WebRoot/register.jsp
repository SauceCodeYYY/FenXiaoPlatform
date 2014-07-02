<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->

</head>
<body>
	<p align="center">
		<font size="+3" color="#FF3300">用户注册</font>
	</p>
	<form id="form1" name="form1" method="get" action="register.do">
		<table width="340" border="0" align="center">
			<tr>
				<td width="141">用户名：</td>
				<td width="189"><label> <input name="userName"
						type="text" id="userName" /> </label>
				</td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><label> <input name="password" type="text"
						id="password" /> </label>
				</td>
			</tr>

			<tr>
				<td>邮箱：</td>
				<td><label> <input name="email" type="text" id="email" />
				</label>
				</td>
			</tr>

			<tr>
				<td>移动电话：</td>
				<td><label> <input name="phone" type="text" id="phone" />
				</label>
				</td>
			</tr>
			<!--


<tr>
<td>支付密码：</td>
<td><label>
<input name="payPwd" type="text" id="payPwd" />
</label></td>
</tr>


-->
			<tr>
				<td><label> <input type="submit" name="Submit"
						value="提交" /> </label>
				</td>
				<td><label> <input type="reset" name="Submit2"
						value="重置" /> </label>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>