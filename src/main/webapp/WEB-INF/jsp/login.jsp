<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="title"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/fonts/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/form-elements.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/styleregistration.css"/>">
    <script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/scripts.js"/>"></script>
</head>
<body>
<div class="top-content">
    <div class="inner-bg">
        <div class="container">
            <a href="<c:url value="/controller?command=VIEW_FILMS&page=1&recordsPerPage=4"/>">На главную</a>
            <div class="row">
                <div class="col-sm-5">
                    <div class="form-box">
                        <div class="form-top">
                            <div class="form-top-left">
                                <h3><fmt:message key="label.enter"/></h3>
                                <p><fmt:message key="label.enter.login.password"/></p>
                            </div>
                        </div>
                        <div class="form-bottom">
                            <form role="form" action="<c:url value="/controller"/>" method="post" class="login-form">
                                <input type="hidden" name="command" value="LOGIN">
                                <div class="form-group">
                                    <label class="sr-only" for="form-username"><fmt:message key="label.login"/></label>
                                    <input type="text" name="login" class="form-username form-control" placeholder="Логин..."
                                           id="form-username"/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-password"><fmt:message
                                            key="label.password"/></label>
                                    <input type="password" name="password" class="form-password form-control" placeholder="Пароль..."
                                           id="form-password"/>
                                </div>
                                <button type="submit" class="btn"><fmt:message key="label.enter"/></button>
                                <p>${errorLogin}</p>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-sm-1 middle-border"></div>
                <div class="col-sm-1"></div>
                <div class="col-sm-5">
                    <div class="form-box">
                        <div class="form-top">
                            <div class="form-top-left">
                                <h3><fmt:message key="label.registration"/></h3>
                                <p><fmt:message key="label.registration.login.password"/></p>
                            </div>
                        </div>
                        <div class="form-bottom">
                            <form role="form" action="<c:url value="/controller"/>" method="post" class="registration-form">
                                <input type="hidden" name="command" value="REGISTRATION">
                                <div class="form-group">
                                    <label class="sr-only" for="form-first-name"><fmt:message
                                            key="label.login"/></label>
                                    <input type="text" name="login" class="form-first-name form-control" placeholder="Логин..."
                                           id="form-first-name" pattern="^[a-zA-Z][a-zA-Z0-9-_]{4,16}$" title="латинские буквы, цифры, _, первый символ – латинская буква. Кол-во символов не менее 5">
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-password"><fmt:message
                                            key="label.password"/></label>
                                    <input type="password" name="password" class="form-password form-control" placeholder="Пароль..."
                                           id="form-password-registration" required="required" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).{6,20}$" title="не менее 6 символов, не менее одной буквы в каждом регистре и не менее одной цифры"/>
                                </div>
                                <button type="submit" class="btn"><fmt:message key="label.registration"/></button>
                                <p>${errorRegistration}</p>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer>
    <p>Copyright © by Alexey Puplikov</p>
</footer>
</body>
</html>