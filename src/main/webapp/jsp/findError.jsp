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
<main>
    <div class="container">
        <a href="/controller?command=VIEW_FILMS"><fmt:message key="label.toHome"/></a>
        <p><fmt:message key="label.findError"/></p>
    </div>
</main>
<footer>
    Copyright © by Alexey Puplikov
</footer>
</body>
</html>