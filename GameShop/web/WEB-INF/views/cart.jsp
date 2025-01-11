<%@ include file="../jsp/common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>购物车</title>
  <!-- jQuery -->
  <script src="js/jquery-3.7.1.min.js"></script>

  <!-- jQuery UI -->
  <link rel="stylesheet" href="css/jquery-ui.css">
  <link rel="stylesheet" href="css/cart.css">
  <script src="js/jquery-ui.min.js"></script>
  <script src="js/cart.js"></script>


</head>
<body>
<div class="container">
  <form action="cart" method="post">
    <div class="container-left">
      <h2 class="cart">我的购物车</h2>
      <table class="cart">
        <thead>
        <tr>
          <th></th>
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
          <tr id="item-${cartItem.item.id}">
            <td>
              <input type="checkbox" name="selectedItems" value="${cartItem.item.id}"
                     data-name="${cartItem.item.name}" data-price="${cartItem.price}"
                ${cartItem.isSelected == 1 ? 'checked' : ''}
                     onchange="updateSelection(${cartItem.item.id}, this.checked)">
            </td>
            <td>${cartItem.item.name}</td>
            <td><img src="${cartItem.item.picture}" alt="${cartItem.item.name}" width="200" height="120"></td>
            <td>¥ ${cartItem.price}</td>
            <td>
              <button type="button" class="cartDelete" onclick="deleteItem(${cartItem.item.id})">删除</button>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <div>
        <h3 class="total" id="totalAmount">总金额: ¥ ${totalAmount}</h3>
        <button type="button" class="btn btn-submit btn-lg" onclick="submitOrder()">提交订单</button>
      </div>
    </div>
    <div id="container-right" class="container-right" style="display: none;">
      <h2 class="cart">订单详情</h2>
      <div id="orderContainer" style="display: none;">
        <div id="tabs">
          <ul>
            <li><a href="#tab1">填写信息</a></li>
            <li><a href="#tab2">确认订单</a></li>
          </ul>
          <form id="orderForm" action="order_confirm" method="post">
            <input type="hidden" name="totalAmount" value="${totalAmount}">
            <c:forEach var="selectedItemId" items="${selectedItemIds}">
              <input type="hidden" name="selectedItemIds" value="${selectedItemId}">
            </c:forEach>
            <div id="tab1" >
              <div>
                <label class="label1">姓名:</label>
                <input type="text" name="name" value="${user.username}" required>
              </div>
              <div>
                <label class="label1">电话:</label>
                <input type="text" name="phone" value="${user.phone}" required>
              </div>
              <div>
                <label class="label1">邮箱:</label>
                <input type="text" name="email" value="${user.email}" required>
              </div>
              <button type="button" onclick="nextTab()">下一步</button>
            </div>
            <div id="tab2" >
              <div id="orderDetails"></div>

              <div id="submitAmount"></div>


              <label>
                支付方式:
                <select id="paytype">
                  <option value="1">微信支付</option>
                  <option value="2">支付宝支付</option>
                </select>
              </label>
              <button type="button" onclick="submitCinfiredOrder() ">确认订单</button>

            </div>
          </form>
        </div>
      </div>
    </div>
  </form>
</div>
</body>
</html>
