$(function() {
    // 为所有商品图片绑定鼠标悬停事件
    $('.item-image').on('mouseover',function(e) {
        e.stopPropagation();
        var itemSrc = $(this).attr('src');
        var imgOffset = $(this).offset();
        $('.floating-window').css({
            top: imgOffset.top + 'px',
            left: imgOffset.left + $(this).width() + 5 +'px'

        });
        // 使用Ajax请求商品信息
        $.ajax({
            url: 'http://localhost:8080/GameShop/floatingWindow?itemSrc='+itemSrc, // 后端接口URL
            type: 'GET',
            success:function (data){
               var floatingWindow='';
               floatingWindow += '<h3 class="floating-window-name">'+data[0].name+'</h3>';
               floatingWindow += '<p class="floating-window-description">'+data[0].description+'</p>';
               floatingWindow += '<p class="floating-window-type">'+data[0].type+'</p>';

               $('.floating-window').html(floatingWindow).fadeIn(500);
            },
            error: function() {
                console.log('Ajax request failed');
            }
        });
    })
    .on('mouseout', function(e){
        e.stopPropagation();
        $('.floating-window').fadeOut(300);
    })
});