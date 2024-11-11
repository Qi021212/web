<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>订单确认</title>
</head>
<body>

<div class="cart-items">
    <div class="container">
        <h2>订单确认</h2>
        <form action="${pageContext.request.contextPath}/order_confirm" method="post">
            <input type="hidden" name="totalAmount" value="${totalAmount}"> <!-- 确保有总金额 -->
            <c:forEach var="itemId" items="${itemIds}">
                <input type="hidden" name="itemIds" value="${itemId}">
            </c:forEach>

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

            <br><hr><br>

            <h2>商品列表</h2>
            <c:forEach var="item" items="${cartItems}">
                <div>
                    <p>商品名称: ${item.name}    价格:¥ ${item.price}</p>

                    <input type="hidden" name="itemIds[]" value="${item.id}">
                </div>
            </c:forEach>

            <h2>选择支付方式</h2>
            <h3>支付金额: ¥ ${totalAmount }</h3><br><br>

            <div class="col-sm-6 col-md-4 col-lg-3 " >
                <label>
                    <div class="thumbnail">
                        <input type="radio" name="paytype" value="1" checked="checked" />
                        <img src="images/wechat.jpg" alt="微信支付" width="100px" height="75px">
                        <input type="radio" name="paytype" value="2"  />
                        <img src="images/alipay.jpg" alt="支付宝支付" width="100px" height="75px">
                    </div>
                </label>
            </div>
            <div class="clearfix"> </div>
            <div class="register-but text-center">
                <input type="submit" value="确认订单">
            </div>
        </form>
    </div>
</div>


<script type="text/javascript">
    function dopay(paytype){
        $("#paytype").val(paytype);
        $("#payform").submit();
    }
</script>

</body>
</html>
<style>
    body{
        background-color: #1b2838;
        color: #ffffff;
        margin: 0;
        padding: 20px;
    }
    h2{
        font-size: 20px;
        color: #a5bec3;
    }
    input[type=text]{
        padding: 10px;
        margin-bottom: 10px;
        border-radius: 3px;
        border-bottom: 1px solid #ccc;
        margin-top:10px
    }
    input[type=radio]{
        margin-right: 10px;
    }
    input[type=submit]{
        width: 100px;
        height: 40px;
        font-size: 15px;
        background-color: #6ca520;
        color: #ffffff;
        border-radius:4px;
        border: 1px solid #1b2838;
        margin-top: 10px;
        margin-left: 5px;
    }
    input[type=submit]:hover{
        box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
    }
</style>


