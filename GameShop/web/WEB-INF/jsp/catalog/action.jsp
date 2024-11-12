<%@ include file="../common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="actionList">${sessionScope.user.username}的日志</h1>
<table class="actionList">
    <tr class="actionList">
        <th>商品名称</th>
        <th>操作</th>
    </tr>
    <c:forEach var="action" items="${sessionScope.actionList}">
        <tr class="actionList">
            <td class="item">${action.itemName}</td>
            <td class="action_type">${action.type}</td>
        </tr>
    </c:forEach>
</table>
<a href="mainForm"><input type="button" class="btn-return" value="返回首页"></a>
