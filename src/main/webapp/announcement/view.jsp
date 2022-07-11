<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="ru">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="<c:url value="/css" />/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css" />/main.css" media="screen,projection"/>
    <title>AUTO Marketplace</title>
</head>
<body>
<div>
    <c:import url="../_menu.jsp"/>
    <div class="container js-main-container main-container">
        <%--<h3 class="center-align page-title"><span class="js-model">BMW</span>, <span class="js-price">20000 &#x24;</span></h3>--%>
        <div class="row card auto-form">
            <div class="col s9 image-container">
                <c:forEach var="carPhoto" items="${announcement.car.carPhotos}">
                    <img src="<c:url value='/announcement/photo?name=${carPhoto.id}'/>" width="500">
                </c:forEach>
                <c:if test="${empty announcement.car.carPhotos}">
                    <img src="img/no-image.jpeg" width="500">
                </c:if>
            </div>
            <div class="col s5 property-container">
                <div class="col s12 description js-description">
                    <c:out value="${announcement.car.description}"/>
                </div>
                <div></div>
                <div class="col s11">
                    <span class="prop-name">Ціна:</span> <span class="prop-val js-price"><c:out value="${announcement.price}"/></span>
                </div>

                <div class="col s11">
                    <span class="prop-name">Mісто:</span> <span class="prop-val js-city"><c:out value="${announcement.city.name}"/></span>
                </div>
                <div class="col s11">
                    <span class="prop-name">Пробіг:</span> <span class="prop-val js-mileage"><c:out value="${announcement.car.mileage}"/></span> км
                </div>
                <div class="col s11">
                    <span class="prop-name">Стан:</span> <span class="prop-val js-is_broken">
                        <c:if test="${announcement.car.broken}">не битий</c:if>
                        <c:if test="${not announcement.car.broken}">новий</c:if>
                    </span>
                </div>
                <div class="col s11">
                    <span class="prop-name">Кузов:</span> <span class="prop-val js-body_type"><c:out value="${announcement.car.carBodyType.name}"/></span>
                </div>
                <div class="col s11">
                    <span class="prop-name">Двигун:</span> <span class="prop-val js-engine"><c:out value="${announcement.car.carEngineType.name}"/></span>
                </div>
                <div class="col s11">
                    <span class="prop-name">Коробка передач:</span> <span class="prop-val js-transmission_box"><c:out value="${announcement.car.carTransmissionBoxType.name}"/></span>
                </div>

                <div class="col s11 phone-number center-align">
                    <c:if test="${announcement.user.id == user.id}">
                        <p>
                            <label>
                                <input data-announcement_id="<c:out value="${announcement.id}"/>" type="checkbox" class="filled-in js-is_sold" <c:if test="${announcement.sold}">checked</c:if>>
                                <span>продано</span>
                            </label>
                        </p>
                    </c:if>
                    <c:if test="${announcement.user.id != user.id}">
                        <a href="tel:<c:out value="${announcement.user.phone}"/>" class="waves-effect waves-light btn">
                            позвонити
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div id="modal1" class="modal js-modal modal-custom">
        <div class="modal-content js-modal-content">
            <p class="js-modal-msg center-align"></p>
        </div>
        <div class="modal-footer">
            <a href="#!" class="modal-close waves-effect waves-green btn-flat">Ok</a>
        </div>
    </div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/js" />/materialize.min.js"></script>
<script type="text/javascript" src="<c:url value="/js" />/functions.js"></script>
<script type="text/javascript" src="<c:url value="/js" />/view.js"></script>
</body>
</html>