<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/commons/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/commons/header.jsp"%>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css"
	href="resources/css/markdown-body.css">
<link rel="stylesheet" href="resources/css/dropload.css">
<script src="resources/libs/dropload.min.js"></script>
<script> 
	var pageNumber = ${pageNumber}; 
</script>
</head>

<body>
	<article id="content" class="markdown-body">
		<h1 id="zhilog">
			<a name="user-content-zhilog" href="#zhilog"
				class="headeranchor-link" aria-hidden="true"><span
				class="headeranchor"></span> </a>进货明细
		</h1>
		<table id="toDropUp">
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
<script type="text/javascript">	
$(document).ready(function(){ 
		var userId = ${userId};
		var pageSize = ${pageSize};
 		// 上拉加载
		 $("#content").dropload({
		    scrollArea : window,
		    loadDownFn : function(me){
				pageNumber ++;
		        $.post("${ctx}/api/purchase/toPurchasePageByIndex", {
						userId: userId,
						pageNumber: pageNumber,
						pageSize: pageSize
					},
					function(data,status){
						if (data == null) {
							alert('无任何数据，请检查接口是否正确');
						} else {
							var list = data.itemDataList;
							if(list != null){
								if(list.length <= 0){
									// 再往下已经没有数据
									// 锁定
									me.lock();
									// 显示无数据
									me.noData();
								} else { 
									var result = '';
									for (var i in list) {
										var itemData = list[i];
										result += 
										"<tr>"
										+ "<td>" + itemData.purchase.id				+ "</td>"
										+ "<td>" + itemData.purchase.purchase_price + "</td>"
										+ "<td>" + itemData.purchase.purchase_num	+ "</td>"
										+ "<td>" + itemData.purchase.client_name	+ "</td>"
										+ "<td>" + itemData.purchase.purchase_time	+ "</td>"
										+ "<td>" + itemData.shop.id					+ "</td>"
										+ "<td>" + itemData.shop.name				+ "</td>"
										+ "</tr>";
									}
//									alert($("#toDropUp").html());
									$("#toDropUp").append(result);
								}
							}
						}
						// 每次数据加载完，必须重置;即使加载出错，也得重置
						me.resetload();
					});
		    }
		}); 
});
</script>
</html>
