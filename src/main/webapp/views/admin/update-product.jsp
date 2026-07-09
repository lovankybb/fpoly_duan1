<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Sản phẩm - Atelier Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/update-product.css">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Cập nhật Sản phẩm</h1>
        <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" /></svg>
            Quay lại
        </a>
    </header>

    <div class="form-card">
        <c:if test="${not empty errorMsg}"><div class="alert alert-error">${errorMsg}</div></c:if>
        <c:if test="${not empty successMsg}"><div class="alert alert-success">${successMsg}</div></c:if>

        <form action="${pageContext.request.contextPath}/admin/products/update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${product.id}">
            <input type="hidden" name="oldImage" value="${product.image}">

            <div class="form-grid">

                <div class="form-group full-width">
                    <label>Tên sản phẩm</label>
                    <input type="text" name="name" value="${product.name}" required>
                </div>

                <div class="form-group">
                    <label>Danh mục</label>
                    <select name="categoryId" required>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.id}" ${product.categoryId == cat.id ? 'selected' : ''}>${cat.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Thương hiệu</label>
                    <select name="brandId" required>
                        <c:forEach var="brand" items="${brands}">
                            <option value="${brand.id}" ${product.brandId == brand.id ? 'selected' : ''}>${brand.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Giá (VNĐ)</label>
                    <input type="number" name="price" value="${product.price}" min="0" required>
                </div>
                <div class="form-group">
                    <label>Giá bán ra(VNĐ)</label>
                    <input type="number" name="salePrice" value="${product.salePrice}" min="0" required>
                </div>

                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="status">
                        <option value="ACTIVE" ${product.status == 'ACTIVE' ? 'selected' : ''}>Đang bán</option>
                        <option value="INACTIVE" ${product.status == 'INACTIVE' ? 'selected' : ''}>Ngừng bán</option>
                        <option value="DRAFT" ${product.status == 'DRAFT' ? 'selected' : ''}>Nháp</option>
                    </select>
                </div>

                <div class="form-group full-width">
                    <label>Ảnh đại diện mới (Bỏ trống nếu muốn giữ ảnh cũ)</label>
                    <input type="file" id="imageInput" name="image" accept="image/*">

                    <div class="img-preview-box" id="previewBox">
                        <img id="imagePreview" src="${pageContext.request.contextPath}/uploads/products/${product.image}" alt="Ảnh hiện tại">
                    </div>
                </div>

                <div class="form-group full-width">
                    <label>Mô tả chi tiết</label>
                    <textarea name="description" rows="5">${product.description}</textarea>
                </div>

            </div>

            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-cancel">Hủy cập nhật</a>
                <button type="submit" class="btn btn-save">Lưu thay đổi</button>
            </div>
        </form>
    </div>
</main>

<script>
    document.getElementById('imageInput').addEventListener('change', function(event) {
        const file = event.target.files[0];
        const imagePreview = document.getElementById('imagePreview');

        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                // Đổi nguồn ảnh sang ảnh vừa chọn
                imagePreview.src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    });
</script>
</body>
</html>