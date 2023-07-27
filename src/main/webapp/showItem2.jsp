<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta charset="UTF-8">
	<title>Show All Items</title>
</head>
<body>
	<p>
	ソート：値段の低い順 値段の高い順<br />
	追加：商品名<input type="text" /> 価格<input type="number" />を<input type="submit" value="追加" /><br />
	検索：<input type="number" />円以下の商品を<input type="submit" value="検索" /><br />
	削除：商品番号<input type="number" />番の商品を<input type="submit" value="削除" />
	</p>
	<hr />
	<table border="1">
		<tr>
			<td>NO</td>
			<td>商品名</td>
			<td>値段</td>
		</tr>
	</table>
</body>
</html>