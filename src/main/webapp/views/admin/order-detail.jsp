<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Đơn hàng #${order.orderCode} - Atelier</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/order-detail-management.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="page-header">
        <div class="header-title-group">
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn-back">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <line x1="19" y1="12" x2="5" y2="12"></line>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                Quay lại
            </a>
            <h1>Đơn hàng #${order.orderCode}</h1>

            <!-- Badge Order Status -->
            <c:if test="${order.orderStatus.name() == 'PENDING'}"><span class="badge pending">Chờ xác nhận</span></c:if>
            <c:if test="${order.orderStatus.name() == 'SHIPPING'}"><span
                    class="badge shipping">Đang giao hàng</span></c:if>
            <c:if test="${order.orderStatus.name() == 'COMPLETED'}"><span
                    class="badge completed">Hoàn thành</span></c:if>
            <c:if test="${order.orderStatus.name() == 'CANCELLED'}"><span class="badge cancelled">Đã hủy</span></c:if>
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
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                </svg>
                Thông tin khách hàng
            </h3>
            <div class="info-row">
                <span class="info-label">Họ và tên</span>
                <span class="info-value">${order.customerName}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Số điện thoại</span>
                <span class="info-value">${order.customerPhone}</span>
            </div>
            <div class="info-row">
                <span class="info-label">UID: </span>
                <span class="info-value">${order.userId != null ? order.userId : 'Không có'}</span>
            </div>
        </div>

        <!-- Giao hàng -->
        <div class="info-card">
            <h3>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2">
                    <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
                    <circle cx="12" cy="10" r="3"></circle>
                </svg>
                Địa chỉ giao hàng
            </h3>
            <div class="info-row">
                <span class="info-label">Địa chỉ chi tiết</span>
                <span class="info-value">${order.customerAddress}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Ghi chú của khách hàng</span>
                <span class="info-value" style="font-weight: 400; font-style: italic;">
                    ${order.customerNote != null && order.customerNote != '' ? order.customerNote : 'Không có ghi chú'}
                </span>
            </div>
            <c:if test="${order.orderStatus.name() == 'CANCELLED'}">
                <div class="info-row">
                    <span class="info-label">Lý do hủy đơn: [ ${order.cancelledAt != null ? order.cancelledAt : 'Không có'} ]</span>
                    <span class="info-value" style="color: #c62828;">
                            ${order.cancelReason != null && order.cancelReason != '' ? order.cancelReason : 'Không có lý do hủy'}
                    </span>
                </div>
            </c:if>
        </div>

        <!-- Thanh toán & Đơn hàng -->
        <div class="info-card">
            <h3>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2">
                    <rect x="2" y="5" width="20" height="14" rx="2" ry="2"></rect>
                    <line x1="2" y1="10" x2="22" y2="10"></line>
                </svg>
                Thanh toán
            </h3>
            <div class="info-row">
                <span class="info-label">Ngày đặt hàng</span>
                <span class="info-value">${order.createdAt}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Phương thức thanh toán</span>
                <span class="info-value">${order.paymentMethod.name()}</span>
            </div>
            <div class="info-row">
                <span class="info-label">Trạng thái thanh toán</span>
                <c:choose>
                    <c:when test="${order.paymentStatus.name() == 'PAID'}">
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
                            <img src="${pageContext.request.contextPath}/image?name=${item.imageUrl}"
                                 alt="${item.productName}" class="product-img">
                            <div>
                                <div class="product-name">${item.productName}</div>
                                <div class="product-variant">${item.colorName} ${item.versionName}</div>
                            </div>
                        </div>
                    </td>
                    <td style="text-align: center;" class="js-format-price" data-price="${item.price}"></td>
                    <td style="text-align: center;">x${item.quantity}</td>
                    <td style="text-align: right; font-weight: 600;" class="js-format-price"
                        data-price="${item.price * item.quantity}"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Box Tổng kết -->
        <div style="display: flex; justify-content: flex-end; margin-top: 25px;">
            <div class="order-summary">
                <div class="summary-row">
                    <span>Tạm tính:</span>
                    <span class="js-format-price" data-price="${order.totalAmount}"></span>
                </div>
                <div class="summary-row">
                    <span>Phí giao hàng:</span>
                    <span class="js-format-price" data-price="0"></span>
                </div>
                <div class="summary-row total">
                    <span>Tổng cộng:</span>
                    <span class="js-format-price" data-price="${order.totalAmount}"></span>
                </div>
            </div>
        </div>

        <c:if test="${order.orderStatus.name() != 'CANCELLED' && order.orderStatus.name() != 'COMPLETED'}">
            <!-- ================= HAI FORM CẬP NHẬT TRẠNG THÁI ================= -->
            <div class="admin-actions-container">

                <c:if test="${order.paymentStatus.name() != 'PAID'}">
                    <div class="action-box">
                        <h4>Trạng thái thanh toán</h4>
                        <form action="${pageContext.request.contextPath}/admin/order/update-payment" method="POST"
                              class="action-form">
                            <input type="hidden" name="id" value="${order.id}">
                            <select name="paymentStatus" class="form-select">
                                <option value="UNPAID" ${order.paymentStatus.name() == 'UNPAID' ? 'selected' : ''}>Chưa
                                    thanh toán
                                </option>
                                <option value="PAID" ${order.paymentStatus.name() == 'PAID' ? 'selected' : ''}>Đã thanh
                                    toán
                                </option>
                            </select>
                            <button type="submit" class="btn btn-outline">Lưu</button>
                        </form>
                    </div>
                </c:if>
                <!-- FORM 1: CẬP NHẬT TRẠNG THÁI THANH TOÁN -->


                <!-- FORM 2: CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG -->
                <div class="action-box">
                    <h4>Trạng thái đơn hàng</h4>
                    <form action="${pageContext.request.contextPath}/admin/order/update-status" method="POST"
                          class="action-form">
                        <input type="hidden" name="id" value="${order.id}">
                        <select name="orderStatus" class="form-select">
                            <option value="PENDING" ${order.orderStatus.name() == 'PENDING' ? 'selected' : ''}>Chờ xác
                                nhận
                            </option>
                            <option value="SHIPPING" ${order.orderStatus.name() == 'SHIPPING' ? 'selected' : ''}>Đang
                                giao hàng
                            </option>
                            <option value="COMPLETED" ${order.orderStatus.name() == 'COMPLETED' ? 'selected' : ''}>
                                Hoàn thành
                            </option>
                            <c:if test="${order.paymentStatus.name() != 'PAID'}">
                                <option value="CANCELLED" ${order.orderStatus.name() == 'CANCELLED' ? 'selected' : ''}>Đã
                                    hủy
                                </option>
                            </c:if>
                        </select>
                        <button type="submit" class="btn btn-primary">Lưu</button>
                    </form>
                </div>

            </div>
        </c:if>
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