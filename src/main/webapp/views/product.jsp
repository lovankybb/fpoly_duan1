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

        <c:forEach var="prod" items="${products}">
            <a href="${pageContext.request.contextPath}/product/detail?id=${prod.id}" class="product-card"
               data-category="điện thoại">
                <div class="product-img-wrap">
                    <span class="badge-new">Mới</span>
                    <img src="${pageContext.request.contextPath}/image?name=${prod.image}"
                         alt="${prod.name}">
                </div>
                <div class="product-info">
                    <div class="product-brand">Apple</div>
                    <h3 class="product-title">${prod.name}</h3>
                    <div class="product-bottom">
                        <div class="price-box">
                            <!-- Thêm class dùng chung và data-price chứa số gốc -->
                            <p class="product-sale-price js-format-price" data-price="${prod.salePrice}"></p>
                            <del class="product-price js-format-price" data-price="${prod.price}"></del>
                        </div>
                        <div class="btn-view">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none"
                                 viewBox="0 0 24 24"
                                 stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                            </svg>
                        </div>
                    </div>
                </div>
            </a>
        </c:forEach>


        <div id="noResult" class="no-result-msg">
            Rất tiếc, chúng tôi không tìm thấy sản phẩm nào khớp với tìm kiếm của bạn.
        </div>

    </div>


    <c:if test="${offset >= 20}">
        <a href="${pageContext.request.contextPath}/products?offset=${offset - 20}">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                 class="lucide lucide-circle-arrow-left-icon lucide-circle-arrow-left">
                <circle cx="12" cy="12" r="10"/>
                <path d="m12 8-4 4 4 4"/>
                <path d="M16 12H8"/>
            </svg>
        </a>
    </c:if>
    <c:if test="${not empty offset}">
        <a href="${pageContext.request.contextPath}/products?offset=${offset + 20}">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                 class="lucide lucide-circle-arrow-right-icon lucide-circle-arrow-right">
                <circle cx="12" cy="12" r="10"/>
                <path d="m12 16 4-4-4-4"/>
                <path d="M8 12h8"/>
            </svg>
        </a>
    </c:if>
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

</body>
</html>