<%@ include file="../jsp/common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>购物车</title>
  <script>
    // 使用AJAX提交复选框状态
    function updateSelection(itemId, isSelected) {
      var xhr = new XMLHttpRequest();
      xhr.open("POST", "cart", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

      var params = "action=updateSelection&itemId=" + itemId + "&isSelected=" + (isSelected ? 1 : 0);

      xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
          // 获取并更新总金额
          var response = JSON.parse(xhr.responseText);
          document.getElementById("totalAmount").innerText = "总金额: ¥ " + response.totalAmount.toFixed(2);
        }
      };
      xhr.send(params);
    }
  </script>
  <script>
    // 使用AJAX删除购物车商品
    function deleteItem(itemId) {
        if(confirm("是否在购物车中删除该商品？")){
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "cart", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            var params = "action=delete&itemId=" + itemId;

            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);
                    // 根据返回的数据更新购物车和总金额
                    document.getElementById("totalAmount").innerText = "总金额: ¥ " + response.totalAmount.toFixed(2);
                    // 移除已删除商品的行
                    document.getElementById("item-" + itemId).remove();
                }
            };
            xhr.send(params);
        }

    }
  </script>
  <script>
    function submitOrder() {
      var selectedItems = [];
      var totalAmount = 0;

      // 获取所有已选择的商品ID和价格
      document.querySelectorAll('input[name="selectedItems"]:checked').forEach(function(checkbox) {
        selectedItems.push(checkbox.value);
        totalAmount += parseFloat(checkbox.getAttribute('data-price'));
      });

      // 发送AJAX请求提交订单
      var xhr = new XMLHttpRequest();
      xhr.open("POST", "cart", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

      var params = "action=submitOrder&totalAmount=" + totalAmount;
      selectedItems.forEach(function(itemId) {
        params += "&itemIds=" + itemId;
      });

      xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
          // 跳转到订单确认页面
          window.location.href = "order_submit";  // 跳转到订单确认页面
        }
      };

      xhr.send(params);
    }
  </script>
</head>
<body>
<div>
  <h2 class="cart">我的购物车</h2>
  <form action="cart" method="post">
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
      <hr>
      <h3 class="cart" id="totalAmount">总金额: ¥ ${totalAmount}</h3>

      <form id="orderForm" action="cart" method="post" style="display: none;">
        <input type="hidden" name="action" value="submitOrder">
        <input type="hidden" name="totalAmount" value="${totalAmount}">
        <c:forEach var="cartItem" items="${cartItems}">
          <input type="hidden" name="itemIds" value="${cartItem.item.id}">
        </c:forEach>
      </form>

      <button type="button" class="btn btn-success btn-lg" onclick="submitOrder()">提交订单</button>
    </div>
  </form>
</div>
</body>
</html>

<%--      <div id="submitOrderForm" action="${pageContext.request.contextPath}/order_submit" method="post">--%>
<%--        <input type="hidden" name="action" value="submitOrder">--%>
<%--        <input type="hidden" name="totalAmount" value="${totalAmount}">--%>
<%--        <c:forEach var="cartItem" items="${cartItems}">--%>
<%--          <input type="hidden" name="itemIds" value="${cartItem.item.id}">--%>
<%--        </c:forEach>--%>
<%--        <button type="submit" class="btn btn-success btn-lg">提交订单</button>--%>
<%--      </div>--%>