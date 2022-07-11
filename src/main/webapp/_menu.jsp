<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<nav>
    <div class="nav-wrapper teal lighten-2 container">
        <a class="brand-logo"> &nbsp;&nbsp;<b>AUTO Marketplace</b></a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <c:if test="${empty user}">
                <li>
                    <a href='<c:url value="/" />'>Головна</a>
                </li>
                <li>
                    <a href='<c:url value="/user?page=registration" />'>Регістрація</a>
                </li>
                <li>
                    <a href='<c:url value="/user?page=login" />'>Увійти</a>
                </li>
            </c:if>
            <c:if test="${not empty user}">
                <li>
                    <a href="<c:url value="/" />">Головна</a>
                </li>
                <li>
                    <a href="<c:url value="/user?page=profile&id=${user.id}" />">Профіль</a>
                </li>
                <li>
                    <a href="<c:url value="/announcement?page=add" />">Продати</a>
                </li>
                <li>
                    <a class="js-do-logout">Вийти</a>
                </li>
                <script type="text/javascript" src="<c:url value="/js" />/logout.js" defer></script>
            </c:if>
        </ul>
    </div>
</nav>