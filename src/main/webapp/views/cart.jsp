<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng của bạn — Atelier.</title>

    <!-- Google Fonts & FontAwesome -->
    <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;0,700;1,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- 1. CSS dùng chung toàn dự án (Reset, Header, Footer, Font...) -->
    <link href="${pageContext.request.contextPath}/styles/base.css" rel="stylesheet">

    <!-- 2. CSS riêng cho trang Giỏ hàng -->
    <link href="${pageContext.request.contextPath}/styles/cart.css" rel="stylesheet">
</head>
<body>

<%@include file="fragments/header.jsp"%>

<main class="cart-container">

    <!-- TRƯỜNG HỢP 1: GIỎ HÀNG TRỐNG -->
    <c:if test="${empty cartItems}">
        <div class="empty-cart">
            <div class="empty-icon">
                <i class="fa-solid fa-bag-shopping"></i>
            </div>
            <h2 class="empty-title">Giỏ hàng đang trống</h2>
            <p class="empty-desc">Bạn chưa có sản phẩm nào trong giỏ hàng. Hãy khám phá bộ sưu tập công nghệ tinh tế nhất của Atelier ngay hôm nay.</p>
            <a href="${pageContext.request.contextPath}/shop" class="btn-shop-now">
                <span>Khám phá cửa hàng</span>
                <i class="fa-solid fa-arrow-right"></i>
            </a>
        </div>
    </c:if>

    <!-- TRƯỜNG HỢP 2: GIỎ HÀNG CÓ SẢN PHẨM -->
    <c:if test="${not empty cartItems}">
        <div class="page-header">
            <h1 class="page-title">Giỏ hàng của bạn</h1>
            <span class="item-count-badge">(${fn:length(cartItems)} sản phẩm)</span>
        </div>

        <div class="cart-grid">

            <!-- CỘT TRÁI: DANH SÁCH ITEM -->
            <div class="cart-list">
                <c:set var="totalAmount" value="0" />

                <c:forEach items="${cartItems}" var="item">
                    <c:set var="itemTotal" value="${item.price * item.quantity}" />
                    <c:set var="totalAmount" value="${totalAmount + itemTotal}" />

                    <div class="cart-item">
                        <!-- 1. Ảnh thumbnail -->
                        <a href="${pageContext.request.contextPath}/product/detail?id=${item.productId}" class="item-img-wrapper">
                            <img src="${pageContext.request.contextPath}/image?name=${item.imageUrl}" alt="${item.productName}">
                        </a>

                        <!-- 2. Thông tin sản phẩm -->
                        <div class="item-info">
                            <a href="${pageContext.request.contextPath}/product/detail?id=${item.productId}" class="item-name">
                                    ${item.productName}
                            </a>
                            <span class="item-variant">Phiên bản: <b>${item.colorName} / ${item.versionName}</b></span>
                            <span class="item-price-unit">
                                    Đơn giá: <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </span>
                        </div>

                        <!-- 3. Actions -->
                        <div class="item-actions" style="display: contents;">
                            <!-- Tăng giảm số lượng -->
                            <div class="quantity-box">
                                <button type="button" onclick="updateCartItem(${item.variantId}, -1, ${item.quantity})" class="qty-btn" title="Giảm 1">
                                    <i class="fa-solid fa-minus"></i>
                                </button>
                                <input type="text" value="${item.quantity}" class="qty-input" readonly>
                                <button type="button" onclick="updateCartItem(${item.variantId}, 1, ${item.quantity})" class="qty-btn" title="Tăng 1">
                                    <i class="fa-solid fa-plus"></i>
                                </button>
                            </div>

                            <!-- Thành tiền -->
                            <div class="item-subtotal">
                                <fmt:formatNumber value="${itemTotal}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                            </div>

                            <!-- Nút xóa -->
                            <button type="button" onclick="removeCartItem(${item.variantId})" class="btn-remove" title="Xóa khỏi giỏ">
                                <i class="fa-regular fa-trash-can"></i>
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- CỘT PHẢI: TÓM TẮT ĐƠN HÀNG -->
            <div class="summary-card">
                <h3 class="summary-title">Tóm tắt đơn hàng</h3>

                <div class="calc-row">
                    <span>Tạm tính</span>
                    <span><fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span>
                </div>
                <div class="calc-row">
                    <span>Phí vận chuyển</span>
                    <span>Miễn phí</span>
                </div>
                <div class="calc-row total">
                    <span>Tổng thanh toán</span>
                    <span><fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span>
                </div>

                <!-- Chuyển hướng sang trang Thanh Toán (checkout.jsp) -->
                <a href="${pageContext.request.contextPath}/checkout" class="btn-checkout">
                    <span>Tiến hành thanh toán</span>
                    <i class="fa-solid fa-arrow-right"></i>
                </a>

                <a href="${pageContext.request.contextPath}/shop" class="btn-continue">
                    Tiếp tục mua sắm
                </a>
            </div>

        </div>
    </c:if>

</main>

<!-- FORM ẨN ĐỂ GỬI REQUEST UPDATE / DELETE VỀ BACKEND -->
<form id="cartActionForm" method="POST" style="display: none;">
    <input type="hidden" name="variantId" id="actionVariantId">
    <input type="hidden" name="quantity" id="actionQuantity">
</form>

<%@include file="fragments/footer.jsp" %>

<script>
    // Cập nhật số lượng (+1 hoặc -1)
    function updateCartItem(variantId, change, currentQty) {
        const newQty = currentQty + change;

        // Nếu giảm về 0 thì hỏi khách có muốn xóa không
        if (newQty < 1) {
            removeCartItem(variantId);
            return;
        }

        const form = document.getElementById('cartActionForm');
        form.action = '${pageContext.request.contextPath}/update-cart';
        document.getElementById('actionVariantId').value = variantId;
        document.getElementById('actionQuantity').value = newQty;
        form.submit();
    }

    // Xóa sản phẩm khỏi giỏ
    function removeCartItem(variantId) {
        if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?")) {
            const form = document.getElementById('cartActionForm');
            form.action = '${pageContext.request.contextPath}/remove-from-cart';
            document.getElementById('actionVariantId').value = variantId;
            form.submit();
        }
    }
</script>
</body>
</html>