<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="../resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="../resources/css/style.css"/>
    <title><fmt:message key="label.main"/></title>
</head>
<body>
<header>
    <div class="container">
        <div class="change-language">
            <a href="/controller?command=CHANGE_LANGUAGE&language=ru_RU&page=/controller?command=VIEW_FILMS"><img src="../resources/images/lang-ru.png"></a>
            <a href="/controller?command=CHANGE_LANGUAGE&language=en_US&page=/controller?command=VIEW_FILMS"><img src="../resources/images/lang-en.png"></a>
        </div>
        <div class="container">
            <div class="login">
                <c:choose>
                    <c:when test="${user == null}">
                        <a href="/jsp/login.jsp"><fmt:message key="label.enter"/></a>
                    </c:when>
                    <c:otherwise>
                        <p>${user.login} <fmt:message key="label.online"/> <a href="/controller?command=LOGOUT"><fmt:message key="label.exit"/></a></p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <nav>
            <ul class="nav nav-pills nav-justified">
                <li class="active"><a href="/controller?command=VIEW_FILMS"><fmt:message key="label.main"/></a></li>
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="container">
        <div class="row">
            <form role="search" action="/controller" method="post">
                <input type="hidden" name="command" value="FIND_FILM">
                <div class="input-group">
                    <input type="text" class="form-control" name="name" placeholder="<fmt:message key="label.find"/>">
                    <span class="input-group-btn">
    	    		    <button class="btn btn-info" type="submit">
	      	  			    <i class="glyphicon glyphicon-search"></i>
    	    		    </button>
                    </span>
                </div>
            </form>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <c:forEach items="${films}" var="film" begin="0" end="7">
                <div class="col-sm-6 col-md-3">
                    <div class="thumbnail">
                        <img src="../resources/images/${film.name}.jpg" alt="...">
                        <div class="caption">
                            <h4>${film.name}</h4>
                            <p class="size">${film.description}</p>
                            <p><a href="/controller?command=VIEW_FILM&filmId=${film.id}" class="btn btn-primary"
                                  role="button"><fmt:message key="label.more"/></a></p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
<footer>
    Copyright © by Alexey Puplikov
</footer>
</body>
</html>