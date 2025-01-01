<%@ include file="../common/top.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="domain.Item" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--轮播图--%>
<div class="slide">
    <ul class="list">
        <li class="item"><a href=""><img src="images/001.png" alt="双人成行"></a></li>
        <li class="item"><a href=""><img src="images/002.png" alt="沙威玛传奇"></a></li>
        <li class="item"><a href=""><img src="images/003.png" alt="纸嫁衣5"></a></li>
        <li class="item"><a href=""><img src="images/004.png" alt="黑神话·悟空"></a></li>
        <li class="item"><a href=""><img src="images/005.png" alt="LIAR'S BAR"></a></li>
        <li class="item"><a href=""><img src="images/006.png" alt="Overcooked!2"></a></li>
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
        <input type="text" placeholder="搜索游戏" name="keyword"/>
        <input type="submit" value="搜索" name="submit"/>
    </form>

</div>

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
                <td class="img"><a href="itemInformation?itemPicture=${item.picture}"><img src="${item.picture}" alt="${item.name}"></a></td>
                <td class="name&type">${item.name}<br/><br/>${item.type}</td>
                <td class="price">¥ ${item.price}</td>
                <td class="add">
<%--                    <form action="addToCart" method="POST">--%>
<%--                        <input type="hidden" name="itemId" value="${item.id}" />--%>
<%--                        <input type="hidden" name="price" value="${item.price}" />--%>
<%--                        <input type="submit" value="添加到购物车" class="addBtn"/>--%>
<%--                    </form>--%>
                    <button class="addBtn" data-item-id="${item.id}" data-price="${item.price}" data-name="${item.name}">
                        添加到购物车
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

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



<%--<script>--%>
<%--    // 为每个“添加到购物车”按钮绑定点击事件--%>
<%--    document.querySelectorAll('.addBtn').forEach(button => {--%>
<%--        button.addEventListener('click', function () {--%>
<%--            // 获取商品信息--%>
<%--            const itemId = this.getAttribute('data-item-id');--%>
<%--            const price = this.getAttribute('data-price');--%>
<%--            const itemName = this.getAttribute('data-name');--%>

<%--            // 弹出确认框--%>
<%--            if (confirm(`是否添加${item.name}到购物车？`)) {--%>
<%--                // 动态创建表单并提交--%>
<%--                const form = document.createElement('form');--%>
<%--                form.method = 'POST';--%>
<%--                form.action = 'addToCart';--%>

<%--                // 创建隐藏字段传递数据--%>
<%--                const itemIdInput = document.createElement('input');--%>
<%--                itemIdInput.type = 'hidden';--%>
<%--                itemIdInput.name = 'itemId';--%>
<%--                itemIdInput.value = itemId;--%>

<%--                const priceInput = document.createElement('input');--%>
<%--                priceInput.type = 'hidden';--%>
<%--                priceInput.name = 'price';--%>
<%--                priceInput.value = price;--%>

<%--                // 将隐藏字段添加到表单--%>
<%--                form.appendChild(itemIdInput);--%>
<%--                form.appendChild(priceInput);--%>

<%--                // 将表单添加到页面并提交--%>
<%--                document.body.appendChild(form);--%>
<%--                form.submit();--%>
<%--            }--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>

<%@ include file="../common/bottom.jsp" %>