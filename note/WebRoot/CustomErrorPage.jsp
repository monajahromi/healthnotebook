<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=utf-8"%>
<html >

<head>
<% String protocol=request.getScheme();
	String base = protocol+"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	%>
<base href="<%=base%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- <title>&#1583;&#1587;&#1578;&#1585;&#1587;&#1740; &#1594;&#1740;&#1585; &#1605;&#1580;&#1575;&#1586;</title> -->
<title>خطای داخلی سرور</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<style>
body{
	direction: rtl;
	font: 0.8em tahoma ;
	text-align:center;
}
#errorBox{
	background: #ffffff;
	border: solid 1px #e5e5e5;
	box-shadow: 0 0 15px #dddddd;
	border-radius: 5px;
	padding: 0px;
	margin: 50px;
	padding: 50px;
	display: inline-block;
}
#loginButton{
	background: #216f94 url(../UISelector?name=/img/loginBtn-bg.png) repeat-x;
	text-shadow: #1b5c7a 1px 1px 0px;
	color: #ffffff;
	font-family: tahoma;
	font-weight: bold;
	text-decoration: none;
	padding:2px 20px 8px 20px;
	border-radius: 10px;
	border: solid 1px transparent;
}
#loginButton:hover{
	border: solid 1px #13455b;
}
</style>

</head>

<body class="popup">
	<div id="centerArea">
	<div id="errorBox">
	<div>
		 <img src="UISelector?name=/portalImage/error.png" /> 
	</div>


<table align="center">

	<tr>
<!-- 	<td style="color: red; font-weight: bold;"> خطا: </td> -->
		<td class="informationPage fieldTitle" style="font-weight: bold;" >خطای داخلی سرور .
		</td>
	</tr>
</table>
	<div style="padding-top:20px">
		<!-- <a id="loginButton" href="/HotelReservationConsole/First.jsp?pageId=0-0" class="informationPage fieldTitle">ورود به سیستم</a> -->
	</div>
	</div>
</div>

</body> 

</html>
