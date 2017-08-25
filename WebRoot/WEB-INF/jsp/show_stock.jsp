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
					<th>编号</th>
					<th>名称</th>
					<th>创建/更新时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${shopList}" var="itemData" varStatus="i">
					<tr>
						<td>${itemData.id}</td>
						<td>${itemData.name}</td>
						<td>${itemData.update_time}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</article>

</body>
</html>
