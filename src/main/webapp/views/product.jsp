<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cửa hàng - Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,600;1,600&family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/product.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/header.jsp" %>

<main class="container">

    <div class="shop-header">
        <div class="shop-title">
            <h1>Tất cả sản phẩm</h1>
            <p>Khám phá bộ sưu tập công nghệ tinh tế nhất.</p>
        </div>

        <div class="shop-filter-group">
            <div class="shop-search">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24"
                     stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
                <input type="text" id="customerSearch" placeholder="Tìm kiếm sản phẩm...">
            </div>

            <select id="brandFilter" class="filter-select">
                <option value="">Tất cả thương hiệu</option>
                <option value="apple">Apple</option>
                <option value="samsung">Samsung</option>
                <option value="phụ kiện">Phụ kiện</option>
            </select>

            <select id="categoryFilter" class="filter-select">
                <option value="">Tất cả danh mục</option>
                <option value="đáp ứng dữ liệu động hoặc thêm tay vào đây">Điện thoại</option>
                <option value="tablet">Máy tính bảng</option>
                <option value="sạc">Củ sạc / Cáp sạc</option>
            </select>
        </div>
    </div>

    <div class="product-grid" id="productGrid">

        <a href="${pageContext.request.contextPath}/product/iphone-17-pro-max" class="product-card" data-category="điện thoại">
            <div class="product-img-wrap">
                <span class="badge-new">Mới</span>
                <img src="${pageContext.request.contextPath}/resources/imgs/iphone17-promax.png"
                     alt="iPhone 17 Pro Max">
            </div>
            <div class="product-info">
                <div class="product-brand">Apple</div>
                <h3 class="product-title">iPhone 17 Pro Max 256GB</h3>
                <div class="product-bottom">
                    <span class="product-price">34.990.000 ₫</span>
                    <div class="btn-view">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                        </svg>
                    </div>
                </div>
            </div>
        </a>

        <a href="#" class="product-card" data-category="điện thoại">
            <div class="product-img-wrap">
                <img src="${pageContext.request.contextPath}/resources/imgs/zfold6.png" alt="Galaxy Z Fold6">
            </div>
            <div class="product-info">
                <div class="product-brand">Samsung</div>
                <h3 class="product-title">Samsung Galaxy Z Fold6</h3>
                <div class="product-bottom">
                    <span class="product-price">43.990.000 ₫</span>
                    <div class="btn-view">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                        </svg>
                    </div>
                </div>
            </div>
        </a>

        <a href="#" class="product-card" data-category="tablet">
            <div class="product-img-wrap">
                <img src="${pageContext.request.contextPath}/resources/imgs/ipad-pro.png" alt="iPad Pro">
            </div>
            <div class="product-info">
                <div class="product-brand">Apple</div>
                <h3 class="product-title">iPad Pro M4 11-inch (2026)</h3>
                <div class="product-bottom">
                    <span class="product-price">28.500.000 ₫</span>
                    <div class="btn-view">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                        </svg>
                    </div>
                </div>
            </div>
        </a>

        <a href="#" class="product-card" data-category="sạc">
            <div class="product-img-wrap">
                <img src="${pageContext.request.contextPath}/resources/imgs/anker.png" alt="Sạc Anker">
            </div>
            <div class="product-info">
                <div class="product-brand">Phụ kiện</div>
                <h3 class="product-title">Sạc nhanh Anker Nano II 65W</h3>
                <div class="product-bottom">
                    <span class="product-price">990.000 ₫</span>
                    <div class="btn-view">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                        </svg>
                    </div>
                </div>
            </div>
        </a>

        <div id="noResult" class="no-result-msg">
            Rất tiếc, chúng tôi không tìm thấy sản phẩm nào khớp với tìm kiếm của bạn.
        </div>

    </div>
</main>

<%@ include file="/views/fragments/footer.jsp" %>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const searchInput = document.getElementById('customerSearch');
        const brandSelect = document.getElementById('brandFilter');
        const categorySelect = document.getElementById('categoryFilter');
        const productCards = document.querySelectorAll('.product-card');
        const noResultMsg = document.getElementById('noResult');

        // Hàm xử lý lọc tổng hợp (kết hợp cả 3 điều kiện)
        function filterProducts() {
            const searchTerm = searchInput.value.toLowerCase().trim();
            const selectedBrand = brandSelect.value.toLowerCase();
            const selectedCategory = categorySelect.value.toLowerCase();

            let visibleCount = 0;

            productCards.forEach(function (card) {
                // Lấy thông tin từ thẻ card
                const title = card.querySelector('.product-title').textContent.toLowerCase();
                const brand = card.querySelector('.product-brand').textContent.toLowerCase();
                const category = card.getAttribute('data-category') ? card.getAttribute('data-category').toLowerCase() : '';

                // Kiểm tra điều kiện lọc
                const matchesSearch = title.includes(searchTerm);
                const matchesBrand = selectedBrand === '' || brand.includes(selectedBrand);
                const matchesCategory = selectedCategory === '' || category.includes(selectedCategory);

                // Nếu thỏa mãn tất cả các điều kiện thì hiển thị
                if (matchesSearch && matchesBrand && matchesCategory) {
                    card.style.display = 'flex';
                    visibleCount++;
                } else {
                    card.style.display = 'none';
                }
            });

            // Hiển thị thông báo nếu không có sản phẩm nào thỏa mãn
            if (visibleCount === 0) {
                noResultMsg.style.display = 'block';
            } else {
                noResultMsg.style.display = 'none';
            }
        }

        // Lắng nghe sự kiện thay đổi của cả 3 bộ lọc
        searchInput.addEventListener('input', filterProducts);
        brandSelect.addEventListener('change', filterProducts);
        categorySelect.addEventListener('change', filterProducts);
    });
</script>

</body>
</html>