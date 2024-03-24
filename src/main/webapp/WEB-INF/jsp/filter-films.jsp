<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Поиск фильмов по фильтрам</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/films" method="post">
    <label>Введите название фильма<input type="text" name="nameFilm" id="nameFilmId"></label><br>
    <label>Выберите жанр
        <c:forEach var="genre" items="${requestScope.genres}">
            <input type="radio" name="nameGenre" value="${genre}">${genre}
        </c:forEach>
    </label><br>
    <label>Введите год
        <input type="number" name="year" id=yearId">
    </label><br>
    <label>Введите имя актера
        <input type="text" name="actor" id="actorId">
    </label><br>
    <label>Порядок
        <select name="order" id="orderId">
            <c:forEach var="order" items="${requestScope.orders}">
                <option value="${order}">${order}</option>
            </c:forEach>
        </select>
    </label>
    <button type="submit">Найти</button>
</form>
</body>
</html>
