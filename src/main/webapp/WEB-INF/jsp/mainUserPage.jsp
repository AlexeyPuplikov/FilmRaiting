<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="myTagLib" prefix="pagedTag" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
        <jsp:include page="header.jsp"/>
    </div>
</header>
<main>
    <div class="container">
        <div class="row">
            <form role="search" action="<c:url value="/controller"/>" method="get">
                <input type="hidden" name="command" value="FIND_FILM">
                <div class="input-group">
                    <input type="text" class="form-control" name="filmName"
                           placeholder="<fmt:message key="label.find"/>">
                    <span class="input-group-btn">
    	    		    <button class="btn btn-info" type="submit">
	      	  			    <i class="glyphicon glyphicon-search"></i>
    	    		    </button>
                    </span>
                </div>
                <p>${errorSearch}</p>
            </form>
        </div>
    </div>
    <pagedTag:getPaged numberOfRecords="${films.size()}" recordsPerPage="4">
        <jsp:body>
            <div class="container">
                <div class="row">
                    <c:forEach items="${limitFilms}" var="film">
                        <div class="col-sm-6 col-md-3">
                            <div class="thumbnail">
                                <img src="/resources/images/${film.name}.jpg" alt="...">
                                <div class="caption">
                                    <h4>${film.name}</h4>
                                    <p class="size">${film.description}</p>
                                    <p><a href="<c:url value="/controller?command=VIEW_FILM&filmId=${film.id}"/>"
                                          class="btn btn-primary" role="button"><fmt:message key="label.more"/></a></p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </jsp:body>
    </pagedTag:getPaged>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>