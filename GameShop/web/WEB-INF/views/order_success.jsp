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

		<h2>
			<c:if test="${!empty msg }">
				<div class="alert alert-success">${msg }</div>
			</c:if>
		</h2>
		<p>
			<a href="<c:url value='/order_list'/>">查看我的订单</a>
		</p>
	</div>
</div>
<!--//cart-items-->

</body>
</html>
<style>
	body{
		background-color: #1b2838;
		color: #ffffff;
		margin: 0;
		padding: 0;
	}
	h2{
		text-align: center;
		margin-top: 200px;
	}
	a{
		color: #f3f3f3;

	}
	p{
		text-align: center;
	}
	a:hover{
		color: #007bff;
		text-decoration: underline;
	}

</style>
