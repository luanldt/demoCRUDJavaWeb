/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function remove(id) {
    if (!confirm("Bạn chắc chắn xóa?"))
        return;
    if (id) {
        $.ajax({
            url: "ProcessData",
            type: "POST",
            data: {id: id, action: "remove"},
            dataType: "json",
            success: function () {
                notify("Xóa thành công", "success");
            },
            error: function () {
                notify("Không thể xóa", "error");
            }
        });
    }
}

function edit(id) {
    if (id) {
        $.get('ProcessData', {id: id, action: "edit"})
                .then(function (data) {
                    if (data) {
                        var observableData = function (data) {
                            this.data = ko.observable(function () {
                                return ko.mapping.fromJSON(data);
                            });
                            this.save = function () {
                                console.log(ko.toJSON(this.data));
                                createOrUpdate();
                            };
                        };
                        ko.cleanNode($("#crudForm")[0]);
                        ko.applyBindings(new observableData(data), $("#crudForm")[0]);
                    } else {
                        alert("Không có nhân viên cần cập nhật.");
                    }
                });
        $("#crudModal").modal('show');
    }
}

function createOrUpdate() {
    if ($("#crudForm").valid()) {
        var formData = new FormData($('#crudForm')[0]);
        $.ajax({
            url: "ProcessData",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                //window.location.reload();

            },
            error: function (data) {
                console.log(data);
            }
        });
    }
}

function addRowInputDiploma() {
    var bangCapClone = $(this).parents('.bangCap').clone();
    bangCapClone.children('label').text("Bằng cấp - " + (parseInt($('.bangCap').size()) + 1));
    $(this).parents('.bangCap').after(bangCapClone);
}

function notify(messege, type) {
    $.notify(messege, type, {
        clickToHide: true,
        autoHide: true,
        autoHideDelay: 3000,
        globalPosition: 'top right'
    });
}

$(document).ready(function () {

    // Gan su kien cho input file 


    var tbl = $("#tbNhanVien").DataTable({
        processing: true,
        serverSide: false,
        ajax: 'ProcessData',
        rowId: 'id',
        columns: [
            {data: 'id'},
            {
                data: null,
                render: function (data, type, row) {
                    return data['ho'] + " " + data['ten'];
                }
            },
            {
                data: 'ngaySinh',
                defaultContent: ''
            },
            {
                data: 'gioiTinh',
                defaultContent: ''
            },
            {
                data: 'noiSinh',
                defaultContent: ''
            },
            {
                data: null,
                render: function (data) {
                    return ' <img src="static/images/' + data.hinhAnh + '" class="img-thumbnail" width="150"/>';
                },
                defaultContent: 'No images'
            },
            {
                data: null,
                defaultContent: '',
                render: function (data) {
                    return '<a href="javascript:void(0);" class="edit" ">Edit</a>' + ' | ' + '<a href="javascript:void(0);" onclick="remove(' + data.id + ')">Delete</a>';
                }
            }//onclick="edit(' + data.id + ')
        ]
    });

    function initViewModel() {
        var observableData = function () {
            var self = this;
            self.id = ko.observable('');
            self.maNhanVien = ko.observable('');
            self.ten = ko.observable('');
            self.ho = ko.observable('');
            self.noiSinh = ko.observable('');
            self.matKhau = ko.observable('');
            self.gioiTinh = ko.observable('1');
            self.ngaySinh = ko.observable('');
            self.diaChiThuongTru = ko.observable('');
            self.diaChiTamTru = ko.observable('');
            self.hinhAnh = ko.observable('');
        };
        return observableData;
    }

    var viewModel = function () {
        var self = this;
        self.id = ko.observable('');
        self.maNhanVien = ko.observable('');
        self.ten = ko.observable('');
        self.ho = ko.observable('');
        self.noiSinh = ko.observable('');
        self.matKhau = ko.observable('');
        self.gioiTinh = ko.observable(1);
        self.ngaySinh = ko.observable('');
        self.diaChiThuongTru = ko.observable('');
        self.diaChiTamTru = ko.observable('');
        self.hinhAnh = ko.observable('');
        self.hinhAnh.subscribe(function(imgUrl) {
             self.hinhAnh(imgUrl);
        });

        self.userdiplomas = ko.observableArray('');

        self.userdiploma = {
            tenBangCap: ko.observable(''),
            ngayCap: ko.observable(''),
            noiCap: ko.observable('')
        };
        self.save = function () {
            var json = ko.toJSON(self);
            console.log(json);
        };
        self.addDiploma = function () {
            self.userdiplomas.push(self.userdiploma);
            console.log(self.userdiplomas());
        };
        self.setHinhAnh = function (imgUrl) {
            self.hinhAnh(imgUrl);
        };
    };

    function _bindDataModel(dt) {
        var observableData = function (dt) {
            this.dt = ko.observable(function () {
                return dt;
            });
            this.save = function () {
                if ($("#crudForm").valid()) {
                    createOrUpdate();
                }
            };
        };
        ko.cleanNode($("#crudForm")[0]);
        ko.applyBindings(new observableData(dt), $("#crudForm")[0]);
    }



    // SU KIEN 
    $(".add").click(function () {
        $('.modal-title').text("Thêm nhân viên");
        ko.cleanNode($("#crudForm")[0]);
        ko.applyBindings(new viewModel(), $("#crudForm")[0]);
        $("#crudModal").modal('show');
    });

    $("#crudModal").on('shown.bs.modal', function () {
        $('input[type="file"]').on('change', function () {
            var formData = new FormData();
            formData.append('images', $(this)[0].files[0]);
            $.post({
                url: "ProcessFileUpload",
                data: formData,
                contentType: false,
                processData: false
            }).then(function (data) {
                var model = new viewModel();
                model.hinhAnh(data);
          
            });
        });
    });

    $("#crudForm").submit(function (e) {
        e.preventDefault();
        createOrUpdate();
    });

//    $("#tbNhanVien").on('click', '.edit', function () {
//        tbl = $("#tbNhanVien").DataTable();
//        var dt = tbl.row().data();
//        if (dt !== undefined) {
//            _bindData(dt);
//            $("#crudModal").modal('show');
//        }
//    });



    // VALIDATE FORM 
    $.validator.addMethod(
            "ngaySinh",
            function (value, element) {
                return value.match("^\\d\\d\\d\\d\/\\d\\d?\/\\d\\d?$");
            }
    );

    $("#crudForm").validate({
        rules: {
            maNhanVien: {
                required: true
            },
            ten: "required",
            ho: "required",
            matKhau: {
                required: true,
                minlength: 6
            },
            tenBangCap: "required",
            noiSinh: "required",
            ngaySinh: {
                required: true,
                ngaySinh: true
            },
            noiCap: "required"
        },
        messages: {
            maNhanVien: "Vui lòng nhập mã nhân viên",
            ten: "Vui lòng nhập tên nhân viên.",
            ho: "Vui lòng nhập họ nhân viên.",
            matKhau: {
                required: "Vui lòng nhập mật khẩu.",
                minlength: "Vui lòng nhập mật khẩu dài hơn 6 ký tự."
            },
            tenBangCap: "Vui lòng nhập tên bằng cấp.",
            noiSinh: "Vui lòng nơi sinh nhân viên.",
            ngaySinh: {
                required: "Vui lòng nhập ngày sinh nhân viên.",
                ngaySinh: "Vui lòng nhập ngày sinh đúng dịnh dạng yyyy/mm/dd"
            },
            noiCap: "Vui lòng nhập nơi cấp bằng."
        },
        debug: true
    });

});