<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Màu Sắc - Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/color.css" rel="stylesheet">
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
                            <div class="color-dot" style="background-color:${color.hex};"></div>
                            <span style="margin-left:8px;">${color.hex}</span>
                        </td>
                        <td><strong>${color.name}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/colors?editId=${color.id}" class="btn-action btn-edit">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/colors/delete?id=${color.id}" class="btn-action btn-delete" onclick="return confirm('Xóa màu này?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty colorList}">
                    <tr><td colspan="4" style="text-align:center;">Chưa có dữ liệu.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="card form-card">

            <c:choose>
                <c:when test="${not empty editColor}">
                    <h2 class="card-title">Cập nhật màu sắc</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/colors/add" />
                </c:when>
                <c:otherwise>
                    <h2 class="card-title">Thêm màu sắc mới</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/colors/add" />
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
                    <label for="hex">Chọn mã màu (Hex Code)</label>
                    <div class="color-input-wrapper">
                        <input type="color" id="colorPicker" value="${not empty editColor ? editColor.hex : '#000000'}">
                        <input type="text" id="hex" name="hex" value="${not empty editColor ? editColor.hex : '#000000'}" placeholder="#000000" required>
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
    const hexInput = document.getElementById('hex');

    colorPicker.addEventListener('input', function () {
        hexInput.value = colorPicker.value;
    });

    hexInput.addEventListener('input', function () {
        if (/^#[0-9A-Fa-f]{6}$/.test(hexInput.value)) {
            colorPicker.value = hexInput.value;
        }
    });
</script>

</body>
</html>