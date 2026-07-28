<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Thanh toán — Atelier.</title>

  <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;0,700;1,400&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link href="${pageContext.request.contextPath}/styles/prod-detail.css" rel="stylesheet">

  <style>
    .checkout-container {
      max-width: 1200px;
      margin: 40px auto 80px;
      padding: 0 20px;
    }
    .page-header {
      margin-bottom: 32px;
      border-bottom: 1px solid #E5E7EB;
      padding-bottom: 16px;
    }
    .page-title {
      font-family: 'Playfair Display', serif;
      font-size: 2.2rem;
      font-weight: 700;
      color: #111111;
      margin: 0;
    }
    /* Grid 2 cột */
    .checkout-grid {
      display: grid;
      grid-template-columns: 1fr 420px;
      gap: 48px;
      align-items: start;
    }
    @media (max-width: 900px) {
      .checkout-grid { grid-template-columns: 1fr; }
    }

    /* Section Title */
    .section-title {
      font-size: 1.15rem;
      font-weight: 600;
      color: #111111;
      margin-bottom: 20px;
      display: flex;
      align-items: center;
      gap: 10px;
    }
    .section-title i { font-size: 1rem; color: #6B7280; }

    /* Form Inputs style Atelier */
    .form-row {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 16px;
    }
    @media (max-width: 600px) {
      .form-row { grid-template-columns: 1fr; }
    }
    .form-group {
      margin-bottom: 20px;
    }
    .form-label {
      display: block;
      font-size: 0.85rem;
      font-weight: 600;
      color: #374151;
      margin-bottom: 8px;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    .form-control {
      width: 100%;
      padding: 14px 16px;
      font-family: 'Be Vietnam Pro', sans-serif;
      font-size: 0.95rem;
      color: #111111;
      background-color: #FFFFFF;
      border: 1.5px solid #E5E7EB;
      border-radius: 8px;
      outline: none;
      transition: all 0.2s ease;
      box-sizing: border-box;
    }
    .form-control:focus {
      border-color: #111111;
      box-shadow: 0 0 0 3px rgba(0, 0, 0, 0.05);
    }
    .form-control::placeholder { color: #9CA3AF; font-weight: 300; }
    textarea.form-control {
      resize: vertical;
      min-height: 100px;
    }

    /* Payment Methods Radio */
    .payment-methods {
      display: flex;
      flex-direction: column;
      gap: 12px;
      margin-bottom: 32px;
    }
    .payment-option {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 16px;
      border: 1.5px solid #E5E7EB;
      border-radius: 8px;
      cursor: pointer;
      transition: all 0.2s;
    }
    .payment-option:hover { border-color: #9CA3AF; }
    .payment-option input[type="radio"] {
      accent-color: #111111;
      width: 18px;
      height: 18px;
    }
    .payment-option.active {
      border-color: #111111;
      background-color: #F9FAFB;
    }
    .payment-label { font-weight: 600; font-size: 0.95rem; color: #111111; }
    .payment-desc { font-size: 0.8rem; color: #6B7280; margin-top: 2px; }

    /* Order Summary Card (Cột phải) */
    .summary-card {
      background-color: #F9FAFB;
      border: 1px solid #E5E7EB;
      border-radius: 12px;
      padding: 28px;
      position: sticky;
      top: 24px;
    }
    .summary-items {
      max-height: 320px;
      overflow-y: auto;
      margin-bottom: 24px;
      padding-right: 8px;
    }
    .summary-item {
      display: flex;
      gap: 14px;
      margin-bottom: 16px;
      padding-bottom: 16px;
      border-bottom: 1px dashed #E5E7EB;
    }
    .summary-item:last-child { margin-bottom: 0; padding-bottom: 0; border-bottom: none; }
    .item-img {
      width: 64px;
      height: 64px;
      border-radius: 6px;
      object-fit: cover;
      border: 1px solid #E5E7EB;
      background: #FFF;
    }
    .item-info { flex-grow: 1; }
    .item-title { font-weight: 600; font-size: 0.9rem; color: #111111; margin: 0 0 4px; }
    .item-variant { font-size: 0.8rem; color: #6B7280; margin: 0; }
    .item-price { font-weight: 600; font-size: 0.9rem; color: #111111; text-align: right; }

    /* Calculation rows */
    .calc-row {
      display: flex;
      justify-content: space-between;
      font-size: 0.95rem;
      color: #4B5563;
      margin-bottom: 12px;
    }
    .calc-row.total {
      font-size: 1.25rem;
      font-weight: 700;
      color: #111111;
      border-top: 1px solid #E5E7EB;
      padding-top: 16px;
      margin-top: 16px;
      margin-bottom: 24px;
    }

    /* Place Order Button */
    .btn-place-order {
      width: 100%;
      background-color: #111111;
      color: #FFFFFF;
      border: 1.5px solid #111111;
      font-weight: 600;
      font-size: 1.05rem;
      padding: 18px 24px;
      border-radius: 9999px;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 12px;
      cursor: pointer;
      transition: all 0.2s ease;
      box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
    }
    .btn-place-order:hover {
      background-color: #333333;
      border-color: #333333;
      transform: translateY(-2px);
    }
  </style>
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
                   placeholder="VD: Nguyễn Văn A" required value="${sessionScope.user.name}">
          </div>

          <div class="form-group">
            <label class="form-label" for="phone">Số điện thoại *</label>
            <input type="tel" id="phone" name="phone" class="form-control" value="${customerPhone}"
                   placeholder="VD: 0912345678" required pattern="[0-9]{9,11}" value="${sessionScope.user.phone}">
          </div>
        </div>

        <div class="form-group">
          <label class="form-label" for="address">Địa chỉ nhận hàng *</label>
          <input type="text" id="address" name="address" class="form-control" value="${customerAddress}"
                 placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố..." required>
        </div>

        <div class="form-group">
          <label class="form-label" for="note">Ghi chú đơn hàng (Tùy chọn)</label>
          <textarea id="note" name="note" class="form-control"
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
            <input type="radio" name="paymentMethod" value="BANKING">
            <div>
              <div class="payment-label">Chuyển khoản Ngân hàng / QR Code</div>
              <div class="payment-desc">Quét mã VietQR tự động xác nhận qua cổng thanh toán.</div>
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
          <span>Hoàn tất đặt hàng</span>
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