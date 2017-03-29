<%-- 
    Document   : CRUD
    Created on : Mar 27, 2017, 1:25:08 PM
    Author     : cod.f
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CRUD User Application</title>

        <link href="static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="static/css/jquery.dataTables.min.css" />
        <link rel="stylesheet" href="static/css/dataTables.editor.css" />    
        <link href="static/css/select.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link href="static/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="static/css/editor.bootstrap.min.css" />    
    </head>

    <body>
        <div id="wrapper">
            <h2 class="text-center h2">Danh sách nhân viên</h2>            

            <!--            <a href="javascript: void(0);" class="btn btn-info">Thêm nhân viên</a>-->
            <table class="table table-hover" id="tbNhanVien">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Họ tên</th>
                        <th>Ngày sinh</th>
                        <th>Giới tính</th>
                        <th>Địa chỉ</th>
                        <th>Hình ảnh</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <%-- <tbody>
                    <c:forEach var="e" items="${listData}">
                        <tr>
                            <td><c:out value="${e.id}"></c:out></td>
                            <td><c:out value="${e.ho} ${e.ten}"></c:out></td>
                            <td><c:out value="${e.ngaySinh}"></c:out></td>
                            <td><c:out value="${e.gioiTinh}"></c:out></td>
                            <td><c:out value="${e.diaChiTamTru}"></c:out></td>
                            <td><img src="static/images/${e.hinhAnh}" width="120" height="120"/></td>
                            <td><a href="javascript: void(0);" class="edit" data-id="${e.id}" data-title="Cập nhật nhân viên">Edit</a> | <a href="javascript: void(0);" data-id="${e.id}" class="delete">Delete</a></td>
                        </tr>
                    </c:forEach>
                </tbody> --%>
            </table>
            <a href="javascript:void(0);" data-title="Thêm nhân viên" class="btn btn-info glyphicon glyphicon-plus add">Thêm nhân viên</a>
            <!-- Modal for create and edit -->
            <!-- Modal -->
            <div id="crudModal" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <form class="form-horizontal" id="crudForm" method="POST" enctype="multipart/form-data">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Modal Header</h4>
                            </div>
                            <div class="modal-body">

                                <div class="form-group">
                                    <label class="control-label col-md-4" for="maNhanVien">Mã nhân viên</label>
                                    <div class="col-md-8">
                                        <input class="form-control" id="maNhanVien" name="maNhanVien" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="hoNhanVien">Họ tên nhân viên</label>
                                    <div class="col-md-4">
                                        <input class="form-control" id="hoNhanVien" placeholder="Họ" name="ho" />
                                    </div>
                                    <div class="col-md-4">
                                        <input class="form-control" id="tenNhanVien" placeholder="Tên" name="ten" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="matKhau">Mật khẩu</label>
                                    <div class="col-md-8">
                                        <input class="form-control" type="password" id="matKhau" name="matKhau" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="gioiTinhNam">Giới tính</label>
                                    <div class="col-md-8">
                                        <input class="radio-inline" checked="true" id="gioiTinhNam" name="gioiTinh" type="radio" value="Nam" />Nam
                                        <input class="radio-inline" id="gioiTinhNu" name="gioiTinh" type="radio" value="Nữ"/>Nữ
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="ngaySinh">Ngày sinh</label>
                                    <div class="col-md-8">
                                        <input class="form-control" type="date" id="ngaySinh" name="ngaySinh" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="noiSinh">Nơi sinh</label>
                                    <div class="col-md-8">
                                        <input class="form-control" type="text" id="noiSinh" name="noiSinh" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="diaChiThuongTru">Địa chỉ thường trú</label>
                                    <div class="col-md-8">
                                        <input class="form-control" type="text" id="diaChiThuongTru" name="diaChiThuongTru" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="diaChiTamTru">Địa chỉ tạm trú</label>
                                    <div class="col-md-8">
                                        <input class="form-control" type="text" id="diaChiTamTru" name="diaChiTamTru" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="hinhAnh">Hình ảnh</label>
                                    <div class="col-md-8">
                                        <input type="file" name="hinhAnh" id="hinhAnh" accept="image/*" />
                                    </div>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-success">Lưu thông tin</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
        <script src="static/js/jquery-2.2.4.min.js"></script>
        <script src="static/js/bootstrap.min.js"></script>
        <script src="static/js/jquery.dataTables.min.js"></script>
        <script src="static/js/dataTables.tableTools.min.js"></script>
        <script src="static/js/dataTables.buttons.min.js"></script>
        <script src="static/js/dataTables.select.min.js"></script>
        <script src="static/js/dataTables.editor.min.js"></script>
        <script src="static/js/editor.bootstrap.min.js" type="text/javascript"></script>

        <script>
            $(document).ready(function () {
                var isUpdate = false;
                var editor;
                editor = new $.fn.dataTable.Editor({
                    ajax: {url: 'ProcessData', method: "post"},
                    table: '#tbNhanVien',
                    idSrc: 'id',
                    fields: [
                        {
                            label: "Mã nhân viên",
                            name: "maNhanVien"
                        },
                        {
                            label: "Họ nhân viên",
                            name: "ho"
                        },
                        {
                            label: "Tên nhân viên",
                            name: "ten"
                        },
                        {
                            label: "Mật khẩu",
                            name: "matKhau",
                            type: "password"
                        },
                        {
                            label: "Giới tính",
                            name: "gioiTinh",
                            type: "radio",
                            options: [
                                {label: "Nam", value: 1},
                                {label: "Nữ", value: 0}
                            ]
                        },
                        {
                            label: "Ngày sinh",
                            name: "ngaySinh"
                        },
                        {
                            label: "Nơi sinh",
                            name: "noiSinh"
                        },
                        {
                            label: "Địa chỉ thường trú",
                            name: "diaChiThuongTru"
                        },
                        {
                            label: "Địa chỉ tạm trú",
                            name: "diaChiTamTru"
                        },
                        {
                            label: "Image:",
                            name: "image",
                            type: "upload",
                            display: function (id) {
                                return '<img src="' + editor.file('images', id).webPath + '"/>';
                            },
                            noImageText: 'No image'
                        }
                    ]
                });
                var table = $("#tbNhanVien").DataTable({
                    dom: "Bfrtip",
                    processing: true,
                    serverSide: true,
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
                        {data: 'ngaySinh'},
                        {data: 'gioiTinh'},
                        {data: 'noiSinh'},
                        {data: 'hinhAnh'}
                    ],
                    select: true,
                    buttons: [
                        {extend: "create", editor: editor},
                        {extend: "edit", editor: editor},
                        {extend: "remove", editor: editor}
                    ]
                });

               
                
                // Event for active edit or delete
                $(".add").click(function () {
                    var title = $(this).data('title');
                    $('.modal-title').text(title);
                    $("#crudModal").modal('show');
                    isUpdate = false;
                });
                $("#crudForm").submit(function (e) {
                    e.preventDefault();
                    var formData = new FormData($(this)[0]);
                    $.ajax({
                        url: "ProccessCreate",
                        type: "POST",
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (data) {
                            alert((isUpdate ? "Cập nhật " : "Thêm") + "thành công!");
                            window.location.reload();

                        },
                        error: function (data) {
                            console.log(data);
                            console.log("Fail");
                        }
                    });
                });
                $(".edit").click(function () {
                    isUpdate = true;
                    var title = $(this).data('title');
                    var id = $(this).data('id');
                    if (id) {
                        $('.modal-title').text(title);
                        $.ajax({
                            url: "ProccessUpdate",
                            type: "POST",
                            data: {id: id},
                            dataType: "json",
                            success: function (data) {
                                console.log(data);
                                $("#crudForm").append("<input type='hidden' value='" + data["id"] + "' name='id' /> ");
                                $("#diaChiTamTru").val(data['diaChiTamTru']);
                                $("#diaChiThuongTru").val(data['diaChiThuongTru']);
                                $("#noiSinh").val(data['noiSinh']);
                                $("#matKhau").val(data['matKhau']);
                                $("#hoNhanVien").val(data['ho']);
                                $("#tenNhanVien").val(data['ten']);
                                $("#maNhanVien").val(data['maNhanVien']);
                                $("#ngaySinh").val(data['ngaySinh']);
                                if (data['gioiTinh'] === 1) {
                                    $("#gioiTinhNam").attr('checked', 'true');
                                } else {
                                    $("#gioiTinhNu").attr('checked', 'true');
                                }

                            },
                            error: function (data) {
                                alert("Khong the update " + data);
                            }
                        });
                        $("#crudModal").modal('show');
                    }
                });
                $(".delete").click(function () {
                    if (!confirm("Bạn chắc chắn xóa?"))
                        return;
                    var id = $(this).data('id');
                    if (id) {
                        $.ajax({
                            url: "ProccessDelete",
                            type: "POST",
                            data: {id: id},
                            dataType: "json",
                            success: function (data) {
                                alert("Xóa thành công!")
                                window.location.reload(true);
                            },
                            error: function (data) {
                                console.log(data);
                                alert("Khong the delete ");
                            }
                        });
                    }
                });
            });
        </script>
    </body>

</html>
