<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>empテーブル検索 ─ 追加課題JM51</title>
</head>
<body>
	<form action="EmpServlet" method="post">
		年齢の下限（必須）<input type="text" name="lowerAge" size="10" placeholder="下限" />,
		年齢の上限（必須）<input type="text" name="upperAge" size="10" placeholder="上限" />
		<input type="hidden" name="action" value="step1" />
		<input type="submit" value="検索" />
	</form>
	<form action="EmpServlet" method="post">
		上位<input type="text" name="count" size="10" placeholder="人数" />人
		<input type="hidden" name="action" value="step2" />
		<input type="submit" value="検索" />
	</form>
	<hr />
	<table border="1">
		<tr>
			<th>No.</th>
			<th>氏名</th>
			<th>年齢</th>
			<th>電話番号</th>
		</tr>
		<c:forEach items="${empList}" var="emp">
		<tr>
			<td>${emp.code}</td>
			<td>${emp.name}</td>
			<td>${emp.age}</td>
			<td>${emp.tel}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>