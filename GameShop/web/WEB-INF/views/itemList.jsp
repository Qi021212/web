<%--
  Created by IntelliJ IDEA.
  User: 86191
  Date: 2024/10/28
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Item" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>商品列表</title>
</head>
<body>
<h1>商品列表</h1>
<table>
  <tr>
    <th>商品名称</th>
    <th>价格</th>
    <th>操作</th>
  </tr>
  <c:if test="${empty items}">
    <tr>
      <td colspan="3">没有找到商品。</td>
    </tr>
  </c:if>
  <c:forEach var="item" items="${items}">
    <tr>
      <td>${item.name}</td>
      <td>${item.price}</td>
      <td>
        <form action="items" method="post">
          <input type="hidden" name="itemId" value="${item.id}"/>
          <input type="hidden" name="price" value="${item.price}"/>
          <input type="number" name="quantity" value="1" min="1"/>
          <input type="submit" value="添加到购物车"/>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>






