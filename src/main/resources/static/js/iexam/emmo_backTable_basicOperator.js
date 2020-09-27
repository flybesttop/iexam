window.onkeydown = function (ev) {
    ev = getEvent();
    if (ev.keyCode == 13 && $(".modal.show").length != 0) {//有modal打开
        $($(".modal.show")[$(".modal.show").length - 1]).find("button[data-type=confirm]").click();
    }
};


var _basic_operator_url = $("#operator_url").attr("path");
var searchMode = false;
var appendMode = false;
var editCloumnMode = false;
var editMode = false;
var data_table = $("#operator_url").attr("dataBase");
var columnInfo = [];
var columnInfoSelectBtnClass = "blue";
var columnCanUse = [];
var columnSort = [];
var columnType = [];
var columnCanSee = [];
var dicinfo = [];

var tableColumns = [];

function dic() {
    $.ajax({
        url: '/xDictionary/findDataBasePre',
        type: "post",
        dataType: "json",
        data: {
            code: data_table + "_",
            type: "column_type"
        },
        cache: false,
        async: false,
        success: function (data) {
            dicinfo = data;
        },
        error: function (data) {
        }
    });

    for (var i = 0; i < dicinfo.length; i++) {
        var tempdata = dicinfo[i];
        var str = tempdata.detail;
        var obj = (JSON.parse(str));
        var code = tempdata.code;
        code = code.substring(data_table.length + 1);
        columnInfo.push({
            id: dicinfo[i].id,
            code: code,
            value: obj.type,
            columnName: obj.ColumnName,
            canEdit: obj.canEdit,
            sort: dicinfo[i].sort,
            canSee: obj.canSee,
        });
        columnCanUse[code] = obj.canEdit === undefined ? true : obj.canEdit;
        columnSort[dicinfo[i].sort] = code;
        columnCanSee[code] = obj.canSee === undefined ? true : obj.canSee;
        columnType[code] = obj.type || "text";
    }

    $.ajax({
        url: _basic_operator_url + "/findForColumns",
        type: "post",
        dataType: "json",
        data: {},
        cache: false,
        async: false,
        success: function (data) {
            console.log(tableColumns);
            tableColumns.push({
                titleTooltip: "operator",
                checkbox: true,
                formatter: checkboxFormatter
            });
            for (var i in data) {
                let columnName = i;
                let find = false;
                for (let j = 0; j < columnInfo.length; j++) {
                    if (columnInfo[j].code == i) {
                        find = true;
                        columnName = columnInfo[j].columnName;
                        break;
                    }
                }
                if (!find) {
                    columnInfo.push({
                        id: null,
                        code: i,
                        value: undefined,
                        columnName: i,
                        canEdit: true,
                        sort: 999,
                        canSee: true,
                    });
                }
                if (columnCanSee[i] == true || columnCanSee[i] == undefined) {
                    tableColumns.push({
                        field: i,
                        title: columnName,
                        align: 'center',
                        valign: 'middle',
                        formatter: textFormatter,
                        sortable: true
                    })
                } else {

                }

            }
        },
        error: function (data) {
        }
    });

}

function tableSearch() {
    _table.bootstrapTable('refresh');
}


// function editColumns() {
//
//     var _editColumns = $("#editColumns");
//     // var _editColumns_body = _editColumns.find(".modal-body");
//     // var _button = _editColumns.find("button[for=editColumnsConfirm]");
//     // if (!editCloumnMode) {
//     //     var str = createEditColumnsForm(_table);
//     //     _editColumns_body.append(str);
//     //     editCloumnMode = true;
//     // }
//     // reRenderEditColumn(_editColumns_body);
//     _editColumns.modal("show");
//     // _button.off();
//     // _button.on({
//     //     click: function () {
//     //         var data = getEidtColumnData(_editColumns_body);
//     //         $.ajax({
//     //             url: '/xDictionary/changeFormBack',
//     //             type: "post",
//     //             dataType: "json",
//     //             contentType: "application/json",
//     //             data: JSON.stringify(data),
//     //             cache: false,
//     //             async: false,
//     //             success: function (data) {
//     //                 if (data.status == 1) {
//     //                     _editColumns.modal("hide");
//     //                     bootoast({
//     //                         message: data.info,
//     //                         type: 'success',
//     //                         position: 'top-right',
//     //                         timeout: 2
//     //                     });
//     //                     var timmer = setInterval(function () {
//     //                         clearInterval(timmer);
//     //                         window.location.reload();
//     //                     }, data.data)
//     //                     _button.off();
//     //                 }
//     //             },
//     //             error: function (data) {
//     //             }
//     //         });
//     //
//     //
//     //     }
//     // })
// }


