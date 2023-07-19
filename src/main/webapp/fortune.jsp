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
	<form action="#" method="">
		<select>
			<% for (int i = 1; i < 13; ++i) { %>
			<option value="<%= i %>"><%= i %></option>
			<% } %>
		</select>
		月
		<input type="submit" value="占ってみる" />
	</form>
</body>
</html>