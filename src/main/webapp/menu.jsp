<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 価格による並べ替え -->
ソート：<a href="ItemServlet2?action=sort&key=price_asc">値段の低い順</a>
<a href="ItemServlet2?action=sort&key=price_desc">値段の高い順</a><br />

<!-- 商品追加 -->
<form action="ItemServlet2" method="post">
追加：商品名<input type="text" name="name" />
価格<input type="number" name="price" />を<input type="submit" value="追加" />
<input type="hidden" name="action" value="add" />
</form>
検索：<input type="number" />円以下の商品を<input type="submit" value="検索" /><br />
削除：商品番号<input type="number" />番の商品を<input type="submit" value="削除" />
