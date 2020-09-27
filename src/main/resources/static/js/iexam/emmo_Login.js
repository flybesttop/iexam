var loginButton = $("button[data-toggle=login]");

window.onkeydown = function (ev) {
    ev = getEvent();
    if (ev.keyCode == 13) {
        loginButton.click();
    }
};

loginButton.off();
loginButton.on({
    click: function () {
        var username = $("#account").val();
        var password = $("#password").val();
        $("#errorMessage").css("display","none");
        if (password == null || username == null || password == "" || username == "") {
            $("#errorMessage").css("display","block");
        } else {
            $.ajax({
                url: "/xUser/login",
                type: "post",
                dataType: "json",
                async: false,
                data: {
                    username: username,
                    password: password
                },
                success: function (data) {
                    console.log(data);
                    if (data.status == 1) {
                        toast(data.info, "success");
                        window.location.href = "/backPage/index";
                    } else {
                        toast(data.info, "danger");
                    }
                }
            });
        }
    }
});

function getEvent() {
    var e = arguments.callee.caller.arguments[0] || window.event;
    return e;
}