<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>"/>
    <title>404Error</title>
</head>
<body>
<div class="container">
    <nav>
        <ul class="nav nav-pills nav-justified">
            <li class="active">
                <a href="<c:url value="/controller?command=VIEW_FILMS&page=1&recordsPerPage=4"/>">Home</a>
            </li>
        </ul>
    </nav>
</div>
<div class="container">
    <div class="row">
        <h1>404 Not Found</h1>
        <h2>The requested URL was not found on this server.</h2>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
