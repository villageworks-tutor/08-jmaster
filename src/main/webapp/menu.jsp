<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
ソート：<a href="ItemServlet2?action=sort&key=price_asc">値段の低い順</a> <a href="ItemServlet2?action=sort&key=price_desc">値段の高い順</a><br />
追加：商品名<input type="text" /> 価格<input type="number" />を<input type="submit" value="追加" /><br />
検索：<input type="number" />円以下の商品を<input type="submit" value="検索" /><br />
削除：商品番号<input type="number" />番の商品を<input type="submit" value="削除" />
