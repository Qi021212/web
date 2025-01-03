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

            var submitAmount1 = 0;
            var details = '';
            for(var i=0;i<selectedItems.length;i++){
                details +='<p>商品名称： ';
                details += selectedItems[i].name;
                details += '<br>';
                details += '  价格： ￥';
                details += selectedItems[i].price;
                details += ' </p>';
                submitAmount1 += selectedItems[i].price;
            }
            var submitAmount = '<h3> 总金额：￥ '+ submitAmount1 + '</h3>';
            $('#orderDetails').html(details);
            $('#submitAmount').html(submitAmount);

            document.getElementById("totalAmount").innerText = "¥ " + response.totalAmount.toFixed(2);

            // 隐藏总金额和提交订单按钮
            document.getElementById("totalAmount").style.display = 'none';
            document.querySelector('button[class="btn btn-submit btn-lg"]').style.display = 'none';
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

            // // 显示订单详情
            // document.getElementById("orderDetails").innerHTML = generateOrderDetails(response.selectedItems);
            // document.getElementById("totalAmount").innerText = "¥ " + response.totalAmount.toFixed(2);

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

