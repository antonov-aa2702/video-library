<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Add new film</title>
</head>
<body>
<%@ include file="header.jsp" %>
<form action="${pageContext.request.contextPath}" method="post" enctype="multipart/form-data">
    <label for="nameId"><fmt:message key="page.add-film.name"/>:
        <input type="text" name="name" id="nameId" required>
    </label><br>
    <label for="releaseDateId"><fmt:message key="page.add-film.release-date"/>:
        <input type="date" name="releaseDate" id="releaseDateId" required>
    </label><br>
    <label><fmt:message key="page.add-film.country"/>
        <select name="country" id="countryId">
            <c:forEach var="country" items="${requestScope.countries}">
                <option value="${country}">${country}</option>
            </c:forEach>
        </select>
    </label><br>
    <label><fmt:message key="page.add-film.genre"/>:
        <c:forEach var="genre" items="${requestScope.genres}">
            <input type="radio" name="genre" value="${genre}"> ${genre}
        </c:forEach>
    </label><br>
    <label><fmt:message key="page.add-film.duration"/>:
        <input type="number" id="durationId" name="duration" min="1" max="1000" required/>
    </label><br>
    <label for="imageId"><fmt:message key="page.add-film.image"/>:
        <input type="file" name="image" id="imageId" required>
    </label><br>
    <c:if test="${requestScope.errors!=null}">
        <c:forEach var="error" items="${requestScope.errors}">
            <div>${error.message}</div>
        </c:forEach>
    </c:if>
    <button type="submit"><fmt:message key="page.add-film.send"/></button>
</form>
</body>
</html>
