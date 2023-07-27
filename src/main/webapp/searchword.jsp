<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>英単語帳 ─ JM31</title>
</head>
<body>
	<a href="/jmaster/registword.jsp">単語登録</a>
	<a href="/jmaster/searchword.jsp">単語検索</a><br />
	<hr />
	<form action="/jmaster/WordServlet" method="post">
		キーワード：<input type="text" name="keyword" /><br />
		<input type="hidden" name="action" value="search" />
		<input type="submit" value="検索" />
	</form>
</body>
</html>