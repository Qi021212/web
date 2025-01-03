function showOrderContainer() {
    document.getElementById("orderContainer").style.display = "block";
    $("#tabs").tabs(); // 初始化选项卡（需引入 jQuery UI）
}

function nextTab() {
    $("#tabs").tabs("option", "active", 1);
    // 更新订单详情信息
    let selectedItems = [];
    let totalAmount = 0;
    document.querySelectorAll('input[name="selectedItems"]:checked').forEach(function (checkbox) {
        selectedItems.push({
            id: checkbox.value,
            name: checkbox.dataset.name,
            price: parseFloat(checkbox.dataset.price),
        });
        totalAmount += parseFloat(checkbox.dataset.price);
    });
    const details = selectedItems.map(item => `<p>${item.name} - ¥${item.price.toFixed(2)}</p>`).join('');
    document.getElementById("orderDetails").innerHTML = details;
    document.getElementById("confirmTotalAmount").innerText = totalAmount.toFixed(2);
}

function submitFinalOrder() {
    const orderData = {
        name: document.getElementById("name").value,
        phone: document.getElementById("phone").value,
        email: document.getElementById("email").value,
        paytype: document.getElementById("paytype").value,
        totalAmount: document.getElementById("confirmTotalAmount").innerText,
        items: [],
    };
    document.querySelectorAll('input[name="selectedItems"]:checked').forEach(function (checkbox) {
        orderData.items.push(checkbox.value);
    });
    // AJAX 提交订单数据
    $.ajax({
        url: "order",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(orderData),
        success: function (response) {
            alert("订单提交成功！");
            window.location.href = "order_list.jsp"; // 可选跳转
        },
        error: function () {
            alert("提交订单失败，请重试！");
        },
    });
}