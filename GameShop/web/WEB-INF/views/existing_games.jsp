<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="existing-games">
    <h3>已有游戏</h3>
    <ul>
        <c:forEach var="game" items="${existingGames}">
            <li>${game.itemName}</li>
        </c:forEach>
    </ul>
</div>
