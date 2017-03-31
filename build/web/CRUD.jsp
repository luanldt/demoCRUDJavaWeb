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
        <meta charset="UTF-8">
        <title>CRUD User Application</title>

        <link href="static/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="static/css/jquery.dataTables.min.css" />
        <link rel="stylesheet" href="static/css/dataTables.editor.css" />    
        <link href="static/css/select.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link href="static/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link href="static/css/knockout-file-bindings.css" rel="stylesheet" type="text/css"/>
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
                        <th>Thao tác</th>
                    </tr>
                </thead>
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
                                <h4 class="modal-title"></h4>
                            </div>
                            <div class="modal-body">
                                <input type="hidden" name="id" data-bind="value: id" />
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="maNhanVien">Mã nhân viên</label>
                                    <div class="col-md-8">
                                        <input data-bind="value: maNhanVien" class="form-control" id="maNhanVien" name="maNhanVien" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="hoNhanVien">Họ tên nhân viên</label>
                                    <div class="col-md-4">
                                        <input class="form-control"  data-bind="value: ho" id="hoNhanVien" placeholder="Họ" name="ho" />
                                    </div>
                                    <div class="col-md-4">
                                        <input class="form-control" data-bind="value: ten" id="tenNhanVien" placeholder="Tên" name="ten" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="matKhau">Mật khẩu</label>
                                    <div class="col-md-8">
                                        <input class="form-control"  data-bind="value: matKhau" type="password" id="matKhau" name="matKhau" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="gioiTinhNam">Giới tính</label>
                                    <div class="col-md-8">
                                        <label><input class="radio-inline" checked="true" id="gioiTinhNam" name="gioiTinh" type="radio" data-bind="checked: gioiTinh, value: 1" />Nam</label>
                                        <label><input class="radio-inline" id="gioiTinhNu" name="gioiTinh" type="radio" data-bind="checked: gioiTinh, value: 0"/>Nữ</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="ngaySinh">Ngày sinh</label>
                                    <div class="col-md-8">
                                        <input class="form-control" data-bind="value: ngaySinh" id="ngaySinh" name="ngaySinh" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="noiSinh">Nơi sinh</label>
                                    <div class="col-md-8">
                                        <input class="form-control" data-bind="value: noiSinh" type="text" id="noiSinh" name="noiSinh" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="diaChiThuongTru">Địa chỉ thường trú</label>
                                    <div class="col-md-8">
                                        <input class="form-control" data-bind="value: diaChiThuongTru" type="text" id="diaChiThuongTru" name="diaChiThuongTru" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-4" for="diaChiTamTru">Địa chỉ tạm trú</label>
                                    <div class="col-md-8">
                                        <input class="form-control" data-bind="value: diaChiTamTru" type="text" id="diaChiTamTru" name="diaChiTamTru" />
                                    </div>
                                </div>
                                <div class="form-group bangCap" data-bind="with: userdiploma">
                                    <label class="control-label col-md-4">Bằng cấp</label>
                                    <div class="col-md-8">
                                        <p><input class="form-control" data-bind="value: tenBangCap" name="tenBangCap" placeholder="Tên bằng cấp"/></p>
                                        <p><input class="form-control" data-bind="value: ngayCap" name="ngayCap" placeholder="Ngày cấp"/></p>
                                        <p><input class="form-control" data-bind="value: noiCap" name="noiCap" placeholder="Nơi cấp"/></p>
                                        <button type="button" class="addBangCap" data-bind="click: $root.addDiploma">+</button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-md-4" for="hinhAnh">Hình ảnh</label>
                                    <div class="col-md-8">
                                        <input type="file" name="hinhAnh" id="hinhAnh" accept="image/*" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-8 col-md-offset-4">
                                        <img class="img-thumbnail" id="imgView" data-bind="visible: hinhAnh, attr: { src : 'static/images/'+hinhAnh() }" />
                                        <!--                                        <img class="img-thumbnail" id="imgView" data-bind="visible: hinhAnh(), attr: { src: 'static/images/'+hinhAnh() }"/>-->
                                    </div>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-success" data-bind="click: save">Lưu thông tin</button>
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
        <script src="static/js/knockout-3.4.2.js" type="text/javascript"></script>
        <script src="static/js/knockout.mapping-latest.js" type="text/javascript"></script>
        <script src="static/js/knockout-file-bindings.js" type="text/javascript"></script>
        <script src="static/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="static/js/notify.min.js" type="text/javascript"></script>
        <script src="static/js/CRUD.js" type="text/javascript"></script>
    </body>

</html>
