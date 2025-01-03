
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
<%--    logo 导入--%>
    <script src="https://kit.fontawesome.com/b912c38033.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="w">
    <div class="user-content">
        <div class="user-title">
            <img src="./images/logo.png" alt="logo" id="logo"  class="user-title-logo">
            GameShop_Login
        </div>
        <form action="login" class="user-form" method="post">
            <p class="login-tip">用账户名称登录</p>
            <div class="user-form-item">
                <label for="username" class="user-form-label">
                    <i class="fa-solid fa-user"></i>
                </label>
                <input type="text" class="user-form-input" name="username" id="username" placeholder="请输入用户名" autocomplete="off">
            </div>

            <div class="user-form-item">
                <label for="password" class="user-form-label">
                    <i class="fa-solid fa-lock"></i>
                </label>
                <input type="password" class="user-form-input" name="password" id="password" placeholder="请输入密码" autocomplete="off">
            </div>

            <br>
            <div class="captcha-container">
                <input type="text" class="captcha-input" name="captcha_input" placeholder="请输入左侧图片中验证码" />
                <img src="captcha" alt="验证码" class="captcha-image" onclick="this.src='captcha?'+Math.random()" />
            </div>
<%--            <div>--%>
<%--                <label for="username" class="user-form-label">--%>
<%--                    <i class="fa-solid fa-user"></i>--%>
<%--                </label>--%>
<%--                <input type="text" name="captcha_input" placeholder="请输入验证码" />--%>
<%--                <img src="captcha" alt="验证码" onclick="this.src='captcha?'+Math.random()" />--%>
<%--            </div>--%>
            <c:if test="${requestScope.loginMsg != null}">
                <div id="feedback" class="feedback">
<%--                    <i class="fa-solid fa-circle-exclamation error-icon"></i>--%>
                    <p class="error-msg">${requestScope.loginMsg}</p>
                </div>
            </c:if>

            <div class="user-form-item">
                <input type="submit" value="登录" class="user-form-submit">
            </div>
            <div class="user-form-link">
                <a href="registerForm" class="link">还没有GameShop账户？点击这里</a>
            </div>
    </form>
    </div>
</div>
</body>
</html>
