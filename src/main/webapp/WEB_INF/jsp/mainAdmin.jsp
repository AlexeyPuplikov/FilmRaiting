<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/WEB_INF/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/WEB_INF/resources/css/style.css"/>"/>
    <title>Главная</title>
</head>
<body>
<header>
    <div class="container">
        <nav>
            <ul class="nav nav-pills nav-justified">
                <li class="active"><a href="<c:url value="/controller?command=OPEN_ADD_FILM_PAGE"/>">Добавить новый фильм</a></li>
                <li class="active"><a href="#">Управлять пользователями</a></li>
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="container">
        <div class="row">
            <P>${successfulDelete}</P>
            <c:forEach items="${films}" var="film">
                <form class="form" action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="DELETE_FILM">
                    <input type="hidden" name="film" value="${film.id}">
                    <div class="col-sm-6 col-md-3">
                        <div class="thumbnail">
                            <img src="/WEB_INF/resources/images/${film.name}.jpg" alt="...">
                            <div class="caption">
                                <h4>${film.name}</h4>
                                <button type="submit" class="btn">Удалить</button>
                            </div>
                        </div>
                    </div>
                </form>
            </c:forEach>
        </div>
    </div>
</main>
<footer>
    <p>Copyright © by Alexey Puplikov</p>
</footer>
</body>
</html>
