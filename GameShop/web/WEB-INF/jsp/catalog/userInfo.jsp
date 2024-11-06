<%@ include file="../common/top.jsp"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="userinfo" method="post">
        <div>
            <table class="userinfo">
                <tr>
                    <td rowspan="3"><img src="images/img.png" alt=""></td>
                    <td>ID:</td>
                    <td><c:out value="${sessionScope.userInfo.id}" /></td>
                </tr>
                <tr>
                    <td>昵称:</td>
                    <td><c:out value="${sessionScope.userInfo.username}" /></td>
                </tr>
                <tr>
                    <td>邮箱:</td>
                    <td><c:out value="${sessionScope.userInfo.email}" /></td>
                </tr>
                <tr>
                    <td colspan="3"><a href=""><input type="button" value="编辑信息" class="editBtn"></a></td>

                </tr>

            </table>
        </div>

    </form>


<%@ include file="../common/bottom.jsp"%>
