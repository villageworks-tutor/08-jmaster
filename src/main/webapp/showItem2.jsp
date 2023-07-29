<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>Show All Items</title>
</head>
<body>
	<jsp:include page="/menu.jsp" />
	<hr />
	<table border="1">
		<tr>
			<td>NO</td>
			<td>商品名</td>
			<td>値段</td>
		</tr>
		<c:forEach items="${items}" var="item">
		<tr>
			<td>${item.code}</td>
			<td>${item.name}</td>
			<td>${item.price}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>