<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Sản phẩm - Admin Atelier Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/update-product.css">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Cập nhật Sản phẩm</h1>
        <a href="${pageContext.request.contextPath}/admin/products?offset=0" class="btn-back">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24"
                 stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
            </svg>
            Quay lại
        </a>
    </header>

    <div class="form-card">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-error">${errorMsg}</div>
        </c:if>
        <c:if test="${not empty successMsg}">
            <div class="alert alert-success">${successMsg}</div>
        </c:if>

                <form action="${pageContext.request.contextPath}/admin/product/update" method="post" enctype="multipart/form-data">
                    <!-- Thẻ hidden để giữ ID của sản phẩm đang cập nhật -->
                    <input type="hidden" name="id" value="${product.id}">

                    <div class="form-grid">

                        <div class="form-group full-width">
                            <label>Tên sản phẩm</label>
                            <input type="text" name="name" value="${product.name}" placeholder="VD: iPhone 17 Pro Max 256GB" required>
                        </div>

                        <div class="form-group">
                            <label>Danh mục</label>
                            <select name="categoryId" required>
                                <option value="">-- Chọn danh mục --</option>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.id}" ${cat.id == product.categoryId ? 'selected' : ''}>${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Thương hiệu</label>
                            <select name="brandId" required>
                                <option value="">-- Chọn thương hiệu --</option>
                                <c:forEach var="brand" items="${brands}">
                                    <option value="${brand.id}" ${brand.id == product.brandId ? 'selected' : ''}>${brand.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Giá nhập (VNĐ)</label>
                            <input type="number" name="price" value="${product.price}" placeholder="VD: 1400000" min="0" required>
                        </div>

                        <div class="form-group">
                            <label>Giá bán ra (VNĐ)</label>
                            <input type="number" name="salePrice" value="${product.salePrice}" placeholder="VD: 830000" min="0" required>
                        </div>

                        <div class="form-group">
                            <label>Trạng thái</label>
                            <select name="status">
                                <option value="ACTIVE" ${product.status == 'ACTIVE' ? 'selected' : ''}>Đang bán</option>
                                <option value="INACTIVE" ${product.status == 'INACTIVE' ? 'selected' : ''}>Ngừng bán</option>
                                <option value="DRAFT" ${product.status == 'DRAFT' ? 'selected' : ''}>Nháp</option>
                            </select>
                        </div>

                        <!-- Khu vực hiển thị ảnh cũ -->
                        <div class="form-group full-width">
                            <label>Ảnh hiện tại</label>
                            <div class="old-images-box">
                                <c:if test="${not empty productImages}">
                                    <c:forEach var="img" items="${productImages}">
                                        <div class="img-preview-item old-img">
                                            <img src="${pageContext.request.contextPath}/image?name=${img.imageUrl}" alt="Old Product Image">
                                            <span class="old-badge">Hiện tại</span>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty productImages}">
                                    <div class="text-muted" style="font-size: 13px; font-style: italic;">Sản phẩm này hiện chưa có ảnh nào.</div>
                                </c:if>
                            </div>
                        </div>

                        <!-- Khu vực upload ảnh mới -->
                        <div class="form-group full-width">
                            <label>Tải lên ảnh mới (Bỏ trống nếu muốn giữ nguyên ảnh hiện tại)</label>
                            <input type="file" id="imageInput" name="image" accept="image/*" multiple="multiple">
                            <div class="img-preview-box" id="previewBox">
                                <!-- Ảnh xem trước mới sẽ tự động hiển thị ở đây bởi JS -->
                            </div>
                        </div>

                        <div class="form-group full-width">
                            <label>Mô tả chi tiết</label>
                            <textarea name="description" rows="5" placeholder="Nhập mô tả sản phẩm...">${product.description}</textarea>
                        </div>

                    </div>

                    <div class="form-actions">
                        <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-cancel">Hủy</a>
                        <button type="submit" class="btn btn-save">Cập nhật sản phẩm</button>
                    </div>
                </form>
    </div>
</main>

<script>
    document.getElementById('imageInput').addEventListener('change', function (event) {
        const files = event.target.files;
        const previewBox = document.getElementById('previewBox');

        // Xóa các ảnh preview mới trước đó
        previewBox.innerHTML = '';

        if (files && files.length > 0) {
            Array.from(files).forEach((file, index) => {
                const reader = new FileReader();

                reader.onload = function (e) {
                    const itemDiv = document.createElement('div');
                    itemDiv.className = 'img-preview-item new-img';

                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.alt = `Ảnh mới ${index + 1}`;

                    const badge = document.createElement('span');
                    badge.className = 'new-badge';
                    badge.innerText = 'Mới';

                    itemDiv.appendChild(img);
                    itemDiv.appendChild(badge);
                    previewBox.appendChild(itemDiv);
                }

                reader.readAsDataURL(file);
            });

            previewBox.style.display = 'flex';
        } else {
            previewBox.style.display = 'none';
        }
    });
</script>
</body>
</html>