var nodes = [];
var role_nodes = [];
var maxOpenNav = 5;
var userRoles = [];

userRoles = findRoleByUserId($("#backUserInfo").attr("data-userid"));  //根据用户的id查找对应的角色
nodes = getXNodes(); //获取所有的节点
role_nodes = getXRoleNodeWithRoles();  // 根据用户的角色查找所有的 角色节点关系

var result = getThisRole_Nodes(nodes, role_nodes); //根据角色节点关系 和 所有的节点信息进行筛选节点。

makeNav(result);

function sortNavData(temp_result) {
    var temp = [];
    var arr = [];
    arr.push(temp_result[0]);
    temp.push({
        level: temp_result[0].level,
        arr: arr
    });

    //分级存放
    for (var i = 1, len = temp_result.length; i < len; i++) {
        var flag = false;
        for (var j = 0, len1 = temp.length; j < len1; j++) {
            if (temp[j].level == temp_result[i].level) {
                flag = true;
                temp[j].arr.push(temp_result[i]);
                break;
            }
        }
        if (!flag) {
            var temparr = [];
            temparr.push(temp_result[i]);
            temp.push({
                level: temp_result[i].level,
                arr: temparr
            })
        }
    }
    for (var i = 0, len1 = temp.length - 1; i < len1; i++) {
        for (var j = 0, len2 = temp.length - i - 1; j < len2; j++) {
            if (temp[j].level > temp[j + 1].level) {
                var t = temp[j];
                temp[j] = temp[j + 1];
                temp[j + 1] = t;
            }
        }
    }
    return temp;
}

function makeNav(result) {

    var temp = [];

    if (result.length != 0) {
        temp = sortNavData(result);

        var nav_body = $("div[data-type=nav]");
        nav_body.empty();
        for (var i = 0, len = temp.length; i < len; i++) {
            var arr = temp[i].arr;
            for (var j = 0, len1 = arr.length; j < len1; j++) {
                var href = "javascript:void(0)";
                if (arr[j].isMenu != 1) {
                    href = arr[j].url;
                }
                var id = arr[j].pid;
                var div = $("div[data-id=" + id + "]")[0];
                div = $(div || nav_body);
                if (arr[j].isMenu == 1) {
                    div.append(
                        "<nav class='navbar navbar-dark darken-2 mb-1' style='width: 100%'>" +
                        "<!-- Navbar brand -->" +
                        "<div style='cursor: pointer;display: flex;width:100%;justify-content: space-between;'" +
                        " onclick='NavCliker(this)' " +
                        " data-toggle='collapse' data-target='#nav-" + arr[j].id + "'" +
                        " aria-controls='nav-" + arr[j].id + "' aria-expanded='false' aria-label='Toggle navigation'>" +
                        "<a class='navbar-brand black-text' style='font-size: 1em;line-height: 28px' href=" + href + ">" + arr[j].name + "</a>" +
                        "<button class='navbar-toggler second-button'>" +
                        "<div class='animated-icon'>" +
                        "    <span></span>" +
                        "    <span></span>" +
                        "    <span></span>" +
                        "    <span></span></div>" +
                        "</button>" +
                        "</div>" +
                        "<div class='collapse navbar-collapse' id='nav-" + arr[j].id + "' data-id='" + arr[j].id + "'>" +
                        "</div>" +
                        "</nav>");
                } else {
                    var length = div.find("ul").length;
                    if (length == 0) {
                        div.append("<ul class='navbar-nav mr-auto darken-2' style='padding: .1rem .5rem;'>" +
                            "    <li class='nav-item'>" +
                            "        <a style='line-height: 20px' class='pl-3 black-text nav-link' data-type='nav-link'  data-href=" + href + ">" + arr[j].name + "</a>" +
                            "    </li>" +
                            "</ul>")
                    } else {
                        div.find("ul").append(
                            "    <li class='nav-item'>" +
                            "        <a style='line-height: 20px' class='pl-3 black-text nav-link' data-type='nav-link'  data-href=" + href + ">" + arr[j].name + "</a>" +
                            "    </li>");
                    }
                }
            }
        }
    } else {

    }
}


