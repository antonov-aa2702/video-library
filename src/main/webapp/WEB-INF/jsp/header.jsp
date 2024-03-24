<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${not empty sessionScope.user}">
    <div>
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit">LOGOUT</button>
        </form>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/locale" method="post">
    <div id="localeId">
        <button type="submit" name="lang" value="ru_RU">RU</button>
        <button type="submit" name="lang" value="en_US">US</button>
    </div>
</form>
<fmt:setLocale value="${sessionScope.lang!=null?sessionScope.lang:'ru_RU'}"/>
<fmt:setBundle basename="translations"/>
</body>
</html>
