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
    <select name="categoryId" class="filter-select"
            onchange="if(this.value) window.location.href=this.value;">
        <option value="${pageContext.request.contextPath}/admin?month=1"
        ${month == '1' ? 'selected' : ''}>
            Tháng 1
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=2"
        ${month == '2' ? 'selected' : ''}>
            Tháng 2
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=3"
        ${month == '3' ? 'selected' : ''}>
            Tháng 3
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=4"
        ${month == '4' ? 'selected' : ''}>
            Tháng 4
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=5"
        ${month == '5' ? 'selected' : ''}>
            Tháng 5
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=6"
        ${month == '6' ? 'selected' : ''}>
            Tháng 6
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=7"
        ${month == '7' ? 'selected' : ''}>
            Tháng 7
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=8"
        ${month == '8' ? 'selected' : ''}>
            Tháng 8
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=9"
        ${month == '9' ? 'selected' : ''}>
            Tháng 9
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=10"
        ${month == '10' ? 'selected' : ''}>
            Tháng 10
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=11"
        ${month == '11' ? 'selected' : ''}>
            Tháng 11
        </option>
        <option value="${pageContext.request.contextPath}/admin?month=12"
        ${month == '12' ? 'selected' : ''}>
            Tháng 12
        </option>
    </select>

    <div class="stats-grid">
        <div class="stat-card">
            <span class="title">Doanh thu tháng ${month}</span>
            <span class="value js-format-price" data-price="${revenue.monthRevenue}"></span>
            <span class="trend up">Tổng đơn hoàn thành: ${revenue.orderCount}</span>
        </div>
        <div class="stat-card">
            <span class="title">Tổng đơn hàng tháng ${month}</span>
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
        <h2>Top 5 sản phẩm tháng ${month}</h2>
        <table>
            <thead>
            <tr>
                <th>Tên sản phẩm</th>
                <th>Tồn kho</th>
                <th>Giá</th>
                <th>Đã bán</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${popularProducts}">
                <tr>
                    <td><strong>${product.name}</strong></td>
                    <td>${product.stock}</td>
                    <td><span style="color: #f43232" class="value js-format-price" data-price="${product.price}"></span>
                    </td>
                    <td><strong>${product.sold}</strong></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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
                    <td><span style="color: #f43232" class="value js-format-price"
                              data-price="${order.totalAmount}"></span></td>
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