// function makeNav(result) {
//
//     var temp = [];
//
//     if (result.length != 0) {
//         temp = sortNavData(result);
//
//         var nav_body = $("ul[data-type=nav]");
//         nav_body.empty();
//         for (var i = 0, len = temp.length; i < len; i++) {
//             var arr = temp[i].arr;
//             for (var j = 0, len1 = arr.length; j < len1; j++) {
//                 var href = "javascript:void(0)";
//                 if (arr[j].isMenu != 1) {
//                     href = arr[j].url;
//                 }
//                 var id = arr[j].pid;
//                 var div = $("div[data-id=" + id + "]")[0];
//                 div = $(div || nav_body);
//                 if (arr[j].isMenu == 1) {
//                     div.append(
//                         "<nav class='navbar navbar-dark grey darken-2 mb-1' style='width: 100%'>" +
//                         "<!-- Navbar brand -->" +
//                         "<div style='cursor: pointer;display: flex;width:100%;justify-content: space-between;'" +
//                         " onclick='NavCliker(this)' " +
//                         " data-toggle='collapse' data-target='#nav-" + arr[j].id + "'" +
//                         " aria-controls='nav-" + arr[j].id + "' aria-expanded='false' aria-label='Toggle navigation'>" +
//                         "<a class='navbar-brand' href=" + href + ">" + arr[j].name + "</a>" +
//                         "<!-- Collapse button -->" +
//                         "<button class='navbar-toggler second-button'>" +
//                         "<div class='animated-icon'>" +
//                         "    <span></span>" +
//                         "    <span></span>" +
//                         "    <span></span>" +
//                         "    <span></span></div>" +
//                         "</button>" +
//                         "</div>" +
//                         "<div class='collapse navbar-collapse' id='nav-" + arr[j].id + "' data-id='" + arr[j].id + "'>" +
//                         "</div>" +
//                         "</nav>");
//                 } else {
//                     var length = div.find("ul").length;
//                     if (length == 0) {
//                         div.append("<ul class='navbar-nav mr-auto grey darken-2' style='padding: .1rem .5rem;'>" +
//                             "    <li class='nav-item'>" +
//                             "        <a style='color: #fff' class='nav-link' data-type='nav-link' data-href=" + href + ">" + arr[j].name + "</a>" +
//                             "    </li>" +
//                             "</ul>")
//                     } else {
//                         div.find("ul").append(
//                             "    <li class='nav-item'>" +
//                             "        <a style='color: #fff' class='nav-link' data-type='nav-link' data-href=" + href + ">" + arr[j].name + "</a>" +
//                             "    </li>");
//                     }
//                 }
//             }
//         }
//     } else {
//
//     }
// }


$(".nav-link[data-type=nav-link]").on({
    click: function () {
        var _this = $(this);
        var href = _this.attr("data-href");
        var text = _this.html();
        var count = $("#TabMenu").find("li").length;
        var lis = $(".nav-link[data-type=nav-link]");
        if ($("#" + href + "-tab").length == 0) {
            if (count >= maxOpenNav) {
                bootoast({
                    message: "最多只能开启" + maxOpenNav + "个顶部菜单，开启太多了",
                    type: 'warning',
                    position: 'top-right',
                    timeout: 2
                });
            } else {
                $("#TabMenu").append("<li class='nav-item waves-effect waves-light' data-type='topNav' >" +
                    "                    <a class='nav-link' id='" + href + "-tab' data-toggle='tab' href='#" + href + "' role='tab'" +
                    "                       aria-controls='" + href + "'" +
                    "                       aria-selected='true'>" + text + "&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-refresh' data-type='nav-refresh'></i>&nbsp; | &nbsp;<i class='fa fa-remove fa-lg' data-type='nav-remove'></i></a>" +
                    "                </li>")
                $("#TabContent").append("<div class='tab-pane fade' style='height: 100%' id='" + href + "' role='tabpanel' aria-labelledby='" + href + "-tab'><iframe height='100%' style='width: 100%;' data-src='http://localhost:8080/backPage/" + href + "' src='http://localhost:8080/backPage/" + href + "?_=" + new Date().getTime() + "' frameborder='0'></iframe></div>");
                $("#" + href + "-tab").tab("show");
                removeNavActive(lis);
                _this.parent().addClass("active");
            }
        } else {
            removeNavActive(lis);
            _this.parent().addClass("active");
            $("#" + href + "-tab").tab("show");
        }
        bindNavRemove();
        bindNavRefresh();
    }
})

