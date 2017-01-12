<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP Timing</title>
</head>
<body>
    <h5>Время</h5>
    <jsp:useBean id="calendar" class="java.util.GregorianCalendar"/>
    <form name="Simple" action="timeaction" method="POST">
        <input type="hidden" name="time" value="${calendar.timeInMillis}"/>
        <input type="submit" name="button" value="Посчитать время"/>
    </form>
</body>
</html>
