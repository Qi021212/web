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
<%--  <link rel="stylesheet" href="../../css/cart.css">--%>
<%--  <link rel="stylesheet" href="../../css/style.css">--%>
<%--  <link rel="stylesheet" href="../../css/jquery-ui.css">--%>
  <link rel="stylesheet" href="css/cart.css">
  <script src="js/jquery-ui.min.js"></script>

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
        <h3 class="cart" id="totalAmount">总金额: ¥ ${totalAmount}</h3>
        <button type="button" class="btn btn-success btn-lg" onclick="submitOrder()">提交订单</button>
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
                <label>姓名:</label>
                <input type="text" name="name" value="${user.username}" required>
              </div>
              <div>
                <label>电话:</label>
                <input type="text" name="phone" value="${user.phone}" required>
              </div>
              <div>
                <label>邮箱:</label>
                <input type="text" name="email" value="${user.email}" required>
              </div>
              <button type="button" onclick="nextTab()">下一步</button>
          </div>
          <div id="tab2" >
            <h3>订单详情</h3>
            <div id="orderDetails"></div>

            <h3>支付金额: ¥ ${totalAmount}</h3>


            <h2>商品列表</h2>
            <c:forEach var="selectedItem" items="${selectedItems}">
              <div>
                <p>商品名称: ${selectedItem.name} 价格: ¥ ${selectedItem.price}</p>
                <input type="hidden" name="itemIds[]" value="${selectedItem.item.id}">
              </div>
            </c:forEach>

            <label>
              支付方式:
              <select id="paytype">
                <option value="1">微信支付</option>
                <option value="2">支付宝支付</option>
              </select>
            </label>
            <button type="button" onclick="submitCinfiredOrder()">确认订单</button>

          </div>
        </form>
      </div>
    </div>
    </div>
  </form>
</div>
</body>
</html>

<script>
  // 修改用于调用CartServlet中的case "submitOrder"，获取当前购物车列表中已选中商品selectedItems，已选中商品id selectedItemIds，总金额totalAmount，并传回信息用于后面选项卡中的信息展示和使用
  function submitOrder() {
    var selectedItems = [];
    var totalAmount = 0;

    // 获取所有已选择的商品ID和价格
    document.querySelectorAll('input[name="selectedItems"]:checked').forEach(function(checkbox) {
      var itemId = checkbox.value;
      var itemName = checkbox.dataset.name;  // 假设你已通过 data-name 存储商品名称
      var itemPrice = parseFloat(checkbox.dataset.price);  // 假设你已通过 data-price 存储商品价格
      selectedItems.push({ id: itemId, name: itemName, price: itemPrice });
      totalAmount += itemPrice;
    });

    // 发送AJAX请求提交订单
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "cart", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    var params = "action=submitOrder&totalAmount=" + totalAmount;
    selectedItems.forEach(function(item) {
      params += "&itemIds=" + item.id;  // 添加每个商品ID
    });

    xhr.onreadystatechange = function () {
      if (xhr.readyState == 4 && xhr.status == 200) {
//// 解析返回的订单数据
        var response = JSON.parse(xhr.responseText);

        var details = '';
        for(var i=0;i<selectedItems.length;i++){
          details +='<p>商品名称： ';
          details += selectedItems[i].name;
          details += '  价格： ';
          details += selectedItems[i].price;
          details += ' </p>';
        }
        $('#orderDetails').html(details);
        document.getElementById("totalAmount").innerText = "¥ " + response.totalAmount.toFixed(2);

        // 隐藏总金额和提交订单按钮
        document.getElementById("totalAmount").style.display = 'none';
        document.querySelector('button[class="btn btn-success btn-lg"]').style.display = 'none';
        document.getElementById("container-right").style.display = "block";

        // 直接显示订单填写选项卡
        showOrderContainer();
      }
    };

    xhr.send(params);
  }

  // 显示填写订单信息的表单
  function showOrderContainer() {
    document.getElementById("orderContainer").style.display = "block";
    $("#tabs").tabs("option", "active", 0); // 首先展示tab1
  }

  // 切换到 tab2
  function nextTab() {
    // 获取填写信息表单内容并展示到订单详情中（如果需要）
    $('#tabs').tabs('option', 'active', 1);  // 使用 jQuery UI 切换到第二个 tab
  }

  // 确保页面加载时初始化tabs
  $(document).ready(function() {
    $("#tabs").tabs(); // 初始化选项卡
  });
</script>

