<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>英単語帳 ─ JM31</title>
</head>
<body>
	<a href="/jmaster/registword.jsp">単語登録</a><br />
	<hr />
	<c:choose>
		<c:when test="${!empty requestScope.resullts}">
		
		</c:when>
		<c:when test="${!empty sessionScope.dictionary}">
			<table border="1">
				<tr>
					<th>英語</th>
					<th>日本語</th>
				</tr>
				<c:forEach items="${dictionary}" var="word">
				<tr>
					<td>${word.english}</td>
					<td>${word.japanese}</td>
				</tr>
				</c:forEach>
			</table>
		</c:when>
	</c:choose>
<%-- 	<c:if test="${!empty sessionScope.dictionary}"> --%>
<!-- 	<table border="1"> -->
<!-- 		<tr> -->
<!-- 			<th>英語</th> -->
<!-- 			<th>日本語</th> -->
<!-- 		</tr> -->
<%-- 		<c:forEach items="${dictionary}" var="word"> --%>
<!-- 		<tr> -->
<%-- 			<td>${word.english}</td> --%>
<%-- 			<td>${word.japanese}</td> --%>
<!-- 		</tr> -->
<%-- 		</c:forEach> --%>
<!-- 	</table> -->
<%-- 	</c:if> --%>
</body>
</html>