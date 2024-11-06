
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<head>
    <title>用户信息修改</title>
    <link rel="stylesheet" type="text/css" href="css/register.css">
    <%--    logo 导入--%>
    <script src="https://kit.fontawesome.com/b912c38033.js" crossorigin="anonymous"></script>
</head>
<body>
<div class="w">
    <div class="user-content">
        <div class="user-title">User_Update</div>
            <form action="update" method="post">
                <div class="user-form-item">
                    <label for="username" class="user-form-label">
                        <i class="fa-solid fa-user"></i>
                    </label>
                    <input type="text" class="user-form-input" name="username" id="username" placeholder="请输入新用户名" autocomplete="off">
                </div>
                <div class="user-form-item">
                    <label for="password" class="user-form-label">
                        <i class="fa-solid fa-lock"></i>
                    </label>
                    <input type="password" class="user-form-input" name="password" id="password" placeholder="请输入新密码" autocomplete="off">
                </div>
                <div class="user-form-item">
                    <label for="repeatPassword" class="user-form-label">
                        <i class="fa-solid fa-lock"></i>
                    </label>
                    <input type="password" class="user-form-input" name="repeatPassword" id="repeatPassword" placeholder="请再次输入密码" autocomplete="off">
                </div>
                <c:if test="${requestScope.updateMsg != null}">
                    <div class="user-form-error">
                        <i class="fa-solid fa-circle-exclamation error-icon"></i>
                        <p class="error-msg">${requestScope.updateMsg}</p>
                    </div>
                </c:if>
                <div class="user-form-item">
                    <input type="submit" value="确认修改" class="user-form-sumbit">
                </div>
            </form>
    </div>
</div>
</body>
</html>
