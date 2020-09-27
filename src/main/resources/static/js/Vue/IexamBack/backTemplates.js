Vue.config.devtools = true;

let bus = new Vue();
let edit_row = {
    template: "#edit_row",
    data() {
        return {
            columninfo: columnInfo,
            test: 123

        }
    },
    methods: {
        choseType(index, type) {
            this.columninfo[index].value = type;
        },
        editRowConfirm() {
            let data = this.columninfo;
            let data1 = getDataForDictionary(data);

            console.log(JSON.stringify(data1));
            $.ajax({
                url: '/xDictionary/changeFormBack',
                type: "post",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(data1),
                cache: false,
                async: false,
                success: function (data) {
                    if (data.status == 1) {
                        // _editColumns.modal("hide");
                        bootoast({
                            message: data.info,
                            type: 'success',
                            position: 'top-right',
                            timeout: 2
                        });
                        var timmer = setInterval(function () {
                            clearInterval(timmer);
                            window.location.reload();
                        }, data.data)

                    }
                },
                error: function (data) {
                }
            });
        }
    },
    watch: {
        columninfo: {
            handler(value, oldValue) {

            },
            deep: true
        }
    }
}

let add_record = {
    template: "#add_record",
    data() {
        return {
            columninfo: columnInfo,
            FormData: {}
        }
    }, methods: {
        BindAddRows() {
            console.log(columnInfo.length);
            let data = {};
            for (let i = 0; i < columnInfo.length; i++) {
                console.log(columnInfo[i].canEdit);
                if (columnInfo[i].canEdit != false) {
                    data[columnInfo[i].code] = "";
                }
            }
            return data;
        },
        addRecord() {
            let JsonStr = (JSON.stringify(this.FormData));
            $.ajax({
                url: _basic_operator_url + '/insert',
                type: "post",
                dataType: "json",
                data: JsonStr,
                contentType: "application/json",
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
                        $("#addRecord").modal('hide');
                        _table.bootstrapTable("refresh");
                    } else {

                    }
                },
                error: function (data) {
                }
            });
        }

    },
    mounted() {
        this.FormData = this.BindAddRows();
    }

}

let edit_record = {
    template: "#edit_record",
    data() {
        return {
            columninfo: columnInfo,
            edit_data: {}
        }
    },
    methods: {

        confirm() {
            let JsonStr = JSON.stringify(this.edit_data);
            $.ajax({
                url: _basic_operator_url + '/modify',
                type: "post",
                dataType: "json",
                data: JsonStr,
                contentType: "application/json",
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
                    } else {

                    }
                },
                error: function (data) {
                }
            });
        }
    },
    created() {
        let _this = this;
        bus.$on('edit_row_info', (data) => {
            _this.edit_data = data;
        })
    }
}

let show_record = {
    template: "#show_record",
    data() {
        return {
            columninfo: columnInfo,
            row_info: {}
        }
    },
    methods: {},
    created() {
        let _this = this;
        bus.$on('show_row_info', (data) => {
            _this.row_info = data;
        })
    }
}

