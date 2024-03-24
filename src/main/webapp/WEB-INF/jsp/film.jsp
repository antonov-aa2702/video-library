<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${requestScope.filmDto.name}</title>
</head>
<body>
<%@ include file="header.jsp" %>
<img width="150" height="100"
     src="${requestScope.request.contextPath}/images/${requestScope.filmDto.image}"
     alt="No Image"
     title="${requestScope.filmDto.description}">
<br>
<ol>
    <c:forEach var="feedback" items="${requestScope.feedbacks}">
        <li></li>
        <ul>
            <li>User:${feedback.userName}</li>
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
<form action="${requestScope.request.contextPath}/add-feedback" method="get">
    <button type="submit" name="filmId" value="${requestScope.filmDto.id}">Написать отзыв</button>
</form>
<%@ include file="footer-film.jsp" %>
<form action="${pageContext.request.contextPath}/films" method="get">
    <button type="submit">Back to all films</button>
</form>
</body>
</html>
