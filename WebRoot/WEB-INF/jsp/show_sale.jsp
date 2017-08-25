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
				class="headeranchor"></span> </a>商品明细
		</h1>
		<table>
			<thead>
				<tr>
					<th>销售编号</th>
					<th>销售总价</th>
					<th>销售数量</th>
					<th>客户名称</th>
					<th>创建/更新时间</th>
					<th>商品编号</th>
					<th>商品名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${saleInfoList}" var="itemData" varStatus="i">
					<tr>
						<td>${itemData.sale.id}</td>
						<td>${itemData.sale.sale_price}</td>
						<td>${itemData.sale.sale_num}</td>
						<td>${itemData.sale.client_name}</td>
						<td>${itemData.sale.sale_time}</td>
						<td>${itemData.shop.id}</td>
						<td>${itemData.shop.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</article>

</body>
</html>
