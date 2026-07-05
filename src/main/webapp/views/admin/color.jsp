<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Màu Sắc - Admin Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link href="/styles/color.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Quản lý Màu sắc</h1>
    </header>

    <c:if test="${not empty errorMsg}"><div class="alert alert-error">${errorMsg}</div></c:if>
    <c:if test="${not empty successMsg}"><div class="alert alert-success">${successMsg}</div></c:if>

    <div class="crud-layout">

        <div class="card">
            <h2 class="card-title">Danh sách Màu sắc</h2>
            <table>
                <thead>
                <tr>
                    <th width="15%">ID</th>
                    <th width="35%">Màu hiển thị</th>
                    <th width="25%">Tên màu</th>
                    <th width="25%">Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="color" items="${colorList}">
                    <tr>
                        <td>#${color.id}</td>
                        <td>
                            <div class="color-dot" style="background-color: ${color.hexCode};"></div>
                            <span style="font-family: monospace; color: #666;">${color.hexCode}</span>
                        </td>
                        <td><strong>${color.name}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/colors?editId=${color.id}" class="btn-action btn-edit">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/color/delete?id=${color.id}" class="btn-action btn-delete" onclick="return confirm('Bạn có chắc chắn muốn xóa màu này?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty colorList}">
                    <tr><td colspan="4" style="text-align:center; padding: 30px;">Chưa có dữ liệu.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="card form-card">

            <c:choose>
                <c:when test="${not empty editColor}">
                    <h2 class="card-title">Cập nhật màu sắc</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/color/update" />
                </c:when>
                <c:otherwise>
                    <h2 class="card-title">Thêm màu sắc mới</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/color/add" />
                </c:otherwise>
            </c:choose>

            <form action="${actionUrl}" method="post">
                <c:if test="${not empty editColor}">
                    <input type="hidden" name="id" value="${editColor.id}">
                </c:if>

                <div class="form-group">
                    <label for="name">Tên màu (Hiển thị cho khách hàng)</label>
                    <input type="text" id="name" name="name" value="${editColor.name}" placeholder="Ví dụ: Đen Titanium..." required>
                </div>

                <div class="form-group">
                    <label for="hexCode">Chọn mã màu (Hex Code)</label>
                    <div class="color-input-wrapper">
                        <input type="color" id="colorPicker" value="${not empty editColor ? editColor.hexCode : '#000000'}">

                        <input type="text" id="hexCode" name="hexCode" value="${not empty editColor ? editColor.hexCode : '#000000'}" placeholder="#000000" maxlength="7" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-save">
                    ${not empty editColor ? 'Lưu thay đổi' : 'Thêm mới'}
                </button>

                <c:if test="${not empty editColor}">
                    <a href="${pageContext.request.contextPath}/admin/colors" class="btn btn-cancel">Hủy cập nhật</a>
                </c:if>
            </form>

        </div>
    </div>

</main>

<script>
    const colorPicker = document.getElementById('colorPicker');
    const hexCode = document.getElementById('hexCode');

    // Khi người dùng click chọn màu từ bảng màu -> Cập nhật Text Input
    colorPicker.addEventListener('input', function() {
        hexCode.value = this.value.toUpperCase();
    });

    // Khi người dùng gõ thẳng mã Hex vào Text Input -> Cập nhật Bảng màu
    hexCode.addEventListener('input', function() {
        let val = this.value;
        // Tự động thêm dấu # nếu người dùng quên gõ
        if(val.length > 0 && val[0] !== '#') {
            val = '#' + val;
            this.value = val;
        }
        // Nếu đủ 7 ký tự (VD: #FF0000) thì cập nhật bảng màu
        if(/^#[0-9A-Fa-f]{6}$/i.test(val)) {
            colorPicker.value = val;
        }
    });
</script>
</body>
</html>