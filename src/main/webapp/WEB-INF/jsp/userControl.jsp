<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>"/>
    <title>Управление пользователями</title>
</head>
<body>
<header>
    <div class="container">
        <nav>
            <ul class="nav nav-pills nav-justified">
                <li class="active"><a href="<c:url value="/controller?command=OPEN_MAIN_ADMIN_PAGE"/>">На главную</a>
                </li>
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="container">
        <div class="row">
            <P>${successfulUpdate}</P>
            <c:forEach items="${users}" var="user">
                <c:choose>
                    <c:when test="${user.blocked == false}">
                        <form class="form" action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="USER_CONTROL">
                            <input type="hidden" name="user" value="${user.id}">
                            <div class="col-sm-6 col-md-3">
                                <div class="thumbnail">
                                    <div class="caption">
                                        <h4>${user.login}</h4>
                                        <button type="submit" class="btn">Забанить</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form class="form" action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="USER_CONTROL">
                            <input type="hidden" name="user" value="${user.id}">
                            <div class="col-sm-6 col-md-3">
                                <div class="thumbnail">
                                    <div class="caption">
                                        <h4>${user.login}</h4>
                                        <button type="submit" class="btn">Разбанить</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</main>
</body>
</html>
