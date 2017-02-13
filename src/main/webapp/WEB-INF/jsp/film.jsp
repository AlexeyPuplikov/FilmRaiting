<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>"/>
    <title>${film.name}</title>
</head>
<body>
<header>
    <div class="container">
        <jsp:include page="header.jsp"/>
    </div>
</header>
<main>
    <div class="container">
        <div class="row">
            <div class="col-md-5 main-img">
                <img src="/resources/images/${film.name}.jpg" alt="...">
            </div>
            <div class="col-md-7">
                <h2>${film.name}</h2>
                <p><fmt:message key="label.stageDirector"/> <a href="#">${film.stageDirector.name}</a></p>
                <p><fmt:message key="label.year"/> ${film.year}</p>
                <p><fmt:message key="label.premiere"/> <fmt:formatDate value="${film.premiere}" type="DATE"/></p>
                <p><fmt:message key="label.time"/> ${film.time} <fmt:message key="label.min"/></p>
                <p><fmt:message key="label.budget"/> <fmt:formatNumber value="${film.budget}" type="NUMBER"/>$</p>
                <p><fmt:message key="label.country"/>
                    <c:forEach items="${film.countries}" var="country">
                        <span>${country.name}; </span>
                    </c:forEach>
                </p>
                <p><fmt:message key="label.genre"/>
                    <c:forEach items="${film.genres}" var="genre">
                        <span>${genre.name}; </span>
                    </c:forEach>
                </p>
                <p><fmt:message key="label.actors"/>
                    <c:forEach items="${film.actors}" var="actor">
                        <a href="#">${actor.name}; </a>
                    </c:forEach>
                </p>
                <p>${film.description}</p>
            </div>
            <div class="col-xs-6">
                <c:choose>
                    <c:when test="${rating == 0}">
                        <span><fmt:message key="label.markInformation"/></span>
                    </c:when>
                    <c:otherwise>
                        <span><fmt:message key="label.rating"/> ${rating}/10</span>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${userMark.userId != user.id}">
                        <form class="form" action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="SET_RATING">
                            <input type="range" name="mark" min="1" max="10" step="1" list="rating"/>
                            <datalist id="rating">
                                <option value="1">
                                <option value="2">
                                <option value="3">
                                <option value="4">
                                <option value="5">
                                <option value="6">
                                <option value="7">
                                <option value="8">
                                <option value="9">
                                <option value="10">
                            </datalist>
                            <input type="hidden" name="filmId" value="${film.id}">
                            <input class="button" type="submit" value="оценить">
                        </form>
                    </c:when>
                    <c:when test="${userMark.userId == user.id && user != null}">
                        <p><fmt:message key="label.markUser"/></p>
                    </c:when>
                    <c:when test="${user == null}">
                        <p><fmt:message key="label.markLogin"/></p>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <div class="row">
            <c:choose>
                <c:when test="${user != null && user.blocked == false}">
                    <form class="form" action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="ADD_COMMENT">
                        <input type="hidden" name="filmId" value="${film.id}">
                        <div class="form-group">
                    <textarea class="form-control" name="text" placeholder="<fmt:message key="label.textComment"/>"></textarea>
                            <button class="btn btn-info"><fmt:message key="label.send"/></button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${user != null && user.blocked == true}">
                    <p><fmt:message key="label.commentBlocked"/></p>
                </c:when>
            </c:choose>
            <c:forEach items="${comments}" var="comment" varStatus="status">
                <div class="comment">
                    <span><fmt:formatDate value="${comment.creationDate}" type="BOTH"/> ${commentUsers[status.index].login}</span>
                    <p><c:out value="${comment.text}"/></p>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
