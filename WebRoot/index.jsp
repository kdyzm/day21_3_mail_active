<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
  	<c:url value="/"></c:url>
	<hr/>
	<p>主页</p>
	<form name="xx" action="" method="post">
		Name:<input type="text" name="name"/><br/>
		Pwd:<input type="text" name="pwd"/><br/>
		Email:<input type="text" name="email"/><br/>
		<input type="button" onclick="_login();" value="登录"/>
		<input type="button" onclick="_reg();" value="注册"/> 
	</form>
  </body>
  <script type="text/javascript">
  	function _reg(){
  		document.forms[0].action="<c:url value='/RegServlet'/>";
  		document.forms[0].submit();
  	}
	function _login(){
  		document.forms[0].action="<c:url value='/LoginSerlvet'/>";
  		document.forms[0].submit();
  	}
  </script>
</html>
