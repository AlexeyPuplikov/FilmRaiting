<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/style.css">
    <title>Главная</title>
</head>
<body>
<header>
    <div class="logo">
        <a href="">
            <img class="img-responsive" src="resources/images/logo.png" alt="Логотип" width="100">
        </a>
    </div>
    <nav>
        <ul class="nav nav-pills nav-justified">
            <li class="active"><a href="#">Главная</a></li>
            <li><a href="#">Избранное</a></li>
            <li><a href="#">О нас</a></li>
        </ul>
    </nav>
    <div class="change-language">
        <a href=""><img src=""></a>
        <a href=""><img src=""></a>
    </div>
</header>
<main>
    <div class="content-search">
        <form action="controller" method="post">
            <select class="form-control">
                <option disabled="disabled">Выберете страну</option>

                    <option value="${film.year}">${film.name}</option>

            </select>
            <button type="submit" class="btn btn-default">Войти</button>
        </form>
    </div>
    <div class="content-films">

    </div>
</main>
</body>
</html>