function openSearch(self) {  // 可封
    var _self = $(self);
    _self.prop("disabled", true);
    _self.attr("onclick", "closeSearch(this)");
    _self.html("关闭搜索");
    var div = $("div[for=searchMode]");
    var form = div.find("form");
    if (!searchMode) {
        createSearchInfo(form);
        searchMode = true;
    }
    searchOpen = true;
    var opentimmer = setInterval(function () {
        _self.prop("disabled", false);
        clearInterval(opentimmer);
    }, 500)
    div.slideToggle(500);
}

function closeSearch(self) {  // 可封
    var _self = $(self);
    _self.prop("disabled", true);
    _self.attr("onclick", "openSearch(this)");
    _self.html("开启搜索");
    var div = $("div[for=searchMode]");
    searchOpen = false;
    var closetimmer = setInterval(function () {
        _self.prop("disabled", false);
        clearInterval(closetimmer);
    }, 500)
    div.slideToggle(500);
}

function createSearchInfo(form) {  // 可封
    var th = $(_table).find("thead").find("th");
    getSearchForm(th, form);
}


// function choseType(_this) {
//     _this = $(_this);
//     var buttons = _this.parent().find("button");
//     for (var i = 0; i < buttons.length; i++) {
//         var _button = $(buttons[i]);
//         _button.removeClass(columnInfoSelectBtnClass);
//         _button.attr("data-choose", "0");
//     }
//     _this.addClass(columnInfoSelectBtnClass);
//     _this.attr("data-choose", "1");
// }

