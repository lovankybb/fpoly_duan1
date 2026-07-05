<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atelier</title>
    <!-- Nhúng Font chữ hiện đại -->
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
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
                <a href="${pageContext.request.contextPath}/products" class="btn-hero">KHÁM PHÁ NGAY</a>
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

                <!-- Sản phẩm 1 -->
                <div class="product-card">
                    <div class="product-image">
                        <img src="path/to/apple_watch_image.png" alt="Apple Watch">
                    </div>
                    <div class="product-details">
                        <h3>Apple Watch Series 10</h3>
                        <p>(Midnight aluminum case)</p>
                        <div class="price-row">
                            <span class="product-price">Từ 12.990.000đ</span>
                        </div>
                        <a href="#" class="btn-view">Xem chi tiết</a>
                    </div>
                </div>

                <!-- Sản phẩm 2 -->
                <div class="product-card">
                    <div class="product-image">
                        <img src="path/to/pixel_image.png" alt="Google Pixel">
                    </div>
                    <div class="product-details">
                        <h3>Google Pixel 9 Pro (hazel)</h3>
                        <p>(128GB, Bản quốc tế)</p>
                        <div class="price-row">
                            <span class="product-price">Từ 26.500.000đ</span>
                        </div>
                        <a href="#" class="btn-view">Xem chi tiết</a>
                    </div>
                </div>

                <!-- Sản phẩm 3 -->
                <div class="product-card">
                    <div class="product-image">
                        <img src="path/to/ipad_image.png" alt="iPad Air">
                    </div>
                    <div class="product-details">
                        <h3>iPad Air M2 (starlight)</h3>
                        <p>(11-inch, Wi-Fi, 128GB)</p>
                        <div class="price-row">
                            <span class="product-price">Từ 16.990.000đ</span>
                        </div>
                        <a href="#" class="btn-view">Xem chi tiết</a>
                    </div>
                </div>

                <!-- Sản phẩm 4 -->
                <div class="product-card">
                    <div class="product-image">
                        <img src="path/to/charger_image.png" alt="Sạc Anker">
                    </div>
                    <div class="product-details">
                        <h3>Sạc Anker Nano II (black)</h3>
                        <p>(65W, USB-C, GaN)</p>
                        <div class="price-row">
                            <span class="product-price">990.000đ</span>
                        </div>
                        <a href="#" class="btn-view">Xem chi tiết</a>
                    </div>
                </div>

            </div>
        </section>

    </div>
</main>

<!-- 3. Cuối trang (Sử dụng file footer thanh lịch bạn đã tạo) -->
<%-- <%@ include file="footer.jsp" %> --%>

<!-- Giả lập Footer tối giản để hoàn thiện giao diện -->
<%@include file="views/fragments/footer.jsp"%>>

</body>
</html>