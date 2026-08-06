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

        <!-- Danh sách Màu sắc -->
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

        <!-- Form Thêm mới -->
        <div class="card form-card">
            <h2 class="card-title">Thêm màu sắc mới</h2>

            <form action="${pageContext.request.contextPath}/admin/colors/add" method="post">

                <div class="form-group">
                    <label for="name">Tên màu (Hiển thị cho khách hàng)</label>
                    <input type="text" id="name" name="name" placeholder="Ví dụ: Đen Titanium..." required>
                </div>

                <div class="form-group">
                    <label for="hex">Chọn mã màu (Hex Code)</label>
                    <div class="color-input-wrapper">
                        <input type="color" id="colorPicker" value="#000000">
                        <input type="text" id="hex" name="hex" value="#000000" placeholder="#000000" maxlength="7" required>
                    </div>
                </div>

                <button type="submit" class="btn btn-save">Thêm mới</button>

            </form>
        </div>

    </div>
</main>

<script>
    const colorPicker = document.getElementById('colorPicker');
    const hexInput = document.getElementById('hex');

    // Cập nhật text input khi chọn màu từ Color Picker
    colorPicker.addEventListener('input', function () {
        hexInput.value = colorPicker.value.toUpperCase();
    });

    // Cập nhật Color Picker khi nhập, paste hoặc thả mã HEX vào text input
    function syncColorPicker() {
        let hexVal = hexInput.value.trim();

        // Tự động thêm dấu # nếu người dùng quên dán vào (ví dụ: dán "FF0000" -> "#FF0000")
        if (hexVal !== '' && !hexVal.startsWith('#')) {
            hexVal = '#' + hexVal;
            hexInput.value = hexVal;
        }

        // Trường hợp mã dạng ngắn #RGB -> chuyển thành #RRGGBB cho thẻ input[type="color"] hiểu
        if (/^#[0-9A-Fa-f]{3}$/.test(hexVal)) {
            hexVal = '#' + hexVal[1] + hexVal[1] + hexVal[2] + hexVal[2] + hexVal[3] + hexVal[3];
        }

        // Kiểm tra đúng định dạng Hex 6 ký tự (#RRGGBB) thì đổi màu ô xem trước
        if (/^#[0-9A-Fa-f]{6}$/.test(hexVal)) {
            colorPicker.value = hexVal;
        }
    }

    // Lắng nghe cả các sự kiện input, change và paste
    hexInput.addEventListener('input', syncColorPicker);
    hexInput.addEventListener('paste', function () {
        // Chờ dữ liệu dán hoàn tất rồi mới đồng bộ
        setTimeout(syncColorPicker, 10);
    });
</script>

</body>
</html>