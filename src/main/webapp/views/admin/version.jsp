<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Phiên bản - Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/color.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Quản lý Phiên bản</h1>
    </header>

    <div class="crud-layout">

        <div class="card">
            <h2 class="card-title">Danh sách Phiên bản</h2>
            <table>
                <thead>
                <tr>
                    <th width="15%">ID</th>
                    <th width="55%">Tên phiên bản</th>
                    <th width="30%">Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="version" items="${versions}">
                    <tr>
                        <td>#${version.id}</td>
                        <td><strong>${version.name}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/version/delete?id=${version.id}" class="btn-action btn-delete" onclick="return confirm('Xóa phiên bản này?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty versions}">
                    <tr><td colspan="3" style="text-align:center;">Chưa có dữ liệu.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="card form-card">
            <c:choose>
                <c:when test="${not empty editVersion}">
                    <h2 class="card-title">Cập nhật phiên bản</h2>
                </c:when>
                <c:otherwise>
                    <h2 class="card-title">Thêm phiên bản mới</h2>
                </c:otherwise>
            </c:choose>

            <form action="${pageContext.request.contextPath}/admin/version/add" method="post">

                <c:if test="${not empty editVersion}">
                    <input type="hidden" name="id" value="${editVersion.id}">
                </c:if>

                <div class="form-group">
                    <label for="name">Tên phiên bản</label>
                    <input type="text" id="name" name="name" value="${editVersion.name}" placeholder="Ví dụ: 128GB, 256GB..." required>
                </div>

                <button type="submit" class="btn btn-save">
                    ${not empty editVersion ? 'Lưu thay đổi' : 'Thêm mới'}
                </button>

                <c:if test="${not empty editVersion}">
                    <a href="${pageContext.request.contextPath}/admin/version" class="btn btn-cancel">Hủy cập nhật</a>
                </c:if>

            </form>
        </div>

    </div>
</main>

</body>
</html>