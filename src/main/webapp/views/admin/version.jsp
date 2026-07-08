<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Phiên bản - Admin Atelier Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">

   <link href="${pageContext.request.contextPath}/styles/version.css" rel="stylesheet" >
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Quản lý Phiên bản</h1>
    </header>

    <c:if test="${not empty errorMsg}"><div class="alert alert-error">${errorMsg}</div></c:if>
    <c:if test="${not empty successMsg}"><div class="alert alert-success">${successMsg}</div></c:if>

    <div class="crud-layout">

        <div class="card">
            <h2 class="card-title">Danh sách hiện tại</h2>
            <table>
                <thead>
                <tr>
                    <th width="15%">ID</th>
                    <th width="60%">Tên phiên bản</th>
                    <th width="25%">Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ver" items="${versionList}">
                    <tr>
                        <td>#${ver.id}</td>
                        <td><strong>${ver.name}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/versions?editId=${ver.id}" class="btn-action btn-edit">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/version/delete?id=${ver.id}" class="btn-action btn-delete" onclick="return confirm('Bạn có chắc chắn muốn xóa phiên bản này?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty versionList}">
                    <tr><td colspan="3" style="text-align:center; padding: 30px; color: #888;">Chưa có dữ liệu phiên bản.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="card form-card">

            <c:choose>
                <c:when test="${not empty editVersion}">
                    <h2 class="card-title">Cập nhật phiên bản</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/version/update" />
                </c:when>
                <c:otherwise>
                    <h2 class="card-title">Thêm phiên bản mới</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/version/add" />
                </c:otherwise>
            </c:choose>

            <form action="${actionUrl}" method="post">
                <c:if test="${not empty editVersion}">
                    <input type="hidden" name="id" value="${editVersion.id}">
                </c:if>

                <div class="form-group">
                    <label for="name">Tên phiên bản</label>
                    <input type="text" id="name" name="name" value="${editVersion.name}" placeholder="Ví dụ: 256GB, Bản Quốc tế..." required>
                </div>

                <button type="submit" class="btn btn-save">
                    ${not empty editVersion ? 'Lưu thay đổi' : 'Thêm mới'}
                </button>

                <c:if test="${not empty editVersion}">
                    <a href="${pageContext.request.contextPath}/admin/versions" class="btn btn-cancel">Hủy cập nhật</a>
                </c:if>
            </form>

        </div>
    </div>

</main>
</body>
</html>