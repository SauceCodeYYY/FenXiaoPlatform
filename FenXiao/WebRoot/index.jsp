<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

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

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<script>
	function check() {
		var frm = document.form1;
		if (frm.userName.value == "") {
			alert("用户名不能为空!");
			document.form1.userName.focus();
			return false;
		} else if (frm.password.value == "") {
			alert("登录密码不能为空!");
			frm.password.focus();
			return false;
		} else {
			return true;
		}
	}
</script>
<body>
<s:form action="register.do" method="post">
	<s:textfield name="userName" label="用户名"></s:textfield>
	<s:password name="password" label="密码"></s:password>
	<s:submit value="注册"></s:submit>
</s:form>
</body>	
</html>
