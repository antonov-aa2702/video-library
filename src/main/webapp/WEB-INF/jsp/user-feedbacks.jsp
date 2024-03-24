<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Отзывы пользователя${param.userName}</title>
</head>
<body>
<%@ include file="header.jsp" %>
<c:if test="${not empty requestScope.userFeedbacks}">
    <div>Все отзывы пользователя:${param.userName}</div>
    <ol>
        <c:forEach var="feedback" items="${requestScope.userFeedbacks}">
            <li></li>
            <ul>
                <li>User:${feedback.userName}</li>
                <li>Film:<a
                        href="${pageContext.request.contextPath}/film?filmId=${feedback.filmId}">${feedback.filmName}</a>
                </li>
                <li>Date:${feedback.date}</li>
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
    </ol>
    <form action="${pageContext.request.contextPath}/download" method="get">
        <button type="submit" name="userName" value="${param.userName}">Download report</button>
    </form>
    <form action="${pageContext.request.contextPath}/see-feedbacks" method="get">
        <button type="submit">Back</button>
    </form>
</c:if>
<c:if test="${empty requestScope.userFeedbacks}">
    <div>У ${param.userName} нет отзывов!</div>
    <form action="${pageContext.request.contextPath}/see-feedbacks" method="get">
        <button type="submit">Back</button>
    </form>
</c:if>
</body>
</html>