//
// function rowEdit(e, uniqueId) {
//     e.stopPropagation();
//     var _editRecord = $("#editRecord");
//     if (!editMode) {
//         var tempForm = createEditForm(1);
//         editMode = true;
//         _editRecord.find(".modal-body").append(tempForm);
//     }
//     bindCropper("edit")
//
//     var _tr = $(e.currentTarget).parent().parent();
//     var index = $(_tr[0]).attr("data-index");
//     var dataToEdit = _table.bootstrapTable("getData")[index];
//     var tempid = dataToEdit['id'];
//     resetForm("edit_form");
//     for (var i in dataToEdit) {
//         var _input = $("form[name=edit_form]").find("input[name=" + i + "]");
//         _input.val(dataToEdit[i]);
//         if (dataToEdit[i] !== "" && dataToEdit[i] != undefined) {
//             _input.parent().find("label").addClass("active");
//         }
//     }
//
//     _editRecord.modal("show");
//     var _button = _editRecord.find("button[for=edit]");
//     _button.off();
//     _button.on({
//         click: function () {
//             var strArray = $("form[name=edit_form]").serializeArray();
//             strArray.push(
//                 {name: "id", value: uniqueId}
//             )
//             var JsonStr = JSON.stringify(serializeArrayToJson(strArray));
//             $.ajax({
//                 url: _basic_operator_url + '/modify',
//                 type: "post",
//                 dataType: "json",
//                 data: JsonStr,
//                 contentType: "application/json",
//                 cache: false,
//                 async: false,
//                 success: function (data) {
//                     if (data.status == 1) {
//                         bootoast({
//                             message: data.info,
//                             type: 'warning',
//                             position: 'top-right',
//                             timeout: 2
//                         });
//                         _button.off();
//                         _editRecord.modal("hide");
//                         _table.bootstrapTable("refresh");
//                     } else {
//
//                     }
//                 },
//                 error: function (data) {
//                 }
//             });
//         }
//     })
// }
//
// function addRecord() {
//     var _addRecord = $("#addRecord");
//     var th = $(_table).find("thead").find("th");
//     if (!appendMode) {
//         var tempForm = createAddRecordForm(th);
//
//         _addRecord.find(".modal-body").append(tempForm);
//         appendMode = true;
//     }
//     bindCropper("add");
//     resetForm("add_form");
//     _addRecord.modal("show");
//     var _button = _addRecord.find("button[for=add]");
//     _button.off();
//     _button.on({
//         click: function () {
//             var strArray = $("form[name=add_form]").serializeArray();
//             for (var i in columnType) {
//                 if (columnType[i] === "pic") {
//                     var photo = $("#add" + i + "pic").cropper('getCroppedCanvas', {
//                         width: 300,
//                         height: 300
//                     }).toDataURL('image/png');
//                     $.ajax({
//                         url: '/upload/uploadBase64', // 要上传的地址
//                         type: 'post',
//                         data: {
//                             'imgData': photo
//                         },
//                         dataType: 'json',
//                         async: false,
//                         success: function (data) {
//
//                             if (data.status == 1) {
//                                 strArray.push({
//                                     name: i,
//                                     value: data.data.imgUrl
//                                 })
//                                 // 将上传的头像的地址填入，为保证不载入缓存加个随机数
//                             }
//                         }
//                     });
//                 }
//             }
//             var JsonStr = JSON.stringify(serializeArrayToJson(strArray));
//             $.ajax({
//                 url: _basic_operator_url + '/insert',
//                 type: "post",
//                 dataType: "json",
//                 data: JsonStr,
//                 contentType: "application/json",
//                 cache: false,
//                 async: false,
//                 success: function (data) {
//                     if (data.status == 1) {
//                         bootoast({
//                             message: data.info,
//                             type: 'warning',
//                             position: 'top-right',
//                             timeout: 2
//                         });
//                         _addRecord.modal("hide");
//                         _button.off();
//                         _table.bootstrapTable("refresh");
//                     } else {
//
//                     }
//                 },
//                 error: function (data) {
//                 }
//             });
//
//         }
//     })
// }


function rowDelete(id) {  // 可封

    var id = id;
    var _confirmAgain_modal = $("#confirmAgain");
    _confirmAgain_modal.find(".modal-body").empty();
    _confirmAgain_modal.find(".modal-body").append("<p>" + deleteComfirmMessage_only + "</p>")
    _confirmAgain_modal.modal("show");
    var _button = _confirmAgain_modal.find("button[for=confirm]");
    _button.off();
    _button.on({
        click: function () {
            $.ajax({
                url: _basic_operator_url + '/delete',
                type: "post",
                dataType: "json",
                data: {
                    id: id
                },
                cache: false,
                async: false,
                success: function (data) {
                    if (data.status == 1) {
                        bootoast({
                            message: data.info,
                            type: 'warning',
                            position: 'top-right',
                            timeout: 2
                        });
                        _confirmAgain_modal.modal("hide");
                        _button.off();
                        _table.bootstrapTable("refresh");
                    }
                },
                error: function (data) {
                }
            });

        }
    })
}

function deleteChecked() { // 可封
    var checkboxes = _table.bootstrapTable('getSelections');
    if (checkboxes.length == 0) {
        bootoast({
            message: "请先选中要删除的行",
            type: 'warning',
            position: 'top-right',
            timeout: 2
        });
    } else {
        var _confirmAgain_modal = $("#confirmAgain");
        _confirmAgain_modal.find(".modal-body").append("<p>" + deleteComfirmMessage_batch + "</p>")
        _confirmAgain_modal.modal("show");
        var _button = _confirmAgain_modal.find("button[for=confirm]");
        _button.off();
        _button.on({
            click: function () {
                deleteBatch(checkboxes);
                _confirmAgain_modal.modal("hide");
                _button.off();
            }
        })
    }
}

