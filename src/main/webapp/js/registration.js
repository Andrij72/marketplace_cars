$(document).ready(function () {
    $('.js-modal').modal();
});

$('.js-do-registration').click(function () {
    let login = $('.js-login').val();
    let password = $('.js-password').val();
    let phone = $('.js-phone').val();
    let name = $('.js-name').val();
    if (login === "" || password === "" || phone === "" || name === "") {
        showModalError(" Fit all fields!");
        return false;
    }
    let data = {
        "login": login,
        "password": password,
        "phone": phone,
        "name": name
    };
    $.ajax({
        type: "POST",
        url: "user?action=registration",
        contentType: "application/json",
        data: JSON.stringify(data),
    }).done(function(data) {
        window.location.reload();
    }).fail(function(err) {
        let instance = M.Modal.getInstance($(".js-modal"));
        instance.open();
        showModalError("Error: registration is failed, try again!");
    });
});