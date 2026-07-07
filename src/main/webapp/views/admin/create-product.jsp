<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Sản phẩm - Admin Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/create-product.css">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Thêm Sản phẩm mới</h1>
        <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" /></svg>
            Quay lại
        </a>
    </header>

    <div class="form-card">
        <c:if test="${not empty errorMsg}"><div class="alert alert-error">${errorMsg}</div></c:if>

        <form action="${pageContext.request.contextPath}/admin/product/add" method="post" enctype="multipart/form-data">
            <div class="form-grid">

                <div class="form-group full-width">
                    <label>Tên sản phẩm</label>
                    <input type="text" name="name" placeholder="VD: iPhone 17 Pro Max 256GB" required>
                </div>

                <div class="form-group">
                    <label>Danh mục</label>
                    <select name="categoryId" required>
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach var="cat" items="${categoryList}">
                            <option value="${cat.id}">${cat.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Thương hiệu</label>
                    <select name="brandId" required>
                        <option value="">-- Chọn thương hiệu --</option>
                        <c:forEach var="brand" items="${brandList}">
                            <option value="${brand.id}">${brand.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Giá bán (VNĐ)</label>
                    <input type="number" name="price" placeholder="VD: 34990000" min="0" required>
                </div>

                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="status">
                        <option value="1">Đang bán (Còn hàng)</option>
                        <option value="0">Ngừng kinh doanh / Hết hàng</option>
                    </select>
                </div>

                <div class="form-group full-width">
                    <label>Ảnh đại diện sản phẩm</label>
                    <input type="file" id="imageInput" name="image" accept="image/*" required>
                    <div class="img-preview-box" id="previewBox">
                        <img id="imagePreview" src="" alt="Preview">
                    </div>
                </div>

                <div class="form-group full-width">
                    <label>Mô tả chi tiết</label>
                    <textarea name="description" rows="5" placeholder="Nhập mô tả sản phẩm..."></textarea>
                </div>

            </div>

            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-cancel">Hủy</a>
                <button type="submit" class="btn btn-save">Tạo sản phẩm</button>
            </div>
        </form>
    </div>
</main>

<script>
    document.getElementById('imageInput').addEventListener('change', function(event) {
        const file = event.target.files[0];
        const previewBox = document.getElementById('previewBox');
        const imagePreview = document.getElementById('imagePreview');

        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                imagePreview.src = e.target.result;
                previewBox.style.display = 'block'; // Hiện ảnh lên
            }
            reader.readAsDataURL(file);
        } else {
            previewBox.style.display = 'none'; // Ẩn nếu user hủy chọn
        }
    });
</script>
</body>
</html>