<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/header.jsp"%>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css"
	href="resources/css/markdown-body.css">
</head>

<body>
	<article class="markdown-body">
		<h1 id="zhilog">
			<a name="user-content-zhilog" href="#zhilog"
				class="headeranchor-link" aria-hidden="true"><span
				class="headeranchor"></span> </a>存货明细
		</h1>
		<table>
			<thead>
				<tr>
					<th>商品编号</th>
					<th>商品名称</th>
					<th>库存商品总价</th>
					<th>库存商品数量</th>
					<th>更新时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${stockInfoList}" var="itemData" varStatus="i">
					<tr>
						<td>${itemData.shop.id}</td>
						<td>${itemData.shop.name}</td>
						<td>${itemData.stock.stock_price}</td>
						<td>${itemData.stock.stock_num}</td>
						<td>${itemData.stock.stock_time}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</article>

</body>
</html>
