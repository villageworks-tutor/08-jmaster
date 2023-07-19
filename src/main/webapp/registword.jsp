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
		英語：<input type="text" name="english" /><br />
		日本語：<input type="text" name="japanese" /><br />
		<input type="submit" value="登録" />
	</form>
</body>
</html>