function bindNavRemove() {
    var removeButton = $("i[data-type=nav-remove]");
    removeButton.off();
    removeButton.on({
        click: function () {
            var _this = $(this);
            var time = setInterval(function () {
                var id = (_this.parent().attr("href"));
                $(id + "[role=tabpanel]").remove();
                _this.parent().parent().remove();
                var remainNav = ($("#TabMenu").find("li"));
                var id = remainNav.eq(remainNav.length - 1).find("a").attr("href");
                $(id + "-tab").tab("show");
                clearInterval(time);
            }, 100)

        }
    })
}


function bindNavRefresh() {
    var refreshButton = $("i[data-type=nav-refresh]");
    refreshButton.off();
    refreshButton.on({
        click: function () {
            var href = $(this).parent().attr("href");
            var iframe_temp = $("" + href).find("iframe");
            var src = iframe_temp.attr("data-src") + "?:=" + Date.now();
            iframe_temp.attr("src", "");
            iframe_temp.attr("src", src);
        }
    })
}


function getThisRole_Nodes(nodes, role_nods) {
    var temp = [];
    for (var i = 0, len = nodes.length; i < len; i++) {
        for (var j = 0, len1 = role_nodes.length; j < len1; j++) {
            if (nodes[i].id == role_nodes[j]) {
                temp.push(nodes[i]);
                role_nods.splice(j, 1);
                len1--;
                j--;
                break
            }
        }
    }
    return temp;
}

function removeNavActive(lis) {
    for (var i = 0, len = lis.length; i < len; i++) {
        $(lis[i]).parent().removeClass("active");
    }
}


function getXNodes() {
    var tempData;
    $.ajax({
        url: '/xNode/findNodeToBack',
        type: "post",
        dataType: "json",
        data: {
            direction: "asc",
            limitSize: 10000
        },
        cache: false,
        async: false,
        success: function (data) {
            tempData = data.data;
        },
        error: function (data) {
        }
    });
    return tempData;
}


function getXRoleNodeWithRoles() {
    var tempData;
    var roleIds = [];
    for (var i = 0; i < userRoles.length; i++) {
        roleIds.push(userRoles[i].roleId);
    }
    $.ajax({
        url: '/xRoleNode/findWithRoleIds',
        type: "post",
        dataType: "json",
        data: {
            roleIds: roleIds,
        },
        traditional:true,
        cache: false,
        async: false,
        success: function (data) {
            console.log(data);
            tempData = data.data;
        },
        error: function (data) {
        }
    });
    return tempData;
}

function getXRoleNodeByRoleId(Rid) {
    var tempData;
    $.ajax({
        url: '/xRoleNode/find',
        type: "post",
        dataType: "json",
        data: {
            direction: "asc",
            limitSize: 10000,
            roleId: Rid
        },

        cache: false,
        async: false,
        success: function (data) {
            tempData = data.data;
        },
        error: function (data) {
        }
    });
    return tempData;
}

function findRoleByUserId(id) {
    var temp = [];
    $.ajax({
        url: '/xUserRole/findByUserId',
        type: "post",
        dataType: "json",
        data: {
            userId: id
        },
        cache: false,
        async: false,
        success: function (data) {
            temp = data;
        },
        error: function (data) {
        }
    });
    return temp;
}

function getAllRoles() {
    var temp = [];
    $.ajax({
        url: '/xRole/findAll',
        type: "post",
        dataType: "json",
        data: {},
        cache: false,
        async: false,
        success: function (data) {
            temp = data;
        },
        error: function (data) {
        }
    });
    return temp;
}