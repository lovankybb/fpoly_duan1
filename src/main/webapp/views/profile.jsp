<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty authentication}">
    <c:redirect url="/sign-in"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tài khoản của tôi - Bảo Bình Mobile</title>
<<<<<<< HEAD
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/profile.css" rel="stylesheet">
=======
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="/styles/profile.css" rel="stylesheet">
>>>>>>> 889b80c (update: complete change password and order history)
</head>
<body>

<%@ include file="fragments/header.jsp" %>

<main class="container">
    <div class="page-header">
        <h1>Tài khoản của tôi</h1>
    </div>

    <div class="profile-layout">

        <aside class="card">
            <div class="user-avatar">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                </svg>
            </div>
            <div class="user-info">
                <h2>${authentication.username}</h2>
                <p>Thành viên từ 2026</p>
                <c:if test="${authentication.roles.contains('ADMIN')}">
                    <span class="user-role">ADMIN</span>
                </c:if>
            </div>

            <ul class="profile-menu">
                <li><a href="#">Thông tin cá nhân</a></li>
                <li><a href="${pageContext.request.contextPath}/user/change-pwd">Đổi mật khẩu</a></li>
                <li>
                    <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
                        <button type="submit"
                                style="background:none; border:none; padding:0; font:inherit; cursor:pointer;"
                                class="btn-logout">Đăng xuất
                        </button>
                    </form>
                </li>
            </ul>
        </aside>

        <section class="card">
            <h3 class="section-title">Lịch sử mua hàng</h3>

            <div class="orders-list">

                <c:forEach var="history" items="${histories}">
                    <div class="order-item">
                        <div class="order-header">
                            <div>
                                <span class="order-id">Mã ĐH: #${history.orderCode}</span>
                                <span class="order-date"> • ${history.createdAt}</span>
                            </div>
                            <c:if test="${history.orderStatus == 'PENDING'}">
                                <span class="status pending">Chờ xác nhận</span>
                            </c:if>
                            <c:if test="${history.orderStatus == 'SHIPPING'}">
                                <span class="status shipping">Đang giao hàng</span>
                            </c:if>
                            <c:if test="${history.orderStatus == 'COMPLETED'}">
                                <span class="status completed">Hoàn thành</span>
                            </c:if>
                            <c:if test="${history.orderStatus == 'CANCELLED'}">
                                <span class="status canceled">Đã hủy</span>
                            </c:if>
                        </div>
                        <div class="order-body">
                            <img src="${pageContext.request.contextPath}/image?name=${history.imageUrl}"
                                 alt="${history.name}" class="order-img">
                            <div class="order-product-info">
                                <div class="order-product-name">${history.name}( ${history.version})</div>
                                <div class="order-qty">Phân loại: ${history.color} | Số lượng:
                                    x${history.quantity}</div>
                                <div class="order-sub-price js-format-price"
                                     data-price="${history.price}"></div>
                            </div>
                            <div class="order-price js-format-price"
                                 data-price="${history.price * history.quantity}"></div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="pagination-container">
                <c:if test="${offset gt 0}">
                    <a href="${pageContext.request.contextPath}/profile?offset=${offset - 3}" title="Trang trước">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="12" cy="12" r="10"/>
                            <path d="m12 8-4 4 4 4"/>
                            <path d="M16 12H8"/>
                        </svg>
                    </a>
                </c:if>
                <a href="${pageContext.request.contextPath}/profile?offset=${offset + 3}" title="Trang sau">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="10"/>
                        <path d="m12 16 4-4-4-4"/>
                        <path d="M8 12h8"/>
                    </svg>
                </a>
            </div>
        </section>

    </div>
</main>

<%@ include file="fragments/footer.jsp" %>

</body>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        const initPriceFormatting = () => {
            const priceElements = document.querySelectorAll('.js-format-price');

            priceElements.forEach(el => {
                const rawPrice = Number(el.dataset.price);

                if (!isNaN(rawPrice) && rawPrice >= 0) {
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
</html>