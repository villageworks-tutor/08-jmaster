<%@page import="la.bean.FortuneBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>占い ─ JM30</title>
</head>
<body>
	<h1>占いたい月を入力してね</h1>
	<form action="FortuneServlet" method="get">
		<select name="month">
			<% for (int i = 1; i < 13; ++i) { %>
			<option value="<%= i %>"><%= i %></option>
			<% } %>
		</select>
		月
		<input type="submit" value="占ってみる" />
	</form>
	<c:if test="${!empty fortune}">
		<p>${fortune.month}月の運勢は、、、</p>
		<ul>
			<li>ラッキーカラー：${fortune.color}</li>
			<li>ラッキーアイテム：${fortune.item}</li>
			<li>順位：${fortune.rank}位</li>
		</ul>
	</c:if>
</body>
</html>