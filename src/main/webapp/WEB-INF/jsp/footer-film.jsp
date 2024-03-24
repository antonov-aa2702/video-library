<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${sessionScope.user.role.name() == 'ADMIN'}">
        <form action="${pageContext.request.contextPath}/remove-film" method="get">
            <button type="submit" name="filmId" value="${requestScope.filmDto.id}">remove</button>
        </form>
        <form action="${pageContext.request.contextPath}/edit-film" method="get">
            <button type="submit" name="filmId" value="${requestScope.filmDto.id}">edit</button>
        </form>
    </c:if>
</div>
