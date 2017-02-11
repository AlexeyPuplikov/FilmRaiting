<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Добавление фильма</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/fonts/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/form-elements.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/styleregistration.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
    <script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/scripts.js"/>"></script>
</head>
<body>
<div class="top-content">
    <div class="inner-bg">
        <div class="container">
            <a href="<c:url value="/controller?command=OPEN_MAIN_ADMIN_PAGE"/>">На главную</a>
            <p>${addError}</p>
            <p>${successfulAddFilm}</p>
            <div class="row">
                <div class="col-md-4">
                    <div class="form-box">
                        <div class="form-top">
                            <div class="form-top-left">
                                <h3>Добавить фильм</h3>
                            </div>
                        </div>
                        <div class="form-bottom">
                            <form class="form" action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="ADD_FILM">
                                <div class="form-group">
                                    <label class="sr-only" for="form-name">Название фильма</label>
                                    <input type="text" name="name" class="form-username form-control"
                                           placeholder="Название" id="form-name" required/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-year">Год</label>
                                    <input type="number" name="year" class="form-username form-control"
                                           placeholder="Год" id="form-year" required/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-description">Описание</label>
                                    <textarea class="form-control" name="description" placeholder="Описание"
                                              id="form-description">
                                    </textarea>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-premiere">Премьера</label>
                                    <p>Премьера</p>
                                    <input type="date" name="premiere" class="form-username form-control"
                                           placeholder="Премьера" id="form-premiere" required/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-time">Время</label>
                                    <input type="number" name="time" class="form-username form-control"
                                           placeholder="Время в минутах" id="form-time" required/>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="form-budget">Бюджет</label>
                                    <input type="number" name="budget" class="form-username form-control"
                                           placeholder="Бюджет" id="form-budget" required/>
                                </div>
                                <div class="form-group">
                                    <p>Режиссер</p>
                                    <label class="sr-only" for="form-stage-director">Режиссер</label>
                                    <select name="stageDirector" id="form-stage-director" class="form-control">
                                        <c:forEach items="${allStageDirectors}" var="stageDirector">
                                            <option value="${stageDirector.name}">${stageDirector.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="submit" class="btn">Добавить</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-box">
                        <div class="form-top">
                            <div class="form-top-left">
                                <h3>Добавить параметры к фильму</h3>
                            </div>
                        </div>
                        <div class="form-bottom">
                            <form class="form" action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="ADDITIONAL_PARAMETERS_TO_FILM">
                                <div class="form-group">
                                    <p>Выберите фильм, к которому необходимо добавить параметры</p>
                                    <label class="sr-only" for="form-film">Фильм</label>
                                    <select name="film" id="form-film" class="form-control"
                                            onchange="this.form.submit()">
                                        <c:choose>
                                            <c:when test="${currentFilm.name != null}">
                                                <option>${currentFilm.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option selected>Выберите фильм</option>
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
                                    <p>Актеры(для выбора нескольких вариантов ужерживайте ctrl)</p>
                                    <label class="sr-only" for="form-actor">Актер</label>
                                    <select multiple name="actor" id="form-actor" class="form-control">
                                        <c:forEach items="${allActors}" var="actor">
                                            <option value="${actor.name}">${actor.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <p>Жанры(для выбора нескольких вариантов ужерживайте ctrl)</p>
                                    <label class="sr-only" for="form-genre">Жанр</label>
                                    <select multiple name="genre" id="form-genre" class="form-control">
                                        <c:forEach items="${allGenres}" var="genre">
                                            <option value="${genre.name}">${genre.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <p>Страны(для выбора нескольких вариантов ужерживайте ctrl)</p>
                                    <label class="sr-only" for="form-country">Страна</label>
                                    <select multiple name="country" id="form-country" class="form-control">
                                        <c:forEach items="${allCountries}" var="country">
                                            <option value="${country.name}">${country.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="submit" class="btn">Добавить</button>
                            </form>
                            <c:choose>
                                <c:when test="${currentFilm.name != null && currentFilm.cover == null}">
                                    <form class="form" action="<c:url value="/controller"/>" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="command" value="UPLOAD_FILE">
                                        <input type="hidden" name="filmName" value="${currentFilm.name}">
                                        <input type="file" name="image">
                                        <button type="submit" class="btn">Добавить</button>
                                    </form>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-box">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#stageDirector" data-toggle="tab">Режиссер</a></li>
                            <li><a href="#actor" data-toggle="tab">Актер</a></li>
                            <li><a href="#genre" data-toggle="tab">Жанр</a></li>
                            <li><a href="#country" data-toggle="tab">Страна</a></li>
                        </ul>
                        <div id="myTabContent" class="tab-content">
                            <div class="tab-pane active in" id="stageDirector">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3>Добавить режиссера</h3>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form role="form" action="<c:url value="/controller"/>" method="post" class="registration-form">
                                        <input type="hidden" name="command" value="ADD_STAGE_DIRECTOR">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name-stage-director">Имя режиссера</label>
                                            <input type="text" name="stageDirectorName" class="form-username form-control"
                                                   placeholder="Имя режиссера" id="form-name-stage-director"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-date-of-birth-stage-director">Датарождения</label>
                                            <input type="date" name="stageDirectorDateOfBirth" class="form-username form-control"
                                                   placeholder="Дата рождения" id="form-date-of-birth-stage-director" required/>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-info-stage-director">Информация</label>
                                            <textarea class="form-control" name="infoStageDirector"
                                                      placeholder="Информация" id="form-info-stage-director"></textarea>
                                        </div>
                                        <button type="submit" class="btn">Добавить</button>
                                    </form>
                                </div>
                            </div>
                            <div class="tab-pane active fade" id="actor">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3>Добавить актера</h3>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form role="form" action="<c:url value="/controller"/>" method="post" class="registration-form">
                                        <input type="hidden" name="command" value="ADD_ACTOR">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name-actor">Имя актера</label>
                                            <input type="text" name="actorName" class="form-username form-control"
                                                   placeholder="Имя актера"
                                                   id="form-name-actor"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-date-of-birth-actor">Дата рождения</label>
                                            <input type="date" name="actorDateOfBirth" class="form-username form-control"
                                                   placeholder="Дата рождения" id="form-date-of-birth-actor" required/>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="form-info-actor">Информация</label>
                                            <textarea class="form-control" name="infoActor" placeholder="Информация"
                                                      id="form-info-actor"></textarea>
                                        </div>
                                        <button type="submit" class="btn">Добавить</button>
                                    </form>
                                </div>
                            </div>
                            <div class="tab-pane active fade" id="genre">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3>Добавить жанр</h3>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form role="form" action="<c:url value="/controller"/>" method="post" class="registration-form">
                                        <input type="hidden" name="command" value="ADD_GENRE">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name-genre">Название жанра</label>
                                            <input type="text" name="genreName" class="form-username form-control"
                                                   placeholder="Название жанра" id="form-name-genre"/>
                                        </div>
                                        <button type="submit" class="btn">Добавить</button>
                                    </form>
                                </div>
                            </div>
                            <div class="tab-pane active fade" id="country">
                                <div class="form-top">
                                    <div class="form-top-left">
                                        <h3>Добавить страну</h3>
                                    </div>
                                </div>
                                <div class="form-bottom">
                                    <form role="form" action="<c:url value="/controller"/>" method="post" class="registration-form">
                                        <input type="hidden" name="command" value="ADD_COUNTRY">
                                        <div class="form-group">
                                            <label class="sr-only" for="form-name-country">Название страны</label>
                                            <input type="text" name="countryName" class="form-username form-control"
                                                   placeholder="Название страны" id="form-name-country"/>
                                        </div>
                                        <button type="submit" class="btn">Добавить</button>
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
<footer>
    <p>Copyright © by Alexey Puplikov</p>
</footer>
</body>
</html>
