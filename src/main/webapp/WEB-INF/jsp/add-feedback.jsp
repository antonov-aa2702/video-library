<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Добавление отзыва</title>
</head>
<body>
<%@ include file="header.jsp" %>
<form action="${pageContext.request.contextPath}/add-feedback?filmId=${requestScope.filmId}" method="post">
    <p><fmt:message key="page.add-feedback.comment"/> <br><textarea name="comment" cols="40" rows="3"></textarea></p>
    <label for="rating"><fmt:message key="page.add-feedback.rating"/></label>
    <input type="number" id="rating" name="rating" min="1" max="10" required/>
    <p>
        <label>
            <input type="submit" value="<fmt:message key="page.add-feedback.send"/>">
        </label>
        <label>
            <input type="reset" value="<fmt:message key="page.add-feedback.reset"/>">
        </label>
</form>
</body>
</html>
