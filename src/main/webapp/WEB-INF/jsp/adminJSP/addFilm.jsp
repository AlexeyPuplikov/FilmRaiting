<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="label.addNewFilm"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/fonts/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/form-elements.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/addStyle.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>">
    <script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/scripts.js"/>"></script>
</head>
<body>
${messageAddFilm}
<div class="top-content">
    <div class="inner-bg">
        <div class="container">
            <a href="<c:url value="/controller?command=OPEN_MAIN_ADMIN_PAGE"/>"><fmt:message key="label.toHome"/></a>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-box">
                        <div class="form-top">
                            <div class="form-top-left">
                                <h3><fmt:message key="label.addNewFilm"/></h3>
                            </div>
                        </div>
                        <div class="form-bottom">
                            <form class="registration-form" action="<c:url value="/controller"/>" method="post"
                                  role="form">
                                <input type="hidden" name="command" value="ADD_FILM">
                                <div class="form-group">
                                    <label class="sr-only" for="form-name"><fmt:message key="label.filmName"/></label>
                                    <input type="text" name="name" class="form-username form-control"
                                           placeholder="<fmt:message key="label.filmName"/>" id="form-name" required/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-year"><fmt:message key="label.year"/></label>
                                    <input type="number" name="year" class="form-username form-control"
                                           placeholder="<fmt:message key="label.year"/>" required id="form-year" min="1900" max="2017"/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-description"><fmt:message
                                            key="label.description"/></label>
                                    <textarea class="form-control" name="description"
                                              placeholder="<fmt:message
                                                    key="label.description"/>" id="form-description"></textarea>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-premiere"><fmt:message
                                            key="label.premiere"/></label>
                                    <p><fmt:message key="label.premiere"/></p>
                                    <input type="date" name="premiere" class="form-username form-control"
                                           id="form-premiere" required min="1900-01-01" max="2017-12-01"/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-time"><fmt:message key="label.time"/></label>
                                    <input type="number" name="time" class="form-username form-control"
                                           placeholder="<fmt:message key="label.timeInMin"/>" id="form-time" required min="15" max="400"/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-budget"><fmt:message key="label.budget"/></label>
                                    <input type="number" name="budget" class="form-username form-control"
                                           placeholder="<fmt:message key="label.budget"/>" id="form-budget" required min="2000000" max="500000000"/>
                                </div>
                                <div class="form-group">
                                    <p><fmt:message key="label.stageDirector"/></p>
                                    <label class="sr-only" for="form-stage-director"><fmt:message
                                            key="label.stageDirector"/></label>
                                    <select name="stageDirector" id="form-stage-director" class="form-control">
                                        <c:forEach items="${allStageDirectors}" var="stageDirector">
                                            <option value="${stageDirector.name}">${stageDirector.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="submit" class="btn"><fmt:message key="label.add"/></button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-box">
                        <div class="form-top">
                            <div class="form-top-left">
                                <h3><fmt:message key="label.addParameters"/></h3>
                            </div>
                        </div>
                        <div class="form-bottom">
                            <form class="form" action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="ADDITIONAL_PARAMETERS_TO_FILM">
                                <div class="form-group">
                                    <p><fmt:message key="label.selectFilm"/></p>
                                    <label class="sr-only" for="form-film"><fmt:message key="label.film"/></label>
                                    <select name="film" id="form-film" class="form-control"
                                            onchange="this.form.submit()">
                                        <c:choose>
                                            <c:when test="${currentFilm.name != null}">
                                                <option>${currentFilm.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option selected><fmt:message key="label.select"/></option>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:forEach items="${allFilms}" var="film">
                                            <option value="${film.name}">${film.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>
                            <form class="form" action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="SELECT_CREATE_PARAMETERS_TO_FILM">
                                <input type="hidden" name="currentFilm" value="${currentFilm.name}">
                                <div class="form-group">
                                    <p><fmt:message key="label.selectActors"/></p>
                                    <label class="sr-only" for="form-actor">Актер</label>
                                    <select multiple name="actor" id="form-actor" class="form-control">
                                        <c:forEach items="${allActors}" var="actor">
                                            <option value="${actor.name}">${actor.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <p><fmt:message key="label.selectGenres"/></p>
                                    <label class="sr-only" for="form-genre">Жанр</label>
                                    <select multiple name="genre" id="form-genre" class="form-control">
                                        <c:forEach items="${allGenres}" var="genre">
                                            <option value="${genre.name}">${genre.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <p><fmt:message key="label.selectCountries"/></p>
                                    <label class="sr-only" for="form-country">Страна</label>
                                    <select multiple name="country" id="form-country" class="form-control">
                                        <c:forEach items="${allCountries}" var="country">
                                            <option value="${country.name}">${country.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="submit" class="btn"><fmt:message key="label.add"/></button>
                            </form>
                            <c:choose>
                                <c:when test="${currentFilm.name != null && currentFilm.cover == null}">
                                    <form class="form" action="<c:url value="/controller"/>" method="post"
                                          enctype="multipart/form-data">
                                        <p><fmt:message key="label.selectCover"/></p>
                                        <input type="hidden" name="command" value="UPLOAD_FILE">
                                        <input type="hidden" name="filmName" value="${currentFilm.name}">
                                        <input type="file" name="image" >
                                        <button type="submit" class="btn"><fmt:message key="label.add"/></button>
                                    </form>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-box">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#stageDirector" data-toggle="tab"><fmt:message key="label.addStageDirector"/></a></li>
                            <li><a href="#actor" data-toggle="tab"><fmt:message key="label.addActor"/></a></li>
                            <li><a href="#genre" data-toggle="tab"><fmt:message key="label.addGenre"/></a></li>
                            <li><a href="#country" data-toggle="tab"><fmt:message key="label.addCountry"/></a></li>
                        </ul>
                        <div id="myTabContent" class="tab-content">
                            <div class="tab-pane active in" id="stageDirector">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3><fmt:message key="stageDirector.add"/></h3>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form role="form" action="<c:url value="/controller"/>" method="post"
                                          class="registration-form">
                                        <input type="hidden" name="command" value="ADD_STAGE_DIRECTOR">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name-stage-director"><fmt:message
                                                    key="stageDirector.name"/></label>
                                            <input type="text" name="stageDirectorName"
                                                   class="form-username form-control"
                                                   placeholder="<fmt:message key="stageDirector.name"/>"
                                                   id="form-name-stage-director"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only"
                                                   for="form-date-of-birth-stage-director"><fmt:message
                                                    key="label.dateOfBirth"/></label>
                                            <input type="date" name="stageDirectorDateOfBirth"
                                                   class="form-username form-control"
                                                   placeholder="<fmt:message key="label.dateOfBirth"/>"
                                                   id="form-date-of-birth-stage-director"
                                                   required/>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-info-stage-director"><fmt:message
                                                    key="label.info"/></label>
                                            <textarea class="form-control" name="infoStageDirector"
                                                      placeholder="<fmt:message
                                                    key="label.info"/>" id="form-info-stage-director"></textarea>
                                        </div>
                                        <button type="submit" class="btn"><fmt:message key="label.add"/></button>
                                    </form>
                                </div>
                            </div>
                            <div class="tab-pane active fade" id="actor">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3><fmt:message key="actor.add"/></h3>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form role="form" action="<c:url value="/controller"/>" method="post"
                                          class="registration-form">
                                        <input type="hidden" name="command" value="ADD_ACTOR">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name-actor"><fmt:message
                                                    key="actor.name"/></label>
                                            <input type="text" name="actorName" class="form-username form-control"
                                                   placeholder="<fmt:message key="actor.name"/>"
                                                   id="form-name-actor"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-date-of-birth-actor"><fmt:message
                                                    key="label.dateOfBirth"/></label>
                                            <input type="date" name="actorDateOfBirth"
                                                   class="form-username form-control"
                                                   placeholder="<fmt:message key="label.dateOfBirth"/>"
                                                   id="form-date-of-birth-actor" required/>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-info-actor"><fmt:message
                                                    key="label.info"/></label>
                                            <textarea class="form-control" name="infoActor"
                                                      placeholder="<fmt:message key="label.info"/>"
                                                      id="form-info-actor"></textarea>
                                        </div>
                                        <button type="submit" class="btn"><fmt:message key="label.add"/></button>
                                    </form>
                                </div>
                            </div>
                            <div class="tab-pane active fade" id="genre">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3><fmt:message key="genre.add"/></h3>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form role="form" action="<c:url value="/controller"/>" method="post"
                                          class="registration-form">
                                        <input type="hidden" name="command" value="ADD_GENRE">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name-genre"><fmt:message
                                                    key="genre.name"/></label>
                                            <input type="text" name="genreName" class="form-username form-control"
                                                   placeholder="<fmt:message key="genre.name"/>" id="form-name-genre"/>
                                        </div>
                                        <button type="submit" class="btn"><fmt:message key="label.add"/></button>
                                    </form>
                                </div>
                            </div>
                            <div class="tab-pane active fade" id="country">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3><fmt:message key="country.add"/></h3>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form role="form" action="<c:url value="/controller"/>" method="post"
                                          class="registration-form">
                                        <input type="hidden" name="command" value="ADD_COUNTRY">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name-country"><fmt:message
                                                    key="country.name"/></label>
                                            <input type="text" name="countryName" class="form-username form-control"
                                                   placeholder="<fmt:message key="country.name"/>"
                                                   id="form-name-country"/>
                                        </div>
                                        <button type="submit" class="btn"><fmt:message key="label.add"/></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../commonJSP/footer.jsp"/>
</body>
</html>
