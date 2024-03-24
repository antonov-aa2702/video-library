<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Authentication</title>
</head>
<body>
<%@ include file="header.jsp" %>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label><fmt:message key="page.login.email"/>
        <input type="text" name="email" id="emailId">
    </label><br>
    <label><fmt:message key="page.login.password"/>
        <input type="password" name="password" id="passwordId">
    </label><br>
    <button type="submit">Login</button>
    <c:if test="${param.error!=null}">
        <div style="color: red">Неверные данные</div>
    </c:if>
 </form>
<form action="${pageContext.request.contextPath}/registration" method="get">
    <button type="submit">Registration</button>
</form>
</body>
</html>