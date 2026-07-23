<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atelier</title>
    <!-- Nhúng Font chữ hiện đại -->
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="styles/index.css" rel="stylesheet">
</head>
<body>

<!-- 1. Đầu trang (Sử dụng file header của bạn) -->
<%@ include file="views/fragments/header.jsp" %>


<!-- 2. PHẦN NỘI DUNG CHÍNH CỦA TRANG CHỦ -->
<main class="main-content">
    <div class="container">

        <!-- 2.1 Hero Banner -->
        <section class="hero-banner">
            <div class="hero-text">
                <h1>Iphone 17 Promax</h1>
                <p>Mở ra thế giới giải trí và năng suất mới. Thiết kế tinh tế, hiệu năng đỉnh cao. Đặt hàng ngay.</p>
                <a href="${pageContext.request.contextPath}/products?offset=0" class="btn-hero">KHÁM PHÁ NGAY</a>
            </div>
            <div class="hero-image">
                <!-- Thay thế bằng đường dẫn ảnh thật của bạn -->
                <img src="${pageContext.request.contextPath}/resources/imgs/iphone17-promax.png" alt="Iphone 17 Promax">
            </div>
        </section>

        <!-- 2.2 Khám phá Thương hiệu -->
        <section class="brands-section">
            <h2 class="section-title">Khám phá Thương hiệu</h2>
            <div class="brands-grid">
                <a href="#" class="brand-card">
                    <img src="path/to/apple_logo.png" alt="Apple">
                    <p>Apple</p>
                    <span>(iPhone, Mac)</span>
                </a>
                <a href="#" class="brand-card">
                    <img src="path/to/samsung_logo.png" alt="Samsung">
                    <p>Samsung</p>
                    <span>(Galaxy, Watch)</span>
                </a>
                <a href="#" class="brand-card">
                    <img src="path/to/xiaomi_logo.png" alt="Xiaomi">
                    <p>Xiaomi</p>
                    <span>(Mi-series, IoT)</span>
                </a>
                <a href="#" class="brand-card">
                    <img src="path/to/google_logo.png" alt="Google">
                    <p>Google</p>
                    <span>(Pixel, Buds)</span>
                </a>
                <a href="#" class="brand-card">
                    <img src="path/to/accessories_icon.png" alt="Phụ kiện">
                    <p>Phụ kiện</p>
                    <span>Cao cấp</span>
                </a>
            </div>
        </section>

        <!-- 2.3 Hàng Mới Về -->
        <section class="new-arrivals-section">
            <h2 class="section-title">Hàng Mới Về</h2>
            <div class="products-grid">

                <c:forEach var="p" items="${products}">
                    <div class="product-card">
                        <div class="product-image">
                            <img src="${pageContext.request.contextPath}/image?name=${p.image}" alt="${p.name}">
                        </div>
                        <div class="product-details">
                            <h3>${p.name}</h3>
                            <p>Brand</p>
                            <div class="price-row">
                                <span class="product-price">
                                     Từ <fmt:formatNumber value="${p.salePrice}" type="currency" currencySymbol=""
                                                          maxFractionDigits="0"/> ₫
                                </span>
                            </div>
                            <a href="${pageContext.request.contextPath}/product/detail?id=${p.id}"
                               class="btn-view">Xem chi tiết</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

    </div>
</main>
<%@include file="views/fragments/footer.jsp" %>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Tìm tất cả các thẻ có class js-format-price
        const priceElements = document.querySelectorAll('.js-format-price');

        priceElements.forEach(function (el) {
            // Lấy con số từ data-price
            const rawPrice = Number(el.getAttribute('data-price'));

            // Nếu có giá trị hợp lệ thì format và gán lại
            if (!isNaN(rawPrice) && rawPrice > 0) {
                el.innerText = rawPrice.toLocaleString('vi-VN') + ' ₫';
            }
        });
    });
</script>
</html>