function addToCart(itemId, itemName) {
    // 发送Ajax请求
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "addToCart", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // 显示弹出框
            showPopup(itemName);
        }
    };

    xhr.send("itemId=" + itemId);
}

// 弹出框显示逻辑
function showPopup(itemName) {
    // 创建弹出框元素
    var popup = document.createElement("div");
    popup.id = "popup";
    popup.innerHTML = `
        <div class="popup-content">
            <p>成功添加 ${itemName} 到购物车！</p>
            <button onclick="continueShopping()">继续浏览商品</button>
            <button onclick="viewCart()">查看购物车</button>
        </div>
    `;
    document.body.appendChild(popup);

    // 添加样式
    popup.style.position = "fixed";
    popup.style.top = "50%";
    popup.style.left = "50%";
    popup.style.transform = "translate(-50%, -50%)";
    popup.style.backgroundColor = "white";
    popup.style.padding = "20px";
    popup.style.boxShadow = "0px 0px 10px rgba(0, 0, 0, 0.5)";

    // 自动关闭弹出框
    setTimeout(() => {
        document.body.removeChild(popup);
    }, 3000);
}

// 继续浏览商品
function continueShopping() {
    document.body.removeChild(document.getElementById("popup"));
}

// 跳转到购物车页面
function viewCart() {
    window.location.href = "cart";
}
