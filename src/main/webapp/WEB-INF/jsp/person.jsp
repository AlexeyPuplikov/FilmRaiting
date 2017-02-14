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
    <title>${person.name}</title>
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
            <div class="col-md-12">
                <h2>${person.name}</h2>
                <p><fmt:message key="label.dateOfBirth"/>: <fmt:formatDate value="${person.dateOfBirth}" type="DATE"/></p>
                <p>${person.info}</p>
            </div>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"/>
</body>
</html>