<script>
  function submitCinfiredOrder() {
    var selectedItems = [];
    var totalAmount = 0;

    // 获取所有已选择的商品ID和价格
    document.querySelectorAll('input[name="selectedItems"]:checked').forEach(function(checkbox) {
      var itemId = checkbox.value;
      var itemName = checkbox.dataset.name;  // 假设你已通过 data-name 存储商品名称
      var itemPrice = parseFloat(checkbox.dataset.price);  // 假设你已通过 data-price 存储商品价格
      selectedItems.push({ id: itemId, name: itemName, price: itemPrice });
      totalAmount += itemPrice;
    });

    // 获取填写订单信息的表单数据
    var name = document.querySelector('input[name="name"]').value;
    var phone = document.querySelector('input[name="phone"]').value;
    var email = document.querySelector('input[name="email"]').value;
    var paytype = document.getElementById('paytype').value; // 支付方式

    // 发送AJAX请求提交订单
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "order_confirm", true);  // 确保是向正确的Servlet提交请求
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    var params = "name=" + encodeURIComponent(name)
            + "&phone=" + encodeURIComponent(phone)
            + "&email=" + encodeURIComponent(email)
            + "&paytype=" + encodeURIComponent(paytype)
            + "&totalAmount=" + totalAmount;

    selectedItems.forEach(function(item) {
      params += "&selectedItemIds=" + item.id;  // 添加每个商品ID
    });

    xhr.onreadystatechange = function () {
      if (xhr.readyState == 4 && xhr.status == 200) {
        // 解析返回的订单数据
        var response = JSON.parse(xhr.responseText);

        // 显示订单详情
        document.getElementById("orderDetails").innerHTML = generateOrderDetails(response.selectedItems);
        document.getElementById("totalAmount").innerText = "¥ " + response.totalAmount.toFixed(2);

        // 跳转到订单成功页面
        alert("订单提交成功！");

        // 跳转到订单列表页面
        window.location.replace("order_list");  // 使用 window.location.replace 进行跳转
      }
    };

    xhr.send(params);
  }

  // 生成订单详情内容
  function generateOrderDetails(selectedItems) {
    var details = '';
    selectedItems.forEach(function(selectedItem) {
      details += `<p>商品名称: ${selectedItem.name} 价格: ¥ ${selectedItem.price}</p>`;
    });
    return details;
  }

</script>

<%--<script>--%>
<%--  //修改用于调用CartServlet中的case "submitOrder"，获取当前购物车列表中已选中商品selectedItems，已选中商品id selectedItemIds，总金额totalAmount，并传回信息用于后面选项卡中的信息展示和使用--%>
<%--  //调用完该方法之后，不进行页面跳转，直接将页面中的订单信息选项卡打开--%>
<%--  function submitOrder() {--%>
<%--    var selectedItems = [];--%>
<%--    var totalAmount = 0;--%>

<%--    // 获取所有已选择的商品ID和价格--%>
<%--    document.querySelectorAll('input[name="selectedItems"]:checked').forEach(function(checkbox) {--%>
<%--      selectedItems.push(checkbox.value);--%>
<%--      totalAmount += parseFloat(checkbox.getAttribute('data-price'));--%>
<%--    });--%>

<%--    // 发送AJAX请求提交订单--%>
<%--    var xhr = new XMLHttpRequest();--%>
<%--    xhr.open("POST", "cart", true);--%>
<%--    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");--%>

<%--    var params = "action=submitOrder&totalAmount=" + totalAmount;--%>
<%--    selectedItems.forEach(function(itemId) {--%>
<%--      params += "&itemIds=" + itemId;--%>
<%--    });--%>

<%--    xhr.onreadystatechange = function () {--%>
<%--      if (xhr.readyState == 4 && xhr.status == 200) {--%>
<%--        // 跳转到订单确认页面--%>
<%--        window.location.href = "cart";--%>
<%--      }--%>
<%--    };--%>

<%--    xhr.send(params);--%>
<%--  }--%>

<%--  // 显示填写订单信息的表单--%>
<%--  function showOrderContainer() {--%>
<%--    document.getElementById("orderContainer").style.display = "block";--%>
<%--    $("#tabs").tabs(); // 初始化选项卡（需引入 jQuery UI）--%>
<%--  }--%>

<%--  // 下一步：从已选商品生成订单详情--%>
<%--  function nextTab() {--%>
<%--    let selectedItems = [];--%>
<%--    let totalAmount = 0;--%>
<%--  }--%>
<%--</script>--%>

<%--<script>--%>
<%--    // 获取所有选中的商品信息--%>
<%--    document.querySelectorAll('input[name="selectedItems"]:checked').forEach(function (checkbox) {--%>
<%--      selectedItems.push({--%>
<%--        id: checkbox.value,--%>
<%--        name: checkbox.dataset.name,--%>
<%--        price: parseFloat(checkbox.dataset.price),--%>
<%--      });--%>
<%--      totalAmount += parseFloat(checkbox.dataset.price);--%>
<%--    });--%>

<%--    // 更新订单详情--%>
<%--    const details = selectedItems.map(item => `<p>${item.name} - ¥${item.price.toFixed(2)}</p>`).join('');--%>
<%--    let orderDetailsElement = document.getElementById("orderDetails");--%>
<%--    if (orderDetailsElement) {--%>
<%--      orderDetailsElement.innerHTML = details;--%>
<%--    }--%>

<%--    // 更新总金额--%>
<%--    let totalAmountElement = document.getElementById("totalAmount");--%>
<%--    if (totalAmountElement) {--%>
<%--      totalAmountElement.innerText = "¥ " + totalAmount.toFixed(2);--%>
<%--    }--%>

<%--    // 切换到 tab2--%>
<%--    $('#tabs').tabs('option', 'active', 1);  // 使用 jQuery UI 切换到第二个 tab--%>

<%--</script>--%>


<%--      <div id="submitOrderForm" action="${pageContext.request.contextPath}/order_submit" method="post">--%>
<%--        <input type="hidden" name="action" value="submitOrder">--%>
<%--        <input type="hidden" name="totalAmount" value="${totalAmount}">--%>
<%--        <c:forEach var="cartItem" items="${cartItems}">--%>
<%--          <input type="hidden" name="itemIds" value="${cartItem.item.id}">--%>
<%--        </c:forEach>--%>
<%--        <button type="submit" class="btn btn-success btn-lg">提交订单</button>--%>
<%--      </div>--%>