window.onload = function (ev) {
    $("body").css("height", window.innerHeight)
    var TabContent = $("#TabContent");
    if (TabContent != undefined) {
        var tabMenu = $("#TabMenu");
        if (tabMenu != undefined) {
            var MemuHeight = (tabMenu[0].offsetHeight + "");
            TabContent.css("height", parseInt(window.innerHeight) - parseInt(MemuHeight));
        }
    }
}

window.onresize = function (ev) {
    $("body").css("height", window.innerHeight);
    var TabContent = $("#TabContent");
    if (TabContent != undefined) {
        var tabMenu = $("#TabMenu");
        if (tabMenu != undefined) {
            var MemuHeight = (tabMenu[0].offsetHeight + "");
            TabContent.css("height", parseInt(window.innerHeight) - parseInt(MemuHeight));
        }
    }
}



