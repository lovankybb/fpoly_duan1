<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Đơn hàng #${order.code} - Atelier</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Plus Jakarta Sans', sans-serif;
            background-color: #f4f6f9;
            color: #1a1a1a;
        }

        .admin-main-content {
            margin-left: 260px;
            padding: 30px 40px;
            min-height: 100vh;
        }

        /* Header */
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .header-title-group {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .page-header h1 {
            font-size: 24px;
            font-weight: 700;
        }

        .btn-back {
            background-color: #ffffff;
            color: #1a1a1a;
            border: 1px solid #eaeaea;
            padding: 10px 16px;
            border-radius: 8px;
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 6px;
            transition: 0.3s;
        }

        .btn-back:hover {
            background-color: #f4f6f9;
        }

        /* Badges trạng thái */
        .badge {
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: 600;
            display: inline-block;
        }
        .badge.completed { background: #e8f5e9; color: #2e7d32; }
        .badge.cancelled { background: #ffebee; color: #c62828; }
        .badge.pending { background: #fffaeb; color: #c6c328; }
        .badge.shipping { background: #e3f2fd; color: #1565c0; }

        /* Grid Thông tin */
        .info-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 20px;
        }

        .info-card {
            background-color: #ffffff;
            border-radius: 12px;
            border: 1px solid #eaeaea;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.02);
        }

        .info-card h3 {
            font-size: 14px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .info-row {
            margin-bottom: 12px;
            font-size: 14px;
            display: flex;
            flex-direction: column;
            gap: 4px;
        }
        .info-row:last-child { margin-bottom: 0; }
        .info-label { color: #888; font-size: 13px;}
        .info-value { font-weight: 600; color: #1a1a1a; line-height: 1.4; }

        /* Card Danh sách sản phẩm */
        .card {
            background-color: #ffffff;
            border-radius: 16px;
            border: 1px solid #eaeaea;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.02);
            padding: 25px;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 15px 10px;
            text-align: left;
            font-size: 14px;
            border-bottom: 1px solid #eaeaea;
            vertical-align: middle;
        }

        th {
            color: #888;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 12px;
            letter-spacing: 0.5px;
        }

        .product-info { display: flex; align-items: center; }
        .product-img {
            width: 50px; height: 50px;
            object-fit: contain; background-color: #fbfbfc;
            border-radius: 8px; border: 1px solid #eaeaea;
            padding: 4px; margin-right: 15px;
        }
        .product-name { font-weight: 600; color: #1a1a1a; margin-bottom: 4px; }
        .product-variant { font-size: 12px; color: #888; }

        /* Tổng kết đơn hàng */
        .order-summary {
            width: 350px;
            margin-left: auto;
            background: #fbfbfc;
            padding: 20px;
            border-radius: 12px;
            border: 1px solid #eaeaea;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 12px;
            font-size: 14px;
            color: #555;
        }
        .summary-row.total {
            border-top: 1px solid #eaeaea;
            padding-top: 12px;
            margin-top: 12px;
            margin-bottom: 0;
            font-size: 18px;
            font-weight: 700;
            color: #cc2525;
        }

        /* ---------------- FORM ACTION MỚI ---------------- */
        .admin-actions-container {
            display: flex;
            gap: 20px;
            margin-top: 30px;
            padding-top: 25px;
            border-top: 1px solid #eaeaea;
        }

        .action-box {
            flex: 1;
            background-color: #fbfbfc;
            border: 1px solid #eaeaea;
            border-radius: 12px;
            padding: 20px;
        }

        .action-box h4 {
            font-size: 14px;
            color: #1a1a1a;
            margin-bottom: 12px;
            font-weight: 600;
        }

        .action-form {
            display: flex;
            gap: 12px;
        }

        .form-select {
            flex: 1;
            padding: 10px 15px;
            border: 1px solid #eaeaea;
            border-radius: 8px;
            font-family: inherit;
            font-size: 14px;
            background-color: #ffffff;
            outline: none;
            cursor: pointer;
            transition: all 0.3s;
        }

        .form-select:focus {
            border-color: #1a1a1a;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            border: none;
            transition: 0.3s;
            white-space: nowrap;
        }

        .btn-primary { background-color: #1a1a1a; color: #fff; }
        .btn-primary:hover { background-color: #333; }

        .btn-outline { background-color: #ffffff; color: #1a1a1a; border: 1px solid #eaeaea; }
        .btn-outline:hover { background-color: #f4f6f9; }

        .alert {
            padding: 14px 16px;
            border-radius: 5px;
            font-size: 14px;
            margin-bottom: 20px;
            font-weight: 500;
            background-color: #bcedbc;
            color: #32b302;
            border: 1px solid #598759;
        }
    </style>
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="page-header">
        <div class="header-title-group">
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn-back">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <line x1="19" y1="12" x2="5" y2="12"></line>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                Quay lại
            </a>
            <h1>Đơn hàng #${order.code}</h1>

            <!-- Badge Order Status -->
            <c:if test="${order.status == 'PENDING'}"><span class="badge pending">Chờ xác nhận</span></c:if>
            <c:if test="${order.status == 'SHIPPING'}"><span class="badge shipping">Đang giao hàng</span></c:if>
            <c:if test="${order.status == 'COMPLETED'}"><span class="badge completed">Hoàn thành</span></c:if>
            <c:if test="${order.status == 'CANCELLED'}"><span class="badge cancelled">Đã hủy</span></c:if>
        </div>
    </header>

    <c:if test="${not empty alertMsg}">
        <div class="alert">${alertMsg}</div>
    </c:if>

    <!-- Khối 3 Card Thông tin -->
    <div class="info-grid">
        <!-- Khách hàng -->
        <div class="info-card">
            <h3>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
                Thông tin khách hàng
            </h3>
            <div class="info-row">
                <span class="info-label">Họ và tên</span>
                <span class="info-value">${order.customerName}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Số điện thoại</span>
                <span class="info-value">${order.phone}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Email</span>
                <span class="info-value">${order.email != null ? order.email : 'Không có'}</span>
            </div>
        </div>

        <!-- Giao hàng -->
        <div class="info-card">
            <h3>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path><circle cx="12" cy="10" r="3"></circle></svg>
                Địa chỉ giao hàng
            </h3>
            <div class="info-row">
                <span class="info-label">Địa chỉ chi tiết</span>
                <span class="info-value">${order.address}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Ghi chú của khách hàng</span>
                <span class="info-value" style="font-weight: 400; font-style: italic;">
                    ${order.note != null && order.note != '' ? order.note : 'Không có ghi chú'}
                </span>
            </div>
        </div>

        <!-- Thanh toán & Đơn hàng -->
        <div class="info-card">
            <h3>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="2" y="5" width="20" height="14" rx="2" ry="2"></rect><line x1="2" y1="10" x2="22" y2="10"></line></svg>
                Thanh toán
            </h3>
            <div class="info-row">
                <span class="info-label">Ngày đặt hàng</span>
                <span class="info-value">${order.createdDate}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Phương thức thanh toán</span>
                <span class="info-value">${order.paymentMethod}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Trạng thái thanh toán</span>
                <c:choose>
                    <c:when test="${order.paymentStatus == 'PAID'}">
                        <span class="info-value" style="color: #2e7d32;">Đã thanh toán</span>
                    </c:when>
                    <c:otherwise>
                        <span class="info-value" style="color: #c6c328;">Chưa thanh toán</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- Bảng danh sách sản phẩm -->
    <div class="card">
        <h3 style="font-size: 16px; margin-bottom: 20px;">Sản phẩm đã đặt</h3>
        <table>
            <thead>
            <tr>
                <th width="45%">Sản phẩm</th>
                <th width="15%" style="text-align: center;">Đơn giá</th>
                <th width="15%" style="text-align: center;">Số lượng</th>
                <th width="25%" style="text-align: right;">Thành tiền</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${orderItems}">
                <tr>
                    <td>
                        <div class="product-info">
                            <img src="${pageContext.request.contextPath}/image?name=${item.productImage}" alt="${item.productName}" class="product-img">
                            <div>
                                <div class="product-name">${item.productName}</div>
                                <div class="product-variant">${item.variantName}</div>
                            </div>
                        </div>
                    </td>
                    <td style="text-align: center;" class="js-format-price" data-price="${item.price}"></td>
                    <td style="text-align: center;">x${item.quantity}</td>
                    <td style="text-align: right; font-weight: 600;" class="js-format-price" data-price="${item.price * item.quantity}"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Box Tổng kết -->
        <div style="display: flex; justify-content: flex-end; margin-top: 25px;">
            <div class="order-summary">
                <div class="summary-row">
                    <span>Tạm tính:</span>
                    <span class="js-format-price" data-price="${order.subTotal}"></span>
                </div>
                <div class="summary-row">
                    <span>Phí giao hàng:</span>
                    <span class="js-format-price" data-price="${order.shippingFee}"></span>
                </div>
                <div class="summary-row total">
                    <span>Tổng cộng:</span>
                    <span class="js-format-price" data-price="${order.totalAmount}"></span>
                </div>
            </div>
        </div>

        <!-- ================= HAI FORM CẬP NHẬT TRẠNG THÁI ================= -->
        <div class="admin-actions-container">

            <!-- FORM 1: CẬP NHẬT TRẠNG THÁI THANH TOÁN -->
            <div class="action-box">
                <h4>Trạng thái thanh toán</h4>
                <form action="${pageContext.request.contextPath}/admin/order/update-payment" method="POST" class="action-form">
                    <input type="hidden" name="id" value="${order.id}">
                    <select name="paymentStatus" class="form-select">
                        <option value="UNPAID" ${order.paymentStatus == 'UNPAID' ? 'selected' : ''}>Chưa thanh toán</option>
                        <option value="PAID" ${order.paymentStatus == 'PAID' ? 'selected' : ''}>Đã thanh toán</option>
                    </select>
                    <button type="submit" class="btn btn-outline">Lưu</button>
                </form>
            </div>

            <!-- FORM 2: CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG -->
            <div class="action-box">
                <h4>Trạng thái đơn hàng</h4>
                <form action="${pageContext.request.contextPath}/admin/order/update-status" method="POST" class="action-form">
                    <input type="hidden" name="id" value="${order.id}">
                    <select name="status" class="form-select">
                        <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>Chờ xác nhận</option>
                        <option value="SHIPPING" ${order.status == 'SHIPPING' ? 'selected' : ''}>Đang giao hàng</option>
                        <option value="COMPLETED" ${order.status == 'COMPLETED' ? 'selected' : ''}>Hoàn thành</option>
                        <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>Đã hủy</option>
                    </select>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </form>
            </div>

        </div>
    </div>
</main>

<script>
    // Format tiền tệ
    document.addEventListener("DOMContentLoaded", function () {
        const priceElements = document.querySelectorAll('.js-format-price');
        priceElements.forEach(function (el) {
            const rawPrice = Number(el.getAttribute('data-price'));
            if (!isNaN(rawPrice) && rawPrice >= 0) {
                el.innerText = rawPrice.toLocaleString('vi-VN') + ' ₫';
            }
        });
    });
</script>
</body>
</html>