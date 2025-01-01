<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加到购物车成功</title>
</head>
<body>
<h1>${message}</h1>
<table>
    <tr>
        <td><a href="mainForm">继续浏览商品</a></td>
        <td><a href="cart">查看购物车</a></td>
    </tr>
</table>
</body>
</html>
<style>
    body{
        background-color: #1b2838;
        color: #ffffff;
        margin: 0;
        padding: 0;
    }
    h1{
        text-align: center;
        margin-top: 200px;
    }
    a{
        color: #f3f3f3;
        text-decoration: none;
    }
    a:hover{
        color: #007bff;
        text-decoration: underline;
    }
    table{
        margin:50px auto;
        border-collapse: collapse;
    }
    td{
        text-align: center;
        padding: 10px;
        border: 1px solid #f3f3f3;
        margin-left: 30px;
    }
</style>



