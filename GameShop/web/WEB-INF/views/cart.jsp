<%@ include file="../jsp/common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<div>
  <h2 class="cart">我的购物车</h2>
  <div>
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

      <form id="submitOrderForm" action="${pageContext.request.contextPath}/order_submit" method="post">
        <input type="hidden" name="action" value="submitOrder">
        <input type="hidden" name="totalAmount" value="${totalAmount}">
        <input type="hidden" name="userId" value="1"> <!-- 硬编码用户ID -->
        <c:forEach var="cartItem" items="${cartItems}">
          <input type="hidden" name="itemIds" value="${cartItem.itemId}">
        </c:forEach>
        <button type="submit" class="btn btn-success btn-lg">提交订单</button>
      </form>
    </div>
  </div>
</div>

</body>
</html>

