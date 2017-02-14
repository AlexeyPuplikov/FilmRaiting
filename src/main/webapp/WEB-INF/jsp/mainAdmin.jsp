<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>"/>
    <title><fmt:message key="label.main"/></title>
</head>
<body>
<header>
    <div class="container">
        <div class="change-language">
            <a href="<c:url value="/controller?command=CHANGE_LANGUAGE&language=ru_RU"/>"><img
                    src="<c:url value="/resources/images/lang-ru.png"/>"></a>
            <a href="<c:url value="/controller?command=CHANGE_LANGUAGE&language=en_US"/>"><img
                    src="<c:url value="/resources/images/lang-en.png"/>"></a>
        </div>
        <div class="login">
            <p>${admin.login} <fmt:message key="label.online"/>
                <a href="<c:url value="/controller?command=LOGOUT"/>"><fmt:message key="label.exit"/></a>
            </p>
        </div>
    </div>
    <div class="container">
        <nav>
            <ul class="nav nav-pills nav-justified">
                <li class="active"><a href="<c:url value="/controller?command=OPEN_ADD_FILM_PAGE"/>">
                    <fmt:message key="label.addNewFilm"/></a></li>
                <li class="active"><a href="<c:url value="/controller?command=OPEN_USER_CONTROL_PAGE"/>">
                    <fmt:message key="label.userControl"/></a></li>
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="container">
        <div class="row">
            ${message}
            <c:forEach items="${films}" var="film">
                <form class="form" action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="DELETE_FILM">
                    <input type="hidden" name="film" value="${film.id}">
                    <div class="col-sm-6 col-md-2">
                        <div class="thumbnail">
                            <img src="/resources/images/${film.name}.jpg" alt="...">
                            <div class="caption">
                                <h5>${film.name}</h5>
                                <button type="submit" class="btn"><fmt:message key="label.delete"/></button>
                            </div>
                        </div>
                    </div>
                </form>
            </c:forEach>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
