<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
        <form action="${pageContext.request.contextPath}/add-film" method="get">
            <button type="submit">Add film</button>
        </form>
        <form action="${pageContext.request.contextPath}/see-feedbacks" method="get">
            <button type="submit">See feedbacks</button>
        </form>
    </c:if>
    <form action="${requestScope.request.contextPath}/films" method="get">
        <button type="submit">Сlear</button>
    </form>
</div>
