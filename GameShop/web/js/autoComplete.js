$(function() {
    $('#keyword').on('keyup', function(){
        var keyword = $(this).val();
        $.ajax({
            type:'GET',
            url:'http://localhost:8080/GameShop/autoComplete?keyword='+keyword,
            success:function(data){
                if(keyword==='')
                {
                    $('#autoComplete').hide();
                }
                else{
                    var productListHtml='';
                    for (var i=0; i<data.length;i++){
                        productListHtml+='<li class="autoCompleteItem" data-picture="'+data[i].picture+'">';
                        productListHtml+='<img class="autoCompleteItemImage" src="';
                        productListHtml+=data[i].picture;
                        productListHtml+='" alt="">';
                        productListHtml+=data[i].name;
                        productListHtml+='</li>';
                    }
                    $('#autoCompleteList').html(productListHtml);
                    $('#autoComplete').show();
                }

            },
            error:function(errorMsg){
                console.log(errorMsg);
            }
        })
    })

    $('#autoCompleteList').on('click', '.autoCompleteItem', function(){
        $('#keyword').val($(this).text());
        $('#autoComplete').hide();
        window.location.href = "itemInformation?itemPicture="+$(this).data('picture');
    })

    $('body').on('click' ,function(){
        $('#autoComplete').hide();
    })
})