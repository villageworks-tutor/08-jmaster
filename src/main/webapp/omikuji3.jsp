<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// リクエストパラメータを取得
	String name = request.getParameter("name");
	// 乱数の発生
	Random random = new Random();
	int fortune = random.nextInt(6);
	// 運勢を判定
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
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>おみくじ ─ JM20</title>
</head>
<body>
	<h1>STEP3</h1>
	<p>リクエストパラメータを取得しておみくじ結果を表示</p>
	<p><%= name %>さんの今日の運勢は、、、「<%= result %>」です。</p>
</body>
</html>