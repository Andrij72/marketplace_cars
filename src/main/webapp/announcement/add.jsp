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
    <title>Car Sales</title>
</head>
<body>
<div>
    <c:import url="../_menu.jsp"/>
    <div class="container js-main-container main-container">
        <h4 class="left-align page-title">Розмістіть ваше оголошення</h4>
        <div class="row">
            <div class="col s14 card auto-form js-auto-info-container">
                <div class="auto-info-container">
                    <input type="hidden" value="" class="js-announcement-type">
                    <input type="hidden" value="" class="js-user">
                    <p class="center-align auto-form-title">характеристики авто</p>
                    <div class="row">
                        <div class="input-field col s3">
                            <select class="js-city">
                                <option value="" selected disabled></option>
                            </select>
                            <label>місто</label>
                        </div>
                        <div class="input-field col s3">
                            <select class="js-model">
                                <option value="" selected disabled></option>
                            </select>
                            <label>марка авто</label>
                        </div>
                        <div class="input-field col s3">
                            <input id="price" type="text" class="validate js-price">
                            <label for="price">ціна, &dollar;</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s3">
                            <select class="js-is_new">
                                <option value="" selected disabled></option>
                                <option value=false>з пробігом</option>
                                <option value=true>новий</option>
                            </select>
                            <label>Стан</label>
                        </div>
                        <div class="input-field col s4">
                            <input id="mileage" type="text" class="validate js-mileage">
                            <label for="mileage">пробіг, км</label>
                        </div>
                        <div class="input-field col s4">
                            <p>
                                <label>
                                    <input type="checkbox" class="filled-in js-is_broken"/>
                                    <span> не на ходу/або битий</span>
                                </label>
                            </p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s4">
                            <select class="js-body_type">
                                <option value="" selected disabled></option>
                            </select>
                            <label>кузов</label>
                        </div>
                        <div class="input-field col s4">
                            <select class="js-engine">
                                <option value="" selected disabled></option>
                            </select>
                            <label>двигун</label>
                        </div>
                        <div class="input-field col s4">
                            <select class="js-transmission_box">
                                <option value="" selected disabled></option>
                            </select>
                            <label>коробка передач</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s8">
                            <textarea id="description" class="materialize-textarea js-description"></textarea>
                            <label for="description">опис авто</label>
                        </div>
                        <div class="input-field col s3 center-align">
                            <button type="button" class="waves-effect waves-purple btn js-add-next" disabled>далі</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col s6 offset-s3 card auto-form js-photo-container" style="display: none">
                <form class="col" action="<c:url value='/announcement/photo'/>" method="post" enctype="multipart/form-data">
                    <div class="photo-container">
                        <input type="hidden" value="" class="js-announcement-id" name="announcementId">
                        <p class="center-align auto-form-title">фото авто</p>
                        <div class="file-field input-field choose-photo">
                            <div class="btn">
                                <span>вибрати</span>
                                <input type="file" name="file">
                            </div>
                            <div class="file-path-wrapper">
                                <input class="file-path validate" type="text">
                            </div>
                        </div>
                        <div class="center-align">
                            <button type="submit" class="waves-effect waves-purple btn">
                                розмістити
                            </button>
                        </div>
                    </div>
                </form>
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
<script type="text/javascript" src="<c:url value="/js" />/add.js"></script>
</body>
</html>