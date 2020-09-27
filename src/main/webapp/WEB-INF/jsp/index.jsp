<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  springboot整合jsp成功..注意(创建项目一定要为war类型)
  
  <c:forEach items="${list }" var="jzw_user">
<tr>
<td>${jzw_user.name }</td>
<td>${jzw_user.path }</td>
<td>${jzw_user.classname }</td>
<td>${jzw_user.protocol }</td>
<td>${jzw_user.generalization }</td>
<td>${jzw_user.port }</td>
</tr>
</c:forEach>
</body>
</html>