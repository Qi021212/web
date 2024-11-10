<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<title>支付成功</title>
</head>
<body>
<!--cart-items-->
<div class="cart-items">
	<div class="container">

		<c:if test="${!empty msg }">
			<div class="alert alert-success">${msg }</div>
		</c:if>

		<p>
			<a href="<c:url value='/order_list'/>">查看我的订单</a>
		</p>
	</div>
</div>
<!--//cart-items-->

</body>
</html>
