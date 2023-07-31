<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 価格による並べ替え -->
ソート：<a href="ItemServlet2?action=sort&key=price_asc">値段の低い順</a>
<a href="ItemServlet2?action=sort&key=price_desc">値段の高い順</a><br />

<!-- 商品追加 -->
<form action="ItemServlet2" method="post">
追加：商品名<input type="text" name="name" />
価格<input type="text" name="price" size="5" />を<input type="submit" value="追加" />
<input type="hidden" name="action" value="add" />
</form>
<form action="ItemServlet2" method="post">
検索：商品名<input type="text" name="name" value="${sessionScope.name}" size="5" /> | 価格<input type="text" name="minPrice" value="${sessionScope.minPrice}" size="5" />円以上 <input type="text" name="maxPrice" value="${sessionScope.maxPrice}" size="5" />円以下の商品を<input type="submit" value="検索" />
<input type="hidden" name="action" value="search" />
</form>
<form action="ItemServlet2" method="post">
削除：商品番号<input type="text" name="code" size="5" />番の商品を<input type="submit" value="削除" />
<input type="hidden" name="action" value="delete" />
</form>
<form action="ItemServlet2" method="post">
修正：商品番号<input type="text" name="code" size="5" />番の値段を<input type="text" name="price" size="5" />に<input type="submit" value="変更" />
<input type="hidden" name="action" value="update" />
</form>
