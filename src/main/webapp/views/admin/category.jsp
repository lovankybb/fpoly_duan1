<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Danh mục - Admin Atelier Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="/styles/category.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Quản lý Danh mục</h1>
    </header>

    <c:if test="${not empty errorMsg}"><div class="alert alert-error">${errorMsg}</div></c:if>
    <c:if test="${not empty successMsg}"><div class="alert alert-success">${successMsg}</div></c:if>

    <div class="crud-layout">

        <div class="card">
            <h2 class="card-title">Danh sách hiện tại</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên danh mục</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cat" items="${categoryList}">
                    <tr>
                        <td>#${cat.id}</td>
                        <td><strong>${cat.name}</strong></td>
                        <td>
                            <c:choose>
                                <c:when test="${cat.status == 1}"><span class="badge active">Đang hiển thị</span></c:when>
                                <c:otherwise><span class="badge hidden">Đang ẩn</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/categories?editId=${cat.id}" class="btn-action btn-edit">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/category/delete?id=${cat.id}" class="btn-action btn-delete" onclick="return confirm('Bạn có chắc chắn muốn xóa?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty categoryList}">
                    <tr><td colspan="4" style="text-align:center; padding: 30px;">Chưa có dữ liệu danh mục.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="card form-card">

            <c:choose>
                <c:when test="${not empty editCategory}">
                    <h2 class="card-title">Cập nhật danh mục</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/category/update" />
                </c:when>
                <c:otherwise>
                    <h2 class="card-title">Thêm danh mục mới</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/category/add" />
                </c:otherwise>
            </c:choose>

            <form action="${actionUrl}" method="post">
                <c:if test="${not empty editCategory}">
                    <input type="hidden" name="id" value="${editCategory.id}">
                </c:if>

                <div class="form-group">
                    <label for="name">Tên danh mục</label>
                    <input type="text" id="name" name="name" value="${editCategory.name}" required>
                </div>

                <div class="form-group">
                    <label for="status">Trạng thái</label>
                    <select id="status" name="status">
                        <option value="1" ${editCategory.status == 1 ? 'selected' : ''}>Đang hoạt động</option>
                        <option value="0" ${editCategory.status == 0 ? 'selected' : ''}>Tạm ẩn</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="description">Mô tả ngắn</label>
                    <textarea id="description" name="description" rows="3">${editCategory.description}</textarea>
                </div>

                <button type="submit" class="btn btn-save">
                    ${not empty editCategory ? 'Lưu thay đổi' : 'Thêm mới'}
                </button>

                <c:if test="${not empty editCategory}">
                    <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-cancel">Hủy cập nhật</a>
                </c:if>
            </form>

        </div>
    </div>

</main>
</body>
</html>