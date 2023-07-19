<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 乱数発生の準備
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
	<title>おみくじ ─ 課題JM20</title>
</head>
<body>
	<h1>STEP1</h1>
	<p>スクリプトレットを使用しておみくじ</p>
	<p>今日の運勢は、、、「<%= result %>」です。</p>
</body>
</html>