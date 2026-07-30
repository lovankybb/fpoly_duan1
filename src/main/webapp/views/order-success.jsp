<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt hàng thành công — Atelier.</title>

    <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;0,700;1,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="${pageContext.request.contextPath}/styles/order-success.css" rel="stylesheet">
</head>
<body>

<%@include file="fragments/header.jsp"%>

<main class="success-container">
    <div class="success-card">
        <div class="icon-wrapper">
            <i class="fa-solid fa-check"></i>
        </div>

        <h1 class="success-title">Cảm ơn bạn đã đặt hàng!</h1>
        <p class="success-desc">
            Đơn hàng của bạn đã được tiếp nhận và đang trong quá trình xử lý. Chúng tôi sẽ liên hệ với bạn sớm nhất để xác nhận đơn hàng.
        </p>

        <!-- Khối thông tin tóm tắt đơn hàng -->
        <div class="order-info-box">
            <div class="info-row">
                <span class="info-label">Mã đơn hàng:</span>
                <span class="info-value">#${orderCode}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Phương thức thanh toán:</span>
                <span class="info-value">${paymentMethod}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Tổng thanh toán:</span>
                <span class="info-value" style="color: #d32f2f;">
          <fmt:formatNumber value="${totalAmount != null ? totalAmount : subTotal}" type="currency" currencySymbol="" maxFractionDigits="0"/> ₫
        </span>
            </div>
        </div>

        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">
                <i class="fa-solid fa-house"></i> Trang chủ
            </a>
            <a href="${pageContext.request.contextPath}/products?offset=0" class="btn btn-primary">
                Tiếp tục mua sắm <i class="fa-solid fa-arrow-right"></i>
            </a>
        </div>
    </div>
</main>

<%@include file="fragments/footer.jsp" %>

</body>
</html>