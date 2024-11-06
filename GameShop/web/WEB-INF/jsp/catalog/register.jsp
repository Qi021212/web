
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <link rel="stylesheet" type="text/css" href="css/register.css">
    <script src="https://kit.fontawesome.com/b912c38033.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="w">
    <div class="user-content">
        <div class="user-title">GameShop_Register</div>
        <form action="register" class="user-form" method="post">
            <p class="login-tip">用户注册</p>
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
            <div class="user-form-item">
                <label for="repeatPassword"class="user-form-label">
                    <i class="fa-solid fa-lock"></i>
                </label>
                <input type="password" class="user-form-input" name="repeatPassword" id="repeatPassword" placeholder="请再次输入密码" autocomplete="off">
            </div>
            <div class="user-form-item">
                <label for="email"class="user-form-label">
                    <i class="fa-solid fa-envelope"></i>
                </label>
                <input type="text" class="user-form-input" name="email" id="email" placeholder="请输入邮箱" autocomplete="off">
            </div>
            <br>
            <c:if test="${requestScope.registerMsg != null}">
                <div class="user-form-error">
                    <i class="fa-solid fa-circle-exclamation error-icon"></i>
                    <p class="error-msg">${requestScope.registerMsg}</p>
                </div>
            </c:if>

            <div class="user-form-item">
                <input type="submit" value="注册" class="user-form-sumbit">
            </div>
            <div class="user-form-link">
                <a href="loginForm" class="link">返回登录>></a>
            </div>
        </form>
    </div>
</div>
</body>
</html>