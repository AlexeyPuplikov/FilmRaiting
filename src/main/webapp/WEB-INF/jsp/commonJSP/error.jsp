<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>"/>
    <title>DatabaseError</title>
</head>
<body>
<div class="container">
    <nav>
        <ul class="nav nav-pills nav-justified">
            <li class="active">
                <a href="<c:url value="/controller?command=OPEN_MAIN_ADMIN_PAGE"/>">Home</a>
            </li>
        </ul>
    </nav>
</div>
<div class="container">
    <div class="row">
        <h2>${exception}</h2>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>