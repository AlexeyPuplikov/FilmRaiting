<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.text"/>
<div class="container">
    <div class="change-language">
        <a href="<c:url value="/controller?command=CHANGE_LANGUAGE&language=ru_RU&currentPage=/controller?command=VIEW_FILMS&page=1&recordsPerPage=4"/>"><img
                src="<c:url value="/resources/images/lang-ru.png"/>"></a>
        <a href="<c:url value="/controller?command=CHANGE_LANGUAGE&language=en_US&currentPage=/controller?command=VIEW_FILMS&page=1&recordsPerPage=4"/>"><img
                src="<c:url value="/resources/images/lang-en.png"/>"></a>
    </div>
    <div class="container">
        <div class="login">
            <c:choose>
                <c:when test="${user == null}">
                    <a href="<c:url value="/controller?command=OPEN_LOGIN_PAGE"/>"><fmt:message key="label.enter"/></a>
                </c:when>
                <c:otherwise>
                    <p>${user.login} <fmt:message key="label.online"/> <fmt:message key="label.status"/> ${status}
                        <a href="<c:url value="/controller?command=LOGOUT"/>"><fmt:message key="label.exit"/></a>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <nav>
        <ul class="nav nav-pills nav-justified">
            <li class="active">
                <a href="<c:url value="/controller?command=VIEW_FILMS&page=1&recordsPerPage=4"/>"><fmt:message key="label.main"/></a>
            </li>
        </ul>
    </nav>
</div>
