<%-- 
    Document   : CRUD
    Created on : Mar 27, 2017, 1:25:08 PM
    Author     : cod.f
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
                    <tr>
                        <td>1</td>
                        <td>NGUYỄN VĂN CHÂU</td>
                        <td>1 / 1 / 1989</td>
                        <td>Nam</td>
                        <td>01 Lê Thánh Tôn, Quận 1, HCM</td>
                        <td><img width="120" height="120" src="static/images/Koala.jpg"></td>
                        <td><a href="javascript: void(0);">Edit</a> | <a href="javascript: void(0);">Delete</a> </td>
                    </tr>
                </tbody>
            </table>

        </div>
        <script src="static/js/jquery-2.2.4.min.js"></script>
        <script src="static/js/jquery.dataTables.min.js"></script>
        <script>
            $(document).ready(function() {
                $("#tbNhanVien").DataTable();
            });
        </script>
    </body>

</html>
