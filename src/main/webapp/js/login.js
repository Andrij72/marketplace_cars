$(document).ready(function () {
    $('.js-modal').modal();
});

$('.js-do-login').click(function () {
    let login = $('.js-login').val();
    let password = $('.js-password').val();
    if (login === "" || password === "") {
        showModalError("Fit all fields!");
        return false;
    }
    let data = {
        "login": login,
        "password": password
    };
    $.ajax({
        type: "POST",
        url: "user?action=login",
        contentType: "application/json",
        data: JSON.stringify(data),
    }).done(function(data) {
        window.location.reload();
    }).fail(function(err) {
        let instance = M.Modal.getInstance($(".js-modal"));
        instance.open();
        showModalError("Exception: password or lolin is illegal!");
    });
});