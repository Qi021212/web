<%@ include file="../common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--商品具体信息--%>
<div class="item-info">
    <table class="item">
        <c:forEach var="item" items="${sessionScope.items}">
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
            <tr class="add">
                <td class="add">¥ ${item.price}</td>
                <td class="add">
                    <button class="addBtn" data-item-id="${item.id}" data-price="${item.price}" data-name="${item.name}">
                        添加到购物车
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<script src="js/main.js"></script>


<%@ include file="../common/bottom.jsp"%>
