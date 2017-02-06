<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="../resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="../resources/css/style.css"/>
    <title>${film.name}</title>
</head>
<body>

<header>
    <div class="container">
        <div class="change-language">
            <a href="/controller?command=CHANGE_LANGUAGE&language=ru_RU&page=/controller?command=VIEW_FILM&filmId=${film.id}"><img src="../resources/images/lang-ru.png"></a>
            <a href="/controller?command=CHANGE_LANGUAGE&language=en_US&page=/controller?command=VIEW_FILM&filmId=${film.id}"><img src="../resources/images/lang-en.png"></a>
        </div>
        <div class="container">
            <div class="login">
                <c:choose>
                    <c:when test="${user == null}">
                        <a href="/jsp/login.jsp"><fmt:message key="label.enter"/></a>
                    </c:when>
                    <c:otherwise>
                        <p>${user.login} <fmt:message key="label.online"/> <a
                                href="/controller?command=LOGOUT"><fmt:message
                                key="label.exit"/></a></p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <nav>
            <ul class="nav nav-pills nav-justified">
                <li class="active"><a href="/controller?command=VIEW_FILMS"><fmt:message key="label.toHome"/></a></li>
            </ul>
        </nav>
    </div>
</header>
<main>
    <div class="container">
        <div class="row">
            <div class="col-md-5 main-img">
                <img src="../resources/images/${film.name}.jpg" alt="...">
            </div>
            <div class="col-md-7">
                <h2>${film.name}</h2>
                <p>Режиссер: <a href="#">${film.stageDirector.name}</a></p>
                <p>Год: ${film.year}</p>
                <p>Премьера: <fmt:formatDate value="${film.premiere}" type="DATE"/></p>
                <p>Продолжительность: ${film.time} мин.</p>
                <p>Бюджет: <fmt:formatNumber value="${film.budget}" type="NUMBER"/>$</p>
                <p>Страна:
                    <c:forEach items="${film.countries}" var="country">
                        <span>${country.name}; </span>
                    </c:forEach>
                </p>
                <p>Жанр:
                    <c:forEach items="${film.genres}" var="genre">
                        <span>${genre.name}; </span>
                    </c:forEach>
                </p>
                <p>Актеры:
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
                        <span>рейтинг: ${rating} из 10</span>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${userRating.userId != user.id}">
                        <form class="form" action="/controller" method="post">
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
                            <input type="hidden" name="userId" value="${user.id}">
                            <input class="button" type="submit" value="оценить">
                        </form>
                    </c:when>
                    <c:when test="${userRating.userId == user.id && user != null}">
                        <span>Вы уже поставили оценку этому фильму</span>
                    </c:when>
                    <c:when test="${user == null}">
                        <span>Авторизуйтесь, чтобы поставить оценку</span>
                    </c:when>
                </c:choose>
            </div>
        </div>
        <div class="row">
            <c:choose>
                <c:when test="${user != null}">
                    <form class="form" action="/controller" method="post">
                        <input type="hidden" name="command" value="ADD_COMMENT">
                        <input type="hidden" name="filmId" value="${film.id}">
                        <input type="hidden" name="userId" value="${user.id}">
                        <div class="form-group">
                    <textarea class="form-control" name="text" required="required"
                              placeholder="введите текст..."></textarea>
                            <button class="btn btn-info">Отправить</button>
                        </div>
                    </form>
                    <c:forEach items="${film.comments}" var="comment">
                        <div class="comment">
                            <span><fmt:formatDate value="${comment.creationDate}" type="BOTH"/> ${user.login}</span>
                            <p><c:out value="${comment.text}"/></p>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </div>
    </div>
</main>
<footer>
    <p>Copyright © by Alexey Puplikov</p>
</footer>
</body>
</html>
