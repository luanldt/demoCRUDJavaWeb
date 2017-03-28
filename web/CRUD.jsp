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
                <tbody>
                    <!--                <tr>
                                            <td>1</td>
                                            <td>NGUYỄN VĂN CHÂU</td>
                                            <td>1 / 1 / 1989</td>
                                            <td>Nam</td>
                                            <td>01 Lê Thánh Tôn, Quận 1, HCM</td>
                                            <td><img width="120" height="120" src="static/images/Koala.jpg"></td>
                                            <td><a href="javascript: void(0);">Edit</a> | <a href="javascript: void(0);">Delete</a> </td>
                                        </tr>
                    -->

                    <c:forEach var="e" items="${listData}">
                        <tr>
                            <td><c:out value="${e.id}"></c:out></td>
                            <td><c:out value="${e.ho} ${e.ten}"></c:out></td>
                            <td><c:out value="${e.ngaySinh}"></c:out></td>
                            <td><c:out value="${e.gioiTinh}"></c:out></td>
                            <td><c:out value="${e.diaChiTamTru}"></c:out></td>
                            <td><img src="static/images/${e.hinhAnh}" width="120" height="120"/></td>
                            <td><a href="javascript: void(0);" class="edit" data-id="${e.id}" data-title="Cập nhật nhân viên">Edit</a> | <a href="javascript: void(0);" onclick="return confirm('Are you sure?')" data-id="${e.id}" class="delete">Delete</a></td>
                        </tr>
                    </c:forEach>

                </tbody>
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
                                        <input class="radio-inline" id="gioiTinhNam" name="gioiTinh" type="radio" value="Nam" />Nam
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
        <script>
            $(document).ready(function () {
                $("#tbNhanVien").DataTable();
                // Event for active edit or delete
                $(".add").click(function () {
                    var title = $(this).data('title');
                    $('.modal-title').text(title);
                    $("#crudModal").modal('show');
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
                            console.log(data);
                            console.log("Success");
                        },
                        error: function (data) {
                            console.log(data);
                            console.log("Fail");
                        }
                    });
                });
                $(".edit").click(function () {
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
                                $("#gioiTinh").val(data['gioiTinh']);   
                            },
                            error: function (data) {
                                alert("Khong the update " + data);
                            }
                        });
                        $("#crudModal").modal('show');
                    }
                });
                $(".delete").click(function () {
                    var id = $(this).data('id');
                    if (id) {
                        $.ajax({
                            url: "ProccessDelete",
                            type: "POST",
                            data: {id: id},
                            dataType: "json",
                            success: function (data) {
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
