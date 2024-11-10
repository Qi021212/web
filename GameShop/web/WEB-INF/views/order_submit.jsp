<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>订单确认</title>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
</head>
<body>

<div class="cart-items">
    <div class="container">
        <h2>订单确认</h2>
<%--        <form class="form-horizontal" action="/order_confirm" method="post" id="payform">--%>
        <form action="${pageContext.request.contextPath}/order_confirm" method="post">
<%--            <input type="hidden" name="userId" value="1">--%>
            <input type="hidden" name="totalAmount" value="${totalAmount}"> <!-- 确保有总金额 -->
            <c:forEach var="itemId" items="${itemIds}">
                <input type="hidden" name="itemIds" value="${itemId}">
            </c:forEach>

            <div>
                <label>姓名</label>
                <input type="text" name="name" value="${user.username}" required>
            </div>
            <div>
                <label>电话</label>
                <input type="text" name="phone" value="${user.phone}" required>
            </div>
            <div>
                <label>邮箱</label>
                <input type="text" name="email" value="${user.email}" required>
            </div>

            <br><hr><br>

            <h3>商品列表</h3>
            <c:forEach var="item" items="${cartItems}">
            <div>
                <p>商品名称: ${item.name}</p>
                <p>商品价格: ¥ ${item.price}</p>
                <p>商品图片: <img src="${item.picture}" alt="${item.name}" width="100" height="100"></p>

                <input type="hidden" name="itemIds[]" value="${item.id}">
<%--                <input type="hidden" name="itemIds" value="${item.id}">--%>
            </div>
            </c:forEach>

            <h2>选择支付方式</h2>
            <h3>支付金额: ${totalAmount }</h3><br><br>

<%--        <form action="/order_confirm" method="post">--%>
            <div class="col-sm-6 col-md-4 col-lg-3 " >
                <label>
                    <div class="thumbnail">
                        <input type="radio" name="paytype" value="1" checked="checked" />
                        <img src="images/wechat.jpg" alt="微信支付">
                    </div>
                </label>
            </div>
            <div class="col-sm-6 col-md-4 col-lg-3 " >
                <label>
                    <div class="thumbnail">
                        <input type="radio" name="paytype" value="2"  />
                        <img src="images/alipay.jpg" alt="支付宝支付">
                    </div>
                </label>
            </div>
            <%--				<div class="col-sm-6 col-md-4 col-lg-3 ">
                                <label>
                                    <div class="thumbnail">
                                        <input type="radio" name="paytype" value="3"  />
                                        <img src="images/offline.jpg" alt="货到付款">
                                    </div>
                                </label>
                            </div>--%>
            <div class="clearfix"> </div>
            <div class="register-but text-center">
                <input type="submit" value="确认订单">
<%--                <div class="clearfix"> </div>--%>
            </div>
        </form>
    </div>
</div>


<script type="text/javascript">
    function dopay(paytype){
        $("#paytype").val(paytype);
        $("#payform").submit();
    }
</script>

</body>
</html>

<%--            <c:forEach var="itemId" items="${itemIds}">--%>
<%--            <input type="hidden" name="itemIds" value="${itemId}">--%>
<%--            </c:forEach>--%>
<%--            <c:forEach var="quantity" items="${quantities}">--%>
<%--            <input type="hidden" name="quantities" value="${quantity}">--%>
<%--            </c:forEach>--%>
<%--&lt;%&ndash;            <input type="hidden" name="totalAmount" value="${totalAmount}"> <!-- 确保有总金额 -->&ndash;%&gt;--%>
<%--            <div class="row">--%>
<%--                <label class="control-label col-md-1">收货人</label>--%>
<%--                <div class="col-md-6">--%>
<%--                    <input type="text" class="form-control" name="name" required="required" placeholder="输入收货人"><br>--%>
<%--&lt;%&ndash;                    <input type="text" class="form-control" name="name" value="${user.name }" style="height:auto;padding:10px;" placeholder="输入收货人" required="required"><br>&ndash;%&gt;--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="row">--%>
<%--                <label class="control-label col-md-1">收货电话</label>--%>
<%--                <div class="col-md-6">--%>
<%--                    <input type="text" class="form-control" name="phone" required="required" placeholder="输入收货电话"><br>--%>
<%--&lt;%&ndash;                    <input type="text" class="form-control" name="phone" value="${user.phone }" style="height:auto;padding:10px;" placeholder="输入收货电话" required="required"><br>&ndash;%&gt;--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="row">--%>
<%--                <label class="control-label col-md-1">收货地址</label>--%>
<%--                <div class="col-md-6">--%>
<%--                    <input type="text" class="form-control" name="address" required="required" placeholder="输入收货地址"><br>--%>
<%--&lt;%&ndash;                    <input type="text" class="form-control" name="address" value="${user.address }" style="height:auto;padding:10px;" placeholder="输入收货地址" required="required"><br>&ndash;%&gt;--%>
<%--                </div>--%>
<%--            </div>--%>

