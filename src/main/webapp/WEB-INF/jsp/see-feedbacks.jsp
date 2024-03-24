<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Найти все отзывы пользователя</title>
</head>
<body>
<%@ include file="header.jsp" %>
<label>Просмотр отзывов пользователей</label>
<form action="${pageContext.request.contextPath}/see-feedbacks" method="post">
    <label>UserName:
        <input type="text" name="userName" required>
    </label>
    <button type="submit">Find</button>
</form>
<ul>
    Для скачивания отчета
    <c:forEach var="user" items="${requestScope.users}">
        <li>
            <a href="${pageContext.request.contextPath}/user-feedbacks?userId=${user.id}&userName=${user.name}">${user.name}</a>
        </li>
    </c:forEach>
</ul>
<c:if test="${not empty requestScope.feedbacks}">
    <div>Все отзывы пользователя:${param.userName}</div>
    <ol>
        <c:forEach var="feedback" items="${requestScope.feedbacks}">
            <li></li>
            <ul>
                <li>User:${feedback.userName}</li>
                <li>Date:${feedback.date}</li>
                <li>Film:<a href="${pageContext.request.contextPath}/film?filmId=${feedback.filmId}">${feedback.filmName}</a></li>
                <li>Rating:${feedback.rating}</li>
                <li>Text:${feedback.text}</li>
                <c:if test="${sessionScope.user.name == feedback.userName}">
                    <form action="${pageContext.request.contextPath}/remove-feedback" method="get">
                        <button name="feedbackId" value="${feedback.id}">remove feedback
                            <input type="hidden"  name="filmId" value="${feedback.filmId}" />
                        </button>
                    </form>
                </c:if>
            </ul>
        </c:forEach>
        <form action="${pageContext.request.contextPath}/download" method="get">
            <button type="submit" name="userName" value="${param.userName}">Download report</button>
        </form>
    </ol>
</c:if>
<c:if test="${not empty requestScope.error}">
    <div>${requestScope.error}</div>
</c:if>
<c:if test="${requestScope.feedbacks.size()==0}">
    <div>У ${param.userName} нет отзывов</div>
</c:if>
<form action="${pageContext.request.contextPath}/films" method="get">
    <button type="submit">Back to films</button>
</form>
</body>
</html>
