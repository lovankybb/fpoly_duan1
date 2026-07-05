<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty authentication}">
    <c:redirect url="/sign-in"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tài khoản của tôi - Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="/styles/profile.css" rel="stylesheet">
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
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
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
                <li><a href="#">Địa chỉ nhận hàng</a></li>
                <li><a href="#">Đổi mật khẩu</a></li>
                <li>
                    <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
                        <button type="submit" style="background:none; border:none; padding:0; font:inherit; cursor:pointer;" class="btn-logout">Đăng xuất</button>
                    </form>
                </li>
            </ul>
        </aside>

        <section class="card">
            <h3 class="section-title">Lịch sử mua hàng</h3>

            <div class="orders-list">

                <div class="order-item">
                    <div class="order-header">
                        <div>
                            <span class="order-id">Mã ĐH: #BBM-8923</span>
                            <span class="order-date"> • 01/07/2026</span>
                        </div>
                        <span class="status completed">Đã giao hàng</span>
                    </div>
                    <div class="order-body">
                        <img src="https://via.placeholder.com/60" alt="Product" class="order-img">
                        <div class="order-product-info">
                            <div class="order-product-name">Samsung Galaxy Z Fold6 (256GB)</div>
                            <div class="order-qty">Phân loại: Xám kim loại | Số lượng: x1</div>
                        </div>
                        <div class="order-price">43.990.000đ</div>
                    </div>
                </div>

                <div class="order-item">
                    <div class="order-header">
                        <div>
                            <span class="order-id">Mã ĐH: #BBM-9104</span>
                            <span class="order-date"> • 05/07/2026</span>
                        </div>
                        <span class="status pending">Đang xử lý</span>
                    </div>
                    <div class="order-body">
                        <img src="https://via.placeholder.com/60" alt="Product" class="order-img">
                        <div class="order-product-info">
                            <div class="order-product-name">Sạc nhanh Anker Nano II 65W</div>
                            <div class="order-qty">Phân loại: Đen | Số lượng: x2</div>
                        </div>
                        <div class="order-price">1.980.000đ</div>
                    </div>
                </div>

                <div class="order-item">
                    <div class="order-header">
                        <div>
                            <span class="order-id">Mã ĐH: #BBM-7741</span>
                            <span class="order-date"> • 15/06/2026</span>
                        </div>
                        <span class="status canceled">Đã hủy</span>
                    </div>
                    <div class="order-body">
                        <img src="https://via.placeholder.com/60" alt="Product" class="order-img">
                        <div class="order-product-info">
                            <div class="order-product-name">Ốp lưng trong suốt iPhone 17 Pro Max</div>
                            <div class="order-qty">Phân loại: Magsafe | Số lượng: x1</div>
                        </div>
                        <div class="order-price">450.000đ</div>
                    </div>
                </div>

            </div>
        </section>
    </div>
</main>

 <%@ include file="fragments/footer.jsp" %>

</body>
</html>