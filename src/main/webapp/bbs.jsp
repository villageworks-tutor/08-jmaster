<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	
	// セッションからメッセージリストを取得
	List<String> messages = (ArrayList<String>)session.getAttribute("messages");
	if (messages == null)  {
		// 取得したメッセージリストがない場合
		messages = new ArrayList<String>();
		session.setAttribute("messages", messages);
	}
	// リクエストパラメータの文字コードを設定
	request.setCharacterEncoding("utf-8");
	// リクエストパラメータを取得
	String name = request.getParameter("name");
	String message = request.getParameter("message");
	
	if ((name != null && !name.isEmpty()) && (message != null && !message.isEmpty())) {
		messages.add(name + "：" + message);
	}

%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>掲示板 ─ JM21</title>
</head>
<body>
	<form action="/jmaster/bbs.jsp" method="post">
		名前：<br />
		<input type="text" name="name" /><br />
		メッセージ：<br />
		<textarea name="message" rows="5" cols="36"></textarea><br />
		<input type="submit" value="書き込み" />
	</form>
	<hr />
<%
	if (messages != null) {
		for (String comment : messages) {
%>
		<p><%= comment %></p>
		<hr />
<%
		}
	}
%>
</body>
</html>