function deleteBatch(checkboxes) {// 可封
    var ids = [];
    for (var i = 0, len = checkboxes.length; i < len; i++) {
        ids.push(checkboxes[i].id);
    }
    ids.join(",");
    $.ajax({
        url: _basic_operator_url + '/deleteQuery',
        type: "post",
        dataType: "json",
        data: {
            ids: ids
        },

        cache: false,
        async: false,
        success: function (data) {
            if (data.status == 1) {
                bootoast({
                    message: data.info,
                    type: 'warning',
                    position: 'top-right',
                    timeout: 2
                });
                _table.bootstrapTable("refresh");
            }
        },
        error: function (data) {
        }
    });
}

function resetForm(form_name) {
    var _form = $("form[name=" + form_name + "]");
    var _inputs = _form.find("input");
    for (var i = 0; i < _inputs.length; i++) {
        $(_inputs[i]).val("");
        $(_inputs[i]).parent().find("label").removeClass("active");
    }

}

function reRenderThead() {
    var thead = $(_table).find("thead");
    for (var i = 0; i < columnInfo.length; i++) {
        var _th = thead.find("th[data-field=" + columnInfo[i].code + "]");
        var _div = _th.find("div")[0];
        if (_div != undefined)
            _div.innerHTML = columnInfo[i].columnName;
    }
}

function serializeArrayToJson(str) {
    var result = {};
    for (var i = 0; i < str.length; i++) {
        var temp = str[i];
        result[temp['name']] = temp['value'];
    }
    return (result);
}

function humbToLine(s) {
    s = s + "";
    s = s.replace(/([A-Z])/g, "_$1").toLowerCase();
    return s;
}

function editColumnModeDragStart(_this) {
    var event = getEvent();
    event.dataTransfer.setData("old", $(event.currentTarget).attr("data-dragname"));
}

function editColumnModeDragover() {
    var event = getEvent();
    event.preventDefault();
}

