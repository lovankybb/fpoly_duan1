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
        <a href="${pageContext.request.contextPath}/admin/products?offset=0" class="btn-back">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" /></svg>
            Quay lại
        </a>
    </header>

    <div class="form-card">
        <c:if test="${not empty errorMsg}"><div class="alert alert-error">${errorMsg}</div></c:if>
        <c:if test="${not empty successMsg}"><div class="alert alert-success">${successMsg}</div></c:if>

        <form action="${pageContext.request.contextPath}/admin/product/add" method="post" enctype="multipart/form-data">
            <div class="form-grid">

                <div class="form-group full-width">
                    <label>Tên sản phẩm</label>
                    <input type="text" name="name" placeholder="VD: iPhone 17 Pro Max 256GB" required>
                    <span class="error">${nameError}</span>
                </div>

                <div class="form-group">
                    <label>Danh mục</label>
                    <select name="categoryId" required>
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach var="cat" items="${categories}">
                            <option value="${cat.id}">${cat.name}</option>
                        </c:forEach>
                    </select>
                    <span class="error">${categoryError}</span>
                </div>

                <div class="form-group">
                    <label>Thương hiệu</label>
                    <select name="brandId" required>
                        <option value="">-- Chọn thương hiệu --</option>
                        <c:forEach var="brand" items="${brands}">
                            <option value="${brand.id}">${brand.name}</option>
                        </c:forEach>
                    </select>
                    <span class="error">${brandError}</span>
                </div>

                <div class="form-group">
                    <label>Giá (VNĐ)</label>
                    <input type="number" name="price" placeholder="VD: 1400000" min="0" required>
                    <span class="error">${priceError}</span>
                </div>
                <div class="form-group">
                    <label>Giá bán ra(VNĐ)</label>
                    <input type="number" name="salePrice" placeholder="VD: 830000" min="0" required>
                    <span class="error">${priceError}</span>
                </div>

                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="status">
                        <option value="ACTIVE">Đang bán</option>
                        <option value="INACTIVE">Ngừng bán</option>
                        <option value="DRAFT">Nháp</option>
                    </select>
                    <span class="error">${statusError}</span>
                </div>

                <div class="form-group full-width">
                    <label>Ảnh sản phẩm (Có thể chọn nhiều ảnh)</label>
                    <input type="file" id="imageInput" name="image" accept="image/*" required multiple="multiple">
                    <div class="img-preview-box" id="previewBox">
                        <!-- Các ảnh xem trước sẽ được thêm vào đây bằng JavaScript -->
                        <div id="noImagesMessage" style="color: #6c757d; font-style: italic;">Chưa chọn ảnh</div>
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
        const files = event.target.files; // Lấy tất cả các file đã chọn (FileList)
        const previewBox = document.getElementById('previewBox');
        const noImagesMessage = document.getElementById('noImagesMessage');

        // Xóa tất cả các ảnh xem trước hiện có
        // Cần giữ lại noImagesMessage nếu muốn dùng nó để ẩn/hiện, nhưng ở đây tôi sẽ xóa nó khi có ảnh
        previewBox.innerHTML = ''; // Cách đơn giản nhất để xóa nội dung cũ

        // Đảm bảo tin nhắn 'Chưa chọn ảnh' bị xóa nếu có file được chọn
        if (files && files.length > 0) {
            Array.from(files).forEach((file, index) => { // Cần chuyển đổi FileList thành mảng
                const reader = new FileReader();

                reader.onload = function(e) {
                    // Tạo một container cho mỗi ảnh xem trước
                    const itemDiv = document.createElement('div');
                    itemDiv.className = 'img-preview-item';

                    // Tạo thẻ img
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.alt = `Xem trước ảnh ${index + 1}`; // Thêm alt để trợ năng tốt hơn

                    // Thêm img vào container
                    itemDiv.appendChild(img);

                    // Thêm container vào previewBox
                    previewBox.appendChild(itemDiv);
                }

                reader.readAsDataURL(file); // Bắt đầu đọc file ảnh
            });

            previewBox.style.display = 'flex'; // Hiển thị khung xem trước dưới dạng flexbox
        } else {
            // Nếu không có file nào được chọn (user hủy), ẩn khung xem trước và hiển thị lại tin nhắn (nếu có)
            previewBox.style.display = 'none';
            // Ở đây tôi xóa `innerHTML` ở trên nên tin nhắn `noImagesMessage` đã mất.
            // Nếu muốn hiện lại tin nhắn, tôi cần tạo lại nó hoặc ẩn/hiện thay vì xóa `innerHTML`.
            // Cách đơn giản nhất là chỉ ẩn khung xem trước.
        }
    });
</script>
</body>
</html>