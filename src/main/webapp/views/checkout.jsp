<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đặt hàng — Atelier.</title>

  <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;0,700;1,400&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link href="${pageContext.request.contextPath}/styles/checkout.css" rel="stylesheet">

</head>
<body>

<%@include file="fragments/header.jsp"%>

<main class="checkout-container">
  <div class="page-header">
    <h1 class="page-title">Thanh toán đơn hàng</h1>
  </div>

  <form action="${pageContext.request.contextPath}/place-order" method="POST" id="checkoutForm">
    <div class="checkout-grid">

      <div class="form-section">
        <h2 class="section-title">
          <i class="fa-solid fa-location-dot"></i> Thông tin giao hàng
        </h2>

        <div class="form-row">
          <div class="form-group">
            <label class="form-label" for="customerName">Họ và tên *</label>
            <input type="text" id="customerName" name="customerName" class="form-control" value="${customerName}"
                   placeholder="VD: Nguyễn Văn A" required>
          </div>

          <div class="form-group">
            <label class="form-label" for="phone">Số điện thoại *</label>
            <input type="tel" id="phone" name="customerPhone" class="form-control" value="${customerPhone}"
                   placeholder="VD: 0912345678" required pattern="[0-9]{9,11}" value="${sessionScope.user.phone}">
          </div>
        </div>

        <div class="form-group">
          <label class="form-label" for="address">Địa chỉ nhận hàng *</label>
          <input type="text" id="address" name="customerAddress" class="form-control" value="${customerAddress}"
                 placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố..." required>
        </div>

        <div class="form-group">
          <label class="form-label" for="note">Ghi chú đơn hàng (Tùy chọn)</label>
          <textarea id="note" name="customerNote" class="form-control"
                    placeholder="VD: Giao hàng trong giờ hành chính, gọi trước khi đến..."></textarea>
        </div>

        <h2 class="section-title" style="margin-top: 36px;">
          <i class="fa-solid fa-credit-card"></i> Phương thức thanh toán
        </h2>

        <div class="payment-methods">
          <label class="payment-option active" onclick="selectPayment(this)">
            <input type="radio" name="paymentMethod" value="COD" checked>
            <div>
              <div class="payment-label">Thanh toán khi nhận hàng (COD)</div>
              <div class="payment-desc">Khách hàng kiểm tra và thanh toán trực tiếp cho shipper.</div>
            </div>
          </label>

          <label class="payment-option" onclick="selectPayment(this)">
            <input type="radio" name="paymentMethod" value="VNPAY">
            <div>
              <div class="payment-label">VN Pay</div>
              <div class="payment-desc">Thanh toán bằng VnPay.</div>
            </div>
          </label>
        </div>
      </div>

      <div class="summary-card">
        <h2 class="section-title">
          <i class="fa-solid fa-bag-shopping"></i> Đơn hàng của bạn
        </h2>

        <div class="summary-items">
          <c:forEach items="${checkoutItems}" var="item">
            <div class="summary-item">
              <img src="${pageContext.request.contextPath}/image?name=${item.imageUrl}" alt="${item.productName}" class="item-img">
              <div class="item-info">
                <h4 class="item-title">${item.productName}</h4>
                <p class="item-variant">${item.colorName} / ${item.versionName} × <b>${item.quantity}</b></p>
              </div>
              <div class="item-price">
                <fmt:formatNumber value="${item.price * item.quantity}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
              </div>
            </div>
          </c:forEach>
        </div>

        <div class="summary-calculations">
          <div class="calc-row">
            <span>Tạm tính</span>
            <span><fmt:formatNumber value="${subTotal}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span>
          </div>
          <div class="calc-row">
            <span>Phí vận chuyển</span>
            <span>Miễn phí</span>
          </div>
          <div class="calc-row total">
            <span>Tổng thanh toán</span>
            <span><fmt:formatNumber value="${subTotal}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span>
          </div>
        </div>

        <button type="submit" class="btn-place-order">
          <span>Đặt hàng</span>
          <i class="fa-solid fa-arrow-right"></i>
        </button>
      </div>

    </div>
  </form>
</main>

<%@include file="fragments/footer.jsp" %>

<script>
  // Hiệu ứng đổi màu viền khi chọn phương thức thanh toán
  function selectPayment(selectedLabel) {
    document.querySelectorAll('.payment-option').forEach(el => el.classList.remove('active'));
    selectedLabel.classList.add('active');
    const radio = selectedLabel.querySelector('input[type="radio"]');
    if(radio) radio.checked = true;
  }

  // Chống bấm nút Đặt Hàng nhiều lần (Prevent Double Submit)
  document.getElementById('checkoutForm').addEventListener('submit', function(e) {
    const btn = this.querySelector('.btn-place-order');
    if (btn) {
      btn.style.opacity = '0.7';
      btn.style.pointerEvents = 'none';
      btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> <span>Đang xử lý đơn hàng...</span>';
    }
  });
</script>
</body>
</html>