function editColumnModeDrop() {
    var event = getEvent();
    var _this = $(event.currentTarget);
    event.preventDefault();
    var mousey = parseInt(event.clientY);
    var dropY = parseInt($(_this).offset().top) - parseInt($(document).scrollTop());
    var domHeight = parseFloat(_this[0].clientHeight);
    var ofss = (mousey - dropY);
    var old = event.dataTransfer.getData("old");
    old = $("#editColumnsTBody").find("tr[data-dragname=" + old + "][draggable=true]")[0];
    if (ofss < domHeight / 2) {
        _this.before(old);
    } else {
        _this.after(old);
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


function getXRoleNode(Rid) {
    var tempData;
    $.ajax({
        url: '/xRoleNode/find',
        type: "post",
        dataType: "json",
        data: {
            direction: "asc",
            limitSize: 10000,
            roleId: Rid,
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


function getColumns() {

    tableColumns.push({
        titleTooltip: "operator",
        field: 'operator',
        title: '操作',
        align: 'center',
        valign: 'middle',
        formatter: actionFormatter,
        width: 500
    });
    return tableColumns;
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

function getDataForDictionary(data) {
    let temp = [];
    for (let i = 0; i < data.length; i++) {
        temp.push({
            id: data[i].id,
            code: data_table + "_" + data[i].code,
            detail: JSON.stringify({
                ColumnName: data[i].columnName,
                canEdit: data[i].canEdit,
                canSee: data[i].canSee,
                type: data[i].value
            }),
            type: "column_type",
            sort: i,
        })
    }
    return temp;
}


function sortNodesData(temp_result) {
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


function getEvent() {
    var e = arguments.callee.caller.arguments[0] || window.event;
    return e;
}


//html操作区域
//搜索栏的html构建

function getSearchForm(th, form) {
    var tempCount = 0;
    var str = "";
    for (var i = 0, len = th.length; i < len; i++) {
        var temp = $(th[i]);
        if (temp.attr("title") == "operator") {

        } else {
            if (tempCount == 0) {
                str += "<div class='form-row mb-1'>" +
                    "<div class='md-form input-group mb-3'>";
            }
            var name = temp.find(".th-inner").html();
            var field_name = temp.attr("data-field");
            if (columnType[field_name] === "date") {
                str += "<div class='input-group-prepend'><span class='input-group-text md-addon'>" + name + "：</span></div>" +
                    "        <input type='text' autocomplete='off'  id='" + field_name + "Start' name='" + field_name + "Start' class='form-control pl-0 rounded-0' placeholder='" + name + "Start'>" +
                    "        <span class='input-group-text md-addon'>to</span>" +
                    "        <input type='text' autocomplete='off'  id='" + field_name + "End' name='" + field_name + "End' class='form-control pl-0 rounded-0'  placeholder='" + name + "End'>";
                tempCount++;
            } else {
                str += "<div class='input-group-prepend'>" +
                    "<span class='input-group-text md-addon'>" + name + "：</span>" +
                    "</div>" +
                    "<input type='text' name='" + field_name + "' class='form-control pl-0 rounded-0' id='" + field_name + "' placeholder='" + name + "' style='width: 0'>";
                tempCount++;
            }
            if (tempCount >= searchItemInLine) {
                str += "</div>" +
                    "</div>";
                tempCount = 0;
                form.append(str);
                str = "";
            }
        }
    }
    if (tempCount != 0) {
        str += "    </div>" +
            "</div>";
        form.append(str);
    }
    form.append("<button type='button' class='btn btn-default' onclick='tableSearch();'>" +
        "    <span class='fa fa-search' aria-hidden='true'></span> 查找" +
        "</button>");
    //initDatePciker();
}

//
// //列编辑的html构建
// function createEditColumnsForm(_table) {
//
//     var th = $(_table).find("thead").find("th");
//     var temp = undefined;
//     var tempi = 0;
//     var templen = 0;
//     if (columnInfo.length == 0) {
//         temp = th;
//         tempi = 1;
//         templen = 1;
//     } else {
//         temp = columnInfo;
//     }
//     console.log(temp);
//     var str = "<table class='table'><thead><tr>" +
//         "        <th scope='col'>列名</th><th scope='col'>别名</th><th scope='col'>列类型</th><th>能否编辑</th><th>是否显示</th>" +
//         "    </tr>" +
//         "    </thead>" +
//         "    <tbody id='editColumnsTBody'>";
//     for (var i = 0 + tempi, len = temp.length - templen; i < len; i++) {
//         var field_name = temp[i].code || $(temp[i]).attr("data-field");
//         str += "<tr draggable='false' data-dragname='" + field_name + "' ondragstart='editColumnModeDragStart()' ondragover='editColumnModeDragover()' ondrop='editColumnModeDrop()'>" +
//             "<td>" + field_name + "</td>" +
//             "<td>" +
//             "<div class='md-form mb-2 my-2' style='width: 100%'>" +
//             "<input type='text' name='" + field_name + "' class='form-control'>" +
//             "<label class=''>" + field_name + "</label>" +
//             "</div></td>" +
//             "<td>" +
//             "<button onclick='choseType(this)' for='editColumns' data-type='text' data-name='" + field_name + "' class='btn btn-sm waves-effect waves-light'" +
//             " style='border-radius: 50px'>text</button>" +
//             "<button onclick='choseType(this)' for='editColumns' data-type='file' data-name='" + field_name + "' class='btn btn-sm waves-effect waves-light'" +
//             " style='border-radius: 50px'>file</button>" +
//             "<button onclick='choseType(this)' for='editColumns' data-type='pic' data-name='" + field_name + "' class='btn btn-sm waves-effect waves-light'" +
//             " style='border-radius: 50px'>pic</button>" +
//             "<button onclick='choseType(this)' for='editColumns' data-type='date' data-name='" + field_name + "' class='btn btn-sm waves-effect waves-light'" +
//             " style='border-radius: 50px'>date</button>" +
//             "</td><td><input type='checkbox' name='" + field_name + "' data-name='canEdit'  id='canEdit" + field_name + "'><label for='canEdit" + field_name + "'></label></td>" +
//             "<td><input type='checkbox' checked='checked' name='" + field_name + "' data-name='canSee' id='canSee" + field_name + "'><label for='canSee" + field_name + "'></label></td>" +
//             "</tr>";
//
//     }
//     str += "</tbody></table>";
//     return str;
// }
//
//
// //重新渲染列编辑的选中情况
// function reRenderEditColumn(modal_body) {
//
//     for (var i = 0; i < columnInfo.length; i++) {
//         var _button = modal_body.find("button[data-name=" + columnInfo[i].code + "]");
//         modal_body.find("button[data-name=" + columnInfo[i].code + "][data-type=" + columnInfo[i].value + "]").addClass(columnInfoSelectBtnClass);
//         modal_body.find("button[data-name=" + columnInfo[i].code + "][data-type=" + columnInfo[i].value + "]").attr("data-choose", "1");
//         var _parent = _button.parent().parent();
//         _parent.attr("data-id", columnInfo[i].id);
//         $(_parent.find("input")[0]).val(columnInfo[i].columnName);
//         if (columnInfo[i].columnName !== "")
//             _parent.find("label").addClass("active");
//         _parent.find("input[type=checkbox][data-name=canEdit]").prop("checked", columnInfo[i].canEdit)
//         _parent.find("input[type=checkbox][data-name=canSee]").prop("checked", columnInfo[i].canSee)
//     }
// }

//信息修改表单的构建
function createEditForm(flag) {
    var tempForm = "";
    tempForm = "<form class='text-center border border-light p-3 ' name='edit_form' style='width: 100%'>";
    for (var i = 0, len = columnInfo.length; i < len; i++) {
        var field_name = columnInfo[i].code;
        var name = columnInfo[i].columnName;
        if (!columnCanSee[field_name] && flag == 1) {

        } else {
            if (columnCanUse[field_name]) {
                if (columnType[field_name] === "pic") {
                    tempForm += "<div class='form-row mb-1'>" + field_name + "：<label class='btn btn-default pull-left' for='photoInput' style='margin: 0;padding: 5px'>" +
                        "    <input type='file' class='sr-only' id='edit" + field_name + "picInput' accept='image/*'><span>选择图片</span></label>" +
                        "<div class='img-container hidden'>" +
                        "    <img src='' alt='' id='edit" + field_name + "pic' class='idphoto'>" +
                        "</div></div>"
                } else if (columnType[field_name] === "file") {
                    tempForm += "<div>file</div>"
                } else {
                    tempForm += "    <div class='form-row mb-1'><div class='md-form mb-2 my-2' style='width: 100%'>" +
                        " <input type='text' name='" + field_name + "' class='form-control'>" +
                        " <label class=''>" + name + "</label>" +
                        "</div></div>";
                }
            } else {

            }
        }
    }
    tempForm += "</form>"
    return tempForm;
}

//
// function createAddRecordForm(th) {
//     var tempForm = "<form class='text-center border border-light p-3 ' name='add_form' style='width: 100%'>";
//     console.log(columnCanSee);
//     for (var i = 0, len = th.length; i < len; i++) {
//         var temp = $(th[i]);
//         var name = temp.find(".th-inner").html();
//         var field_name = temp.attr("data-field");
//         console.log(field_name);
//         if (temp.attr("title") == "operator") {
//
//         } else {
//             console.log(columnCanUse['isShow']);
//             if (columnCanUse[field_name]) {
//
//                 if (columnType[field_name] === "pic") {
//                     tempForm += "<div class='form-row mb-1'>" + field_name + "：<label class='btn btn-default pull-left'  style='margin: 0;padding: 5px'>" +
//                         "    <input type='file' class='sr-only' id='add" + field_name + "picInput' accept='image/*'><span>选择图片</span></label>" +
//                         "<div class='img-container hidden'>" +
//                         "    <img src='' alt='' id='add" + field_name + "pic' class='idphoto'>" +
//                         "</div></div>"
//                 } else if (columnType[field_name] === "img") {
//                     tempForm += "<div>img</div>"
//                 } else {
//                     tempForm += "<div class='form-row mb-1'><div class='md-form mb-2 my-2' style='width: 100%'>" +
//                         "<input type='text' name='" + field_name + "' class='form-control'>" +
//                         "<label class=''>" + name + "</label>" +
//                         "</div></div>";
//                 }
//             } else {
//
//             }
//         }
//     }
//     tempForm += "</form>";
//     return tempForm;
// }

//查找栏的添加渲染（基本不动了）
function addSearchDiv() {
    $(".fixed-table-toolbar").after("<div class='searchMode' style='display: none'  for='searchMode'>" +
        "<form class='text-center border border-light p-3 ' name='search_form' style='width: 100%'></div>")
}


//html操作区域

function bindCropper(type) {
    for (var i in columnType) {
        if (columnType[i] === "pic") {
            initCropperInModal($("#" + type + i + "pic"), $("#" + type + i + "picInput"));
        }
    }
}

function Queue(size) {
    var list = [];

    //向队列中添加数据
    this.push = function (data) {
        if (data == null) {
            return false;
        }
        //如果传递了size参数就设置了队列的大小
        if (size != null && !isNaN(size)) {
            if (list.length == size) {
                this.pop();
            }
        }
        list.unshift(data);
        return true;
    }

    //从队列中取出数据
    this.pop = function () {
        return list.pop();
    }

    //返回队列的大小
    this.size = function () {
        return list.length;
    }

    //返回队列的内容
    this.quere = function () {
        return list;
    }
}

function initDatePciker() {
    for (var i in columnType) {
        if (columnType[i] === "date") {
            $("#" + i + "Start").datetimepicker({
                language: 'zh-CN',
                format: "yyyy-mm-dd hh:ii:ss",
                autoclose: true,
                startView: 2,
                weekStart: 1,
                todayBtn: 1,
                todayHighlight: 1,
                forceParse: 0,
                showMeridian: 1
            });
            $("#" + i + "End").datetimepicker({
                language: 'zh-CN',
                format: "yyyy-mm-dd hh:ii:ss",
                autoclose: true,
                startView: 2,
                weekStart: 1,
                todayBtn: 1,
                todayHighlight: 1,
                forceParse: 0,
                showMeridian: 1
            });
        }
    }
}


var initCropperInModal = function (img, input) {
    var $image = img;
    var $inputImage = input;
    var options = {
        aspectRatio: 1 / 1,// 默认比例
        preview: '.img-preview',// 预览视图
        guides: false, // 裁剪框的虚线(九宫格)
        autoCropArea: 0.5, // 0-1之间的数值，定义自动剪裁区域的大小，默认0.8
        movable: true, // 是否允许移动图片
        cropBoxResizable: true, // 是否允许改变裁剪框的大小
        mouseWheelZoom: true, // 是否允许通过鼠标滚轮来缩放图片
        touchDragZoom: true // 是否允许通过触摸移动来缩放图片
    };
    // 模态框隐藏后需要保存的数据对象
    var saveData = {};
    var URL = window.URL || window.webkitURL;
    var blobURL;
    $image.cropper($.extend(options, {
        ready: function () {
            // 当剪切界面就绪后，恢复数据
            if (saveData.canvasData) {
                $image.cropper('setCanvasData', saveData.canvasData);
                $image.cropper('setCropBoxData', saveData.cropBoxData);
            }
        }
    }));
    if (URL) {
        $inputImage.change(function () {
            var files = this.files;
            var file;
            if (!$image.data('cropper')) {
                return;
            }
            if (files && files.length) {
                file = files[0];
                if (/^image\/\w+$/.test(file.type)) {
                    if (blobURL) {
                        URL.revokeObjectURL(blobURL);
                    }
                    blobURL = URL.createObjectURL(file);

                    // 重置cropper，将图像替换
                    $image.cropper('reset').cropper('replace', blobURL);

                    // 选择文件后，显示和隐藏相关内容
                    $('.img-container').removeClass('hidden');
                    $('.img-preview-box').removeClass('hidden');
                    $('#changeModal .disabled').removeAttr('disabled').removeClass('disabled');
                    $('#changeModal .tip-info').addClass('hidden');

                } else {
                    window.alert('请选择一个图像文件！');
                }
            }
        });
    } else {
        $inputImage.prop('disabled', true).addClass('disabled');
    }
}