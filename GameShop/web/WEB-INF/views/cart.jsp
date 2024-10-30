<%--
  Created by IntelliJ IDEA.
  User: 86191
  Date: 2024/10/29
  Time: 8:51
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="model.Cart" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>

<%--<html>--%>
<%--<head>--%>
<%--  <title>购物车</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>购物车</h1>--%>
<%--<table>--%>
<%--  <tr>--%>
<%--    <th>商品ID</th>--%>
<%--    <th>数量</th>--%>
<%--    <th>价格</th>--%>
<%--    <th>总价</th>--%>
<%--  </tr>--%>
<%--  <c:if test="${empty cartItems}">--%>
<%--    <tr>--%>
<%--      <td colspan="4">购物车为空。</td>--%>
<%--    </tr>--%>
<%--  </c:if>--%>
<%--  <c:forEach var="cartItem" items="${cartItems}">--%>
<%--    <tr>--%>
<%--      <td>${cartItem.itemId}</td>--%>
<%--      <td>${cartItem.quantity}</td>--%>
<%--      <td>${cartItem.price}</td>--%>
<%--      <td>${cartItem.amount}</td>--%>
<%--    </tr>--%>
<%--  </c:forEach>--%>
<%--</table>--%>
<%--<a href="items">继续浏览商品</a>--%>
<%--</body>--%>
<%--</html>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <title>购物车</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/cart.js"></script>
</head>
<body>

<%--<jsp:include page="header.jsp">--%>
<%--  <jsp:param name="flag" value="7"></jsp:param>--%>
<%--</jsp:include>--%>

<div class="container">
  <h2>我的购物车</h2>
  <div class="row">
    <table class="table table-striped">
      <thead>
      <tr>
        <th>商品ID</th>
        <th>商品名称</th>
        <th>数量</th>
        <th>单价</th>
        <th>总价</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <c:if test="${empty cartItems}">
        <tr>
          <td colspan="6" class="text-center">购物车为空。</td>
        </tr>
      </c:if>
      <c:forEach var="cartItem" items="${cartItems}">
        <c:if test="${not empty cartItem}">
          <tr>
            <td>${cartItem.itemId}</td>
            <td><a href="/goods_detail?id=${cartItem.itemId}">${cartItem.name}</a></td>
            <td>${cartItem.quantity}</td>
            <td>¥ ${cartItem.price}</td>
            <td>¥ ${cartItem.amount}</td>
            <td>
              <button class="btn btn-info" onclick="buy(${cartItem.itemId});">增加</button>
              <button class="btn btn-warning" onclick="lessen(${cartItem.itemId});">减少</button>
              <button class="btn btn-danger" onclick="deletes(${cartItem.itemId});">删除</button>
            </td>
          </tr>
        </c:if>
      </c:forEach>
      </tbody>
    </table>
    <div class="col-md-12 text-right">
      <hr>
      <h3>订单总金额: ¥ <span id="cart-total">${totalAmount}</span></h3>
      <a class="btn btn-success btn-lg" href="/order_submit">提交订单</a>
    </div>
  </div>
</div>

<%--<jsp:include page="footer.jsp"></jsp:include>--%>

</body>
</html>


