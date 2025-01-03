<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <!-- jQuery UI -->
    <link rel="stylesheet" href="../../css/jquery-ui.css">
    <script src="../../js/jquery-ui.min.js"></script>
    <script src="../../js/jquery-3.7.1.min.js"></script>

</head>
<body>
<div id="orderContainer" style="display: none;">
    <div id="tabs">
        <ul>
            <li><a href="#tab1">填写信息</a></li>
            <li><a href="#tab2">确认订单</a></li>
        </ul>
        <form action="${pageContext.request.contextPath}/order_confirm" method="post">
            <input type="hidden" name="totalAmount" value="${totalAmount}">
            <c:forEach var="selectedItemId" items="${selectedItemIds}">
            <input type="hidden" name="selectedItemIds" value="${selectedItemId}">
            </c:forEach>
        <div id="tab1">
            <form id="orderForm">
                <div>
                    <label>姓名:</label>
                    <input type="text" name="name" value="${user.username}" required>
                </div>
                <div>
                    <label>电话:</label>
                    <input type="text" name="phone" value="${user.phone}" required>
                </div>
                <div>
                    <label>邮箱:</label>
                    <input type="text" name="email" value="${user.email}" required>
                </div>
                <button type="button" onclick="nextTab()">下一步</button>
            </form>
        </div>
        <div id="tab2">
            <h3>订单详情</h3>
            <div id="orderDetails"></div>

            <h2>商品列表</h2>
            <c:forEach var="selectedItem" items="${selectedItems}">
                <div>
                    <p>商品名称: ${selectedItem.item.name} 价格: ¥ ${selectedItem.price}</p>
                    <input type="hidden" name="itemIds[]" value="${selectedItem.item.id}">
                </div>
            </c:forEach>

            <h3>支付金额: ¥ ${totalAmount}</h3>
            <label>
                支付方式:
                <select id="paytype">
                    <option value="1">微信支付</option>
                    <option value="2">支付宝支付</option>
                </select>
            </label>

            <div class="clearfix"> </div>
            <button type="button" onclick="submitFinalOrder()">确认订单</button>

        </div>
        </form>
    </div>
</div>

