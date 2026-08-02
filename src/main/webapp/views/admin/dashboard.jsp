<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Atelier</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;1,600&display=swap"
          rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dashboard.css">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="dashboard-header">
        <h1>Tổng quan Hệ thống</h1>
        <div class="admin-profile">
            <span>Chào, ${not empty authentication ? authentication.username : 'Admin'}</span>
            <div class="admin-avatar">AD</div>
        </div>
    </header>

    <div class="stats-grid">
        <div class="stat-card">
            <span class="title">Doanh thu tháng này</span>
            <span class="value js-format-price" data-price="${revenue.monthRevenue}"></span>
            <span class="trend up" >Tổng đơn hoàn thành: ${revenue.orderCount}</span>
        </div>
        <div class="stat-card">
            <span class="title">Tổng đơn hàng tháng này</span>
            <span class="value">${totalOrder}</span>
            <span class="trend up">Hoàn thành: ${revenue.orderCount}</span>
            <span class="trend down">Chưa hoàn thành: ${totalOrder - revenue.orderCount}</span>
        </div>
        <div class="stat-card">
            <span class="title">Khách hàng</span>
            <span class="value">${newUserCount}</span>
            <span class="trend up">Khách hàng đăng ký mới</span>
        </div>
        <div class="stat-card">
            <span class="title">Sản phẩm sắp hết</span>
            <span class="value">${lowStockCount}</span>
            <span class="trend down">Cần nhập thêm hàng</span>
        </div>
    </div>

    <div class="recent-orders">
        <h2>Đơn hàng mới nhất</h2>
        <table>
            <thead>
            <tr>
                <th>Mã ĐH</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Thanh toán</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.orderCode}</td>
                    <td>${order.customerName}</td>
                    <td><span class="value js-format-price" data-price="${order.totalAmount}"></span></td>
                    <td>
                        <c:choose>
                            <c:when test="${order.orderStatus.name() == 'PENDING'}">
                                <span class="badge pending">Chờ xử lý</span>
                            </c:when>
                            <c:when test="${order.orderStatus.name() == 'SHIPPING'}">
                                <span class="badge shipping">Đang giao hàng</span>
                            </c:when>
                            <c:when test="${order.orderStatus.name() == 'COMPLETED'}">
                                <span class="badge success">Hoàn thành</span>
                            </c:when>
                            <c:when test="${order.orderStatus.name() == 'CANCELLED'}">
                                <span class="badge cancel">Đã hủy</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge unknown">Không xác định</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${order.paymentStatus.name() == 'PAID'}">
                                <span style="color: #2e7d32;">Đã thanh toán</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: #c6c328;">Chưa thanh toán</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/admin/order/detail?id=${order.id}"
                           class="btn-action">Chi tiết</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</main>

</body>
<script>
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
</html>