<%@ include file="../common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--轮播图--%>
<div class="slide">
    <ul class="list">
        <li class="item"><a href=""><img src="images/001.png" alt="双人成行"></a></li>
        <li class="item"><a href=""><img src="images/013.png" alt="纸嫁衣6千秋魇"></a></li>
        <li class="item"><a href=""><img src="images/006.png" alt="Overcooked!2"></a></li>
        <li class="item"><a href=""><img src="images/004.png" alt="黑神话·悟空"></a></li>
        <li class="item"><a href=""><img src="images/005.png" alt="LIAR'S BAR"></a></li>
        <li class="item"><a href=""><img src="images/031.png" alt="卡通农场"></a></li>
    </ul>
    <ul class="ulNum">
        <li class="num">1</li>
        <li class="num">2</li>
        <li class="num">3</li>
        <li class="num">4</li>
        <li class="num">5</li>
        <li class="num">6</li>
    </ul>
</div>
<script src="js/slide.js"></script>

<%--搜索栏--%>
<div class="search">
    <form action="search" method="post">
        <input type="text" placeholder="搜索游戏" name="keyword" id="keyword"/>
        <input type="submit" value="搜索" name="submit"/>
    </form>
    <div id="autoComplete">
        <ul id="autoCompleteList">
        </ul>
    </div>
</div>
<script src="js/autoComplete.js"></script>

<%--商品展示--%>
<div class="catalog">
<%--导航栏--%>
    <ul class="itemCategory">
        <li><a href="catalogForm?categoryId=all">全部</a></li>
        <li><a href="catalogForm?categoryId=casual">休闲</a></li>
        <li><a href="catalogForm?categoryId=action">动作</a></li>
        <li><a href="catalogForm?categoryId=role">角色扮演</a></li>
        <li><a href="catalogForm?categoryId=adventure">冒险</a></li>
        <li><a href="catalogForm?categoryId=simulation">模拟</a></li>
        <li><a href="catalogForm?categoryId=sports">体育及竞速</a></li>
    </ul>
<%--商品列表--%>
    <table class="itemList">
        <c:forEach var="item" items="${sessionScope.itemList}">
            <tr class="itemList">
                <td class="img"><a href="itemInformation?itemPicture=${item.picture}"><img src="${item.picture}" alt="${item.name}" class="item-image"></a></td>
<%--                <td class="floating-window" id="">--%>
<%--                    <div class="floating-window-content">--%>
<%--                        <h3 class="floating-window-name">${item.name}</h3>--%>
<%--                        <p class="floating-window-description">${item.description}</p>--%>
<%--                        <p class="floating-window-type">${item.type}</p>--%>
<%--                    </div>--%>
<%--                    <div class="floating-window-mask"></div>--%>
<%--                </td>--%>
                <td class="name&type">${item.name}<br/><br/>${item.type}</td>
                <td class="price">¥ ${item.price}</td>
                <td class="add">
                    <button class="addBtn" data-item-id="${item.id}" data-price="${item.price}" data-name="${item.name}">
                        添加到购物车
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<%--悬浮窗--%>
<div class="floating-window">

</div>

<script src="js/floatingWindow.js"></script>
<script>
    $(document).ready(function () {
        $('.addBtn').click(function () {
            const itemId = $(this).data('item-id');
            const price = $(this).data('price');
            const itemName = $(this).data('name');

            // 弹出确认框
            const confirmAdd = confirm(`是否添加 ${itemName} 到购物车？`);
            if (confirmAdd) {
                // 使用 jQuery AJAX 发送请求
                $.ajax({
                    url: 'addToCart',
                    type: 'POST',
                    data: {
                        itemId: itemId,
                        price: price
                    },
                    success: function (response) {
                        alert("添加成功！");
                    },
                    error: function (xhr, status, error) {
                        alert('添加失败，请重试。');
                    }
                });
            }
        });
    });
</script>

<%@ include file="../common/bottom.jsp"%>