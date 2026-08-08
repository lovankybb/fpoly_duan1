<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Đơn hàng - Atelier</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/order.css" rel="stylesheet">

</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="page-header">
        <h1>Quản lý Đơn hàng</h1>
    </header>

    <c:if test="${not empty alertMsg}">
        <div class="alert">${alertMsg}</div>
    </c:if>

    <div class="card">
        <div class="toolbar">
            <div class="search-box">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
                <input type="text" id="searchInput" placeholder="Nhập mã đơn hoặc tên khách hàng...">
            </div>

            <div class="filters">
                <span style="font-size: 13px; color: #888;">Tổng: ${orders.size()} đơn hàng</span>
            </div>
        </div>

        <table>
            <thead>
            <tr>
                <th width="15%">Mã đơn</th>
                <th width="20%">Khách hàng</th>
                <th width="20%">Giá trị đơn</th>
                <th width="15%">Thanh toán</th>
                <th width="15%">Trạng thái</th>
                <th width="15%">Thao tác</th>
            </tr>
            </thead>
            <tbody id="orderTableBody">

            <c:forEach var="order" items="${orders}">
                <tr class="order-row">
                    <!-- Cột Mã đơn -->
                    <td>
                        <div class="order-code search-target">#${order.orderCode}</div>
                        <div class="order-date">${order.createdAt}</div>
                    </td>

                    <!-- Cột Khách hàng -->
                    <td>
                        <div class="customer-name search-target">${order.customerName}</div>
                        <div class="customer-contact">${order.customerPhone}</div>
                    </td>

                    <!-- Cột Giá trị -->
                    <td>
                        <div class="total-price js-format-price" data-price="${order.totalAmount}"></div>
<%--                        <div style="font-size: 12px; color: #888; margin-top: 4px;">${order.totalItems} sản phẩm</div>--%>
                    </td>

<%--                    <!-- Cột Thanh toán -->--%>
                    <td>
                        <div style="font-size: 13px; font-weight: 500;">${order.paymentMethod.name()}</div>
                        <div class="payment-method">
                            <c:choose>
                                <c:when test="${order.paymentStatus.name() == 'PAID'}">
                                    <span style="color: #2e7d32;">Đã thanh toán</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: #c6c328;">Chưa thanh toán</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>

                    <!-- Cột Trạng thái -->
                    <td>
                        <c:if test="${order.orderStatus.name() == 'PENDING'}">
                            <span class="badge pending">Chờ xác nhận</span>
                        </c:if>
                        <c:if test="${order.orderStatus.name() == 'SHIPPING'}">
                            <span class="badge shipping">Đang giao hàng</span>
                        </c:if>
                        <c:if test="${order.orderStatus.name() == 'COMPLETED'}">
                            <span class="badge completed">Hoàn thành</span>
                        </c:if>
                        <c:if test="${order.orderStatus.name() == 'CANCELLED'}">
                            <span class="badge cancelled">Đã hủy</span>
                        </c:if>
                    </td>

<%--                    <!-- Cột Thao tác -->--%>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/order/detail?id=${order.id}" class="btn-action btn-view">Chi tiết</a>

                        <c:if test="${order.orderStatus.name() != 'CANCELLED' && order.orderStatus.name() != 'COMPLETED'}">
                            <button class="btn-action btn-cancel" value="${order.id}">Hủy</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <div id="noResultMessage" class="no-result">
            Không tìm thấy đơn hàng nào phù hợp với từ khóa của bạn.
        </div>
    </div>

    <!-- Phân trang -->
    <div class="pagination-container">
        <c:if test="${offset gt 0}">
            <a href="${pageContext.request.contextPath}/admin/orders?offset=${offset - 10}" title="Trang trước">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10"/>
                    <path d="m12 8-4 4 4 4"/>
                    <path d="M16 12H8"/>
                </svg>
            </a>
        </c:if>
        <a href="${pageContext.request.contextPath}/admin/orders?offset=${offset + 10}" title="Trang sau">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/>
                <path d="m12 16 4-4-4-4"/>
                <path d="M8 12h8"/>
            </svg>
        </a>
    </div>
</main>

<script>
    // Xử lý Tìm kiếm (Theo mã đơn hoặc tên KH)
    const searchInput = document.getElementById('searchInput');
    const orderRows = document.querySelectorAll('.order-row');
    const noResultMessage = document.getElementById('noResultMessage');

    searchInput.addEventListener('input', function (e) {
        const searchTerm = e.target.value.toLowerCase().trim();
        let hasVisibleRow = false;

        orderRows.forEach(function (row) {
            // Gom tất cả các text có class search-target (Mã đơn + Tên Khách hàng) lại để tìm kiếm
            const targets = row.querySelectorAll('.search-target');
            let match = false;

            targets.forEach(target => {
                if(target.textContent.toLowerCase().includes(searchTerm)) {
                    match = true;
                }
            });

            if (match) {
                row.style.display = '';
                hasVisibleRow = true;
            } else {
                row.style.display = 'none';
            }
        });

        if (!hasVisibleRow) {
            noResultMessage.style.display = 'block';
        } else {
            noResultMessage.style.display = 'none';
        }
    });

    // Xử lý nút Hủy đơn hàng
    const cancelButtons = document.querySelectorAll('.btn-cancel');
    cancelButtons.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();

            // Hiện popup nhập lý do
            let reason = prompt('Vui lòng nhập lý do hủy đơn hàng:');

            // Kiểm tra người dùng có bấm OK và có nhập nội dung không (tránh trường hợp để trống)
            if (reason !== null) {
                reason = reason.trim();
                if (reason === "") {
                    alert("Bạn phải nhập lý do mới có thể hủy đơn!");
                    return;
                }

                // Lấy orderId (dùng currentTarget để tránh lỗi click trúng thẻ con bên trong button)
                const orderId = e.currentTarget.value;

                // Chuyển hướng kèm thêm query parameter 'reason'
                window.location.href ="${pageContext.request.contextPath}/admin/order/cancel?orderId=" + orderId + "&reason=" + encodeURIComponent(reason);
            }
        });
    });

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