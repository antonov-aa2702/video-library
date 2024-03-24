<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Регистрация нового пользователя</title>
</head>
<body>
<div>Введите данные:</div>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label>name
        <input type="text" name="name" id="nameId" required>
    </label><br>LastName
    <label>birtday
        <input type="date" name="birthday" id="birthdayId">
    </label><br>
    <label>Email
        <input type="text" name="email" id="emailId" required>
    </label><br>
    <label>Password
        <input type="password" name="password" id="passwordId" required>
    </label><br>
    <label>Role
        <select name="role">
            <c:forEach var="role" items="${requestScope.roles}">
                <option value="${role}">${role}</option>
            </c:forEach>
        </select>
    </label><br>
     <c:if test="${not empty requestScope.errors}">
         <div style="color: red">
             <c:forEach var="error" items="${requestScope.errors}">
                 <div>${error.message}</div>
             </c:forEach>
         </div>
    </c:if>
    <button type="submit">Registration</button>
</form>
</body>
</html>
