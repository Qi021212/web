// /**
//  * 加入购物车
//  */
// function buy(goodid) {
//     $.post("/api/cart/CartBuyServlet", { goodsid: goodid }, function(data) {
//         if (data === "ok") {
//             layer.msg("添加到购物车!", { time: 800 }, function() {
//                 location.reload();
//             });
//         } else if (data === "fail") {
//             layer.msg("库存不足,请购买其他商品!", { time: 800 });
//         } else if (data === "login") {
//             alert("请登录后购买!");
//             location.href = "/user_login.jsp";
//         } else {
//             alert("请求失败!");
//         }
//     });
// }
//
// /**
//  * 减少购物车商品
//  */
// function lessen(goodsid) {
//     $.post("/api/cart/CartLessenServlet", { goodsid: goodsid }, function(data) {
//         if (data === "ok") {
//             layer.msg("操作成功!", { time: 800 }, function() {
//                 location.reload();
//             });
//         } else if (data === "login") {
//             alert("请登录后购买!");
//             location.href = "/user_login.jsp";
//         } else {
//             alert("请求失败!");
//         }
//     });
// }
//
// /**
//  * 购物车删除
//  */
// function deletes(goodid) {
//     $.post("/api/cart/CartDeleteServlet", { goodsid: goodid }, function(data) {
//         if (data === "ok") {
//             layer.msg("删除成功!", { time: 800 }, function() {
//                 location.reload();
//             });
//         } else if (data === "login") {
//             alert("请登录后购买!");
//             location.href = "/user_login.jsp";
//         } else {
//             alert("请求失败!");
//         }
//     });
// }
//
// /**
//  * 计算总金额
//  */
// function calTotal() {
//     var total = 0;
//     $(".cart-data").each(function() {
//         var price = $(this).data("price");
//         var amount = $(this).data("amount");
//         total += price * amount;
//     });
//     $("#cart-total").text(total);
// }
//
// $(function() {
//     calTotal();
// });

// function buy(itemId) {
//     // 发起 AJAX 请求增加数量
//     $.post('/cart/update', { itemId: itemId, action: 'buy' }, function(data) {
//         location.reload(); // 刷新页面
//     });
// }
//
// function lessen(itemId) {
//     // 发起 AJAX 请求减少数量
//     $.post('/cart/update', { itemId: itemId, action: 'lessen' }, function(data) {
//         location.reload(); // 刷新页面
//     });
// }
//
// // 删除商品的方法
// function deletes(itemId) {
//     $.post('/cart/delete', { itemId: itemId }, function(data) {
//         location.reload(); // 刷新页面
//     });
// }


function buy(itemId,price) {
    $.post('${pageContext.request.contextPath}/cart', { itemId: itemId, action: 'buy', quantity: 1, price: price}, function(data) {
        location.reload();
    });
}

function lessen(itemId) {
    $.post('/cart', { itemId: itemId, action: 'lessen' }, function(data) {
        location.reload();
    });
}

function deletes(itemId) {
    $.post('/cart', { itemId: itemId, action: 'delete' }, function(data) {
        location.reload();
    });
}


