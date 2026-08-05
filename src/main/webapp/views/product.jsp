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
    <style>
        /* Bổ sung CSS nhẹ cho form lọc và nút Lọc để nằm ngang đẹp mắt */
        .shop-filter-form { display: flex; gap: 12px; align-items: center; flex-wrap: wrap; }
        .btn-submit-filter {
            padding: 10px 20px; background-color: #000; color: #fff; font-weight: 600;
            border: none; border-radius: 6px; cursor: pointer; transition: background 0.3s;
        }
        .btn-submit-filter:hover { background-color: #333; }
    </style>
</head>
<body>

<%@ include file="/views/fragments/header.jsp" %>

<main class="container">

    <div class="shop-header">
        <div class="shop-title">
            <h1>Tất cả sản phẩm</h1>
            <p>Khám phá bộ sưu tập công nghệ tinh tế nhất.</p>
        </div>

        <!-- Chuyển thành FORM Gửi dữ liệu GET -->
        <form action="${pageContext.request.contextPath}/products" method="GET" class="shop-filter-group shop-filter-form">
            <input type="hidden" name="offset" value="${offset}"> <!-- Reset offset khi lọc -->
            <div class="shop-search">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24"
                     stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
                <input type="text" name="partName" value="${param.partname}" placeholder="Tìm kiếm sản phẩm...">
            </div>

            <select name="brandId" class="filter-select">
                <option value="">Tất cả thương hiệu</option>
                <c:forEach var="brand" items="${brands}">
                    <option value="${brand.id}" ${param.brandId == brand.id ? 'selected' : ''}>${brand.name}</option>
                </c:forEach>
            </select>

            <select name="categoryId" class="filter-select">
                <option value="">Tất cả danh mục</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}" ${param.categoryId == category.id ? 'selected' : ''}>${category.name}</option>
                </c:forEach>
            </select>

            <button type="submit" class="btn-submit-filter">Lọc</button>
        </form>
    </div>

    <div class="product-grid" id="productGrid">
        <c:choose>
            <%-- Nếu có sản phẩm --%>
            <c:when test="${not empty products}">
                <c:forEach var="prod" items="${products}">
                    <a href="${pageContext.request.contextPath}/product/detail?id=${prod.id}" class="product-card">
                        <div class="product-img-wrap">
                            <span class="badge-new">Mới</span>
                            <img src="${pageContext.request.contextPath}/image?name=${prod.image}" alt="${prod.name}">
                        </div>
                        <div class="product-info">
                            <div class="product-brand">${prod.brand}</div>
                            <h3 class="product-title">${prod.name}</h3>
                            <div class="product-bottom">
                                <div class="price-box">
                                    <p class="product-sale-price js-format-price" data-price="${prod.salePrice}"></p>
                                    <del class="product-price js-format-price" data-price="${prod.price}"></del>
                                </div>
                                <div class="btn-view">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none"
                                         viewBox="0 0 24 24" stroke="currentColor">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                              d="M14 5l7 7m0 0l-7 7m7-7H3"/>
                                    </svg>
                                </div>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:when>

            <%-- Nếu không tìm thấy sản phẩm nào --%>
            <c:otherwise>
                <div id="noResult" class="no-result-msg" style="display: block; grid-column: 1 / -1;">
                    Rất tiếc, chúng tôi không tìm thấy sản phẩm nào khớp với bộ lọc của bạn.
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- TẠO CHUỖI PARAM ĐỂ CHUYỂN TRANG KHÔNG BỊ MẤT BỘ LỌC -->
    <c:set var="filterParams" value="&partname=${param.partname}&brandId=${param.brandId}&categoryId=${param.categoryId}" />

    <!-- PHÂN TRANG -->
    <div style="display: flex; gap: 15px; margin-top: 30px;">
        <c:if test="${offset >= 20}">
            <a href="${pageContext.request.contextPath}/products?offset=${offset - 20}${filterParams}">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10"/><path d="m12 8-4 4 4 4"/><path d="M16 12H8"/>
                </svg>
            </a>
        </c:if>

        <c:if test="${not empty offset}">
            <!-- Lưu ý: Cần có logic kiểm tra xem có còn trang sau không (ví dụ kiểm tra products.size() == 20) trước khi hiện nút Next -->
            <a href="${pageContext.request.contextPath}/products?offset=${offset + 20}${filterParams}">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10"/><path d="m12 16 4-4-4-4"/><path d="M8 12h8"/>
                </svg>
            </a>
        </c:if>
    </div>

</main>

<%@ include file="/views/fragments/footer.jsp" %>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        // --- CHỈ GIỮ LẠI LOGIC FORMAT GIÁ TIỀN ---
        // (Logic lọc sản phẩm đã chuyển về cho Server xử lý qua form)
        const initPriceFormatting = () => {
            const priceElements = document.querySelectorAll('.js-format-price');

            priceElements.forEach(el => {
                const rawPrice = Number(el.dataset.price);

                if (!isNaN(rawPrice) && rawPrice > 0) {
                    el.textContent = new Intl.NumberFormat('vi-VN', {
                        style: 'currency',
                        currency: 'VND'
                    }).format(rawPrice);
                }
            });
        };

        initPriceFormatting();
    });
</script>

</body>
</html>