<%@ include file="../jsp/common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>购物车</title>
</head>
<body>
<div>
  <h2 class="cart">我的购物车</h2>
  <form action="cart" method="post">
    <table class="cart">
      <thead>
      <tr>
        <th>商品名称</th>
        <th>商品图片</th>
        <th>单价</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <c:if test="${empty cartItems}">
        <tr>
          <td colspan="4" class="text-center">购物车为空。</td>
        </tr>
      </c:if>
      <c:forEach var="cartItem" items="${cartItems}">
        <tr>
          <td>
            <form action="cart" method="post">
              <input type="checkbox"
                     name="selectedItem"
                     value="${cartItem.item.id}"
                ${cartItem.isSelected == 1 ? 'checked' : ''}>
              <input type="hidden" name="action" value="updateSelection">
            </form>
          </td>
          <td>${cartItem.item.name}</td>
          <td><img src="${cartItem.item.picture}" alt="${cartItem.item.name}" width="200" height="120"></td>
          <td>¥ ${cartItem.price}</td>
          <td>
            <form action="cart" method="post">
              <input type="hidden" name="action" value="delete">
              <input type="hidden" name="itemId" value="${cartItem.item.id}">
              <button type="submit" class="cartDelete">删除</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <div>
      <hr>
      <h3 class="cart">总金额: ¥ ${totalAmount}</h3>

      <div id="submitOrderForm" action="${pageContext.request.contextPath}/order_submit" method="post">
        <input type="hidden" name="action" value="submitOrder">
        <input type="hidden" name="totalAmount" value="${totalAmount}">
        <c:forEach var="cartItem" items="${cartItems}">
          <input type="hidden" name="itemIds" value="${cartItem.itemId}">
        </c:forEach>
        <button type="submit" class="btn btn-success btn-lg">提交订单</button>
      </div>
    </div>
  </form>
</div>
</body>
</html>



