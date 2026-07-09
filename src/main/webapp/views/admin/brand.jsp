<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Thương Hiệu - Admin Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">

   <link href="${pageContext.request.contextPath}/styles/brand.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Quản lý Thương hiệu</h1>
    </header>

    <c:if test="${not empty errorMsg}"><div class="alert alert-error">${errorMsg}</div></c:if>
    <c:if test="${not empty successMsg}"><div class="alert alert-success">${successMsg}</div></c:if>

    <div class="crud-layout">

        <div class="card">
            <h2 class="card-title">Danh sách Thương hiệu</h2>
            <table>
                <thead>
                <tr>
                    <th>Logo</th>
                    <th>Tên thương hiệu</th>
                    <th>Mô tả</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="brand" items="${brandList}">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${not empty brand.image}">
                                    <img src="${pageContext.request.contextPath}/uploads/brands/${brand.image}" alt="${brand.name}" class="brand-thumb">
                                </c:when>
                                <c:otherwise>
                                    <span class="no-img">Trống</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><strong>${brand.name}</strong></td>
                        <td>
                            <div style="max-width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                                    ${brand.description}
                            </div>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/brands?editId=${brand.id}" class="btn-action btn-edit">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/brand/delete?id=${brand.id}" class="btn-action btn-delete" onclick="return confirm('Xóa thương hiệu này?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty brandList}">
                    <tr><td colspan="4" style="text-align:center; padding: 30px;">Chưa có dữ liệu.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="card form-card">

            <c:choose>
                <c:when test="${not empty editBrand}">
                    <h2 class="card-title">Cập nhật Thương hiệu</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/brand/update" />
                </c:when>
                <c:otherwise>
                    <h2 class="card-title">Thêm Thương hiệu</h2>
                    <c:set var="actionUrl" value="${pageContext.request.contextPath}/admin/brand/add" />
                </c:otherwise>
            </c:choose>

            <form action="${actionUrl}" method="post" enctype="multipart/form-data">
                <c:if test="${not empty editBrand}">
                    <input type="hidden" name="id" value="${editBrand.id}">
                    <input type="hidden" name="oldImage" value="${editBrand.image}">
                </c:if>

                <div class="form-group">
                    <label for="name">Tên thương hiệu</label>
                    <input type="text" id="name" name="name" value="${editBrand.name}" required>
                </div>

                <div class="form-group">
                    <label for="image">Ảnh đại diện (Logo)</label>
                    <input type="file" id="image" name="image" accept="image/png, image/jpeg, image/webp">

                    <c:if test="${not empty editBrand and not empty editBrand.image}">
                        <div class="img-preview-box">
                            <p style="font-size: 11px; color: #888; margin-bottom: 5px;">Logo hiện tại:</p>
                            <img src="${pageContext.request.contextPath}/uploads/brands/${editBrand.image}" alt="Preview">
                        </div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="description">Mô tả thương hiệu</label>
                    <textarea id="description" name="description" rows="4">${editBrand.description}</textarea>
                </div>

                <button type="submit" class="btn btn-save">
                    ${not empty editBrand ? 'Lưu thay đổi' : 'Thêm mới'}
                </button>

                <c:if test="${not empty editBrand}">
                    <a href="${pageContext.request.contextPath}/admin/brands" class="btn btn-cancel">Hủy cập nhật</a>
                </c:if>
            </form>

        </div>
    </div>

</main>
</body>
</html>