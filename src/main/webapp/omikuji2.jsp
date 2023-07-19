<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>おみくじ ─ 課題JM20</title>
</head>
<body>
	<h1>STEP2</h1>
	<p>スクリプトレットで繰り返し処理を利用しておみくじ</p>
	<% for (int i = 1; i < 13; i++) { %>
	<%
		// 乱数発生の準備
		Random random = new Random();
		int fortune = random.nextInt(6);
		// 運勢を判定：
		String result = "";
		if (fortune == 1) {
			result = "大吉";
		} else if (fortune == 2) {
			result = "小吉";
		} else if (fortune == 3) {
			result = "凶";
		} else {
			result = "吉";
		}
	%>
	<p><%= i %>月の運勢は、、、「<%= result %>」です。</p>
	<% } %>
</body>
</html>