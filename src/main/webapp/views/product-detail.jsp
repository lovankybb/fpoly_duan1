<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Sản phẩm - Admin Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/product-detail.css">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Chi tiết Sản phẩm</h1>
        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/admin/products?" class="btn-back">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" /></svg>
                Quay lại
            </a>
            <a href="${pageContext.request.contextPath}/admin/products/edit?id=${product.id}" class="btn btn-edit">
                Sửa sản phẩm
            </a>
        </div>
    </header>

    <div class="detail-card">
        <div class="detail-grid">

            <!-- Cột trái: Thư viện ảnh -->
            <div class="product-gallery">
                <!-- Ảnh chính -->
                <div class="main-image-box">
                    <c:choose>
                        <c:when test="${not empty product.images}">
                            <img id="mainImage" src="${pageContext.request.contextPath}/uploads/${product.images[0]}" alt="${product.name}">
                        </c:when>
                        <c:otherwise>
                            <div class="no-image-placeholder">Chưa có ảnh</div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Danh sách ảnh nhỏ -->
                <c:if test="${not empty product.images and product.images.size() > 1}">
                    <div class="thumbnail-list">
                        <c:forEach var="img" items="${product.images}">
                            <div class="thumbnail-item" onclick="changeMainImage('${pageContext.request.contextPath}/uploads/${img}', this)">
                                <img src="${pageContext.request.contextPath}/uploads/${img}" alt="Thumbnail">
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>

            <!-- Cột phải: Thông tin chi tiết -->
            <div class="product-info">
                <div class="info-header">
                    <c:choose>
                        <c:when test="${product.status == 'ACTIVE'}"><span class="badge badge-active">Đang bán</span></c:when>
                        <c:when test="${product.status == 'INACTIVE'}"><span class="badge badge-inactive">Ngừng bán</span></c:when>
                        <c:otherwise><span class="badge badge-draft">Nháp</span></c:otherwise>
                    </c:choose>
                    <span class="info-id">ID: #${product.id}</span>
                </div>

                <h2 class="product-name">${product.name}</h2>

                <div class="price-box">
                    <span class="sale-price"><fmt:formatNumber value="${product.salePrice}" pattern="#,###" /> ₫</span>
                    <del class="original-price"><fmt:formatNumber value="${product.price}" pattern="#,###" /> ₫</del>
                </div>

                <div class="info-attributes">
                    <div class="attr-row">
                        <span class="attr-label">Danh mục:</span>
                        <span class="attr-value">${categoryName}</span>
                    </div>
                    <div class="attr-row">
                        <span class="attr-label">Thương hiệu:</span>
                        <span class="attr-value">${brandName}</span>
                    </div>
                </div>

                <div class="info-description">
                    <h3 class="desc-title">Mô tả sản phẩm</h3>
                    <div class="desc-content">
                        ${empty product.description ? '<i>Không có mô tả cho sản phẩm này.</i>' : product.description}
                    </div>
                </div>
            </div>

        </div>
    </div>
</main>

<script>
    // Logic đổi ảnh chính khi click vào ảnh thumbnail
    function changeMainImage(src, element) {
        document.getElementById('mainImage').src = src;

        // Cập nhật viền cho thumbnail đang được chọn (Active)
        let thumbnails = document.querySelectorAll('.thumbnail-item');
        thumbnails.forEach(t => t.classList.remove('active'));
        element.classList.add('active');
    }

    // Set active cho thumbnail đầu tiên khi load trang
    window.onload = function() {
        let firstThumb = document.querySelector('.thumbnail-item');
        if (firstThumb) {
            firstThumb.classList.add('active');
        }
    };
</script>
</body>
</html>