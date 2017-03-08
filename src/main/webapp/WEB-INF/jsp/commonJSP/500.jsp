<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>"/>
    <title>505Error</title>
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
        <h1>Website Error</h1>
        <h2>We track these errors automatically, but if the problem persists feel free to contact us. In the meantime,
            try refreshing.</h2>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
