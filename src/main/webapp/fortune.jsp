<%@page import="la.bean.FortuneBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<%
		FortuneBean fortune = (FortuneBean)request.getAttribute("fortune");
		if (fortune != null) {
	%>
		<p><%= fortune.getMonth() %>月の運勢は、、、</p>
		<ul>
			<li>ラッキーカラー：<%= fortune.getColor() %></li>
			<li>ラッキーアイテム：<%= fortune.getItem() %></li>
			<li>順位：<%= fortune.getRank() %>位</li>
		</ul>
	<%			
		}
	%>
</body>
</html>