<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<title>我的订单</title>
</head>
<body>

	<!--cart-items-->
	<div class="cart-items">
		<div class="container">
			<h2>我的订单</h2>
				<table class="table table-bordered table-hover">
				<tr>
					<th width="10%">订单编号</th>
					<th width="10%">下单时间</th>
					<th width="20%">商品详情</th>
					<th width="30%">收货信息</th>
					<th width="10%">订单状态</th>
					<th width="10%">支付方式</th>
					<th width="10%">总价</th>
				</tr>
					<!--订单列表-->
					<c:forEach items="${orderList }" var="order">
						<tr>
							<td><p>${order.id }</p></td>
							<td><p>${order.datetime }</p></td>
							<td>
								<!--对应订单项列表（商品详情列）-->
								<c:forEach items="${order.itemList }" var="item">
<%--									<p>${item.itemName }(${item.price })</p>--%>
									<p>
										<a href="OrderItemInformationServlet?itemId=${item.itemId}">${item.itemName}</a> (¥${item.price})
									</p>
								</c:forEach>
								<!--对应订单项列表（商品详情列）-->
							</td>
							<td>
								<p>${order.name }</p>
								<p>${order.phone }</p>
								<p>${order.email }</p>
							</td>
							<td>
								<p>
									<c:if test="${order.status==2 }"><span style="color:red;">已付款</span></c:if>
									<c:if test="${order.status==3 }"><span style="color:green;">已发货</span></c:if>
									<c:if test="${order.status==4 }"><span style="color:black;">已完成</span></c:if>
								</p>
							</td>
							<td>
								<p>
									<c:if test="${order.paytype==1 }">微信</c:if>
									<c:if test="${order.paytype==2 }">支付宝</c:if>
									<c:if test="${order.paytype==3 }">货到付款</c:if>
								</p>
							</td>
							<td><p>${order.total }</p></td>
						</tr>

					</c:forEach>
					<!--订单列表-->
				</table>
		</div>
	</div>
	<!--//cart-items-->

	<!-- 引入已有游戏列表 -->
	<jsp:include page="/WEB-INF/views/existing_games.jsp">
		<jsp:param name="existingGames" value="${existingGames}" />
	</jsp:include>
</body>
</html>
