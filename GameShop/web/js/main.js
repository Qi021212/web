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