<%@ include file="../jsp/common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--商品具体信息--%>
<div class="item-info">
  <table class="item">
      <tr class="name">
        <td class="name">${item.name}</td>
      </tr>
      <tr class="img">
        <td class="img"><img src="${item.picture}" alt="${item.name}"></td>
      </tr>
      <tr class="type">
        <td class="type">标签：${item.type}</td>
      </tr>
      <tr class="description">
        <td class="description">${item.description}</td>
      </tr>
      <tr >
        <td>¥ ${item.price}</td>
      </tr>
  </table>
</div>


<%@ include file="../jsp/common/bottom.jsp"%>
