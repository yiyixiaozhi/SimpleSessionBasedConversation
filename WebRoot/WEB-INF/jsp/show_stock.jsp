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
				class="headeranchor"></span> </a>存货明细
		</h1>
		<table id="toDropUp">
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
						<td>${itemData.shop.id}				</td>
						<td>${itemData.shop.name}			</td>
						<td>${itemData.stock.stock_price}	</td>
						<td>${itemData.stock.stock_num}		</td>
						<td>${itemData.stock.stock_time}	</td>
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
		        $.post("${ctx}/api/stock/toStockPageByIndex", {
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
										+ "<td>" + itemData.shop.id				+ "</td>"
										+ "<td>" + itemData.shop.name			+ "</td>"
										+ "<td>" + itemData.stock.stock_price	+ "</td>"
										+ "<td>" + itemData.stock.stock_num		+ "</td>"
										+ "<td>" + itemData.stock.stock_time	+ "</td>"
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
