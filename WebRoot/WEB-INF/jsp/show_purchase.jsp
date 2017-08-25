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
				class="headeranchor"></span> </a>进货明细
		</h1>
		<table>
			<thead>
				<tr>
					<th>进货编号</th>
					<th>进货总价</th>
					<th>进货数量</th>
					<th>客户名称</th>
					<th>创建/更新时间</th>
					<th>商品编号</th>
					<th>商品名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${purchaseInfoList}" var="itemData" varStatus="i">
					<tr>
						<td>${itemData.purchase.id}</td>
						<td>${itemData.purchase.purchase_price}</td>
						<td>${itemData.purchase.purchase_num}</td>
						<td>${itemData.purchase.client_name}</td>
						<td>${itemData.purchase.purchase_time}</td>
						<td>${itemData.shop.id}</td>
						<td>${itemData.shop.name}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</article>

</body>
</html>
