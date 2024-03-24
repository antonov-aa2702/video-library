<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
     alt="No Image"><br>
<label><fmt:message key="page.edit-film.inputData"/></label>

<form action="${pageContext.request.contextPath}" method="post" enctype="multipart/form-data">
    <label for="nameId"><fmt:message key="page.edit-film.name"/>:
        <input type="text" name="name" id="nameId" placeholder="${requestScope.filmDto.name}" required>
    </label><br>
    <label for="releaseDateId"><fmt:message key="page.edit-film.release-date"/>:
        <input type="date" name="releaseDate" id="releaseDateId" placeholder="${requestScope.filmDto.releaseDate}"
               required>
    </label><br>
    <label><fmt:message key="page.edit-film.country"/>:
        <select name="country" id="countryId">
            <c:forEach var="country" items="${requestScope.countries}">
                <option value="${country}">${country}</option>
            </c:forEach>
        </select>
    </label><br>
    <label><fmt:message key="page.edit-film.genre"/>:
        <c:forEach var="genres" items="${requestScope.genres}">
            <input type="radio" name="genre" value="${genres}"> ${genres}
        </c:forEach>
    </label><br>
    <label><fmt:message key="page.edit-film.duration"/>:
        <input type="number" id="durationId" name="duration" min="1" max="1000"
               placeholder="${requestScope.filmDto.duration}" required/>
    </label><br>
    <label for="imageId"><fmt:message key="page.edit-film.image"/>:
        <input type="file" name="image" id="imageId" required>
    </label><br>
    <c:if test="${requestScope.errors!=null}">
        <c:forEach var="error" items="${requestScope.errors}">
            <div>${error.message}</div>
        </c:forEach>
    </c:if>
    <button type="submit"><fmt:message key="page.edit-film.send"/></button>
</form>
</body>
</html>
