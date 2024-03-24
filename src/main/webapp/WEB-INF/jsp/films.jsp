<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Все фильмы</title>
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="filter-films.jsp" %>
<c:if test="${not empty requestScope.films}">
    <div>Список фильмов</div>
    <c:forEach var="film" items="${requestScope.films}">
        <li>
            <img width="150" height="100" src="${requestScope.request.contextPath}/images/${film.image}" alt="No Image"><br>
            <a href="${requestScope.request.contextPath}/film?filmId=${film.id}">${film.name}</a>
        </li>
    </c:forEach>
</c:if>
<c:if test="${empty requestScope.films}">
    <div>Фильмов не найдено!</div>
</c:if>
<%@ include file="footer-films.jsp" %>
</body>
</html>
